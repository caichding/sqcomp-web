package br.ufmg.dcc.lp.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;  

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.ufmg.dcc.lp.model.Processo;
import br.ufmg.dcc.lp.model.Sistema;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@SuppressWarnings("serial")  
@ManagedBean(name = "processManagerBean", eager = true)
@ApplicationScoped
public class ProcessManagerBean implements Serializable {

	private XStream stream;

	private File file;
	private File dir = new File("processos");  

	private Map<String, Processo> mapFiles = new TreeMap<String, Processo>();

	public ProcessManagerBean() throws IOException {
		stream = new XStream(new DomDriver());
		stream.omitField(Sistema.class, "classes");
		stream.omitField(Processo.class, "jGraph");
		stream.omitField(Processo.class, "graph");
		stream.omitField(Processo.class, "adapter");
		stream.omitField(Processo.class, "cliques");
		
		FileInputStream fis = null;
		
		if (dir.exists() || dir.mkdir()) {
			for (File f : dir.listFiles()) {
				try {
					fis = new FileInputStream(f);
					ObjectInputStream is = stream.createObjectInputStream(fis);
					mapFiles.put(f.getName(), (Processo) is.readObject());
					is.close();
				} catch (Exception e) {}
			}
		}
		
		if(fis != null){
			fis.close();
		}

	}

	public synchronized void add(Processo processo)	throws IOException {

		mapFiles.put(processo.hashCode() + ".xml", processo);

		file = new File(dir, processo.hashCode() + ".xml");
		
		ObjectOutputStream out = stream.createObjectOutputStream(new FileOutputStream(file));
		out.writeObject(processo);
		out.close();

	}

	public synchronized Processo getProcesso(int valor, Set<Sistema> sistemas, Set<String> metricas) {
		
		for (Processo p : mapFiles.values()) {
		
			if (p.getValor() == valor && p.getSistemas().containsAll(sistemas) && p.getMetricas().containsAll(metricas)) {
				p.getSistemas().clear();
				p.getSistemas().addAll(sistemas);
				p.construirGrafo(sistemas);
				p.getAllMaximalCliques();
				return p;
			}

		}
		
		return null;
		
	}

}
