package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;

public interface ArtistaRepository extends CrudRepository<Artista, Long> {

	public List<Artista> findByNome(String nome);

	public List<Artista> findByNomeAndCognome(String nome, String cognome);

	public List<Artista> findByNomeOrCognome(String nome, String cognome);
	

	@Query("SELECT a"
	 + " FROM Artista a"
	 + " WHERE a.nome = nome AND a.cognome = cognome")
		List<Artista> findArtista(String nome, String cognome);
}