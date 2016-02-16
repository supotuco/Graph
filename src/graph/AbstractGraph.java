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
import graph.Edge;

public abstract class AbstractGraph implements Graph{
    
    protected Object[] vertices;
    protected java.util.LinkedList[] neighbors;
    
    protected AbstractGraph( int[][] edges, Object[] vertices){
        this.vertices = vertices;
        createAdjacencyLists(edges, vertices.length);
    }
    
    protected AbstractGraph( List<Edge> edges, List vertices){
        this.vertices = vertices.toArray();
        createAdjacencyLists(edges, vertices.size());
        
    }
    
    protected AbstractGraph(int[][] edges, int numerOfVertices){
        
    }
    
    
    public static class Tree{
        
    }
    
    public static class PathIterator implements java.util.Iterator{
        
    }
}
