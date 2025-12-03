package br.com.oliveirawillian.services;

import br.com.oliveirawillian.controllers.BooksController;
import br.com.oliveirawillian.controllers.PersonController;
import br.com.oliveirawillian.data.dto.v1.BooksDTO;
import br.com.oliveirawillian.data.dto.v1.PersonDTO;
import br.com.oliveirawillian.exception.RequiredObjectIsNullException;
import br.com.oliveirawillian.exception.ResourceNotFoundException;
import br.com.oliveirawillian.mapper.BooksMapper;
import br.com.oliveirawillian.model.Books;
import br.com.oliveirawillian.repository.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class BooksService {
    private final AtomicLong conter = new AtomicLong();

    private Logger logger = LoggerFactory.getLogger(BooksService.class.getName());

   
    
    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BooksMapper booksMapper;

    @Autowired
    private PagedResourcesAssembler<BooksDTO> assembler;

    public PagedModel<EntityModel<BooksDTO>> findAll(Pageable pageable) {    
        logger.info("Finding All Books");

        var boook = booksRepository.findAll(pageable);
        var bookWithLinks = boook.map(books ->{
            var dtoListLoaded = booksMapper.toDTO(books);
            addHateoasList(dtoListLoaded);
            return dtoListLoaded;
        });
        Link findAllLink = linkTo(methodOn(BooksController.class).findAll(
                pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort()))).withSelfRel();
        return assembler.toModel(bookWithLinks,findAllLink);

    }


    public PagedModel<EntityModel<BooksDTO>> findByTitle(String title, Pageable pageable) {
        logger.info("Finding People by name!");
        var books = booksRepository.FindbooksByTitle(title,pageable);
        var bookWithLinks = books.map(  book->{
            var dtoListLoaded = booksMapper.toDTO(book);
            addHateoasList(dtoListLoaded);
            return dtoListLoaded;
        });
        Link findAllLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findAll(
                pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort()))).withSelfRel();
        return assembler.toModel(bookWithLinks, findAllLink);

    }

    public BooksDTO findById(Long id) {
        logger.info("Finding one Books");

        var entityLoaded = booksRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("no records found for this ID"));
        var dtoLoaded = booksMapper.toDTO(entityLoaded);
        addHateoasList(dtoLoaded);
        return dtoLoaded;

    }


    public BooksDTO create(BooksDTO booksDTO) {
        if (booksDTO == null) throw new RequiredObjectIsNullException();
        logger.info("Creating one Books");

        var entity = booksMapper.toEntity(booksDTO); //2
        var entityPersisted = booksRepository.save(entity); //1
        var dtoBooksPersisted = booksMapper.toDTO(entityPersisted); //3
        addHateoasList(dtoBooksPersisted);
        return dtoBooksPersisted;
    }

    public BooksDTO update(BooksDTO booksDTO) {
        if (booksDTO == null) throw new RequiredObjectIsNullException();
        logger.info("updating one Books");

        Books entityLoaded = booksRepository.findById(booksDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("no records found for this ID"));
        entityLoaded.setAuthor(booksDTO.getAuthor());
        entityLoaded.setLaunchDate(booksDTO.getLaunchDate());
        entityLoaded.setTitle(booksDTO.getTitle());
        entityLoaded.setPrice(booksDTO.getPrice());
        var entityPersisted = booksRepository.save(entityLoaded);

        var dtoPersisted = booksMapper.toDTO(entityPersisted);
        addHateoasList(dtoPersisted);
        return dtoPersisted;
    }

    public void delete(Long id) {
        logger.info("Deleting one Books");

        Books entityLoaded = booksRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("no records found for this ID"));
        booksRepository.delete(entityLoaded);

    }

    private void addHateoasList(BooksDTO booksDTO) {
        booksDTO.add(linkTo(methodOn(BooksController.class).findById(booksDTO.getId())).withSelfRel().withType("GET"));
        booksDTO.add(linkTo(methodOn(BooksController.class).findAll(1,12,"asc")).withRel("findAll").withType("GET"));
        booksDTO.add(linkTo(methodOn(BooksController.class).create(booksDTO)).withRel("create").withType("POST"));
        booksDTO.add(linkTo(methodOn(BooksController.class).update(booksDTO)).withRel("update").withType("PUT"));
        booksDTO.add(linkTo(methodOn(BooksController.class).delete(booksDTO.getId())).withRel("delete").withType("DELETE"));
    }

}
