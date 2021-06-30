package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.spring.repository.OperaRepository;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class MainController {

	@Autowired
	private OperaService operaService;
	
	@Autowired
	private OperaRepository operaRepository;
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/loginCustom")
	public String loginCustom(Model model){
		return "loginCustom.html";
	}

	@RequestMapping("/informazioni")
	public String mostraInfo(Model model){
		return "informazioni.html";
	}
	
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
			
			int nOpere = operaRepository.contaOpere();
			logger.debug("Opere contate: " + nOpere);
			
			
			Long randID1 = 1 + (long) (Math.random() * (nOpere - 0));
			logger.debug("Primo ID random: " + randID1);
			Long randID2 = 1 + (long) (Math.random() * (nOpere - 0));
			logger.debug("Secondo ID random: " + randID2);
			Long randID3 = 1 + (long) (Math.random() * (nOpere - 0));
			logger.debug("Terzo ID random: " + randID3);
			Long randID4 = 1 + (long) (Math.random() * (nOpere - 0));
			logger.debug("Quarto ID random: " + randID4);
			
			model.addAttribute("opera1", operaService.operaPerId(randID1));
			
			model.addAttribute("opera2", operaService.operaPerId(randID2));
			
			model.addAttribute("opera3", operaService.operaPerId(randID3));
			
			model.addAttribute("opera4", operaService.operaPerId(randID4));
			
			return "index";
	}
}
