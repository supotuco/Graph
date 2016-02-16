/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import graph.Graph;
/**
 *
 * @author Diego
 */

import java.util.List;


public abstract class AbstractGraph implements Graph{
    
    protected Object[] vertices;
    protected java.util.List<Integer>[] neighbors;
    
    protected AbstractGraph( int[][] edges, Object[] vertices){
        this.vertices = vertices;
        createAdjacencyLists(edges, vertices.length);
    }
    
    protected AbstractGraph( List<Edge> edges, List vertices){
        this.vertices = vertices.toArray();
        createAdjacencyLists(edges, vertices.size());
        
    }
    
    protected AbstractGraph( List<Edge> edges, int numberOfVertices){
        vertices = new Integer[numberOfVertices];
        for(int i = 0; i < numberOfVertices; i = i + 1){
            vertices[i] = new Integer(i);
        }
        
        createAdjacencyLists(edges, numberOfVertices);
    }
    
    protected AbstractGraph(int[][] edges, int numberOfVertices){
        vertices = new Integer[numberOfVertices];
        for(int i = 0; i < numberOfVertices; i = i + 1){
            vertices[i] = new Integer(i);
        }
        
        createAdjacencyLists(edges, numberOfVertices);
    }
    
    private void createAdjacencyLists(int[][] edges, int numberOfVertices){
        neighbors = new java.util.LinkedList[numberOfVertices];
        for(int i = 0; i < numberOfVertices; i = i + 1){
            neighbors[i] = new java.util.LinkedList<Integer>();
        }
        
        for(int i = 0; i < edges.length; i = i + 1){
            int u = edges[i][0];
            int v = edges[i][1];
                    neighbors[u].add(v);
        }
    }
    
    private void createAdjacencyLists( List<Edge> edges, int numberOfVertices){
        neighbors = new java.util.LinkedList[numberOfVertices];
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            neighbors[i] = new java.util.LinkedList<Integer>();
        }
        
        for(Edge edge: edges){
            neighbors[edge.u].add(edge.v);
        }
    }
    
    public int getSize(){
        return vertices.length;
    }
    
    public Object[] getVertices(){
        
        return vertices;
    }
    
    public int getIndex(Object vertex){
        for(int i = 0; i < getSize(); i = i + 1){
            if( vertex.equals(vertices[i])){
                return i;
            }
        }
        return - 1;
    }
    
    public java.util.List getNeighbors(int v){
        return neighbors[v];
    }
    
    public int getDegree(int v){
        return neighbors[v].size();
    }
    
    public int[][] getAdjacencyMatrix(){
        int[][] adjacencyMatrix = new int[getSize()][getSize()];
        
        for( int i = 0; i < neighbors.length; i = i + 1){
            for(int j = 0; j < neighbors[i].size(); j = j + 1){
                int v = neighbors[i].get(j);
                adjacencyMatrix[i][v] = 1;
            }
        }
        
        return adjacencyMatrix;
    }
    
    public void printAdjacencyMatrix(){
        int[][] adjacencyMatrix = getAdjacencyMatrix();
        
        for(int i = 0; i < adjacencyMatrix.length; i = i + 1){
            for(int j = 0; j < adjacencyMatrix[0].length; j = j + 1){
                System.out.print(adjacencyMatrix[i][j] + " ");
            }System.out.println();
        }
    }
    
    public void printEdges(){
        for(int u = 0; u < neighbors.length; u = u + 1){
            System.out.print("Vertex " + u + ":");
            for(int j = 0; j < neighbors[u].size(); j = j + 1){
                System.out.print("(" + u +  ", "  + neighbors[u].get(j) + ")" );
                
            }System.out.println();
        }
    }
    
    public class Edge {
        int u;
        int v;
        
        public Edge(int u, int v){
            this.u = u;
            this.v = v;
        }
    }
    
    public Tree dvs(int v){
        List<Integer> searchOrders = new ArrayList<Integer>();
        int[] parent = new int[vertices.length];
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        boolean[] isVisited = new boolean[vertices.length];
        
        dfs( v, parent, searchOrders, isVisited);
        
        return new Tree(v, parent, searchOrders);
        
    }
    
    private void dfs(int v, int[] parent, List<Integer> searchOrders, boolean[] isVisited){
        searchOrders.add(v);
        isVisited[v] = true;
        
        for(int i: neighbors[v]){
            if(! isVisited[i]){
                parent[i] = v;
                dfs(i, parent, searchOrders, isVisited);
            }
        }
    }
    
    public Tree bfs(int v){
        List<In
    }
    
    
    
    public static class Tree{
        
    }
    
    public static class PathIterator implements java.util.Iterator{
        
    }
}
