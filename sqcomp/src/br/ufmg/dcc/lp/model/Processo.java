package br.ufmg.dcc.lp.model;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.math.stat.clustering.EuclideanIntegerPoint;
import org.apache.commons.math.stat.clustering.KMeansPlusPlusClusterer;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.stromberglabs.cluster.Point;

@SuppressWarnings("serial")
public class Processo implements Serializable,Comparable<Processo>,Comparator<Processo>{
	
	public static Processo getInstance(Set<Sistema> sistemas, Set<String> metricas, int valor){
		
		Processo process = new Processo(sistemas,metricas,valor);
		process.primaryCluster();
		process.secundaryCluster();
		process.groupClusters();
		process.systemsAnalisys();
		process.construirGrafo(sistemas);
		process.getAllMaximalCliques();
		
		return process;
		
	}
	
	private JGraphModelAdapter<Sistema, DefaultEdge> adapter;
	
	private Collection<Set<Sistema>> cliques;
	
	private Dimension dimension;
	
	private SimpleGraph<Sistema, DefaultEdge> graph;
	
	private Object[][] groups;
	
	private JGraph jGraph;
    
    private Object[][] matrizNaoDiscretizada;
    
    private Set<String> metricas;
	private Set<Sistema> sistemas;
	private int valor;

	private KMeansPlusPlusClusterer<EuclideanIntegerPoint> kMeansPlusPlusClusterer;

	private HashMap<Sistema, Map<String,EuclideanIntegerPoint>> mapsClustersPri;

	private HashMap<String, List<org.apache.commons.math.stat.clustering.Cluster<EuclideanIntegerPoint>>> mapClustersSec;

	private Processo(Set<Sistema> sistemas, Set<String> metricas, int valor){
		this.sistemas = sistemas;
		this.metricas = metricas;
		this.valor = valor;
	}
	
	@Override
	public int compareTo(Processo o) {
		if(this.valor == o.valor && this.sistemas.size() == o.sistemas.size() && this.metricas.size() == o.metricas.size()){
			if(this.sistemas.containsAll(o.sistemas) && this.metricas.containsAll(o.metricas)){
				return 0;
			}else{
				if(this.valor > o.valor){
					return 1;
				}else{
					return -1;
				}
			}
		}else if(this.valor > o.valor){
			return 1;
		}else{
			return -1;
		}
	}
	
	public void construirGrafo(Set<Sistema> sistemas){
		
		this.dimension = new Dimension(1700,1500);
		
		graph = new SimpleGraph<Sistema, DefaultEdge>(DefaultEdge.class);
		
		for(Sistema s : sistemas){
			graph.addVertex(s);
		}
		
        ArrayList<Sistema[]> listArestas = new ArrayList<Sistema[]>();
		for(int i = 1; i <= this.sistemas.size(); i++){
			for(int j = i; j <= this.sistemas.size(); j++){
				
				Sistema s1 = (Sistema)matrizNaoDiscretizada[0][j];
				Sistema s2 = (Sistema)matrizNaoDiscretizada[i][0];
				
				try{
					if(i != j && Integer.parseInt(matrizNaoDiscretizada[i][j].toString()) >= valor){

							for(Sistema v : graph.vertexSet()){
								if(v.compareTo(s1) == 0){
									s1 = v;
								}
								if(v.compareTo(s2) == 0){
									s2 = v;
								}
							}
							graph.addEdge(s1,s2);
							listArestas.add(new Sistema[]{s1,s2});
						
					}
				}catch (Exception e) {}
			}
		}
		
		adapter = new JGraphModelAdapter<Sistema,DefaultEdge>(graph);
		
        float angulo = new Float(360) / sistemas.size();
        int raio = (int) (dimension.getHeight() > dimension.getWidth() ? dimension.getWidth() / 2 : dimension.getHeight() / 2);
        float cont = 0;
        for (Sistema v : sistemas) {
            Point p = getPosition(raio, cont);
            int x = (int) p.getX();
            int y = (int) p.getY();
            this.positionVertexAt(v, x, y);
            cont += angulo;
        }

        jGraph = new JGraph(adapter);
        jGraph.setSize((int)dimension.getWidth(),(int)dimension.getHeight());
		jGraph.setGridVisible(true);
		
	}
	
