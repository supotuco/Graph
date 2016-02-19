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
    protected int numberOfVertices;
    protected final int INITIAL_SIZE = 4;
    
    
    protected AbstractGraph(){
        int numberOfVertices = 0;
        vertices = new Object[INITIAL_SIZE];
        neighbors = new java.util.LinkedList[INITIAL_SIZE];
        for(int i = 0; i < INITIAL_SIZE; i = i + 1){
            neighbors[i] = new java.util.LinkedList<Integer>();
        }
    }
    
    protected AbstractGraph( int[][] edges, Object[] vertices){
        this.vertices = vertices;
        numberOfVertices = vertices.length;
        createAdjacencyLists(edges, numberOfVertices);
    }
    
    protected AbstractGraph( List<? extends Edge> edges, List vertices){
        this.vertices = vertices.toArray();
        numberOfVertices = vertices.size();
        createAdjacencyLists(edges, vertices.size());
        
    }
    
    protected AbstractGraph( List<? extends Edge> edges, int numberOfVertices){
        this.numberOfVertices = numberOfVertices;
        vertices = new Integer[numberOfVertices * 2];
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
    
    private boolean isSame( java.util.List<Integer> edgeSet1, java.util.List<Integer> edgeSet2){
        for(int i = 0; i < edgeSet1.size(); i = i + 1){
            if( ! edgeSet2.contains( edgeSet1.get(i))){
                return false;
            }
        }
        
        for(int i = 0; i < edgeSet2.size(); i = i + 1){
            if( ! edgeSet1.contains( edgeSet2.get(i))){
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public boolean equals(Object obj){
        if( obj instanceof AbstractGraph ){
            if( ((AbstractGraph)obj).numberOfVertices != this.numberOfVertices){
                return false;
            }
            
            for(int i = 0; i < numberOfVertices; i = i + 1){
                if( !isSame(this.neighbors[i], ((AbstractGraph)obj).neighbors[i]) ){
                    return false;
                }
            }
            
            return true;
            
        }else{
            return false;
        }
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
    
    private void createAdjacencyLists( List<? extends Edge> edges, int numberOfVertices){
        
        neighbors = new java.util.LinkedList[numberOfVertices];
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            neighbors[i] = new java.util.LinkedList<Integer>();
        }
        
        for(Edge edge: edges){
            neighbors[edge.u].add(edge.v);
        }
    }
    
    public int getSize(){
        return numberOfVertices;
    }
    
    public Object getVertex(int index){
        return vertices[index];
    }
    
    public Object[] getVertices(){
        java.util.ArrayList retV = new java.util.ArrayList();
        
        for(int i = 0; i < vertices.length; i = i + 1){
            if( vertices[i] != null ){
                retV.add(vertices[i]);
            }
        }
        return retV.toArray();
    }
    
    public int getIndex(Object vertex){
        if(vertex == null){
            return -1;
        }
        for(int i = 0; i < getSize(); i = i + 1){
            if( vertex.equals(vertices[i])){
                return i;
            }
        }
        return - 1;
    }
    
    public java.util.List getNeighbors(int v){
        if( vertices[v] != null){
            return neighbors[v];
        }
        return null;
    }
    
    public int getDegree(int v){
        if(vertices[v] == null){
            return -1;
        }
        
        return neighbors[v].size();
    }
    
    private int getCol(int v){
        int col = 0;
        for(int i = 0; i < vertices.length && i < v; i = i + 1){
            if( vertices[v] != null){
                col = col + 1;
            }
        }
        
        return col;
    }
    
    public int[][] getAdjacencyMatrix(){
        int[][] adjacencyMatrix = new int[getSize()][getSize()];
        
        int row = 0;
        for( int i = 0; i < neighbors.length; i = i + 1){
            if(neighbors[i] != null){
                
                for(int j = 0; neighbors[i] != null && j < neighbors[i].size(); j = j + 1){
                    int v = neighbors[i].get(j);
                    adjacencyMatrix[row][getCol(v)] = 1;
                }
                row = row + 1;
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
        int[] parent = new int[numberOfVertices];
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        boolean[] isVisited = new boolean[numberOfVertices];
        
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
        int[] parent = new int[numberOfVertices];
        
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        java.util.LinkedList<Integer> queue = new java.util.LinkedList<Integer>();
        
        boolean[] isVisited = new boolean[numberOfVertices];
        
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
        
        boolean[] seenBefore = new boolean[numberOfVertices];
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
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
        
        boolean[] seenBefore = new boolean[numberOfVertices];
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
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
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
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
        boolean[] seenBefore = new boolean[numberOfVertices];
        
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            if(! seenBefore[i]){
                java.util.Queue<Integer> vertexList = new java.util.LinkedList<>();
                vertexList.offer(i);
                seenBefore[i] = true;
                
                while( ! vertexList.isEmpty()){
                    int tempV = vertexList.poll();
                    for(int j = 0; j < neighbors[tempV].size(); j = j + 1){
                        if( seenBefore[ neighbors[tempV].get(j) ]){
                            return true;
                        }else{
                            vertexList.offer( neighbors[tempV].get(j) );
                            seenBefore[ neighbors[tempV].get(j) ] = true;
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
        
        int[] parent = new int[numberOfVertices];// keep track of the sources
                                                // the minimal values in the flow
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        boolean[] seenBefore = new boolean[parent.length];
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            if( !seenBefore[i]){
                java.util.LinkedList<Integer> vertexQueue = new java.util.LinkedList<>();
                vertexQueue.offer(i);
                seenBefore[i] = true;
                
                while( !vertexQueue.isEmpty()){
                    int tempV = vertexQueue.poll();
                    
                    for(int j = 0; j < neighbors[tempV].size(); j = j + 1){
                        if( seenBefore[ neighbors[tempV].get(j) ] ){
                            if( neighbors[tempV].get(j) != parent[tempV] ){
                                return true;
                            }
                        }else{
                            vertexQueue.offer( neighbors[tempV].get(j) );
                            parent[ neighbors[tempV].get(j) ] = tempV;
                            seenBefore[tempV] = true;
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
        boolean[] seenBefore = new boolean[numberOfVertices];
        int[] parent = new int[numberOfVertices];
        for(int i = 0 ; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            if(! seenBefore[i]){
                java.util.Queue<Integer> vertexList = new java.util.LinkedList<>();
                vertexList.offer(i);
                seenBefore[i] = true;
                
                while( ! vertexList.isEmpty()){
                    int tempV = vertexList.poll();
                    
                    
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
                            seenBefore[ neighbors[tempV].get(j)] = true;
                        }
                    }
                }
            }
        }
        
        return null;
    }
    
    public java.util.List<Integer> getACycleUndirected(){
        int[] parent = new int[numberOfVertices];// keep track of the sources
                                                // the minimal values in the flow
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        boolean[] seenBefore = new boolean[parent.length];
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            if( !seenBefore[i]){
                java.util.LinkedList<Integer> vertexQueue = new java.util.LinkedList<>();
                vertexQueue.offer(i);
                seenBefore[i] = true;
                
                while( !vertexQueue.isEmpty()){
                    int tempV = vertexQueue.poll();
                    
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
                            seenBefore[ neighbors[tempV].get(j) ] = true;
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
    
    public boolean isBipartite(){
        int[] color = new int[numberOfVertices];// we will color the vertices with 0, or 1
        //if the color tries to change from 1 to 0 return false;
        int[] parent = new int[numberOfVertices];
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            parent[i] = -1;
            color[i] = -1;
        }
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            if(  color[i] == -1 ){
                java.util.LinkedList<Integer> bfsQueue = new java.util.LinkedList<>();
                bfsQueue.offer(i);
                color[i] = 0;
                
                
                while( ! bfsQueue.isEmpty() ){
                    int topV = bfsQueue.poll();
                    
                    for(int j = 0; j < neighbors[i].size(); j = j + 1){
                        if(  color[ neighbors[i].get(j) ]  == -1){
                            bfsQueue.add( neighbors[i].get(j) );
                            color[neighbors[i].get(j)] = (color[ i ] + 1) % 2;
                            parent[ neighbors[i].get(j) ] = i;
                        }else{
                            if( color[ neighbors[i].get(j) ] != ( color[i] + 1 ) % 2){
                                return false;
                            }
                        }
                    }
                    
                }
                
                
            }
        }
        
        return true;
    }
    
    public List< java.util.HashSet <Integer> > getBipartition(){
        int[] color = new int[numberOfVertices];// we will color the vertices with 0, or 1
        //if the color tries to change from 1 to 0 return false;
        int[] parent = new int[numberOfVertices];
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            parent[i] = -1;
            color[i] = -1;
        }
        
        for(int i = 0; i < numberOfVertices; i = i + 1){
            if(  color[i] == -1 ){
                java.util.LinkedList<Integer> bfsQueue = new java.util.LinkedList<>();
                bfsQueue.offer(i);
                color[i] = 0;
                
                
                while( ! bfsQueue.isEmpty() ){
                    int topV = bfsQueue.poll();
                    
                    for(int j = 0; j < neighbors[i].size(); j = j + 1){
                        if(  color[ neighbors[i].get(j) ]  == -1){
                            bfsQueue.add( neighbors[i].get(j) );
                            color[neighbors[i].get(j)] = (color[ i ] + 1) % 2;
                            parent[ neighbors[i].get(j) ] = i;
                        }else{
                            if( color[ neighbors[i].get(j) ] != ( color[i] + 1 ) % 2){
                                return null;
                            }
                        }
                    }
                    
                }
                
                
            }
        }
        
        java.util.List< java.util.HashSet <Integer> > retV = new java.util.ArrayList<>();
        
        java.util.HashSet<Integer> zeroColor = new java.util.HashSet<>();
        java.util.HashSet<Integer> oneColor = new java.util.HashSet<>();
        
        for(int i = 0; i < color.length; i = i + 1){
            if(color[i] == 0){
                zeroColor.add(i);
            }else{
                oneColor.add(i);
            }
        }
        
        retV.add(zeroColor);
        retV.add(oneColor);
        
        return retV;
    }
    
}
