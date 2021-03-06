package it.uniroma3.siw.spring.service;

import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.repository.OperaRepository;

@Service
public class OperaService {
	@Autowired
	private OperaRepository operaRepository; 
	
	@Transactional
	public Opera inserisci(Opera opera) {
		return operaRepository.save(opera);
	}
	
	@Transactional
	public List<Opera> operePerTitoloAutore(String nome, Artista autore) {
		return operaRepository.findByTitoloAndAutore(nome, autore);
	}

	@Transactional
	public List<Opera> tutte() {
		return (List<Opera>) operaRepository.findAll();
	}

	@Transactional
	public Opera operaPerId(Long id) {
		Optional<Opera> optional = operaRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Opera opera) {
		List<Opera> opere = this.operaRepository.findByTitoloAndAutore(opera.getTitolo(),opera.getAutore());
		if (opere.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void delete(Opera opera){
		this.operaRepository.delete(opera);
	}
}
