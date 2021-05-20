package it.uniroma3.siw.spring.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Opera {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column (nullable = false)
	private String titolo;
	
	
	@Column (nullable = true)
	private LocalDate anno;
	
	
	@Column (nullable = true)
	private String descrizione;
	
	@ManyToOne (cascade= {CascadeType.ALL})
	private Collezione collezione;
	
	@ManyToOne (cascade= {CascadeType.ALL})
	private Artista autore;

	public Opera(String titolo, LocalDate anno, String descrizione, Collezione collezione, Artista autore) {
		this.titolo = titolo;
		this.anno = anno;
		this.descrizione = descrizione;
		this.collezione = collezione;
		this.autore = autore;
	}
	
	public Opera() {
		
	}

}
