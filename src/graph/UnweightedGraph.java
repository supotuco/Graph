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
    
    public UnweightedGraph(){
        super();
    }
    
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
    public boolean add(Object vertex){
        try{
            if( numberOfVertices < vertices.length){
                vertices[numberOfVertices] = vertex;
                numberOfVertices = numberOfVertices + 1;
            }else{
                Object[] tempVert = new Object[ 2 * vertices.length];
                java.util.List<Integer>[] tempNeigh = new java.util.LinkedList[ 2 * vertices.length];
                System.arraycopy(vertices, 0, tempVert, 0, vertices.length);
                System.arraycopy(neighbors, 0, tempNeigh, 0, vertices.length);
                
                for( int i = vertices.length; i < tempNeigh.length; i = i + 1){
                    tempNeigh[i] = new java.util.LinkedList<Integer>();
                }
                
                vertices = tempVert;
                vertices[numberOfVertices] = vertex;
                numberOfVertices = numberOfVertices + 1;
                neighbors = tempNeigh;
                
            }
            
            actualSize = actualSize + 1;
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    
    
    @Override
    public boolean remove(Object vertex){
        boolean retV = false;
        for(int i = 0; i < numberOfVertices; i = i + 1){
            if( vertices[i].equals(vertex) ){
                vertices[i] = null;// remove the vertex
                neighbors[i] = null;// remove the neighbors associated to the vertex
                retV = true;
                
                Integer removeObj = new Integer(i);
                
                for(int j = 0; j < i; j = j + 1){
                    while( neighbors[j].remove((Integer)(removeObj)) );
                }
                
                for(int j = i + 1; j < numberOfVertices; j = j + 1){
                    while( neighbors[j].remove((Integer)(removeObj)) );
                }
                break;
            }
        }
        
        return retV;
    }
    
    
    @Override
    public boolean add(int u, int v){
        return neighbors[u].add(new Integer(v));
    }
    
    
    @Override
    public boolean remove(int u, int v){
        try{
            while(neighbors[u].remove(new Integer(v)));
            return true;
        }catch(Exception ex){
            return false;
        }
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
