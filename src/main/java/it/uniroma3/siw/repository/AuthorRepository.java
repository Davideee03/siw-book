package it.uniroma3.siw.repository;

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
}
