package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Persona;
import it.uniroma3.siw.spring.service.PersonaService;

@Controller
public class PersonaController {
	
	@Autowired
	private PersonaService personaService;
	
    @Autowired
    private PersonaValidator personaValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/addPersona", method = RequestMethod.GET)
    public String addPersona(Model model) {
    	logger.debug("addPersona");
    	model.addAttribute("persona", new Persona());
        return "personaForm.html";
    }

    @RequestMapping(value = "/persona/{id}", method = RequestMethod.GET)
    public String getPersona(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("persona", this.personaService.personaPerId(id));
    	return "persona.html";
    }

    @RequestMapping(value = "/persona", method = RequestMethod.GET)
    public String getPersone(Model model) {
    		model.addAttribute("persone", this.personaService.tutti());
    		return "persone.html";
    }
    
    @RequestMapping(value = "/persona", method = RequestMethod.POST)
    public String newPersona(@ModelAttribute("persona") Persona persona, 
    									Model model, BindingResult bindingResult) {
    	this.personaValidator.validate(persona, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.personaService.inserisci(persona);
            model.addAttribute("persone", this.personaService.tutti());
            return "persone.html";
        }
        return "personaForm.html";
    }
}
