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
    
    @Override
    public Tree dfs(int v){
        java.util.Stack<Integer> vertexStack = new java.util.Stack<>();
        vertexStack.push(v);
        int[] parent = new int[vertices.length];
        
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        boolean[] seenBefore = new boolean[vertices.length];
        java.util.ArrayList<Integer> searchOrder = new java.util.ArrayList<>();
        
        
        while( ! vertexStack.isEmpty() ){
            int topV = vertexStack.pop();
            if(! seenBefore[topV]){
                searchOrder.add(topV);
                seenBefore[topV] = true;
                for(int i = 0; i < neighbors[topV].size(); i = i + 1){
                    if( ! seenBefore[neighbors[topV].get(i)] ){
                        parent[neighbors[topV].get(i)] = topV;
                        vertexStack.push(neighbors[topV].get(i));
                    }
                }
            }
        }
        
        return new Tree(v, parent, searchOrder);
        
    }
    
    
    
}
