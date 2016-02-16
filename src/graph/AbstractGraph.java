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
    
    public Object getVertex(int index){
        return vertices[index];
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
    
    public Tree dfs(int v){
        List<Integer> searchOrders = new java.util.ArrayList<Integer>();
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
        List<Integer> searchOrders = new java.util.ArrayList<Integer>();
        int[] parent = new int[vertices.length];
        
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        java.util.LinkedList<Integer> queue = new java.util.LinkedList<Integer>();
        
        boolean[] isVisited = new boolean[vertices.length];
        
        queue.offer(v);
        isVisited[v] = true;
        
        while(! queue.isEmpty()){
            int u = queue.poll();
            searchOrders.add(u);
            for(int w: neighbors[u]){
                if(!isVisited[w]){
                    queue.offer(w);
                    parent[w] = u;
                    isVisited[w] = true;
                }
            }
        }
        
        return new Tree(v, parent, searchOrders);
    }
    
    public class Tree{
        private int root;// the root of the tree
        private int[] parent;//given the vetex contains the parent in the tree
        private java.util.List<Integer> searchOrders;//order in which the vertices were traversed
        
        public Tree(int root, int[] parent, List<Integer> searchOrders){
            this.root = root;
            this.parent = parent;
            this.searchOrders = searchOrders;
        }
        
        public Tree(int root, int[] parent){
            this.root = root;
            this.parent = parent;
        }
        
        public int getRoot(){
            return root;
        }
        
        public int getParent(int v){
            return parent[v];
        }
        
        public List<Integer> getSearchOrders(){
            return searchOrders;
        }
        
        public int getNumberOfVerticesFound(){
            return searchOrders.size();
        }
        
        public java.util.Iterator pathIterator(int v){
            return new PathIterator(v);
        }
        
        public class PathIterator implements java.util.Iterator{
            private java.util.Stack<Integer> stack;
            
            public PathIterator(int v){
                stack = new java.util.Stack<Integer>();
                do{
                    stack.push(v);
                    v = parent[v];
                }while( v != -1);
            }
            
            public boolean hasNext(){
                return ! stack.isEmpty();
            }
            
            public Object next(){
                return vertices[stack.pop()];
            }
            
            public void remove(){
                //do nothing;
            }
            
        }
        
        public void printPath(int v){
            java.util.Iterator iter = pathIterator(v);
            System.out.print("A path from " + vertices[root] + " to " + vertices[v] + ": ");
            while( iter.hasNext()){
                System.out.print(iter.next() + " ");
            }
            
        }
        
        public void printTree(){
            System.out.println("Root is: " + vertices[root]);
            System.out.print("Edges: ");
            for(int i = 0; i < parent.length; i = i + 1){
                if(parent[i] != -1){
                    System.out.print("(" + vertices[parent[i]] + ", " + vertices[i] + ") ");
                }
            }
            System.out.println();
        }   
    }
}
