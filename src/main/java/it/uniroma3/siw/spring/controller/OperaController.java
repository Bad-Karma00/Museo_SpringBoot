package it.uniroma3.siw.spring.controller;


import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
								 @RequestParam(value = "operaSelezionata") Long operaID) throws IOException {
    		List<Opera> opere = (List<Opera>) operaService.tutte();
    		Collections.sort(opere);
    		Opera operaDaRim = operaService.operaPerId(operaID);
    		if(!(operaDaRim.getImmagine()==null)) {
    	   	String uploadDir ="photos/"+ operaDaRim.getId()+operaDaRim.getTitolo();
    		Path uploadPath = Paths.get(uploadDir);
    		  FileUtils.deleteDirectory(uploadPath.toFile());;
    		}
    		this.operaService.delete(operaDaRim);
    		model.addAttribute("opere", this.operaService.tutte());
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
    								 @RequestParam(value="img") MultipartFile immagine,
    								 Model model, BindingResult bindingResult) throws IOException{
    	    
    		Opera operaDaRim = operaService.operaPerId(operaID);
	     	String uploadDir1 ="photos/"+ operaDaRim.getId()+operaDaRim.getTitolo();
		    Path uploadPath1 = Paths.get(uploadDir1);
		    FileUtils.deleteDirectory(uploadPath1.toFile());;
        	List<Artista> artisti = (List<Artista>) artistaService.tutti();
        	Collections.sort(artisti);
        	Artista artistaNuovo = artistaService.artistaPerId(artistaID);
        	
        	List<Collezione> collezioni = (List<Collezione>) collezioneService.tutti();
        	Collections.sort(collezioni);
        	Collezione collezioneNuova = collezioneService.collezionePerId(collezioneID);
        	String fileName = StringUtils.cleanPath(immagine.getOriginalFilename());
        	Opera operaNuova = new Opera();
        	operaNuova.setId(operaID);
        	operaNuova.setTitolo(titoloNuovo);
        	operaNuova.setAnno(annoNuovo);
        	operaNuova.setDescrizione(descNuovo);
        	operaNuova.setAutore(artistaNuovo);
        	operaNuova.setCollezione(collezioneNuova);
        	if(!(immagine.getSize()==0)) {
        	operaNuova.setImmagine(fileName);
            operaNuova.setImmagine(immagine.getOriginalFilename());
        	}
        	else { operaNuova.setImmagine(null);}
        	operaService.inserisci(operaNuova);
        	
            model.addAttribute("opere", this.operaService.tutte());
            if(!(immagine.getSize()==0)) {
            String uploadDir ="photos/"+ operaNuova.getId()+operaNuova.getTitolo();
            
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
             
            try (InputStream inputStream = immagine.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {        
                throw new IOException("Salvataggio non riuscito: " + fileName, ioe);
            }  
            }
            return "opere.html";
    }
    
    @RequestMapping(value = "/opera/{id}", method = RequestMethod.GET)
    public String getOpera(@ModelAttribute("id") Long id, Model model) {
    	Opera opera=this.operaService.operaPerId(id);
    	model.addAttribute("opera", opera);
    	return "opera.html";
    }

    @RequestMapping(value = "/opera", method = RequestMethod.GET)
    public String getOpere(Model model) {
    		model.addAttribute("opere", this.operaService.tutte());
    		return "opere.html";
    }
    
    @RequestMapping(value = "/opera", method = RequestMethod.POST)
    public String newOpera(@Valid @ModelAttribute("opera") Opera opera, 
    									Model model, BindingResult bindingResult,
    									@RequestParam(value = "autoreSelezionato") Long autoreID,
    									@RequestParam(value = "collezioneSelezionata") Long collezioneID,
    									@RequestParam(value="img", required=false)  MultipartFile immagine) throws IOException {
    	
    	this.operaValidator.validate(opera, bindingResult);
        if (!bindingResult.hasErrors()) {
        	//Recupero collezione
        	List<Collezione> collezioni = (List<Collezione>) collezioneService.tutti();
        	Collections.sort(collezioni);
        	Collezione collezione = collezioneService.collezionePerId(collezioneID);
        	if(!(immagine.getSize()==0)) {
			String fileName = StringUtils.cleanPath(immagine.getOriginalFilename());
			opera.setImmagine(fileName);
        	//Recupero artista
            opera.setImmagine(immagine.getOriginalFilename());
        	}
        	List<Artista> artisti = (List<Artista>) artistaService.tutti();
        	Collections.sort(artisti);
        	Artista artista = artistaService.artistaPerId(autoreID);
        	
        	//Aggiunta opera
        	opera.setAutore(artista);
        	opera.setCollezione(collezione);
        	this.operaService.inserisci(opera);
            model.addAttribute("opere", this.operaService.tutte());
            if(!(immagine.getSize()==0)) {
           String uploadDir ="photos/"+ opera.getId()+opera.getTitolo();
           String fileName = StringUtils.cleanPath(immagine.getOriginalFilename());
           Path uploadPath = Paths.get(uploadDir);
           
           if (!Files.exists(uploadPath)) {
               Files.createDirectories(uploadPath);
           }
            
           try (InputStream inputStream = immagine.getInputStream()) {
               Path filePath = uploadPath.resolve(fileName);
               Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
           } catch (IOException ioe) {        
               throw new IOException("Salvataggio non riuscito: " + fileName, ioe);
           } 
            }
            return "opere.html";
        }
        model.addAttribute("artisti",this.artistaService.tutti());
        model.addAttribute("collezioni",this.collezioneService.tutti());      
        return "InserisciOpera.html";
    }
    
    @RequestMapping(value="/ordineAlfabetico", method = RequestMethod.GET)
    public String ordineAlfabetico(Model model) {
    		logger.debug("ordineAlfabetico");
    		List<Opera> opereAlfabetico = this.operaService.tutte();
    		
    		if (opereAlfabetico.size() > 0) {
    			  Collections.sort(opereAlfabetico, new Comparator<Opera>() {
    			      @Override
    			      public int compare(final Opera opera1, final Opera opera2) {
    			          return opera1.getTitolo().compareTo(opera2.getTitolo());
    			      }
    			  });
    			}
    		model.addAttribute("opere", opereAlfabetico);
    		
        	return "opere.html";
    }
    
    @RequestMapping(value="/ordinePerAnno", method = RequestMethod.GET)
    public String ordinePerAnno(Model model) {
    		logger.debug("ordinePerAnno");
    		List<Opera> opereAnno = this.operaService.tutte();
    		
    		if (opereAnno.size() > 0) {
    			  Collections.sort(opereAnno, new Comparator<Opera>() {
    			      @Override
    			      public int compare(final Opera opera1, final Opera opera2) {
    			          return opera1.getAnno().compareTo(opera2.getAnno());
    			      }
    			  });
    			}
    		model.addAttribute("opere", opereAnno);
    		
        	return "opere.html";
    }
}
