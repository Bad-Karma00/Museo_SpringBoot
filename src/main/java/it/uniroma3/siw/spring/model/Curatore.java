package it.uniroma3.siw.spring.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nome", "cognome"}))
public class Curatore implements Comparable<Curatore> {
	
	@Column (nullable = false)
	private String nome;
	
	@Column (nullable = false)
	private String cognome;
	
	@Column (nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;
	
	@Column (nullable = true)
	private String luogo;
	
	@Column (nullable = false)
	private String mail;
	
	@Column (nullable = true)
	private String numero;
	
	@Id
	private Long matricola;
	
	@OneToMany (mappedBy = "curatore", cascade= {CascadeType.PERSIST})
	private List<Collezione> collezioni;

	
	public Curatore() {
		
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


	public LocalDate getData() {
		return data;
	}


	public void setData(LocalDate data) {
		this.data = data;
	}


	public String getLuogo() {
		return luogo;
	}


	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public Long getMatricola() {
		return matricola;
	}


	public void setMatricola(Long matricola) {
		this.matricola = matricola;
	}


	public List<Collezione> getCollezioni() {
		return collezioni;
	}


	public void setCollezioni(List<Collezione> collezioni) {
		this.collezioni = collezioni;
	}

	
	@Override
	public int compareTo(Curatore curatore){
		int result;
		result = this.getNome().compareTo(curatore.getNome());
		if (result == 0)
			result = this.getCognome().compareTo(curatore.getCognome());
		return result;
	}

}
