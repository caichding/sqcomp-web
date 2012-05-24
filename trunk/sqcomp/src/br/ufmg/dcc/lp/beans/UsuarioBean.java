package br.ufmg.dcc.lp.beans;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.ufmg.dcc.lp.dao.UsuarioDAO;
import br.ufmg.dcc.lp.model.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable{
	
	private Usuario usuario;
	
	private boolean logado = false; 
	
	@ManagedProperty(name="usuarioDAO",value="#{usuarioDAO}")
	private UsuarioDAO usuarioDAO;
	
	public UsuarioBean(){
		this.usuario = new Usuario();
	}
	
	public void valida(){
		try{

			Usuario u = usuarioDAO.find(usuario.getUsername());
			if(u.getUsername().equals(usuario.getUsername()) && u.getPassword().equals(usuario.getPassword())){
				this.logado = true;
				this.usuario = u;
			}else{
				throw new SQLException("Usuario ou senha incorreto");
			}
			
		}catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void deslogar(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioBean");
	}
	
	public void cadastrar(){
		try{

			usuarioDAO.persist(usuario);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","O usuario foi cadastrado com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	
	

}
