package br.com.oliveirawillian.data.dto.v1;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
@Relation(collectionRelation = "books")

public class BooksDTO extends RepresentationModel<BooksDTO> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String author;

    private Date launchDate;

    private Double price;

    private String title;

    public BooksDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BooksDTO booksDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(id, booksDTO.id) && Objects.equals(author, booksDTO.author) && Objects.equals(launchDate, booksDTO.launchDate) && Objects.equals(price, booksDTO.price) && Objects.equals(title, booksDTO.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, author, launchDate, price, title);
    }
}
