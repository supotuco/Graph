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
        
        parent[v] = -1;
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
    
    public java.util.List getConnectedComponents(){
        java.util.List componentRoot = new java.util.ArrayList();
        
        boolean[] seenBefore = new boolean[vertices.length];
        
        for(int i = 0; i < vertices.length; i = i + 1){
            if( !seenBefore[i]){
                AbstractGraph.Tree tempTree = bfs(i);// create a spanning tree with root i
                componentRoot.add(getVertex(i));// add it to the list of root elements
                seenBefore[i] = true;
                java.util.List<Integer> searchOrders = tempTree.getSearchOrders();
                for(int j = 0; j < searchOrders.size(); j = j + 1){//indicates that they have appeared in a spanning tree;
                    seenBefore[searchOrders.get(j)] = true;
                }
            }
        }
            
        
        return componentRoot;
    }
    
}
