package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> graph = new Multigraph<Country, DefaultEdge>(DefaultEdge.class);
	private CountryIdMap countryIdMap ;

	public Model() {
		countryIdMap = new CountryIdMap();
	}

	public void createGraph(int anno) {
		BordersDAO dao = new BordersDAO();
		List<Country> vertex = dao.loadAllCountries(countryIdMap, anno);
		
		//1) ho creato i vertici
		Graphs.addAllVertices(this.graph, vertex);
		
		//2) creo gli archi
		List<Border> edges = dao.getCountryPairs(anno, countryIdMap);
		
		//3) aggiungo gli archi al grafo
		for(Border b:edges) {
			if(vertex.contains(b.getState1()) && vertex.contains(b.getState2())) {
				this.graph.addEdge(b.getState1(), b.getState2());
			}
		}
	}

	public Map<Country, Integer> getCountries() {
		Map<Country, Integer> map = new TreeMap<>();
		
		for(Country c:this.graph.vertexSet()) {
			map.put(c, this.graph.degreeOf(c));
		}
		
		return map ;
	}

	public int getCC() {
		Set<Country> visitati = new HashSet<>();		
		
		DepthFirstIterator<Country, DefaultEdge> dfv = new DepthFirstIterator<>(this.graph);
		
		while(dfv.hasNext()) {
			visitati.add(dfv.next());
		}
		
		return visitati.size();
	}

	public List<Country> getAllCountries() {
		BordersDAO dao = new BordersDAO() ;
		List<Country> list = dao.getCountries(countryIdMap);
		Collections.sort(list);
		return list;
	}

	public List<Country> cercaVicini(String country) {
		
		Country start = countryIdMap.get(country);
		
		if(start != null) {
			
			List<Country> visitati = new ArrayList<Country>();
			
			BreadthFirstIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(this.graph, start);
			
			while(bfv.hasNext()) {
				visitati.add(bfv.next());
			}
			
			Collections.sort(visitati);

			return visitati ;
		}
		
		return null ;
	}

}
