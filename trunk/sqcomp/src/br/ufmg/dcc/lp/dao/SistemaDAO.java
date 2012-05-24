package br.ufmg.dcc.lp.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import br.ufmg.dcc.lp.beans.UsuarioBean;
import br.ufmg.dcc.lp.model.Sistema;

@SuppressWarnings("serial")
@ManagedBean(name="sistemaDAO")
@ApplicationScoped
public class SistemaDAO implements Serializable{
	
	@ManagedProperty(name="dao",value="#{dao}")
	private DAO dao;
	
	@SuppressWarnings("rawtypes")
	public void persist(Sistema sistema) throws Exception {

		try {
			sistema.setName(sistema.getName().trim().toLowerCase());
			sistema.setVersion(sistema.getVersion().trim().toLowerCase());
			List aux = dao.getEntityManager().createQuery("FROM Sistema s WHERE s.name = '"+sistema.getName()+"' AND s.version = '"+sistema.getVersion()+"'").getResultList();
			dao.getNewEntityManager().getTransaction().begin();
			if(sistema.getId() != null){
				dao.getEntityManager().merge(sistema);
			}else if(aux == null || aux.isEmpty()){
				dao.getEntityManager().persist(sistema);
			}else{
				throw new SQLException("Sistema ja cadastrado");
			}
			dao.getEntityManager().getTransaction().commit();
			
		} catch (Exception e) {
			dao.getEntityManager().getTransaction().rollback();
			throw e;
		}
		 
	}
	
	public void delete(Long systemId){
		
		try{
			dao.getNewEntityManager().getTransaction().begin();
			dao.getEntityManager().remove(dao.getEntityManager().find(Sistema.class,systemId));
			dao.getEntityManager().getTransaction().commit();
		}catch (Exception e) {
			dao.getEntityManager().getTransaction().rollback();
		}
		
		
	}
	
	public void merge(Sistema system){
		
		try{
			dao.getNewEntityManager().getTransaction().begin();
			dao.getEntityManager().merge(system);
			dao.getEntityManager().getTransaction().commit();		
		}catch (Exception e) {
			dao.getEntityManager().getTransaction().rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Sistema> get(Long id,String name, String version) {

		Map<String, Object> params = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		UsuarioBean usuarioBean = params.get("usuarioBean") != null ? (UsuarioBean)params.get("usuarioBean") : null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Sistema s ");
		sb.append("WHERE (s.disponivel = true ");
		if(usuarioBean.isLogado()){
			sb.append("or s.disponivel = false) ");
		}else{
			sb.append(") ");
		}
		if(id != null){
			sb.append("and s.id = " + id + " ");
		}else if (name != null || version != null) {
			if (name != null) {
				sb.append("and s.name = '" + name.trim().toLowerCase() + "' ");
			}
			if (version != null) {
				sb.append("and s.version = '" + version.trim().toLowerCase() + "' ");
			}

		}
		sb.append("ORDER BY s.name,s.version");
		
		List<Sistema> list = dao.getEntityManager().createQuery(sb.toString()).getResultList();
		
		return list;

	}

	public DAO getDao() {
		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}

}
