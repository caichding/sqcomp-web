package br.ufmg.dcc.lp.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.ufmg.dcc.lp.dao.SistemaDAO;
import br.ufmg.dcc.lp.model.Classe;
import br.ufmg.dcc.lp.model.Metricas;
import br.ufmg.dcc.lp.model.Sistema;
import br.ufmg.dcc.lp.model.Usuario;
import br.ufmg.dcc.lp.util.Util;

@SuppressWarnings("serial") 
@ManagedBean
@SessionScoped 
public class SistemaBean implements Serializable{

	private Sistema sistema = null;
	private UploadedFile uploadedFile = null;
	private DataModel<?> model = null;
	
	private CartesianChartModel powerLow = null;
	
	@ManagedProperty(name="sistemaDAO",value="#{sistemaDAO}")
	private SistemaDAO sistemaDAO;
	
	@ManagedProperty(value="#{usuarioBean.usuario}")
	private Usuario usuario;

	public SistemaBean() {}
	
	@PostConstruct
	public void create(){
		this.sistema = new Sistema();
		this.uploadedFile = null;
		this.model = null;
		this.powerLow = null;
	}
	
	public void destroy(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("sistemaBean");
	}
	
	public void verSistema() throws IOException{
		try{
			create();
			Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSistema"));
			this.sistema = sistemaDAO.get(id,null,null).get(0);
			FacesContext.getCurrentInstance().getExternalContext().redirect("/"+FacesContext.getCurrentInstance().getExternalContext().getContextName()+"/systems/sistema.xhtml");
		}catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public boolean sistemaDisponivel(){
		
		try{
			if(this.sistema.isDisponivel()){
				return true;
			}
		}catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		return false;
		
	}
	
	public boolean liberaCadastro(){
		create();
		return true;
	}

	public void salvar() {

		List<Classe> list = new ArrayList<Classe>();
		Classe classe;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedFile.getInputstream()));
			
			String values[] = null;
			String colName[] = null;
			
			String aux = reader.readLine();
			values = aux.split(";");
			
			HashMap<String, Integer> mapMetricsCSV = new HashMap<String,Integer>();

			mapMetricsCSV.put("numberOfLinesOfCode",Util.searchBruteForce(values,"numberOfLinesOfCode"));
			mapMetricsCSV.put("numberOfMethods",Util.searchBruteForce(values,"numberOfMethods"));
			mapMetricsCSV.put("numberOfPublicMethods",Util.searchBruteForce(values,"numberOfPublicMethods"));
			mapMetricsCSV.put("numberOfPrivateMethods",Util.searchBruteForce(values,"numberOfPrivateMethods"));
			mapMetricsCSV.put("numberOfMethodsInherited",Util.searchBruteForce(values,"numberOfMethodsInherited"));
			mapMetricsCSV.put("numberOfAttributes",Util.searchBruteForce(values,"numberOfAttributes"));
			mapMetricsCSV.put("numberOfPrivateAttributes",Util.searchBruteForce(values,"numberOfPrivateAttributes"));
			mapMetricsCSV.put("numberOfAttributesInherited",Util.searchBruteForce(values,"numberOfAttributesInherited"));
			mapMetricsCSV.put("numberOfPublicAttributes",Util.searchBruteForce(values,"numberOfPublicAttributes"));
			
			mapMetricsCSV.put("numberOfChildren",Util.searchBruteForce(values,"numberOfChildren"));
			mapMetricsCSV.put("fanOut",Util.searchBruteForce(values,"fanOut"));
			mapMetricsCSV.put("fanIn",Util.searchBruteForce(values,"fanIn"));
			mapMetricsCSV.put("numberOfMethodsOverriden",Util.searchBruteForce(values,"numberOfMethodsOverriden"));
			mapMetricsCSV.put("totalNumberOfChildren",Util.searchBruteForce(values,"totalNumberOfChildren"));
			mapMetricsCSV.put("weightOfAClass",Util.searchBruteForce(values,"weightOfAClass"));
			mapMetricsCSV.put("weightedMethodCount",Util.searchBruteForce(values,"weightedMethodCount"));
			mapMetricsCSV.put("inheritedMethods",Util.searchBruteForce(values,"inheritedMethods"));
			mapMetricsCSV.put("couplingBetweenClasses",Util.searchBruteForce(values,"couplingBetweenClasses"));
			mapMetricsCSV.put("lackOfCohesionInMethods",Util.searchBruteForce(values,"lackOfCohesionInMethods"));
			mapMetricsCSV.put("responseForClass",Util.searchBruteForce(values,"responseForClass"));
			mapMetricsCSV.put("hierarchyNestingLevel",Util.searchBruteForce(values,"hierarchyNestingLevel"));
			
