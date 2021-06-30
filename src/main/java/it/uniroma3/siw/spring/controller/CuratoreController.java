package it.uniroma3.siw.spring.controller;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.model.Curatore;
import it.uniroma3.siw.spring.service.CuratoreService;
import it.uniroma3.siw.spring.validator.CuratoreValidator;



@Controller
public class CuratoreController {

	@Autowired
	private CuratoreService curatoreService;
	
    @Autowired
    private CuratoreValidator curatoreValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/addCuratore", method = RequestMethod.GET)
    public String addCuratore(Model model) {
    	logger.debug("addCuratore");
    	model.addAttribute("curatore", new Curatore());
        return "InserisciCuratore.html";
    }
    
    @RequestMapping(value="/rimCuratore", method = RequestMethod.GET)
    public String rimCuratore(Model model) {
    		logger.debug("rimCuratore");
    		model.addAttribute("curatori", this.curatoreService.tutti());
        	return "RimuoviCuratore.html";
    }
    
    @RequestMapping(value = "/rimozioneCuratore", method = RequestMethod.POST)
    public String rimozioneCuratore(@ModelAttribute("curatore") Curatore curatore,
    							 Model model, BindingResult bindingResult,
								 @RequestParam(value = "curatoreSelezionato") Long curatoreID) {
    		List<Curatore> curatori = (List<Curatore>) curatoreService.tutti();
    		Collections.sort(curatori);
    		Curatore curatoreDaRim = curatoreService.curatorePerId(curatoreID);
    		this.curatoreService.delete(curatoreDaRim);
    		model.addAttribute("curatori",this.curatoreService.tutti());
    		return "curatori.html";
    }


    @RequestMapping(value = "/curatore", method = RequestMethod.GET)
    public String getCuratori(Model model) {
    		model.addAttribute("curatori", this.curatoreService.tutti());
    		return "curatori.html";
    }
    
    @RequestMapping(value = "/curatore", method = RequestMethod.POST)
    public String newCuratore(@ModelAttribute("curatore") Curatore curatore, 
    									Model model, BindingResult bindingResult){
    	this.curatoreValidator.validate(curatore, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.curatoreService.inserisci(curatore);
            model.addAttribute("curatori", this.curatoreService.tutti());
            return "curatori.html";
        }
        return "InserisciCuratore.html";
    }
    
    @RequestMapping(value="/ordineAlfabeticoCuratore", method = RequestMethod.GET)
    public String ordineAlfabeticoCuratore(Model model) {
    		logger.debug("ordineAlfabetico");
    		List<Curatore> curatoriAlfabetico = this.curatoreService.tutti();
    		
    		if (curatoriAlfabetico.size() > 0) {
  			  Collections.sort(curatoriAlfabetico, new Comparator<Curatore>() {
  			      @Override
  			      public int compare(final Curatore curatore1, final Curatore curatore2 ) {
  			    	  int differenza = 0;
  			          differenza = curatore1.getNome().compareTo(curatore2.getNome());
  			          if(differenza == 0) {
  			        	  return curatore1.getCognome().compareTo(curatore2.getCognome());
  			          }
  			          else {
  			        	  return differenza;
  			          }
  			      }
  			  });
  			}
  		model.addAttribute("curatori", curatoriAlfabetico);
  		
      	return "curatori.html";
  }
    
    @RequestMapping(value="/ordineMatricola", method = RequestMethod.GET)
    public String ordineMatricola(Model model) {
    		List<Curatore> curatoriMatricola = this.curatoreService.tutti();
    		
    		if (curatoriMatricola.size() > 0) {
  			  Collections.sort(curatoriMatricola, new Comparator<Curatore>() {
  			      @Override
  			      public int compare(final Curatore curatore1, final Curatore curatore2 ) {
  			    	return curatore1.getMatricola().compareTo(curatore2.getMatricola());
  			      }
  			  });
  			}
  		model.addAttribute("curatori", curatoriMatricola);
  		
      	return "curatori.html";
  }
}