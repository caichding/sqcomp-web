package br.ufmg.dcc.lp.dao;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SuppressWarnings("serial")
@ManagedBean(name="dao",eager=true)
@ApplicationScoped
public class DAO implements Serializable{

	public static final String H2SQL = "h2sql";
	public static final String POSTGRES = "postgres";
	
	private static final String ATUAL = POSTGRES;
	
	private EntityManagerFactory emf;
	private EntityManager em;

	public DAO() {
		super();
		emf = Persistence.createEntityManagerFactory(ATUAL);
		em = emf.createEntityManager();
	}
	
	protected EntityManager getNewEntityManager(){
		em = emf.createEntityManager();
		return em;
	}
	
	protected EntityManager getEntityManager(){
		return em;
	}
	
	public void close(){
		emf.close();
	}

}