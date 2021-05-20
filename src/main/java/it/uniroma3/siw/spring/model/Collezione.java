package it.uniroma3.siw.spring.model;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import it.uniroma3.progetto.model.Autore;



@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nome", "curatore_id"}))
public class Collezione {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column (nullable = false)
	@NotEmpty
	private String nome;
	
	@Column (nullable = true)
	@NotEmpty
	private String descrizione;
	
	@ManyToOne(cascade= {CascadeType.ALL})
	private Curatore curatore;
	
	@ManyToOne(cascade= {CascadeType.ALL})
	private Museo museo;
	
	@OneToMany (mappedBy = "collezione", cascade= {CascadeType.ALL})
	private List<Opera> opereDellaCollezione;

	public Collezione() {
		
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Curatore getCuratore() {
		return curatore;
	}

	public void setCuratore(Curatore curatore) {
		this.curatore = curatore;
	}

	public Museo getMuseo() {
		return museo;
	}

	public void setMuseo(Museo museo) {
		this.museo = museo;
	}

	public List<Opera> getOpereDellaCollezione() {
		return opereDellaCollezione;
	}

	public void setOpereDellaCollezione(List<Opera> opereDellaCollezione) {
		this.opereDellaCollezione = opereDellaCollezione;
	}
	
	@Override
	public int compareTo(Collezione collezione){
		int result;
		result = this.getNome().compareTo(collezione.getNome());
		if (result == 0)
			result = this.getCuratore().compareTo(collezione.getCuratore());
		return result;
	}

}
