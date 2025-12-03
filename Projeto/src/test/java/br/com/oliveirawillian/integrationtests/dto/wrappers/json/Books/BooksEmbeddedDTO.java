package br.com.oliveirawillian.integrationtests.dto.wrappers.json.Books;

import br.com.oliveirawillian.integrationtests.dto.BooksDTO;
import br.com.oliveirawillian.integrationtests.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class BooksEmbeddedDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("books")
    private List<BooksDTO> books;

    public BooksEmbeddedDTO() {

    }

    public List<BooksDTO> getbooks() {
        return books;
    }

    public void setbooks(List<BooksDTO> books) {
        this.books = books;
    }
}
