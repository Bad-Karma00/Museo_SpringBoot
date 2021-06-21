package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Opera;


public interface OperaRepository extends CrudRepository<Opera, Long> {
	

	public List<Opera> findByTitolo(String titolo);

	public List<Opera> findByTitoloAndAutore(String titolo, Artista autore);

	public List<Opera> findByTitoloOrAutore(String titolo, Artista autore);

	@Query("SELECT COUNT(p.id)"
			 + " FROM  Opera p")
		int contaOpere();
}
