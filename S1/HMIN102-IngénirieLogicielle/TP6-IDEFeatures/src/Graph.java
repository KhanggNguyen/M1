import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
	private ArrayList<Node> listeNode = new ArrayList<Node>();
	private ArrayList<Edge> listeEdge = new ArrayList<Edge>();
	private static Scanner sc;
	
	public Graph(){
		
	}
	
	public ArrayList<Node> getListeNode() {
		return listeNode;
	}
	
	public void addNode(Node n){
		listeNode.add(n);
	}

	public void setListeNode(ArrayList<Node> listeNode) {
		this.listeNode = listeNode;
	}

	public ArrayList<Edge> getListeEdge() {
		return listeEdge;
	}
	
	public void addEdge(Edge e){
		listeEdge.add(e);
	}

	public void setListeEdge(ArrayList<Edge> listeEdge) {
		this.listeEdge = listeEdge;
	}
	
	public Graph createNewGraph(){
		Graph g = new Graph();
		
		System.out.println("Entrez le nombre de node que vous souhaitez ");
		int n = sc.nextInt();
		
		for(int i=0; i<n; i++){
			Node newNode = new Node();
			newNode.createNewNode();
			g.addNode(newNode);
		}
		return g;
	}
	
	public void printInGraphviz(){
		
	}
}
