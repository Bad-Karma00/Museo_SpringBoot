package it.uniroma3.siw.spring.model;

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
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nome"}))
public class Museo {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column (nullable = false)
	private String nome;
	
	@OneToMany (mappedBy = "museo", cascade= {CascadeType.ALL})
	private List<Collezione> collezioneMuseo;

	@OneToMany (mappedBy = "museo", cascade= {CascadeType.ALL})
	private List<Curatore> dipendenti;

	public Museo(String nome, List<Collezione> collezioneMuseo, List<Curatore> dipendenti) {
		this.nome = nome;
		this.collezioneMuseo = collezioneMuseo;
		this.dipendenti = dipendenti;
	}
	
	public Museo() {
		
	}

	
}
