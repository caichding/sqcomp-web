package br.ufmg.dcc.lp.beans;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.apache.commons.math.stat.clustering.EuclideanIntegerPoint;
import org.jgraph.JGraph;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;

import br.ufmg.dcc.lp.dao.SistemaDAO;
import br.ufmg.dcc.lp.model.Metricas;
import br.ufmg.dcc.lp.model.Processo;
import br.ufmg.dcc.lp.model.Sistema;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class ClusterBean implements Serializable {

    private int clique;
    
    private JGraph jGraph;
	
	private DualListModel<String> metricas;
	
	@ManagedProperty(name="processManagerBean",value="#{processManagerBean}")
    private ProcessManagerBean processManagerBean;
	
	private Processo processo = null;

    @ManagedProperty(name="sistemaDAO",value="#{sistemaDAO}")
    private SistemaDAO sistemaDAO;
    
    private DualListModel<Sistema> sistemas;
    
    private DataModel<Object[]> model;

	private Integer valor;
		
	public void calcular(){
		
		if(sistemas.getTarget().size() < 5 || metricas.getTarget().size() < 1 || valor > metricas.getTarget().size() || valor < 1){
			
			if(sistemas.getTarget().size() < 5){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!","Voc� deve escolher pelo menos 5 sistemas!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			if(metricas.getTarget().size() < 1){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!","Escolha pelo menos uma metrica!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			if(valor > metricas.getTarget().size() || valor < 1){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!","O atributo valor deve estar entre 1 e "+metricas.getTarget().size()+"!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			create();
			
			return;
		}
		
		//processo = processManagerBean.getProcesso(valor,new TreeSet<Sistema>(sistemas.getTarget()),new TreeSet<String>(metricas.getTarget()));
		
		if(processo == null){
			
			processo = Processo.getInstance(new TreeSet<Sistema>(sistemas.getTarget()),new TreeSet<String>(metricas.getTarget()), valor);
			processo.construirGrafo(new TreeSet<Sistema>(sistemas.getTarget()));
			
			/*
			try {
				processManagerBean.add(processo);
			} catch (FileNotFoundException e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (IOException e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			*/
			
		}
		
		this.jGraph = processo.getjGraph();
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/"+FacesContext.getCurrentInstance().getExternalContext().getContextName()+"/process/result.xhtml");
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!","Por favor tente novamente mais tarde");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public boolean create(){
		
		this.sistemas = new DualListModel<Sistema>(sistemaDAO.get(null,null, null),new ArrayList<Sistema>());
		this.metricas = new DualListModel<String>(Arrays.asList(Metricas.ALL_METRICS),new ArrayList<String>());
		this.clique = -1;
		this.jGraph = null;
		this.processo = null;
		this.valor = null;
		
		return true;
	}
	
	public int getClique() {
		return clique;
	}
	
	public List<Set<Sistema>> getCliques() {
		
        Comparator<Set<Sistema>> comparator = new Comparator<Set<Sistema>>() {
			
			@Override
			public int compare(Set<Sistema> o1, Set<Sistema> o2) {
				if(o1.size() == o2.size()){
					return 0;
				}else if(o1.size() > o2.size()){
					return -1;
				}else{
					return 1;
				}
			}
		};
		
		ArrayList<Set<Sistema>> l = new ArrayList<Set<Sistema>>(processo.getCliques());
		Collections.sort(l,comparator);
		
		return l;
	}

	public void getGraph(){
		Set<Sistema> set = new TreeSet<Sistema>(sistemas.getTarget());
		for(Set<Sistema> s : processo.getCliques()){
			if(s.hashCode() == clique){
				set = s;
				break;
			}
		}
		processo.construirGrafo(set);
		jGraph = processo.getjGraph();
	}
	
	public DefaultStreamedContent getGraphImage() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( jGraph.getImage(Color.white,1), "png", baos );
		return new DefaultStreamedContent(new ByteArrayInputStream(baos.toByteArray()), "image/png");
	}

	public DualListModel<String> getMetricas() {
		return metricas;
	}

	public ProcessManagerBean getProcessManagerBean() {
		return processManagerBean;
	}

	public SistemaDAO getSistemaDAO() {
		return sistemaDAO;
	}

	public DualListModel<Sistema> getSistemas() {
		return sistemas;
	}

	public SelectItem getSistemasSelecionados(){
		return new SelectItem(sistemas.getTarget().hashCode(),"Todos Sistemas Selecionados");
	}

	public Integer getValor() {
		return valor;
	}

	public void setClique(int clique) {
		this.clique = clique;
	}
	
	public void setMetricas(DualListModel<String> metricas) {
		this.metricas = metricas;
	}

	public void setProcessManagerBean(ProcessManagerBean processManagerBean) {
		this.processManagerBean = processManagerBean;
	}
	
	public void setSistemaDAO(SistemaDAO sistemaDAO) {
		this.sistemaDAO = sistemaDAO;
	}

	public void setSistemas(DualListModel<Sistema> sistemas) {
		this.sistemas = sistemas;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Processo getProcesso() {
		return processo;
	}
	
	public void constructModel(String param){
		
		if(param != null){
			
			List<Object[]> l = new ArrayList<Object[]>();
			Set<Sistema> conjuntoSistemas = processo.getMapsClustersPri().keySet();
			EuclideanIntegerPoint clusters = null;
			
			for(Sistema s : conjuntoSistemas){
				Map<String, EuclideanIntegerPoint> m = processo.getMapsClustersPri().get(s);
				if((clusters = m.get(param)) != null){
					int[] points = clusters.getPoint();
					l.add(new Object[]{s.getName(),points[0],points[1],points[2],points[3],points[4]});
				}
			}
			
			this.model = new ListDataModel<Object[]>(l);
			
		}else{
			
			this.model = new ListDataModel<Object[]>();
			
		}
		
	}

	public DataModel<Object[]> getModel() {
		return model != null ? model : new ListDataModel<Object[]>();
	}
	
	public List<Object[]> getMatrizNaoDiscretizada(){
		List<Object[]> l = new ArrayList<Object[]>();
		l.addAll(processo.getMatrizNaoDiscretizada());
		l.remove(0);
		return l;
	}

}
