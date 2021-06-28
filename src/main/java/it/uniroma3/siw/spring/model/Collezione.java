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

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nome", "curatore_matricola"}))
public class Collezione implements Comparable<Collezione>{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (nullable = false)
	@NotEmpty
	private String nome;
	
	@Column (nullable = true, length=1024)
	@NotEmpty
	private String descrizione;
	
	@ManyToOne(cascade= {CascadeType.PERSIST})
	private Curatore curatore;
	
	@OneToMany (mappedBy = "collezione", cascade= {CascadeType.PERSIST})
	private List<Opera> opereDellaCollezione;

	public Collezione() {
		
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
