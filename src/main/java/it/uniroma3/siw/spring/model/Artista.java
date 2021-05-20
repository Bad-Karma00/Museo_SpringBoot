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
import javax.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nome","cognome"}))
public class Artista {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column (nullable = false)
	private String nome;
	
	@Column (nullable = false)
	private String cognome;
	
	@Column (nullable = false)
	private LocalDate dataNascita;
	
	@Column (nullable = true)
	private String luogoNascita;
	
	@Column (nullable = true)
	private LocalDate dataMorte;
	
	@Column (nullable = true)
	private String luogoMorte;
	
	@Column (nullable = false)
	private String nazionalita;
	
	@OneToMany (mappedBy = "autore", cascade= {CascadeType.ALL})
	private List<Opera> opere;
	
	

	public Artista(String nome, String cognome, LocalDate dataNascita, String luogoNascita, LocalDate dataMorte, String luogoMorte, String nazionalita, List<Opera> opere) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.dataMorte = dataMorte;
		this.luogoMorte = luogoMorte;
		this.nazionalita = nazionalita;
		this.opere = opere;
	}
	
	public Artista() {
		
	}
	
	
	

	
}
