package br.com.oliveirawillian.mapper;

import br.com.oliveirawillian.data.dto.v1.BooksDTO;
import br.com.oliveirawillian.model.Books;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BooksMapper {

    //@Mapping(source = "enabled", target = "habilitado")
        //Source = books
        //Target booksDTO




    BooksDTO toDTO(Books books);

    Books toEntity(BooksDTO booksDTO);

    List<BooksDTO> toDTOList(List<Books> booksList);

    List<Books> toEntityList(List<BooksDTO> booksDTOList);
}





