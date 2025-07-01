package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import it.uniroma3.siw.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long>{

	@Query(value = "SELECT * FROM author ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Author getRandomAuthor();

	@Query("SELECT a FROM Author a WHERE CONCAT(a.firstName, ' ', a.lastName) = :author")
	Author getAuthorByName(@Param("author") String authorName);

	@Query("SELECT COUNT(a) > 0 FROM Author a WHERE CONCAT(CONCAT(a.firstName, ' '), a.lastName) = :name AND a.birthDate = :birth")
	boolean existsByNameAndBirth(@Param("name") String name, @Param("birth") LocalDate birth);
}
