package br.com.oliveirawillian.unittetests.mapper.mocks;

import br.com.oliveirawillian.data.dto.v1.BooksDTO;
import br.com.oliveirawillian.model.Books;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBooks {


    public Books mockEntity() {
        return mockEntity(0);
    }

    public BooksDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Books> mockEntityList() {
        List<Books> bookss = new ArrayList<Books>();
        for (int i = 0; i < 14; i++) {
            bookss.add(mockEntity(i));
        }
        return bookss;
    }

    public List<BooksDTO> mockDTOList() {
        List<BooksDTO> bookss = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            bookss.add(mockDTO(i));
        }
        return bookss;
    }

    public Books mockEntity(Integer number) {
        Books books = new Books();
        books.setAuthor("Author Test" + number);
        books.setTitle("Title Test" + number);
        books.setId(number.longValue());
        books.setPrice(number.doubleValue());
        books.setLaunchDate(new Date());

        return books;
    }

    public BooksDTO mockDTO(Integer number) {
        BooksDTO books = new BooksDTO();
        books.setAuthor("Author Test" + number);
        books.setTitle("Title Test" + number);
        books.setId(number.longValue());
        books.setPrice(number.doubleValue());
        books.setLaunchDate(new Date());
        return books;
    }

}