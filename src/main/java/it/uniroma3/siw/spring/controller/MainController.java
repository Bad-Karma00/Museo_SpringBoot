package it.uniroma3.siw.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

		List<Long> nOpere = operaRepository.contaOpere(PageRequest.of(0,4));
		logger.debug("Id estratti :" + nOpere);
			
			model.addAttribute("opera1", operaService.operaPerId(nOpere.get(0)));
			
			model.addAttribute("opera2", operaService.operaPerId(nOpere.get(1)));
			
			model.addAttribute("opera3", operaService.operaPerId(nOpere.get(2)));
			
			model.addAttribute("opera4", operaService.operaPerId(nOpere.get(3)));
			
			return "index";
	}
}
