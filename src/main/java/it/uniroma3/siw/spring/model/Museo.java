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
	private Long id;
	
	@Column (nullable = false)
	private String nome;
	
	@OneToMany (mappedBy = "museo", cascade= {CascadeType.ALL})
	private List<Collezione> collezioneMuseo;

	@OneToMany (mappedBy = "museo", cascade= {CascadeType.ALL})
	private List<Curatore> dipendenti;

	
	public Museo() {
		
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


	public List<Collezione> getCollezioneMuseo() {
		return collezioneMuseo;
	}


	public void setCollezioneMuseo(List<Collezione> collezioneMuseo) {
		this.collezioneMuseo = collezioneMuseo;
	}


	public List<Curatore> getDipendenti() {
		return dipendenti;
	}


	public void setDipendenti(List<Curatore> dipendenti) {
		this.dipendenti = dipendenti;
	}

	
}
