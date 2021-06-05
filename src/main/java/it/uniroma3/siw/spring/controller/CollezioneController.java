package it.uniroma3.siw.spring.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Curatore;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CuratoreService;
import it.uniroma3.siw.spring.validator.CollezioneValidator;

@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
    @Autowired
    private CollezioneValidator collezioneValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
	private CuratoreService curatoreService;
    

    @RequestMapping(value="/addCollezione", method = RequestMethod.GET)
    public String addCollezione(Model model) {
    	logger.debug("addCollezione");
    	model.addAttribute("collezione", new Collezione());
    	model.addAttribute("curatori", this.curatoreService.tutti());
        return "InserisciCollezione.html";
    }
    

    @RequestMapping(value = "/collezione", method = RequestMethod.POST)
    public String newCollezione(@ModelAttribute("collezione") Collezione collezione, 
    									Model model, BindingResult bindingResult,
    									@RequestParam(value = "curatoreSelezionato") Long matricola) {
    	this.collezioneValidator.validate(collezione, bindingResult);
       if (!bindingResult.hasErrors()) {
        	List<Curatore> curatori = (List<Curatore>) curatoreService.tutti();
        	Collections.sort(curatori);
        	Curatore curatore = curatoreService.curatorePerId(matricola);
        	collezione.setCuratore(curatore);
        	this.collezioneService.inserisci(collezione);
            model.addAttribute("collezioni", this.collezioneService.tutti());
            return "collezioni.html";
        }
       return "InserisciCollezione.html";
    }
    
    
    @RequestMapping(value="/rimCollezione", method = RequestMethod.GET)
    public String rimCollezione(Model model) {
    		logger.debug("rimCollezione");
    		model.addAttribute("collezione", this.collezioneService.tutti());
        	return "RimuoviCollezione.html";
    }
    
    @RequestMapping(value = "/rimozioneCollezione", method = RequestMethod.POST)
    public String rimozioneCollezione(@ModelAttribute("collezione") Collezione collezione,
    								  Model model, BindingResult bindingResult,
								 	  @RequestParam(value = "collezioneSelezionata") Long collezioneID) {
    		List<Collezione> collezioni = (List<Collezione>) collezioneService.tutti();
    		Collections.sort(collezioni);
    		Collezione collezioneDaRim = collezioneService.collezionePerId(collezioneID);
    		this.collezioneService.delete(collezioneDaRim);
    		return "collezioni.html";
    }
    
    @RequestMapping(value="/editCollezione", method = RequestMethod.GET)
    public String selezionaCollezione(Model model) {
    	logger.debug("editCollezione");
    	model.addAttribute("collezione", new Collezione());
    	model.addAttribute("collezioni", this.collezioneService.tutti());
    	model.addAttribute("curatori", this.curatoreService.tutti());
        return "editCollezione.html";
    }
    
    @RequestMapping(value = "/modificaCollezione", method = RequestMethod.POST)
    public String modificaCollezione(@ModelAttribute("collezioneSelezionata") Long collezioneID,
    								 @ModelAttribute("nome") String nomeNuovo,
    								 @ModelAttribute("descrizione") String descNuovo,
    								 @ModelAttribute("curatoreSelezionato") Long curNuovo,
    								 Model model, BindingResult bindingResult){
    	
    		
        	List<Curatore> curatori = (List<Curatore>) curatoreService.tutti();
        	Collections.sort(curatori);
        	Curatore curatoreNuovo = curatoreService.curatorePerId(curNuovo);
        	
        	Collezione collezioneNuova = new Collezione();
        	collezioneNuova.setId(collezioneID);
        	collezioneNuova.setNome(nomeNuovo);
        	collezioneNuova.setDescrizione(descNuovo);
        	collezioneNuova.setCuratore(curatoreNuovo);
        	
        	collezioneService.inserisci(collezioneNuova);
            model.addAttribute("collezioni", this.collezioneService.tutti());
            return "collezioni.html";
    }

    @RequestMapping(value = "/collezione/{id}", method = RequestMethod.GET)
    public String getCollezione(@PathVariable("id") Long id, Model model) {
    	Collezione collezione= this.collezioneService.collezionePerId(id);
    	List<Opera> opere = collezione.getOpereDellaCollezione();
    	Curatore curatore= this.curatoreService.curatorePerId(collezione.getCuratore().getMatricola());
    	model.addAttribute("collezione", collezione);
    	model.addAttribute("curatore", curatore);
    	model.addAttribute("opere", opere);
    	return "collezione.html";
    }

    @RequestMapping(value = "/collezione", method = RequestMethod.GET)
    public String getCollezione(Model model) {
    		model.addAttribute("collezioni", this.collezioneService.tutti());    		
    		return "collezioni.html";
    }
    
}
