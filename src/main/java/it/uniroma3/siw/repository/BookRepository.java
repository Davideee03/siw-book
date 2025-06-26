package it.uniroma3.siw.repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Genre;

public interface BookRepository extends CrudRepository<Book, Long>{

	@Query("SELECT b FROM Book b JOIN b.reviews r GROUP BY b ORDER BY AVG(r.mark) DESC")
	List<Book> findTopBooksByAverageMark(Pageable pageable);
	
	@Query("SELECT b FROM Book b LEFT JOIN b.reviews r GROUP BY b ORDER BY COUNT(r) DESC")
	List<Book> findMostReviewedBooks(Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.genre = 'THRILLER' ORDER BY function('RANDOM')")
	List<Book> findThrillerBooks(Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.genre = 'ROMANCE' ORDER BY function('RANDOM')")
    List<Book> findRomanceBooks(Pageable pageable);
	
	@Query("SELECT b FROM Book b WHERE b.genre = :genre")
	List<Book> findByGenre(@Param("genre") Genre genre);

}
