package it.uniroma3.siw.repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Book;

public interface BookRepository extends CrudRepository<Book, Long>{

	@Query("SELECT b FROM Book b JOIN b.reviews r GROUP BY b ORDER BY AVG(r.mark) DESC")
	List<Book> findTopBooksByAverageMark(Pageable pageable);
}