	public JGraphModelAdapter<Sistema, DefaultEdge> getAdapter() {
		return adapter;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getAllMaximalCliques() {
		
        BronKerboschCliqueFinder cliqueFinder = new BronKerboschCliqueFinder(graph);

        cliques = cliqueFinder.getAllMaximalCliques();
 
    }
	
	public Collection<Set<Sistema>> getCliques() {
		return cliques;
	}

	public Dimension getDimension() {
		return dimension;
	}
	
	public SimpleGraph<Sistema, DefaultEdge> getGraph() {
		return graph;
	}
	
	public Object[][] getGroups() {
		return groups;
	}

	public JGraph getjGraph() {
		return jGraph;
	}

	public List<Object[]> getMatrizNaoDiscretizada() {
		List<Object[]> lm = new ArrayList<Object[]>();
		for(Object[] o : matrizNaoDiscretizada){
			lm.add(o);
		}
		return lm;
	}

	public List<String> getMetricas() {
		List<String> l = new ArrayList<String>();
		l.addAll(metricas);
		return l;
	}

	private Point getPosition(int raio, float grau) {
        double x = Math.cos(Math.toRadians(grau)) * raio + raio;
        double y = Math.sin(Math.toRadians(grau)) * (raio * 0.95) + (raio * 0.95);
        return new Point((int) x, (int) y);
    }

	public List<Sistema> getSistemas() {
		List<Sistema> l = new ArrayList<Sistema>();
		l.addAll(sistemas);
		return l;
	}

	public int getValor() {
		return valor;
	}

	public void groupClusters() {
		
		Map<Sistema,Integer> indices = new TreeMap<Sistema, Integer>();

        groups = new Object[sistemas.size() + 1][metricas.size() + 1];
        groups[0][0] = new String("Sistema/Metrica");

        int i = 0;
        for (Sistema s : sistemas) {
            groups[++i][0] = s;
            indices.put(s,i);
        }

        i = 0;
        for(String metrica : metricas){
        	groups[0][++i] = metrica;
        	for (Sistema s : mapsClustersPri.keySet()) {
        		for(int j = 0; j < mapClustersSec.get(metrica).size(); j++){
        			if (mapClustersSec.get(metrica).get(j).getPoints().contains(mapsClustersPri.get(s).get(metrica))) {
                        groups[indices.get(s)][i] = j+1;
                        break;
                    }
        		}
            }
        }
        
    }

	@SuppressWarnings("unchecked")
	private void positionVertexAt(Sistema vertex, int x, int y) {
        DefaultGraphCell cell = adapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
                new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth(),
                bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);

        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        adapter.edit(cellAttr, null, null, null);
    }

