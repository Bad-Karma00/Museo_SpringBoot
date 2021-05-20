package it.uniroma3.siw.spring.model;

import java.time.LocalDate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nome", "cognome"}))
public class Curatore {
	
	@Column (nullable = false)
	private String nome;
	
	@Column (nullable = false)
	private String cognome;
	
	@Column (nullable = false)
	private LocalDate data;
	
	@Column (nullable = true)
	private String luogo;
	
	@Column (nullable = false)
	private String mail;
	
	@Column (nullable = true)
	private String numero;
	
	@Id
	private Long matricola;
	
	@OneToMany (mappedBy = "curatore", cascade= {CascadeType.ALL})
	private List<Collezione> collezioni;
	
	@ManyToOne(cascade= {CascadeType.ALL})
	private Museo museo;

	public Curatore(String nome, String cognome, LocalDate data, String luogo, String mail, String numero,
			Long matricola, List<Collezione> collezioni, Museo museo) {
		this.nome = nome;
		this.cognome = cognome;
		this.data = data;
		this.luogo = luogo;
		this.mail = mail;
		this.numero = numero;
		this.matricola = matricola;
		this.collezioni = collezioni;
		this.museo = museo;
	}
	
	public Curatore() {
		
	}

}
