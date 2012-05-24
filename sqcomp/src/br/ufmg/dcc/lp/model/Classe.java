package br.ufmg.dcc.lp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity(name="Classe")
public class Classe implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;

	private String superEntity;

	private int loc;
	private int noa;
	private int nopuba;
	private int nopria;
	private int noia;
	private int nom;
	private int nopubm;
	private int noprim;
	private int noih;
	
	private int fin;
	private int fout;
	private int nomo;
	private int noc;
	private int tnoc;
	private int woc;
	private int wmc;
	private String im;
	private int cbc;
	private int lcm;
	private int rfc;
	private int hnl;
	
	public int getMetrica(String name){
		
		if(Metricas.LOC.equals(name)) return loc;
		if(Metricas.NOA.equals(name)) return noa;
		if(Metricas.PUBA.equals(name)) return nopuba;
		if(Metricas.PRIA.equals(name)) return nopria;
		if(Metricas.NOAH.equals(name)) return noia;
		if(Metricas.NOM.equals(name)) return nom;
		if(Metricas.PUBM.equals(name)) return nopubm;
		if(Metricas.PRIM.equals(name)) return noprim;
		if(Metricas.NOMH.equals(name)) return noih;
		
		if(Metricas.FIN.equals(name)) return fin;
		if(Metricas.FOUT.equals(name)) return fout;
		if(Metricas.NOMO.equals(name)) return nomo;
		if(Metricas.NOC.equals(name)) return noc;
		if(Metricas.TNOC.equals(name)) return tnoc;
		if(Metricas.WOC.equals(name)) return woc;
		if(Metricas.WMC.equals(name)) return wmc;
		if(Metricas.CBC.equals(name)) return cbc;
		if(Metricas.LCM.equals(name)) return lcm;
		if(Metricas.RFC.equals(name)) return rfc;
		if(Metricas.HNL.equals(name)) return hnl;
		
		return 0;
		
	}
	
	
	public Classe() {
		super();
	}
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSuperEntity() {
		return superEntity;
	}
	public void setSuperEntity(String superEntity) {
		this.superEntity = superEntity;
	}
	public int getLoc() {
		return loc;
	}
	public void setLoc(int loc) {
		this.loc = loc;
	}
	public int getNoa() {
		return noa;
	}
	public void setNoa(int noa) {
		this.noa = noa;
	}
	public int getNopuba() {
		return nopuba;
	}
	public void setNopuba(int nopuba) {
		this.nopuba = nopuba;
	}
	public int getNopria() {
		return nopria;
	}
	public void setNopria(int nopria) {
		this.nopria = nopria;
	}
	public int getNoia() {
		return noia;
	}
	public void setNoia(int noia) {
		this.noia = noia;
	}
	public int getNom() {
		return nom;
	}
	public void setNom(int nom) {
		this.nom = nom;
	}
	public int getNopubm() {
		return nopubm;
	}
	public void setNopubm(int nopubm) {
		this.nopubm = nopubm;
	}
	public int getNoprim() {
		return noprim;
	}
	public void setNoprim(int noprim) {
		this.noprim = noprim;
	}
	public int getNoih() {
		return noih;
	}
	public void setNoih(int noih) {
		this.noih = noih;
	}

	@Override
	public String toString() {
		return "Classe [id=" + id + ", name=" + name + ", superEntity="
				+ superEntity + ", loc=" + loc + ", noa=" + noa + ", nopuba="
				+ nopuba + ", nopria=" + nopria + ", noia=" + noia + ", nom="
				+ nom + ", nopubm=" + nopubm + ", noprim=" + noprim + ", noih="
				+ noih + "]";
	}

	public int getFin() {
		return fin;
	}

	public void setFin(int fin) {
		this.fin = fin;
	}

	public int getFout() {
		return fout;
	}

	public void setFout(int fout) {
		this.fout = fout;
	}

	public int getNomo() {
		return nomo;
	}

	public void setNomo(int nomo) {
		this.nomo = nomo;
	}

	public int getNoc() {
		return noc;
	}

	public void setNoc(int noc) {
		this.noc = noc;
	}

	public int getTnoc() {
		return tnoc;
	}

	public void setTnoc(int tnoc) {
		this.tnoc = tnoc;
	}

	public int getWoc() {
		return woc;
	}

	public void setWoc(int woc) {
		this.woc = woc;
	}

	public int getWmc() {
		return wmc;
	}

	public void setWmc(int wmc) {
		this.wmc = wmc;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public int getCbc() {
		return cbc;
	}

	public void setCbc(int cbc) {
		this.cbc = cbc;
	}

	public int getLcm() {
		return lcm;
	}

	public void setLcm(int lcm) {
		this.lcm = lcm;
	}

	public int getRfc() {
		return rfc;
	}

	public void setRfc(int rfc) {
		this.rfc = rfc;
	}

	public int getHnl() {
		return hnl;
	}

	public void setHnl(int hnl) {
		this.hnl = hnl;
	}
	
	

}
