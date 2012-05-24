package br.ufmg.dcc.lp.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import br.ufmg.dcc.lp.model.Usuario;

@SuppressWarnings("serial")
@ManagedBean(name="usuarioDAO")
@ApplicationScoped
public class UsuarioDAO implements Serializable{
	
	@ManagedProperty(name="dao",value="#{dao}")
	private DAO dao;
	
	@SuppressWarnings("rawtypes")
	public void persist(Usuario usuario) throws Exception{
		
		try {

			usuario.setUsername(usuario.getUsername().trim().toLowerCase());
			usuario.setPassword(usuario.getPassword().trim());
			List l = dao.getEntityManager().createQuery("FROM Usuario u WHERE u.username = '"+usuario.getUsername()+"'").getResultList();

			dao.getNewEntityManager().getTransaction().begin();
			if(l == null || l.isEmpty()){
				dao.getEntityManager().persist(usuario);
			}else{
				dao.getEntityManager().getTransaction().rollback();
				throw new SQLException("Username ja cadastrado");
			}
			
			dao.getEntityManager().getTransaction().commit();
			
		} catch (Exception e) {
			dao.getEntityManager().getTransaction().rollback();
			throw e;
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public Usuario find(String username) throws Exception{
		
		try {
			
			List l = dao.getEntityManager().createQuery("FROM Usuario u WHERE u.username = '"+username.trim().toLowerCase()+"'").getResultList();
			
			if(l == null || l.isEmpty()){
				throw new SQLException("Usuario ou senha incorreto");
			}
			
			return (Usuario) l.get(0);
			
		} catch (Exception e) {
			throw e;
		}
		
		
		
	}

	public DAO getDao() {
		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}

}