			while((aux = reader.readLine()) != null){
				
				try{
					
					values = aux.split(";");
					colName = values[0].split(",");
					classe = new Classe();
					classe.setName(colName[0].length() < 254 ? colName[0] : colName[0].substring(0,253));
					classe.setSuperEntity(colName[1].length() < 254 ? colName[1] : colName[1].substring(0,253));
					
					classe.setLoc(Integer.valueOf(values[mapMetricsCSV.get("numberOfLinesOfCode")].trim()));
					classe.setNom(Integer.valueOf(values[mapMetricsCSV.get("numberOfMethods")].trim()));
					classe.setNopubm(Integer.valueOf(values[mapMetricsCSV.get("numberOfPublicMethods")].trim()));
					classe.setNoprim(Integer.valueOf(values[mapMetricsCSV.get("numberOfPrivateMethods")].trim()));
					classe.setNoih(Integer.valueOf(values[mapMetricsCSV.get("numberOfMethodsInherited")].trim()));
					classe.setNoa(Integer.valueOf(values[mapMetricsCSV.get("numberOfAttributes")].trim()));
					classe.setNopria(Integer.valueOf(values[mapMetricsCSV.get("numberOfPrivateAttributes")].trim()));
					classe.setNoia(Integer.valueOf(values[mapMetricsCSV.get("numberOfAttributesInherited")].trim()));
					classe.setNopuba(Integer.valueOf(values[mapMetricsCSV.get("numberOfPublicAttributes")].trim()));
					
					classe.setNoc(Integer.valueOf(values[mapMetricsCSV.get("numberOfChildren")].trim()));
					classe.setFin(Integer.valueOf(values[mapMetricsCSV.get("fanIn")].trim()));
					classe.setFout(Integer.valueOf(values[mapMetricsCSV.get("fanOut")].trim()));
					classe.setNomo(Integer.valueOf(values[mapMetricsCSV.get("numberOfMethodsOverriden")].trim()));
					classe.setTnoc(Integer.valueOf(values[mapMetricsCSV.get("totalNumberOfChildren")].trim()));
					classe.setWoc(Integer.valueOf(values[mapMetricsCSV.get("weightOfAClass")].trim()));
					classe.setWmc(Integer.valueOf(values[mapMetricsCSV.get("weightedMethodCount")].trim()));
					classe.setIm(values[mapMetricsCSV.get("inheritedMethods")].length() < 254 ? values[mapMetricsCSV.get("inheritedMethods")].trim() : values[mapMetricsCSV.get("inheritedMethods")].substring(0,253));
					classe.setCbc(Integer.valueOf(values[mapMetricsCSV.get("couplingBetweenClasses")].trim()));
					classe.setLcm(Integer.valueOf(values[mapMetricsCSV.get("lackOfCohesionInMethods")].trim()));
					classe.setRfc(Integer.valueOf(values[mapMetricsCSV.get("responseForClass")].trim()));
					classe.setHnl(Integer.valueOf(values[mapMetricsCSV.get("hierarchyNestingLevel")].trim()));
					
					list.add(classe);
					
				}catch (Exception e) {
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!","O arquivo nao correspondeu ao esperado");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
					FacesContext.getCurrentInstance().addMessage(null, msg);
					
					create();
					
					return;
				}
			}
			reader.close();
			
		} catch (IOException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!","O arquivo nao correspondeu ao esperado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			create();
			
			return;
		}
		
		try{
			if(list != null && !list.isEmpty()){
				
				this.sistema.setClasses(list);
				this.sistema.setData(new Timestamp(System.currentTimeMillis()));
				this.sistema.setUsuario(usuario);
				this.sistema.setDisponivel(true);
				
				this.sistemaDAO.persist(this.sistema);
				
				create();
				
				FacesMessage msg = new FacesMessage("O sistema foi cadastrado com sucesso!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
			}else{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!","O arquivo nao correspondeu ao esperado");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			create();
			
			return;
		}
		
	}
	
	public void deleta(){
		
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long id = Long.valueOf(params.get("id"));
		
		this.sistemaDAO.delete(id);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","O sistema foi excluido do sistema permanentemente");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}
	
	public void ocultar(){
		
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long id = Long.valueOf(params.get("id"));
		
		this.sistema = this.sistemaDAO.get(id, null, null).get(0);
		this.sistema.setDisponivel(this.sistema.isDisponivel() ? false : true);
		
		try {
			this.sistemaDAO.persist(this.sistema);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!","O sistema foi tornou-se indisponivel");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		
		
	}
	
	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataModel<?> getTodas() {
		model = new ListDataModel(this.sistemaDAO.get(null,null, null));
		return model;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataModel<?> getDetalhes() {
		model = new ListDataModel(this.sistemaDAO.get(this.sistema.getId(),null,null).get(0).getClasses());
		return model;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public void construirGrafico(String metrica) {

		if (metrica != null && getMetricas().contains(metrica)) {

			try {
				this.powerLow = new CartesianChartModel();
				ChartSeries cs = new ChartSeries();

				cs.setLabel(metrica);

				List<Object[]> o = new ArrayList<Object[]>();

				for (Classe c : sistema.getClasses()) {
					
					if(metrica.equals(Metricas.LOC)){
						o.add(new Object[] { c.getId(), c.getLoc() });
					}else if(metrica.equals(Metricas.CBC)){
						o.add(new Object[] { c.getId(), c.getCbc() });
					}else if(metrica.equals(Metricas.FIN)){
						o.add(new Object[] { c.getId(), c.getFin() });
					}else if(metrica.equals(Metricas.FOUT)){
						o.add(new Object[] { c.getId(), c.getFout() });
					}else if(metrica.equals(Metricas.HNL)){
						o.add(new Object[] { c.getId(), c.getHnl() });
					}else if(metrica.equals(Metricas.LCM)){
						o.add(new Object[] { c.getId(), c.getLcm() });
					}else if(metrica.equals(Metricas.NOA)){
						o.add(new Object[] { c.getId(), c.getNoa() });
					}else if(metrica.equals(Metricas.NOAH)){
						o.add(new Object[] { c.getId(), c.getNoia() });
					}else if(metrica.equals(Metricas.NOC)){
						o.add(new Object[] { c.getId(), c.getNoc() });
					}else if(metrica.equals(Metricas.NOM)){
						o.add(new Object[] { c.getId(), c.getNom() });
					}else if(metrica.equals(Metricas.NOMH)){
						o.add(new Object[] { c.getId(), c.getNoih() });
					}else if(metrica.equals(Metricas.NOMO)){
						o.add(new Object[] { c.getId(), c.getNomo() });
					}else if(metrica.equals(Metricas.PRIA)){
						o.add(new Object[] { c.getId(), c.getNopria() });
					}else if(metrica.equals(Metricas.PRIM)){
						o.add(new Object[] { c.getId(), c.getNoprim() });
					}else if(metrica.equals(Metricas.PUBA)){
						o.add(new Object[] { c.getId(), c.getNopuba() });
					}else if(metrica.equals(Metricas.PUBM)){
						o.add(new Object[] { c.getId(), c.getNopubm() });
					}else if(metrica.equals(Metricas.RFC)){
						o.add(new Object[] { c.getId(), c.getRfc() });
					}else if(metrica.equals(Metricas.TNOC)){
						o.add(new Object[] { c.getId(), c.getTnoc() });
					}else if(metrica.equals(Metricas.WMC)){
						o.add(new Object[] { c.getId(), c.getWmc() });
					}else if(metrica.equals(Metricas.WOC)){
						o.add(new Object[] { c.getId(), c.getWoc() });
					}
					
				}

				Collections.sort(o, new Comparator<Object[]>() {

					@Override
					public int compare(Object[] o1, Object[] o2) {
						if (Long.parseLong(o1[1].toString()) > Long.parseLong(o2[1].toString())) {
							return 1;
						} else if (Long.parseLong(o1[1].toString()) < Long.parseLong(o2[1].toString())) {
							return -1;
						} else {
							return 0;
						}
					}
				});

				int sum = 0;
				Long maior = Long.parseLong(o.get(o.size() - 1)[1].toString());
				Long blocos = maior / 10;
				Long cont = blocos;

				for (Object[] o1 : o) {
					sum++;
					if (Long.parseLong(o1[1].toString()) >= cont) {
						cs.set("" + (cont), sum);
						cont += blocos;
						sum = 0;
					}
				}

				this.powerLow.addSeries(cs);

			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!",e.getLocalizedMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			return;

		}
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Indisponível","Grafico para metrica selecionada indisponível");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}

	public CartesianChartModel getPowerLow() {
		return powerLow;
	}

	public SistemaDAO getSistemaDAO() {
		return sistemaDAO;
	}

	public void setSistemaDAO(SistemaDAO sistemaDAO) {
		this.sistemaDAO = sistemaDAO;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<String> getMetricas() {
		return Arrays.asList(Metricas.ALL_METRICS);
	}

}
