package it.uniroma3.siw.spring.model;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nome","cognome"}))
public class Artista implements Comparable<Artista>{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (nullable = false)
	@NotEmpty
	private String nome;
	
	@Column (nullable = false)
	@NotEmpty
	private String cognome;
	
	@Column (nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;
	
	@Column (nullable = true)
	private String luogoNascita;
	
	@Column (nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataMorte;
	
	@Column (nullable = true)
	private String luogoMorte;
	
	@Column (nullable = false)
	@NotEmpty
	private String nazionalita;
	
	@OneToMany (mappedBy = "autore", cascade= {CascadeType.PERSIST})
	private List<Opera> opere;
	
	@Column(nullable = true)
	private String immagine;
	
	@Column(nullable=true, length=1024)
	private String biografia;
	
	public Artista() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public LocalDate getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}


	public String getLuogoNascita() {
		return luogoNascita;
	}


	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}


	public LocalDate getDataMorte() {
		return dataMorte;
	}


	public void setDataMorte(LocalDate dataMorte) {
		this.dataMorte = dataMorte;
	}


	public String getLuogoMorte() {
		return luogoMorte;
	}


	public void setLuogoMorte(String luogoMorte) {
		this.luogoMorte = luogoMorte;
	}


	public String getNazionalita() {
		return nazionalita;
	}


	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}


	public List<Opera> getOpere() {
		return opere;
	}


	public void setOpere(List<Opera> opere) {
		this.opere = opere;
	}
	
	public String getImmagine() {
		return immagine;
	}


	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	
	public String getBiografia() {
		return biografia;
	}


	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}
	
	 @Transient
	    public String getPhotosImagePath() {
	        if (immagine == null || id == null) return null;
	         
	        return "/photos/" +id+nome+cognome+ "/" + immagine;
	    }
	 
	@Override
	public int compareTo(Artista artista){
		int result;
		result = this.getNome().compareTo(artista.getNome());
		if (result == 0)
			result = this.getCognome().compareTo(artista.getCognome());
		return result;
	}
	

	
}
