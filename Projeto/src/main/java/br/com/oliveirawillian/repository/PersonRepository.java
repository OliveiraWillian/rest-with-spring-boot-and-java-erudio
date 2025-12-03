package br.com.oliveirawillian.repository;

import br.com.oliveirawillian.model.Person;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person p SET p.enabled = false where p.id = :id")
    void disablePerson(@Param("id") Long id);

    @Query("SELECT p FROM Person p  WHERE p.firstName LIKE LOWER(CONCAT('%',:firstName,'%'))")
    Page<Person> FindPeopleByName(@Param("firstName") String firstName, Pageable pageable);


}
