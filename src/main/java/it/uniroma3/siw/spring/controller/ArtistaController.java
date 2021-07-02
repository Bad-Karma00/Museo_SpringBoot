package it.uniroma3.siw.spring.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.tomcat.util.http.fileupload.FileUtils;
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
import it.uniroma3.siw.spring.validator.ArtistaValidator;

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
    public String getArtista(@PathVariable("id") Long id, Model model) throws ParseException {
    	DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataNascita = this.artistaService.artistaPerId(id).getDataNascita().format(formatters);
        if(!(this.artistaService.artistaPerId(id).getDataMorte()==null)) {
        String dataMorte = this.artistaService.artistaPerId(id).getDataMorte().format(formatters);
        model.addAttribute("dataM",dataMorte);
        }
        model.addAttribute("artista", this.artistaService.artistaPerId(id));
    	model.addAttribute("dataN",dataNascita);
    	return "artista.html";
    }

    @RequestMapping(value = "/artista", method = RequestMethod.GET)
    public String getArtista(Model model) {
    		model.addAttribute("artisti", this.artistaService.tutti());
    		return "artisti.html";
    }
    
    @RequestMapping(value="/editArtista", method = RequestMethod.GET)
    public String selezionaArtista(Model model) {
    	logger.debug("editArtista");
    	model.addAttribute("artista", new Artista());
    	model.addAttribute("artisti", this.artistaService.tutti());
        return "editArtista.html";
    }
    
    @RequestMapping(value = "/modificaArtista", method = RequestMethod.POST)
    public String modificaArtista(@ModelAttribute("artistaSelezionato") Long artistaID,
    								 @ModelAttribute("nome") String nomeNuovo,
    								 @ModelAttribute("cognome") String cognomeNuovo,
    								 @ModelAttribute("biografia") String biografia,
    								 @ModelAttribute("dataNascita") String dataNascita,
    								 @ModelAttribute("luogoNascita") String luogoNascita,
    								 @ModelAttribute("dataMorte") String dataMorte,
    								 @ModelAttribute("luogoMorte") String luogoMorte,
    								 @ModelAttribute("nazionalita") String nazionalita,
    								 @RequestParam(value="img", required=false) MultipartFile immagine,
    								 Model model, BindingResult bindingResult) throws IOException {
    	    
    		Artista artistaDaRim = artistaService.artistaPerId(artistaID);
	     	String uploadDir1 ="photos/"+ artistaDaRim.getId()+artistaDaRim.getNome()+artistaDaRim.getCognome();
		    Path uploadPath1 = Paths.get(uploadDir1);
		    FileUtils.deleteDirectory(uploadPath1.toFile());;
        		String fileName = StringUtils.cleanPath(immagine.getOriginalFilename());
        	Artista artistaNuovo = new Artista();
        	
        	artistaNuovo.setId(artistaID);
        	artistaNuovo.setNome(nomeNuovo);
        	artistaNuovo.setCognome(cognomeNuovo);
        	artistaNuovo.setBiografia(biografia);
        	artistaNuovo.setDataNascita(LocalDate.parse(dataNascita));
        	artistaNuovo.setLuogoNascita(luogoNascita);
        	if(!(dataMorte==null)) {
        	artistaNuovo.setDataMorte(LocalDate.parse(dataMorte));
        	}
        	else {
        		artistaNuovo.setDataMorte(null);
        	}
        	artistaNuovo.setLuogoMorte(luogoMorte);
        	artistaNuovo.setNazionalita(nazionalita);        	
            artistaNuovo.setImmagine(immagine.getOriginalFilename());
        	
        	artistaService.inserisci(artistaNuovo);
            model.addAttribute("artisti", this.artistaService.tutti());
            if(!(immagine.getSize()==0)) {
            String uploadDir ="photos/"+ artistaNuovo.getId()+artistaNuovo.getNome()+artistaNuovo.getCognome();
            
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
            }
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
            String uploadDir ="photos/"+ artista.getId()+artista.getNome()+artista.getCognome();
            
            Path uploadPath = Paths.get(uploadDir);
            if(!(immagine.getSize()==0)) {
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
            return "artisti.html";
        }
        return "InserisciArtista.html";
    }
    
    @RequestMapping(value="/ordineAlfabeticoArtista", method = RequestMethod.GET)
    public String ordineAlfabeticoArtista(Model model) {
    		logger.debug("ordineAlfabeticoArtista");
    		List<Artista> artistaAlfabetico = this.artistaService.tutti();
    		
    		if (artistaAlfabetico.size() > 0) {
    			  Collections.sort(artistaAlfabetico, new Comparator<Artista>() {
    			      @Override
    			      public int compare(final Artista artista1, final Artista artista2) {
    			    	  int differenza = 0;
    			          differenza = artista1.getNome().compareTo(artista2.getNome());
    			          if(differenza == 0) {
    			        	  return artista1.getCognome().compareTo(artista2.getCognome());
    			          }
    			          else {
    			        	  return differenza;
    			          }
    			      }
    			  });
    			}
    		model.addAttribute("artisti", artistaAlfabetico);
    		
        	return "artisti.html";
    }
 
}
