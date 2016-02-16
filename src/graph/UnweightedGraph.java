/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

/**
 *
 * @author Diego
 */
public class UnweightedGraph extends AbstractGraph{
    public UnweightedGraph(int[][] edges, Object[] vertices){
        super(edges,vertices);
    }
    
    public UnweightedGraph(java.util.List<Edge> edges, java.util.List vertices){
        super(edges,vertices);
    }
    
    public UnweightedGraph(java.util.List<Edge> edges, int numberOfVertices){
        super(edges, numberOfVertices);
    }
    
    public UnweightedGraph(int[][] edges, int numberOfVertices){
        super(edges, numberOfVertices);
    }
    
    public Tree dvs(int v){
        java.util.Stack<Integer> vertexStack = new java.util.Stack<>();
        vertexStack.push(v);
        int[] parent = new int[vertices.length];
    }
    
}
