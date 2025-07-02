package it.uniroma3.siw.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Genre;

public interface BookRepository extends CrudRepository<Book, Long> {

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

	@Query("SELECT b FROM Book b LEFT JOIN b.reviews r GROUP BY b HAVING COUNT(r) = 0")
	List<Book> getUnknownBooks();

	@Query("SELECT b FROM Book b WHERE b.title = :title")
	List<Book> getBooksByTitle(String title);

	@Query("""
			    SELECT DISTINCT b FROM Book b
			    JOIN b.authors a
			    WHERE (:title = '' OR b.title = :title)
			      AND (:year = 0 OR b.year = :year)
			      AND (:author = '' OR CONCAT(a.firstName, ' ', a.lastName) = :author)
			      AND (:genre IS NULL OR b.genre = :genre)
			""")
	List<Book> filterBooks(@Param("title") String title, @Param("year") int year, @Param("author") String author,
			@Param("genre") Genre genre);

	@Query("""
			SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
			FROM Book b
			JOIN b.authors a
			WHERE b.title = :title
			  AND b.year = :year
			  AND SIZE(b.authors) = :size
			  AND a IN :authors
			GROUP BY b.id
			HAVING COUNT(a) = :size
			""")
	boolean existsBookTitleYearAuthor(@Param("title") String title, @Param("year") int year,
			@Param("authors") List<Author> authors, @Param("size") long size);

}
