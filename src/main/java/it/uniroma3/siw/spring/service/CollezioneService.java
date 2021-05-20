package it.uniroma3.siw.spring.service;

import java.util.List;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.controller.collezione;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Curatore;
import it.uniroma3.siw.spring.repository.CollezioneRepository;

@Service
public class CollezioneService {
	
	@Autowired
	private CollezioneRepository collezioneRepository; 
	
	@Transactional
	public Collezione inserisci(Collezione collezione) {
		return collezioneRepository.save(collezione);
	}
	
	@Transactional
	public List<Collezione> collezioniPerNomeAndCuratore(String nome, Curatore curatore) {
		return collezioneRepository.findByNomeAndCuratore(nome, curatore);
	}

	@Transactional
	public List<Collezione> tutti() {
		return (List<Collezione>) collezioneRepository.findAll();
	}

	@Transactional
	public Collezione collezionePerId(Long id) {
		Optional<Collezione> optional = collezioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Collezione collezione) {
		List<Collezione> collezioni = this.collezioneRepository.findByNomeAndCuratore(collezione.getNome(), collezione.getCuratore());
		if (collezioni.size() > 0)
			return true;
		else 
			return false;
	}
}
