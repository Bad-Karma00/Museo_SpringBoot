package it.uniroma3.siw.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Curatore;

public interface CollezioneRepository extends CrudRepository<Collezione, Long> {

	public List<Collezione> findByNome(String nome);

	public List<Collezione> findByNomeAndCuratore(String nome, Curatore curatore);

	public List<Collezione> findByNomeOrCuratore(String nome, Curatore curatore);
	
	@Query("SELECT a.cognome, Count(o)"
	 + " FROM Artista a, Opera o, Collezione c"
	 + " WHERE a.id = o.autore AND ?1 = o.collezione"
	 + " GROUP BY a.nome,a.cognome")
		List<String> countOpere(Collezione collezioneID);
	
}