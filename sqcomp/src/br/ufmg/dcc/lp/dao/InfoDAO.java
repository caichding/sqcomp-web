package br.ufmg.dcc.lp.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import br.ufmg.dcc.lp.beans.UsuarioBean;
import br.ufmg.dcc.lp.model.Informacoes;
import br.ufmg.dcc.lp.model.Log;
import br.ufmg.dcc.lp.model.Noticia;

@SuppressWarnings("serial")
@ManagedBean(name="infoDAO")
@ApplicationScoped
public class InfoDAO implements Serializable {

	@ManagedProperty(name="dao",value="#{dao}")
	private DAO dao;
	
	public Noticia getNoticia(Long id){
		
		Noticia n =  dao.getEntityManager().find(Noticia.class,id);
		return n;
		
	}
	
	public Log getLog(Long id){
		
		Log l =  dao.getEntityManager().find(Log.class,id);
		return l;
		
	}
	
	public void persist(Log log) throws Exception{
				
		try {
			dao.getNewEntityManager().getTransaction().begin();
			if(log != null){
				if(log.getId() != null){
					dao.getEntityManager().merge(log);
				}else{
					dao.getEntityManager().persist(log);
				}
			}
			dao.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			dao.getEntityManager().getTransaction().rollback();
			throw e;
		}
				
	}
	
	public void persist(Noticia noticias) throws Exception{
		
		try {
			dao.getNewEntityManager().getTransaction().begin();
			if(noticias != null){
				if(noticias.getId() != null){
					dao.getEntityManager().merge(noticias);
				}else{
					dao.getEntityManager().persist(noticias);
				}
			}
			dao.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			dao.getEntityManager().getTransaction().rollback();
			throw e;
		}
		
	}
	
	public void persist(Informacoes info) throws Exception{
				
		try {
			dao.getNewEntityManager().getTransaction().begin();
			if(info != null){
				if(info.getId() != null){
					dao.getEntityManager().merge(info);
				}else{
					dao.getEntityManager().persist(info);
				}
			}
			dao.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			dao.getEntityManager().getTransaction().rollback();
			throw e;
		}
		
	}
	
	public void delete(Long noticiaId) throws Exception{
		
		try {
			dao.getNewEntityManager().getTransaction().begin();
			if(noticiaId != null){
				if(noticiaId != null){
					dao.getEntityManager().remove(dao.getEntityManager().find(Noticia.class,noticiaId));
				}
			}
			dao.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			dao.getEntityManager().getTransaction().rollback();
			throw e;
		}
		
	}
	
	public Informacoes getLastInfo(){
		
		Informacoes info = null;
		try{
			info = (Informacoes) dao.getEntityManager().createQuery("FROM Informacoes ORDER BY id desc").getResultList().get(0);
		}catch (Exception e) {
			System.err.print(e.getLocalizedMessage());
		}
		
		return info == null ? new Informacoes() : info;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Noticia> getNoticias(){
		
		Map<String, Object> params = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		UsuarioBean usuarioBean = params.get("usuarioBean") != null ? (UsuarioBean)params.get("usuarioBean") : null;
		
		try{
			if(usuarioBean.getUsuario().getId() != null){
				return dao.getEntityManager().createQuery("FROM Noticia WHERE deletado = false ORDER BY data desc").getResultList();
			}else{
				return dao.getEntityManager().createQuery("FROM Noticia WHERE visivel = true AND deletado = false ORDER BY data desc").getResultList();
			}
		}catch (Exception e) {
			System.err.print(e.getLocalizedMessage());
		}
		
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Log> getLogs(){
		
		Map<String, Object> params = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		UsuarioBean usuarioBean = params.get("usuarioBean") != null ? (UsuarioBean)params.get("usuarioBean") : null;
		
		try{
			if(usuarioBean.getUsuario().getId() != null){
				return dao.getEntityManager().createQuery("FROM Log WHERE deletado = false ORDER BY data desc").getResultList();
			}else{
				return dao.getEntityManager().createQuery("FROM Log WHERE visivel = true AND deletado = false ORDER BY data desc").getResultList();
			}
		}catch (Exception e) {
			System.err.print(e.getLocalizedMessage());
		}
		
		return null;
		
	}

	public DAO getDao() {
		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}
	
}
