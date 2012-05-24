package br.ufmg.dcc.lp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity(name="Sistema")
public class Sistema implements Serializable,Comparable<Sistema>,Comparator<Sistema>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String version;
	
	@OneToOne(fetch=FetchType.LAZY)
	private Usuario usuario;
	
	private Timestamp data;
	
	private boolean disponivel;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Classe> classes;
	
	public Sistema() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim().toLowerCase();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version.trim().toLowerCase();
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return name.toString()+"-"+version.toString();
	}

	@Override
	public int compareTo(Sistema o) {
		if(this.id == o.id){
			return 0;
		}else if(this.id > o.id){
			return 1;
		}else{
			return -1;
		}
	}

	@Override
	public int compare(Sistema o1, Sistema o2) {
		if(o1.id == o2.id){
			return 0;
		}else if(o1.id > o2.id){
			return 1;
		}else{
			return -1;
		}
	}

	@Override
	public boolean equals(Object obj) {
		Sistema o = (Sistema) obj;
		if(this.id == o.id){
			return true;
		}
		return false;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
	
	
	
	

}
