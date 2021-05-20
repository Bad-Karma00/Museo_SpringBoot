package it.uniroma3.siw.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Persona;
import it.uniroma3.siw.spring.repository.PersonaRepository;

@Service
public class PersonaService {
	
	@Autowired
	private PersonaRepository personaRepository; 
	
	@Transactional
	public Persona inserisci(Persona persona) {
		return personaRepository.save(persona);
	}
	
	@Transactional
	public List<Persona> studentiPerNomeAndCognome(String nome, String cognome) {
		return personaRepository.findByNomeAndCognome(nome, cognome);
	}

	@Transactional
	public List<Persona> tutti() {
		return (List<Persona>) personaRepository.findAll();
	}

	@Transactional
	public Persona personaPerId(Long id) {
		Optional<Persona> optional = personaRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Persona persona) {
		List<Persona> studenti = this.personaRepository.findByNomeAndCognome(persona.getNome(), persona.getCognome());
		if (studenti.size() > 0)
			return true;
		else 
			return false;
	}
}
