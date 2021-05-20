package it.uniroma3.siw.spring.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.model.Persona;
import it.uniroma3.siw.spring.repository.OperaRepository;
import it.uniroma3.siw.spring.repository.PersonaRepository;
import it.uniroma3.siw.spring.service.OperaService;
import it.uniroma3.siw.spring.service.PersonaService;

@Component
public class OperaValidator implements Validator {
	@Autowired
	private OperaService operaService;
	
    private static final Logger logger = LoggerFactory.getLogger(OperaValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titolo", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autore", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.operaService.alreadyExists((Opera)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Opera.class.equals(aClass);
	}
}
