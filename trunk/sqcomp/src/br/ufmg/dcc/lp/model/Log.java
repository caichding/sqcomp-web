package br.ufmg.dcc.lp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class Log implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String versao;
	
	private Timestamp data;
	private Usuario usuarioCadastro;
	
	private Timestamp dataEdicao;
	private Usuario usuarioEdicao;
	
	private boolean deletado;
	private boolean visivel;
	
	private byte[] descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public String getDescricao() {
		return new String(descricao != null ? descricao : new byte[0]);
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao.getBytes();
	}

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Timestamp getDataEdicao() {
		return dataEdicao;
	}

	public void setDataEdicao(Timestamp dataEdicao) {
		this.dataEdicao = dataEdicao;
	}

	public Usuario getUsuarioEdicao() {
		return usuarioEdicao;
	}

	public void setUsuarioEdicao(Usuario usuarioEdicao) {
		this.usuarioEdicao = usuarioEdicao;
	}

	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}

	public boolean isVisivel() {
		return visivel;
	}

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}
	
	

}
