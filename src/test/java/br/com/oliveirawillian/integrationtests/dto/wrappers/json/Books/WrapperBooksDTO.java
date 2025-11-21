package br.com.oliveirawillian.integrationtests.dto.wrappers.json.Books;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperBooksDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("_embedded")
    private BooksEmbeddedDTO embedded;

    public WrapperBooksDTO() {
    }

    public BooksEmbeddedDTO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(BooksEmbeddedDTO embedded) {
        this.embedded = embedded;
    }


}
