/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import graph.AbstractGraph;
/**
 *
 * @author Diego
 */

public interface Graph{

    /**
     * @param args the command line arguments
     */
    
    public int getSize();
    public Object[] getVertices();
    public int getIndex(Object obj);
    public java.util.List getNeighbors(int v);
    public int getDegree(int v);
    public int[][] getAdjacencyMatrix();
    public void printAdjacencyMatrix();
    public void printEdges();
    public AbstractGraph.Tree dfs(int v);
    public AbstractGraph.Tree bfs(int v);
    
}
