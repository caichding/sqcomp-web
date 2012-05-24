package br.ufmg.dcc.lp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity(name="Informacoes")
public class Informacoes implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private byte[] quemSomos;
	
	private byte[] oProblema;
	
	private byte[] conceitos;
	
	private byte[] solucao;
	
	private byte[] sistemas;
	
	private byte[] versaoDesktop;
	
	private byte[] contato;

	public String getConceitos() {
		return new String(conceitos != null ? conceitos : new byte[0]);
	}

	public String getContato() {
		return new String(contato  != null ? contato : new byte[0]);
	}

	public Long getId() {
		return id;
	}
	
	public String getoProblema() {
		return new String(oProblema  != null ? oProblema : new byte[0]);
	}

	public String getQuemSomos() {
		return new String(quemSomos  != null ? quemSomos : new byte[0]);
	}

	public String getSistemas() {
		return new String(sistemas  != null ? sistemas : new byte[0]);
	}

	public String getSolucao() {
		return new String(solucao  != null ? solucao : new byte[0]);
	}

	public void setConceitos(String conceitos) {
		this.conceitos = conceitos.getBytes();
	}

	public void setContato(String contato) {
		this.contato = contato.getBytes();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setoProblema(String oProblema) {
		this.oProblema = oProblema.getBytes();
	}

	public void setQuemSomos(String quemSomos) {
		this.quemSomos = quemSomos.getBytes();
	}

	public void setSistemas(String sistemas) {
		this.sistemas = sistemas.getBytes();
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao.getBytes();
	}

	public String getVersaoDesktop() {
		return new String(versaoDesktop  != null ? versaoDesktop : new byte[0]);
	}

	public void setVersaoDesktop(String versaoDesktop) {
		this.versaoDesktop = versaoDesktop.getBytes();
	}
	
	

}
