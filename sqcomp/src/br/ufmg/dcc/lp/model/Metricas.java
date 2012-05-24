package br.ufmg.dcc.lp.model;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@SuppressWarnings("serial")
@ManagedBean(name="metricas",eager=true)
@ApplicationScoped
public class Metricas implements Serializable{
		
	public static final String LOC = "Number Of Lines Of Code";
    public static final String NOM = "Number Of Methods";
    public static final String PUBM = "Number Of Public Methods";
    public static final String PRIM = "Number Of Private Methods";
    public static final String NOMH = "Number Of Methods Inherited";
    public static final String NOA = "Number Of Attributes";
    public static final String PUBA = "Number Of Public Attributes";
    public static final String PRIA = "Number Of Private Attributes";
    public static final String NOAH = "Number Of Attributes Inherited";
    
    public static final String FIN = "Fan-In";
    public static final String FOUT = "Fan-Out";
    public static final String NOMO = "Number Of Methods Overriden";
    public static final String NOC  = "Number Of Children";
    public static final String TNOC  = "Total Number Of Children"; 
    public static final String WOC = "Weight Of a Class";
    public static final String WMC = "Weighted Method Count";
    public static final String IM = "Inherited Methods";
    public static final String CBC = "Coupling Between Classes";
    public static final String LCM = "Lack Of Cohesion In Methods";
    public static final String RFC = "Response For Class";
    public static final String HNL = "Hierarchy Nesting Level";
    
    public static final String[] ALL_METRICS = {LOC,NOM,PUBM,PRIM,NOMH,NOA,PUBA,PRIA,NOAH,FIN,FOUT,NOMO,NOC,TNOC,WOC,WMC,CBC,LCM,RFC,HNL};
    
    public Metricas(){}

	public String getLOC() {
		return LOC;
	}

	public String getNOM() {
		return NOM;
	}

	public String getPUBM() {
		return PUBM;
	}

	public String getPRIM() {
		return PRIM;
	}

	public String getNOMH() {
		return NOMH;
	}

	public String getNOA() {
		return NOA;
	}

	public String getPUBA() {
		return PUBA;
	}

	public String getPRIA() {
		return PRIA;
	}

	public String getNOAH() {
		return NOAH;
	}

	public String getFIN() {
		return FIN;
	}

	public String getFOUT() {
		return FOUT;
	}

	public String getNOMO() {
		return NOMO;
	}

	public String getNOC() {
		return NOC;
	}

	public String getTNOC() {
		return TNOC;
	}

	public String getWOC() {
		return WOC;
	}

	public String getWMC() {
		return WMC;
	}

	public String getIM() {
		return IM;
	}

	public String getCBC() {
		return CBC;
	}

	public String getLCM() {
		return LCM;
	}

	public String getRFC() {
		return RFC;
	}

	public String getHNL() {
		return HNL;
	}

    
    

}
