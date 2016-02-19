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

public interface Graph{

    /**
     * @param args the command line arguments
     */
    
    public int getSize();
    public Object[] getVertices();
    
    public boolean add(Object vertex);
    public boolean remove(Object vertex);
    public boolean add(int u, int v);
    public boolean remove(int u, int v);
    
    public int getIndex(Object obj);
    public Object getVertex(int index);
    public java.util.List getNeighbors(int v);
    public int getDegree(int v);
    public int[][] getAdjacencyMatrix();
    public void printAdjacencyMatrix();
    public void printEdges();
    public AbstractGraph.Tree dfs(int v);//dfs assmes that v does not have null neighbors
    public AbstractGraph.Tree bfs(int v);//bfs assumes that v does not have null neighbors
    public java.util.List<Integer> getHamiltonianCycle();
    
}
