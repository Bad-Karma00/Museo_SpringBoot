package it.uniroma3.siw.spring.controller;


import java.util.Collections;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.OperaService;
import it.uniroma3.siw.spring.validator.OperaValidator;

@Controller
public class OperaController {

	@Autowired
	private OperaService operaService;
	
    @Autowired
    private OperaValidator operaValidator;
    
    @Autowired
    private ArtistaService artistaService;
    
    @Autowired
    private CollezioneService collezioneService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/addOpera", method = RequestMethod.GET)
    public String addOpera(Model model) {
    		logger.debug("addOpera");
    		model.addAttribute("opera", new Opera());
    		model.addAttribute("artisti", this.artistaService.tutti());
    		model.addAttribute("collezioni", this.collezioneService.tutti());
    		return "InserisciOpera.html";
    }
    
    
    @RequestMapping(value="/rimOpera", method = RequestMethod.GET)
    public String rimOpera(Model model) {
    		logger.debug("rimOpera");
    		model.addAttribute("opere", this.operaService.tutte());
        	return "RimuoviOpera.html";
    }
    
    @RequestMapping(value = "/rimozioneOpera", method = RequestMethod.POST)
    public String rimozioneOpera(@ModelAttribute("opera") Opera opera,
    							 Model model, BindingResult bindingResult,
								 @RequestParam(value = "operaSelezionata") Long operaID) {
    		List<Opera> opere = (List<Opera>) operaService.tutte();
    		Collections.sort(opere);
    		Opera operaDaRim = operaService.operaPerId(operaID);
    		this.operaService.delete(operaDaRim);
    		return "opere.html";
    }
    
    @RequestMapping(value="/editOpera", method = RequestMethod.GET)
    public String selezionaCollezione(Model model) {
    	logger.debug("editOpera");
    	model.addAttribute("opera", new Opera());
    	model.addAttribute("opere", this.operaService.tutte());
    	model.addAttribute("collezioni", this.collezioneService.tutti());
    	model.addAttribute("artisti", this.artistaService.tutti());
        return "editOpera.html";
    }
    
    @RequestMapping(value = "/modificaOpera", method = RequestMethod.POST)
    public String modificaCollezione(@ModelAttribute("operaSelezionata") Long operaID,
    								 @ModelAttribute("titolo") String titoloNuovo,
    								 @ModelAttribute("anno") Integer annoNuovo,
    								 @ModelAttribute("descrizione") String descNuovo,
    								 @ModelAttribute("artistaSelezionato") Long artistaID,
    								 @ModelAttribute("collezioneSelezionata") Long collezioneID,
    								 Model model, BindingResult bindingResult){
    	
    		
        	List<Artista> artisti = (List<Artista>) artistaService.tutti();
        	Collections.sort(artisti);
        	Artista artistaNuovo = artistaService.artistaPerId(artistaID);
        	
        	List<Collezione> collezioni = (List<Collezione>) collezioneService.tutti();
        	Collections.sort(collezioni);
        	Collezione collezioneNuova = collezioneService.collezionePerId(collezioneID);
        	
        	Opera operaNuova = new Opera();
        	operaNuova.setId(operaID);
        	operaNuova.setTitolo(titoloNuovo);
        	operaNuova.setAnno(annoNuovo);
        	operaNuova.setDescrizione(descNuovo);
        	operaNuova.setAutore(artistaNuovo);
        	operaNuova.setCollezione(collezioneNuova);
        	
        	operaService.inserisci(operaNuova);
            model.addAttribute("opere", this.operaService.tutte());
            return "opere.html";
    }
    
    @RequestMapping(value = "/opera/{id}", method = RequestMethod.GET)
    public String getOpera(@PathVariable("id") Long id, Model model) {
    		model.addAttribute("opera", this.operaService.operaPerId(id));
    		return "opera.html";
    }

    @RequestMapping(value = "/opera", method = RequestMethod.GET)
    public String getOpere(Model model) {
    		model.addAttribute("opere", this.operaService.tutte());
    		return "opere.html";
    }
    
    @RequestMapping(value = "/opera", method = RequestMethod.POST)
    public String newOpera(@ModelAttribute("opera") Opera opera, 
    									Model model, BindingResult bindingResult,
    									@RequestParam(value = "autoreSelezionato") Long autoreID,
    									@RequestParam(value = "collezioneSelezionata") Long collezioneID) {
    	
    	this.operaValidator.validate(opera, bindingResult);
        if (!bindingResult.hasErrors()) {
        	//Recupero collezione
        	List<Collezione> collezioni = (List<Collezione>) collezioneService.tutti();
        	Collections.sort(collezioni);
        	Collezione collezione = collezioneService.collezionePerId(collezioneID);
        	
        	//Recupero artista
        	List<Artista> artisti = (List<Artista>) artistaService.tutti();
        	Collections.sort(artisti);
        	Artista artista = artistaService.artistaPerId(autoreID);
        	
        	//Aggiunta opera
        	opera.setAutore(artista);
        	opera.setCollezione(collezione);
        	this.operaService.inserisci(opera);
            model.addAttribute("opere", this.operaService.tutte());
            return "opere.html";
        }
        return "InserisciOpera.html";
    }
}
