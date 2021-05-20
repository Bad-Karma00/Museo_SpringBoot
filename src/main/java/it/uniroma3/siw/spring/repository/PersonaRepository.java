package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Persona;

public interface PersonaRepository extends CrudRepository<Persona, Long> {

	public List<Persona> findByNome(String nome);

	public List<Persona> findByNomeAndCognome(String nome, String cognome);

	public List<Persona> findByNomeOrCognome(String nome, String cognome);
}