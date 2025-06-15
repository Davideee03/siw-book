package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.AuthorPhoto;

public interface AuthorPhotoRepository extends CrudRepository<AuthorPhoto, Long>{

}
