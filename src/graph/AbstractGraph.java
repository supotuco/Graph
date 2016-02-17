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
    
    public java.util.List getConnectedComponentObject(){
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
    
    public java.util.List<Integer> getConnectedComponentInt(){
        java.util.List<Integer> componentRoot = new java.util.ArrayList();
        
        boolean[] seenBefore = new boolean[vertices.length];
        
        for(int i = 0; i < vertices.length; i = i + 1){
            if( !seenBefore[i]){
                AbstractGraph.Tree tempTree = bfs(i);// create a spanning tree with root i
                componentRoot.add(i);// add it to the list of root elements
                seenBefore[i] = true;
                java.util.List<Integer> searchOrders = tempTree.getSearchOrders();
                for(int j = 0; j < searchOrders.size(); j = j + 1){//indicates that they have appeared in a spanning tree;
                    seenBefore[searchOrders.get(j)] = true;
                }
            }
        }
            
        
        return componentRoot;
    }
    
    public java.util.List getShortestPath(int u, int v){
        java.util.List pathList = new java.util.ArrayList();
        
        if( u == v){
            pathList.add(u);
            return pathList;
        }
        
        Tree uTree = this.bfs(u);
        
        java.util.Iterator iter = uTree.pathIterator(v);
        
        while( iter.hasNext()){
            pathList.add(iter.next());
        }
        
        
        return pathList;
    }
    
    public java.util.List getPathDFS(int u, int v){
        
        java.util.List pathList = new java.util.ArrayList();
        
        if( u == v){
            pathList.add(u);
            return pathList;
        }
        
        Tree uTree = this.dfs(u);
        
        java.util.Iterator iter = uTree.pathIterator(v);
        
        while( iter.hasNext()){
            pathList.add(iter.next());
        }
        
        
        return pathList;
    }
    
    
    public boolean isDirected(){
        //the graph is considered undircted if for every edge {u,v} there is an edge {v,u}
        //directed otherwise
        //returns true if it is directed
        //false otherwise
        boolean unDirected = true;
        
        for(int i = 0; i < vertices.length; i = i + 1){
            for(int j = 0; j < neighbors[i].size(); j = j + 1){
                if(neighbors[i].get(j) > i){// to speed up assume that v > u  if uv implies vu for all u,v then true
                        //u is i
                        //neighbors[i].get(j) is the edge from u to v
                    int v = neighbors[i].get(j);
                        //check if the reverse edge exists
                    unDirected = unDirected && containsEdge(v,i);
                    // we know uv is true so vu must be true for undirected
                    // this means that uv and vu is always true
                }
                
            }
        }
        
        return !unDirected;
    }
    
    private boolean containsEdge(int start, int end){
        //will see if there is an edge from start to end;
        for(int i = 0; i <  neighbors[start].size(); i = i + 1){
            if(neighbors[start].get(i) == end){
                return true;
            }
        }
        return false;
    }
    
    public boolean isCyclicDirected(){
        boolean[] seenBefore = new boolean[vertices.length];
        
        
        for(int i = 0; i < vertices.length; i = i + 1){
            if(! seenBefore[i]){
                java.util.Queue<Integer> vertexList = new java.util.LinkedList<>();
                vertexList.offer(i);
                
                while( ! vertexList.isEmpty()){
                    int tempV = vertexList.poll();
                    seenBefore[tempV] = true;
                    for(int j = 0; j < neighbors[tempV].size(); j = j + 1){
                        if( seenBefore[ neighbors[tempV].get(j) ]){
                            return true;
                        }else{
                            vertexList.offer( neighbors[tempV].get(j) );
                            
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    
    
    public boolean isCyclicUndirected(){
        //here we ignore all the edges that decrease in value
        //that is we assume for every edge uv then v >= u
        //modeling a downward flow
        
        int[] parent = new int[vertices.length];// keep track of the sources
                                                // the minimal values in the flow
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        boolean[] seenBefore = new boolean[parent.length];
        
        for(int i = 0; i < vertices.length; i = i + 1){
            if( !seenBefore[i]){
                java.util.LinkedList<Integer> vertexQueue = new java.util.LinkedList<>();
                vertexQueue.offer(i);
                
                while( !vertexQueue.isEmpty()){
                    int tempV = vertexQueue.poll();
                    seenBefore[tempV] = true;
                    
                    for(int j = 0; j < neighbors[tempV].size(); j = j + 1){
                        if( seenBefore[ neighbors[tempV].get(j) ] ){
                            if( neighbors[tempV].get(j) != parent[tempV] ){
                                return true;
                            }
                        }else{
                            vertexQueue.offer( neighbors[tempV].get(j) );
                            parent[ neighbors[tempV].get(j) ] = tempV;
                        }
                    }
                    
                }
                
            }
        }
        
        return false;
    }
    
    public boolean isCyclic(){
        if(isDirected()){
            return isCyclicDirected();
        }else{
            return isCyclicUndirected();
        }
        
    }
    
    public java.util.List<Integer> getACycleDirected(){
        boolean[] seenBefore = new boolean[vertices.length];
        int[] parent = new int[vertices.length];
        for(int i = 0 ; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        for(int i = 0; i < vertices.length; i = i + 1){
            if(! seenBefore[i]){
                java.util.Queue<Integer> vertexList = new java.util.LinkedList<>();
                vertexList.offer(i);
                
                while( ! vertexList.isEmpty()){
                    int tempV = vertexList.poll();
                    seenBefore[tempV] = true;
                    
                    for(int j = 0; j < neighbors[tempV].size(); j = j + 1){
                        if( seenBefore[ neighbors[tempV].get(j) ]){
                            //return the cycle that we found
                            
                            java.util.ArrayList<Integer> tempToRoot = new java.util.ArrayList<>();
                            
                            java.util.ArrayList<Integer> cycle = new java.util.ArrayList<>();
                                                      
                            int travNode = tempV;
                            
                            while(parent[travNode] != -1){
                                tempToRoot.add(travNode);
                                travNode = parent[ travNode ];
                            }
                            
                            tempToRoot.add(travNode);
                            
                            java.util.Collections.<Integer>sort(tempToRoot);
                            
                            travNode = neighbors[tempV].get(j);
                            
                            while( java.util.Collections.<Integer>binarySearch( tempToRoot, travNode ) < 0){
                                cycle.add(travNode);
                                travNode = parent[travNode];
                            }
                            
                            cycle.add(travNode);//travNode contains the least common ancestor
                            
                            java.util.Collections.reverse(cycle);// now list is ordered from LCA - neighbor[tempV]
                            
                            while(tempV != travNode){// loop terminates when tempV = LCA which us guarunteed to happen
                                cycle.add(tempV);
                                tempV = parent[tempV];
                            }
                            
                            return cycle;
                            
                            
                        }else{
                            vertexList.offer( neighbors[tempV].get(j) );
                            parent[ neighbors[tempV].get(j) ] = tempV;
                        }
                    }
                }
            }
        }
        
        return null;
    }
    
    public java.util.List<Integer> getACycleUndirected(){
        int[] parent = new int[vertices.length];// keep track of the sources
                                                // the minimal values in the flow
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        boolean[] seenBefore = new boolean[parent.length];
        
        for(int i = 0; i < vertices.length; i = i + 1){
            if( !seenBefore[i]){
                java.util.LinkedList<Integer> vertexQueue = new java.util.LinkedList<>();
                vertexQueue.offer(i);
                
                while( !vertexQueue.isEmpty()){
                    int tempV = vertexQueue.poll();
                    seenBefore[tempV] = true;
                    
                    for(int j = 0; j < neighbors[tempV].size(); j = j + 1){
                        if( seenBefore[ neighbors[tempV].get(j) ] ){
                            if( neighbors[tempV].get(j) != parent[tempV] ){
                                java.util.ArrayList<Integer> tempToRoot = new java.util.ArrayList<>();
                                
                                
                                java.util.ArrayList<Integer> cycle = new java.util.ArrayList<>();
                                
                                int travNode = tempV;
                                
                                while(parent[travNode] != -1){
                                    tempToRoot.add(travNode);
                                    travNode = parent[ travNode ];
                                }
                            
                                tempToRoot.add(travNode);
                                
                                java.util.Collections.<Integer>sort(tempToRoot);
                            
                                travNode = neighbors[tempV].get(j);
                            
                                while( java.util.Collections.<Integer>binarySearch( tempToRoot, travNode ) < 0){
                                    cycle.add(travNode);
                                    travNode = parent[travNode];
                                }
                            
                                cycle.add(travNode);//travNode contains the least common ancestor
                                
                                java.util.Collections.reverse(cycle);// now list is ordered from LCA - neighbor[tempV]
                            
                                while(tempV != travNode){// loop terminates when tempV = LCA which us guarunteed to happen
                                    cycle.add(tempV);
                                    tempV = parent[tempV];
                                }
                            
                                return cycle;
                            }
                        }else{
                            vertexQueue.offer( neighbors[tempV].get(j) );
                            parent[ neighbors[tempV].get(j) ] = tempV;
                        }
                    }
                    
                }
                
            }
        }
        
        return null;
    }
    
    public java.util.List<Integer> getACycle(){
        if(isDirected()){
            return getACycleDirected();
        }else{
            return getACycleUndirected();
        }
    }
    
}
