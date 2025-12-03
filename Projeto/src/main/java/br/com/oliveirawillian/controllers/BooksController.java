package br.com.oliveirawillian.controllers;
import br.com.oliveirawillian.controllers.docs.BooksControllerDocs;
import br.com.oliveirawillian.data.dto.v1.BooksDTO;
import br.com.oliveirawillian.data.dto.v1.PersonDTO;
import br.com.oliveirawillian.services.BooksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/books/v1")
@Tag(name = "Books", description = "Endpoints for Managing Books")
public class BooksController implements BooksControllerDocs {
    @Autowired
    private BooksService booksService;


    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})


    @Override
    public ResponseEntity<PagedModel<EntityModel<BooksDTO>>> findAll(
            @RequestParam(value =  "page", defaultValue = "0") Integer page,
            @RequestParam(value =  "size", defaultValue = "12") Integer size ,
            @RequestParam(value =  "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok(booksService.findAll(pageable));

    }

    //findByName
    @GetMapping(value = "/findbooksByTitle/{title}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})

    @Override
    public  ResponseEntity<PagedModel<EntityModel<BooksDTO>>> findByTitle(
            @PathVariable("title") String title,
            @RequestParam(value =  "page", defaultValue = "0") Integer page,
            @RequestParam(value =  "size", defaultValue = "12") Integer size ,
            @RequestParam(value =  "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok(booksService.findByTitle(title,pageable));
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public BooksDTO findById(@PathVariable("id") Long id) {

        var books = booksService.findById(id);


        return books;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public BooksDTO create(@RequestBody BooksDTO books) {
        return booksService.create(books);
    }

    //v2
//    @PostMapping(value = "/v2",
//            consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
//    public BooksDTOV2 createv2(@RequestBody BooksDTOV2 books) {
//        return booksService.createV2(books);
//    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public BooksDTO update(@RequestBody BooksDTO books) {
        return booksService.update(books);
    }

    @DeleteMapping(value = "/{id}")

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
         booksService.delete(id);
         return ResponseEntity.noContent().build();
    }

}
