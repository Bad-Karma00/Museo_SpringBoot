package it.uniroma3.siw.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Curatore;

public interface CollezioneRepository extends CrudRepository<Collezione, Long> {

	public List<Collezione> findByNome(String nome);

	public List<Collezione> findByNomeAndCuratore(String nome, Curatore curatore);

	public List<Collezione> findByNomeOrCuratore(String nome, Curatore curatore);
	
	@Query("SELECT Count(o)"
	 + " FROM Artista a, Opera o, Collezione c"
	 + " WHERE a.id = o.autore AND c.id = o.collezione AND c.id = ?1"
	 + " GROUP BY a.nome,a.cognome,a.id"
	 + " ORDER BY a.id")
		List<Integer> countOpere(Long collezioneID);
	
	@Query("SELECT a"
	 + " FROM Artista a, Opera o, Collezione c"
	 + " WHERE a.id = o.autore AND c.id = o.collezione AND c.id = ?1"
	 + " GROUP BY a.id, a.biografia, a.cognome, a.nome, a.dataMorte, a.dataNascita, a.immagine, a.luogoMorte, a.luogoNascita, a.nazionalita"
	 + " ORDER BY a.id")
		List<Artista> countAutori(Long collezioneID);
	
}