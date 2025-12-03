package br.com.oliveirawillian.mapper;

import br.com.oliveirawillian.data.dto.v1.BooksDTO;
import br.com.oliveirawillian.model.Books;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-03T10:55:39-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class BooksMapperImpl implements BooksMapper {

    @Override
    public BooksDTO toDTO(Books books) {
        if ( books == null ) {
            return null;
        }

        BooksDTO booksDTO = new BooksDTO();

        booksDTO.setId( books.getId() );
        booksDTO.setAuthor( books.getAuthor() );
        booksDTO.setLaunchDate( books.getLaunchDate() );
        booksDTO.setPrice( books.getPrice() );
        booksDTO.setTitle( books.getTitle() );

        return booksDTO;
    }

    @Override
    public Books toEntity(BooksDTO booksDTO) {
        if ( booksDTO == null ) {
            return null;
        }

        Books books = new Books();

        books.setId( booksDTO.getId() );
        books.setAuthor( booksDTO.getAuthor() );
        books.setLaunchDate( booksDTO.getLaunchDate() );
        books.setPrice( booksDTO.getPrice() );
        books.setTitle( booksDTO.getTitle() );

        return books;
    }

    @Override
    public List<BooksDTO> toDTOList(List<Books> booksList) {
        if ( booksList == null ) {
            return null;
        }

        List<BooksDTO> list = new ArrayList<BooksDTO>( booksList.size() );
        for ( Books books : booksList ) {
            list.add( toDTO( books ) );
        }

        return list;
    }

    @Override
    public List<Books> toEntityList(List<BooksDTO> booksDTOList) {
        if ( booksDTOList == null ) {
            return null;
        }

        List<Books> list = new ArrayList<Books>( booksDTOList.size() );
        for ( BooksDTO booksDTO : booksDTOList ) {
            list.add( toEntity( booksDTO ) );
        }

        return list;
    }
}
