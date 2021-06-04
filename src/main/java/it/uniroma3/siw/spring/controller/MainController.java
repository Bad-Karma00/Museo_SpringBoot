package it.uniroma3.siw.spring.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class MainController {

	@Autowired
	private OperaService operaService;

	@RequestMapping("/informazioni")
	public String mostraInfo(Model model){
		return "informazioni.html";
	}
	
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
			
			List<Opera> opere = (List<Opera>) operaService.tutte();

			Collections.shuffle(opere);
			
			if(opere.size()>=4) {	
				
			
			model.addAttribute("opera1", opere.get(0));
			
			model.addAttribute("opera2", opere.get(1));
			
			model.addAttribute("opera3", opere.get(2));
			
			model.addAttribute("opera4", opere.get(3));
			
			}
			
			else if(opere.size()==3) {	
				
			
			model.addAttribute("opera1", opere.get(0));
			
			model.addAttribute("opera2", opere.get(1));
			
			model.addAttribute("opera3", opere.get(2));
			
			model.addAttribute("opera4", null);
			
			}
			
			else if(opere.size()==2) {	
				
				
				model.addAttribute("opera1", opere.get(0));
				
				model.addAttribute("opera2", opere.get(1));
				
				model.addAttribute("opera3", null);
				
				model.addAttribute("opera4", null);
				
			}
			
			else if(opere.size()==1) {	
				
				
				model.addAttribute("opera1", opere.get(0));
				
				model.addAttribute("opera2", null);
				
				model.addAttribute("opera3", null);
				
				model.addAttribute("opera4", null);
				
			}
			
			else {
				
				model.addAttribute("opera1", null);
				
				model.addAttribute("opera2", null);
				
				model.addAttribute("opera3", null);
				
				model.addAttribute("opera4", null);
				
			}
			
			return "index";
	}
}
