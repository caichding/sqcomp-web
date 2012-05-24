package br.ufmg.dcc.lp.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.ufmg.dcc.lp.dao.InfoDAO;
import br.ufmg.dcc.lp.model.Informacoes;
import br.ufmg.dcc.lp.model.Log;
import br.ufmg.dcc.lp.model.Noticia;
import br.ufmg.dcc.lp.model.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class InfoBean implements Serializable{

	@ManagedProperty(name="infoDAO",value="#{infoDAO}")
    private InfoDAO infoDAO;
	
	@ManagedProperty(value="#{usuarioBean.usuario}")
	private Usuario usuario;
	
	private Long noticiaId;
	private Long logId;
	
	private Informacoes lastInfo;
	
	private List<Noticia> noticias;
	private List<Log> modificacoes;
	
	private Noticia noticia = new Noticia();
	private Log log = new Log();
	
	public void salvarInformacoes(){
		
		try{
			
			infoDAO.persist(lastInfo);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","Informação salvas com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void salvarLog(){
		
		try{
			
			this.log.setData(new Timestamp(System.currentTimeMillis()));
			this.log.setDeletado(false);
			this.log.setDataEdicao(null);
			this.log.setUsuarioCadastro(usuario);
			this.log.setUsuarioEdicao(null);
			this.log.setVisivel(true);
			
			infoDAO.persist(this.log);
			
			this.log = new Log();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","O log foi cadastrado com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void editarLog(){
		
		try{
			
			this.log.setUsuarioEdicao(usuario);
			this.log.setDataEdicao(new Timestamp(System.currentTimeMillis()));
			
			infoDAO.persist(this.log);
			
			this.noticia = new Noticia();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","O log foi editado com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void ocultarLog(){
		
		try{
			
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			this.logId = params.get("logId") != null ? Long.parseLong(params.get("logId")) : null;
			
			this.log = infoDAO.getLog(logId);
			this.log.setVisivel(false);
			
			infoDAO.persist(this.log);
			this.log = new Log();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","A noticia foi oculta com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void desocultarLog(){
		
		try{
			
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			this.logId = params.get("logId") != null ? Long.parseLong(params.get("logId")) : null;
			
			this.log = infoDAO.getLog(logId);
			this.log.setVisivel(true);
			
			infoDAO.persist(this.log);
			this.log = new Log();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","A noticia foi habilitada com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void deletarLog(){
		
		try{
			
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			this.logId = params.get("logId") != null ? Long.parseLong(params.get("logId")) : null;
			
			this.log = infoDAO.getLog(logId);
			this.log.setDeletado(true);
			
			infoDAO.persist(this.log);
			this.log = new Log();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","A noticia foi deletada com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}

	public void salvarNoticia(){
		
		try{
			
			noticia.setData(new Timestamp(System.currentTimeMillis()));
			noticia.setDeletado(false);
			noticia.setDataEdicao(null);
			noticia.setUsuarioCadastro(usuario);
			noticia.setUsuarioEdicao(null);
			noticia.setVisivel(true);
			
			infoDAO.persist(noticia);
			
			noticia = new Noticia();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","A noticia foi cadastrada com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void editarNoticia(){
		
		try{
			
			this.noticia.setUsuarioEdicao(usuario);
			this.noticia.setDataEdicao(new Timestamp(System.currentTimeMillis()));
			
			infoDAO.persist(this.noticia);
			
			this.noticia = new Noticia();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","A noticia foi editada com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void ocultarNoticia(){
		
		try{
			
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			this.noticiaId = params.get("noticiaId") != null ? Long.parseLong(params.get("noticiaId")) : null;
			
			this.noticia = infoDAO.getNoticia(noticiaId);
			this.noticia.setVisivel(false);
			
			infoDAO.persist(this.noticia);
			this.noticia = new Noticia();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","A noticia foi oculta com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void desocultarNoticia(){
		
		try{
			
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			this.noticiaId = params.get("noticiaId") != null ? Long.parseLong(params.get("noticiaId")) : null;
			
			this.noticia = infoDAO.getNoticia(noticiaId);
			this.noticia.setVisivel(true);
			
			infoDAO.persist(this.noticia);
			this.noticia = new Noticia();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","A noticia foi habilitada com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void deletarNoticia(){
		
		try{
			
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			this.noticiaId = params.get("noticiaId") != null ? Long.parseLong(params.get("noticiaId")) : null;
			
			this.noticia = infoDAO.getNoticia(noticiaId);
			this.noticia.setDeletado(true);
			
			infoDAO.persist(this.noticia);
			this.noticia = new Noticia();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","A noticia foi deletada com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}

	public boolean atualize(){
		
		this.lastInfo = infoDAO.getLastInfo();
		this.noticias = infoDAO.getNoticias();
		this.modificacoes = infoDAO.getLogs();
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		this.noticiaId = params.get("noticiaId") != null ? Long.parseLong(params.get("noticiaId")) : null;
		this.logId = params.get("logId") != null ? Long.parseLong(params.get("logId")) : null;
		
		if(noticiaId != null){this.noticia = infoDAO.getNoticia(noticiaId);}
		else{this.noticia = new Noticia();}
		
		if(this.logId != null){this.log = infoDAO.getLog(logId);}
		else{this.log = new Log();}
		
		return true;
	}
	
	public InfoDAO getInfoDAO() {
		return infoDAO;
	}

	public void setInfoDAO(InfoDAO infoDAO) {
		this.infoDAO = infoDAO;
	}

	public Informacoes getLastInfo() {
		return lastInfo;
	}

	public void setLastInfo(Informacoes lastInfo) {
		this.lastInfo = lastInfo;
	}

	public List<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public List<Log> getModificacoes() {
		return modificacoes;
	}

	public void setModificacoes(List<Log> modificacoes) {
		this.modificacoes = modificacoes;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getNoticiaId() {
		return noticiaId;
	}

	public void setNoticiaId(Long noticiaId) {
		this.noticiaId = noticiaId;
	}
	
}
