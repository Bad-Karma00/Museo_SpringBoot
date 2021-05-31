package it.uniroma3.siw.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.service.ArtistaService;

@Controller
public class ArtistaController {
	
	@Autowired
	private ArtistaService artistaService;
	
    @Autowired
    private ArtistaValidator artistaValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/addArtista", method = RequestMethod.GET)
    public String addArtista(Model model) {
    	logger.debug("addArtista");
    	model.addAttribute("artista", new Artista());
        return "InserisciArtista.html";
    }

    @RequestMapping(value = "/artista/{id}", method = RequestMethod.GET)
    public String getArtista(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("artista", this.artistaService.artistaPerId(id));
    	return "artista.html";
    }

    @RequestMapping(value = "/artista", method = RequestMethod.GET)
    public String getArtista(Model model) {
    		model.addAttribute("artisti", this.artistaService.tutti());
    		return "artisti.html";
    }
    
    @RequestMapping(value = "/artista", method = RequestMethod.POST)
    public String newArtista(@ModelAttribute("artista") Artista artista, 
    									Model model, BindingResult bindingResult,
    									@RequestParam(value="img", required=false)  MultipartFile immagine) throws IOException {
    	this.artistaValidator.validate(artista, bindingResult);
        if (!bindingResult.hasErrors()) {
        	String fileName = StringUtils.cleanPath(immagine.getOriginalFilename());
        	artista.setImmagine(fileName);
        	this.artistaService.inserisci(artista);
            model.addAttribute("artisti", this.artistaService.tutti());
            String uploadDir ="photos/"+ artista.getId();
            
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
             
            try (InputStream inputStream = immagine.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {        
                throw new IOException("Could not save image file: " + fileName, ioe);
            }  
            return "artisti.html";
        }
        return "InserisciArtista.html";
    }
 
}
