package it.polito.tdp.genes.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;


public class Model {
	
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private List<Integer> vertici;
	private List<Adiacenza> archi;
	private GenesDao dao= new GenesDao();
	private List<Integer> best;
	
	
	public Model() {
		this.vertici= new LinkedList<Integer>(this.dao.getVertici());
		this.archi= new LinkedList<Adiacenza>();
	}
	
	
	public void creaGrafo() {
		
		this.grafo= new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		for(Integer i1: this.vertici) {
			for(Integer i2: this.vertici) {
				if(i1!=i2){	
						Adiacenza a= this.dao.getArchi(i1, i2);
						if(!this.grafo.containsEdge(a.getChrom1(), a.getChrom2()) && a.getChrom1()!=null 
								&& a.getChrom2()!=null && a.getChrom1()!=0 && a.getChrom2()!=0) {
							Graphs.addEdgeWithVertices(this.grafo, a.getChrom1(), a.getChrom2(), a.getPeso());
							this.archi.add(a);
					}
					
				}
			}
		}
		
	}
	
	public String nVertici() {
		return "Grafo creato!"+"\n"+"#verici: "+ this.grafo.vertexSet().size()+"\n";
	}
	
	public String nArchi() {
		return "#archi: "+ this.grafo.edgeSet().size()+"\n";
	}
	
	public Double pesoMin() {
		
		double min=9999999;
		DefaultWeightedEdge arcoMin=null;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)<min) {
				min= this.grafo.getEdgeWeight(e);
				arcoMin= e;
			}
		}
		 
		return this.grafo.getEdgeWeight(arcoMin);
		
	}
	
	public String sPesoMin() {
		
		return  "peso minimo: " +this.pesoMin();
		
	}

	
	public Double pesoMax() {
		double max=0;
		DefaultWeightedEdge arcoMax=null;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>max) {
				max= this.grafo.getEdgeWeight(e);
				arcoMax= e;
			}
		}

		return this.grafo.getEdgeWeight(arcoMax);
		
	}
	
	public String sPesoMax() {
		
		return  "peso massimo: " +this.pesoMax();
	}
	
	
	public String nArchiMaggS(double s) {
		int cnt=0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) > s) {
				cnt++;
			}
		}
		return "Il numero di archi con peso maggiore di s è "+cnt;
	}
	
	public String nArchiMinS(double s) {
		int cnt=0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e) < s) {
				cnt++;
			}
		}
		return "Il numero di archi con peso minore di s è "+cnt;
	}
	
	public List<Integer> camminoCromosomi(double s){
		List<Adiacenza> parziale= new LinkedList<Adiacenza>();
		this.best=new LinkedList<Integer>();
		double somma=0;
		ricerca(parziale, somma, s);
		return best;
		
	}


	private void ricerca(List<Adiacenza> parziale, double somma, double s) {
		double somma1=0;
		for(Adiacenza a: parziale) {
			somma1 += a.getPeso();
		}
		if(somma1> somma) {
			somma=somma1;
			List<Integer> par= new LinkedList<Integer>();
			for(Adiacenza ai: parziale) {
				par.add(ai.getChrom1());
				par.add(ai.getChrom2());
			}
			this.best=new LinkedList<Integer>(par);
		}
		for(Adiacenza aa: this.archi) {
			if(aa.getPeso()>s && !(parziale.contains(aa))) {
				parziale.add(aa);
				ricerca(parziale, somma, s);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
		
	}
	public String getCammino(double ss) {
		String s="";
		for(Integer i: this.camminoCromosomi(ss)) {
			s+=i+" ";
		}
		return s;
	}

}