	@SuppressWarnings("unchecked")
	public void primaryCluster() {
		
		/*
		 * Algoritmo para clusteriza��o das instancias, testando com Kmeans++ (otimiza��o do Kmeans)
		 */
		kMeansPlusPlusClusterer = new KMeansPlusPlusClusterer<EuclideanIntegerPoint>(new Random());
		
		/*
		 * Estrutura que mapeira o nome do sistema com um outro mapa.
		 * Estou outro mapa, faz o mapeamento das metricas com seus pontos do cluster primario.
		 */
		mapsClustersPri = new HashMap<Sistema, Map<String,EuclideanIntegerPoint>>();
		
		/* 
		 * Inicia-se o calculo para cada sistema
		 */
        for (Sistema s : sistemas) {
        	
        	Map<String, List<EuclideanIntegerPoint>> map = new HashMap<String, List<EuclideanIntegerPoint>>();
        	
        	for(String metrica : metricas){
        		map.put(metrica,new ArrayList<EuclideanIntegerPoint>());
        	}

			for (Classe c : s.getClasses()) {
					
			    for(String metrica : metricas){
		        	map.get(metrica).add(new EuclideanIntegerPoint(new int[]{c.getMetrica(metrica)}));
		        }
			    
			}

			@SuppressWarnings("rawtypes")
			Comparator comparator = new Comparator<EuclideanIntegerPoint>() {

				@Override
				public int compare(EuclideanIntegerPoint p1, EuclideanIntegerPoint p2) {
					if (p1.getPoint()[0] > p2.getPoint()[0]) {
			            return 1;
			        } else if (p1.getPoint()[0] < p2.getPoint()[0]) {
			            return -1;
			        } else {
			            return 0;
			        }
				}
			};
			
			Map<String,EuclideanIntegerPoint> mapAux = new HashMap<String,EuclideanIntegerPoint>();
			
			for(String metrica : metricas){
        		Collections.sort(map.get(metrica),comparator);
        		List<org.apache.commons.math.stat.clustering.Cluster<EuclideanIntegerPoint>> o = kMeansPlusPlusClusterer.cluster(map.get(metrica),5,100);
        		int[] item = new int[5];
        		for(int i = 0 ; i < o.size(); i++){
        			item[i] = o.get(i).getCenter().getPoint()[0];
        		}
        		
        		Arrays.sort(item);
        		mapAux.put(metrica,new EuclideanIntegerPoint(item));
        			
        	}
			
			mapsClustersPri.put(s,mapAux);
			
        }

    }

	public void secundaryCluster() {
        
		mapClustersSec = new HashMap<String,List<org.apache.commons.math.stat.clustering.Cluster<EuclideanIntegerPoint>>>();
		
		for(String metrica : metricas){
			List<EuclideanIntegerPoint> list = new ArrayList<EuclideanIntegerPoint>();
	        for(Sistema s : mapsClustersPri.keySet()){
	        	list.add(mapsClustersPri.get(s).get(metrica));
	        }
	        mapClustersSec.put(metrica,kMeansPlusPlusClusterer.cluster(list,5, 100));
	        list.clear();
		}

    }

	public void systemsAnalisys() {

        matrizNaoDiscretizada = new Object[sistemas.size() + 1][sistemas.size() + 1];

        for (int i = 1; i <= sistemas.size(); i++) {
            for (int j = 1; j <= sistemas.size(); j++) {
                matrizNaoDiscretizada[i][j] = 0;
            }
        }

        matrizNaoDiscretizada[0][0] = "Sistema/Sistema";

        int aux = 0;
        for (Sistema s : sistemas) {
            matrizNaoDiscretizada[++aux][0] = s;
            matrizNaoDiscretizada[0][aux] = s;
        }

        for (int i = 1; i <= sistemas.size(); i++) {
            for (int j = 1; j <= metricas.size(); j++) {
                for (int k = 1; k <= sistemas.size(); k++) {
                    if (groups[i][j] == groups[k][j]) {
                        matrizNaoDiscretizada[i][k] = new Integer(matrizNaoDiscretizada[i][k].toString()) + 1;
                    }
                }
            }
        }
        
    }

	@Override
	public int compare(Processo o1, Processo o2) {
		if(o1.valor == o2.valor && o1.sistemas.size() == o2.sistemas.size() && o1.metricas.size() == o2.metricas.size()){
			if(o1.sistemas.containsAll(o2.sistemas) && o1.metricas.containsAll(o2.metricas)){
				return 0;
			}else{
				if(o1.valor > o2.valor){
					return 1;
				}else{
					return -1;
				}
			}
		}else if(o1.valor > o2.valor){
			return 1;
		}else{
			return -1;
		}
	}

	@Override
	public boolean equals(Object obj) {
		Processo p = (Processo) obj;
		if(this.valor == p.valor && this.sistemas.containsAll(p.sistemas) && this.metricas.containsAll(p.metricas)){
			return true;
		}
		return false;
	}
	
	
	public List<String[]> getInfoPrimaryCluster(String metrica){
		
		
		
		
		return null;
		
	}

	public HashMap<Sistema, Map<String, EuclideanIntegerPoint>> getMapsClustersPri() {
		return mapsClustersPri;
	}

}
