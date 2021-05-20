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



@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nome"}))
public class Collezione {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column (nullable = false)
	private String nome;
	
	@Column (nullable = true)
	private String descrizione;
	
	@ManyToOne(cascade= {CascadeType.ALL})
	private Curatore curatore;
	
	@ManyToOne(cascade= {CascadeType.ALL})
	private Museo museo;
	
	@OneToMany (mappedBy = "collezione", cascade= {CascadeType.ALL})
	private List<Opera> opereDellaCollezione;

	public Collezione(String nome, String descrizione, Curatore curatore, Museo museo, List<Opera> opereDellaCollezione) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.curatore = curatore;
		this.museo = museo;
		this.opereDellaCollezione = opereDellaCollezione;
	}
	
	public Collezione() {
		
	}

}
