package it.uniroma3.siw.spring.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"titolo","autore_id"}))
public class Opera implements Comparable<Opera> {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column (nullable = false)
	private String titolo;
	
	
	@Column (nullable = true)
	private Integer anno;
	
	
	@Column (nullable = true)
	private String descrizione;
	
	@ManyToOne (cascade= {CascadeType.ALL})
	private Collezione collezione;
	
	@ManyToOne (cascade= {CascadeType.ALL})
	private Artista autore;

    private String immagine;
	
	public Opera() {
		
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Collezione getCollezione() {
		return collezione;
	}

	public void setCollezione(Collezione collezione) {
		this.collezione = collezione;
	}

	public Artista getAutore() {
		return autore;
	}

	public void setAutore(Artista autore) {
		this.autore = autore;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	
	public int compareTo(Opera opera){
		int result;
		result = this.getTitolo().compareTo(opera.getTitolo());
		if (result == 0)
			result = this.getAutore().compareTo(opera.getAutore());
		return result;
	}
}
