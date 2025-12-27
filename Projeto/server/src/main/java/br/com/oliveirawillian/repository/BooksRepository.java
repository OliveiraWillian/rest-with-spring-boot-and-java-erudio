package br.com.oliveirawillian.repository;

import br.com.oliveirawillian.model.Books;
import br.com.oliveirawillian.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BooksRepository extends JpaRepository<Books, Long> {


    @Query("SELECT b FROM Books b  WHERE LOWER(b.title) LIKE LOWER(CONCAT('%',:title,'%'))")
    Page<Books> FindbooksByTitle(@Param("title") String title, Pageable pageable);

}
