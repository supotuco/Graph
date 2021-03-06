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
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

public class WeightedGraph extends AbstractGraph{
    private PriorityQueue<WeightedEdge>[] queues;
    
    @Override
    public boolean add(Object vertex){
        try{
            for(int i = 0; i < numberOfVertices; i = i + 1){
                if(vertex.equals(vertices[i])){
                    return false;
                }
            }
            if( numberOfVertices < vertices.length){
                for(int i = 0; i < vertices.length; i = i + 1){
                    if(vertices[i] == null){
                        vertices[i] = vertex;
                        queues[i] = new PriorityQueue<WeightedEdge>();
                        numberOfVertices = numberOfVertices + 1;
                        return true;
                    }
                }
                
            }else{
                Object[] tempV = new Object[ vertices.length * 2];
                PriorityQueue<WeightedEdge>[] tempQueue = new PriorityQueue[vertices.length * 2];
                System.arraycopy(vertices, 0, tempV, 0, vertices.length);
                System.arraycopy( queues, 0, tempQueue, 0, vertices.length);
                
                for(int i = vertices.length; i < tempQueue.length;  i = i + 1){
                    tempQueue[i] = new PriorityQueue<WeightedEdge>();
                }
                
                vertices = tempV;
                queues = tempQueue;
                vertices[ numberOfVertices ] = vertex;
                numberOfVertices = numberOfVertices + 1;
                
            }
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    
    @Override
    public boolean remove(Object vertex){
        if(vertex == null){
            return false;
        }
        
        for(int i = 0; i < vertices.length; i = i + 1){
           if( vertices[i] != null && vertex.equals( vertices[i] ) ){
               vertices[i] = null;//remove vertex i
               queues[i] = null;//remove neighbors
               
               for(int j = 0; j < i; j = j + 1){
                   if( vertices[j] != null){
                       removeAll(j,i);//remove all edges from j to i
                   }
               }
               
               for(int j = i + 1; j < vertices.length; j = j + 1){
                   if( vertices[j] != null){
                       removeAll(j,i);//remove all edges from j to i
                   }
               }
               
           }
        }
        return false;
    }
    
    
    @Override
    public boolean add(int u, int v){
        return add(u, v, 1);
    }
    
    public boolean add(int u, int v, int weight){
        WeightedEdge newEdge = new WeightedEdge(u,v, weight);
        
        if( queues[u].contains(newEdge) ){
            return false;
        }else{
            return queues[u].offer(newEdge);
        }
        
    }
    
    @Override
    public boolean remove(int u, int v){
        return remove(u,v,1);
    }
    
    public boolean remove(int u, int v, int weight){
        
        return queues[u].remove( new WeightedEdge(u, v, weight));
    }
    
    public boolean removeAll(int u, int v){
        //Assume that u is a valid index;
        //Else will throw an exception
        try{
            PriorityQueue<WeightedEdge> newQueue = new PriorityQueue<WeightedEdge>();
            
            
            while( ! queues[u].isEmpty() ){
                WeightedEdge temp = queues[u].poll();
                if(  temp.v != v){
                    newQueue.offer(temp);
                }
            }
            
            queues[u] = newQueue;
            
            
            return true;
        }catch(Exception ex){
            return false;
        }
        
    }
    
    public class WeightedEdge extends AbstractGraph.Edge implements Comparable<WeightedEdge>{
        public int weight;
        
        public WeightedEdge(int u, int v, int weight){
            super(u, v);
            this.weight = weight;
        }
        
        public int compareTo(WeightedEdge edge){
            if( weight > edge.weight){
                return 1;
            }else{
                if(weight == edge.weight){
                    return 0;
                }else{
                    return -1;
                }
            }
        }
    }
    
    public WeightedGraph(int[][] edges, Object[] vertices){
        super(edges, vertices);
        createQueues(edges, numberOfVertices);
    }
    
    public WeightedGraph(int[][] edges, int numberOfVertices){
        super(edges, numberOfVertices);
        createQueues(edges, numberOfVertices);
        
    }
    
    public WeightedGraph(List<WeightedEdge> edges, List vertices){
        super((List)edges, vertices);
        createQueues(edges, vertices.size());
        
    }
    
    public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices){
        super( (List)edges, numberOfVertices);
        createQueues(edges, numberOfVertices);
    }
    
    public void createQueues(int[][] edges, int numberOfVertices){
        queues = new PriorityQueue[numberOfVertices];
        for(int i = 0; i < queues.length; i = i + 1){
            queues[i] = new java.util.PriorityQueue();
        }
        
        for(int i = 0; i < edges.length; i = i + 1){
            int u = edges[i][0];
            int v = edges[i][1];
            int weight = edges[i][2];
            
            queues[u].offer(new WeightedEdge(u, v, weight));
        }
    }
    
    public void createQueues(List< WeightedEdge> edges, int numberOfVertices){
        queues = new PriorityQueue[numberOfVertices];
        for(int i = 0; i < queues.length; i = i + 1){
            queues[i] = new java.util.PriorityQueue();
        }
        
        for(WeightedEdge edge: edges){
            queues[edge.u].offer(edge);
        }
    }
    
        public int[][] getAdjacencyMatrix(){
        int[][] adjacencyMatrix = new int[numberOfVertices][numberOfVertices];
        
        int[] indexFunction = new int[vertices.length];
        int rcVal = 0;
        
        
        for(int i = 0; i < indexFunction.length; i = i + 1){
            if(vertices[i] != null){
                indexFunction[i] = rcVal;
                
                rcVal = rcVal + 1;
            }else{
                indexFunction[i] = -1;
                
            }
        }
        
        java.util.PriorityQueue<WeightedEdge>[] queues = deepClone(this.queues);
        
        
        for( int i = 0; i < queues.length; i = i + 1){
            
            if(queues[i] != null){
                while( queues[i].size() > 0 ){
                    WeightedEdge topEdge = queues[i].poll();
                    adjacencyMatrix[ indexFunction[ topEdge.u ] ][ indexFunction[ topEdge.v ] ] = topEdge.weight;
                }
                
            }
            
        }
        
        return adjacencyMatrix;
    }
    
    public void printWeightedEdges(){
        for(int i = 0; i < queues.length; i = i + 1){
            System.out.print("Vertex " + i + ": ");
            for (WeightedEdge edge : queues[i]){
                System.out.print("(" + edge.u + ", " + edge.v + ", " + edge.weight + ") ");
            }
            System.out.println();
        }
    }
    
    public MST getMinimumSpanningTree(){
        for(int i = 0; i < vertices.length; i = i + 1){
            if( vertices[i] != null){
                return getMinimumSpanningTree(i);
            }
        }
        return null;
    }
    
    public MST getMinimumSpanningTree(int startingVertex){
        Set<Integer> T = new HashSet<>();
        //need to guaruntee there is a spanning tree
        AbstractGraph.Tree bfs = super.bfs( startingVertex );
        
        java.util.List searchOrders = bfs.getSearchOrders();
        
        T.add(startingVertex);
        
        int[] parent = new int[numberOfVertices];
        
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        int totalWeight = 0;
        
        PriorityQueue<WeightedEdge>[] queues = deepClone(this.queues);
        
        while(T.size() < searchOrders.size()){
            int v = -1;
            int par = -1;
            int smallestWeight = Integer.MAX_VALUE;
            
            for(int u: T){
                while( !queues[u].isEmpty() && T.contains( queues[u].peek().v ) ){
                    queues[u].remove();
                }
                
                if(queues[u].isEmpty()){
                    continue;
                }
                
                WeightedEdge edge = queues[u].peek();
                
                if(edge.weight < smallestWeight){
                    v = edge.v;
                    par = u;
                    smallestWeight = edge.weight;
                    //parent[v] = u;
                }
            }
            
            parent[v] = par;
            T.add(v);
            totalWeight  = totalWeight + smallestWeight;
        }
        
        return new MST(startingVertex, parent, totalWeight);
        
    }
    
    private PriorityQueue<WeightedEdge>[] deepClone( PriorityQueue<WeightedEdge>[] queues){
        PriorityQueue<WeightedEdge>[] copiedQueues = new PriorityQueue[queues.length];
        
        for(int i = 0; i < queues.length; i = i + 1){
            if(queues[i] != null){
                copiedQueues[i] = new PriorityQueue<WeightedEdge>();
            
                for(WeightedEdge e: queues[i]){
                    copiedQueues[i].add(e);
                }
            }else{
                copiedQueues[i] = null;
            }
            
        }
        
        return copiedQueues;
    }
    
    public class MST extends Tree{
        private int totalWeight;
        
        public MST(int root, int[] parent, int totalWeight){
            super(root,parent);
            
            this.totalWeight = totalWeight;
        }
        
        public int getTotalWeight(){
            
            return totalWeight;
        }
    }
    
    
    
    public ShortestPathTree getShortestPath(int sourceVertex){
        Set<Integer> T = new HashSet<Integer>();
        
        T.add(sourceVertex);
        
        int[] parent = new int[numberOfVertices];
        
        parent[sourceVertex] = -1;
        
        int[] costs = new int[numberOfVertices];
        for(int i = 0; i < costs.length; i = i + 1){
            costs[i] = Integer.MAX_VALUE;
        }
        
        costs[sourceVertex] = 0;
        
        PriorityQueue<WeightedEdge>[] queues = deepClone(this.queues);
        
        while(T.size() < numberOfVertices){
            int v = -1;
            int smallestCost = Integer.MAX_VALUE;
            for(int u: T){
                while( ! queues[u].isEmpty() && T.contains(queues[u].peek().v)){
                    queues[u].remove();
                }
                
                if(queues[u].isEmpty()){
                    continue;
                }
                
                WeightedEdge e = queues[u].peek();
                
                if(costs[u] + e.weight < smallestCost){
                    v = e.v;
                    smallestCost = costs[u] + e.weight;
                    parent[v] = u;
                }
            }
            
            T.add(v);
            costs[v] = smallestCost;
                
            
        }
        
        return new ShortestPathTree(sourceVertex, parent, costs);
    }
    
    public class ShortestPathTree extends Tree{
        private int[] costs;
        
        public ShortestPathTree(int source, int[] parent, int[] costs){
            super(source, parent);
            this.costs = costs;
        }
        
        public int getCost(int v){
            return costs[v];
        }
        
        
        public void printAllPaths(){
            System.out.println("All shortest paths from " + vertices[getRoot()] + " are: ");
            for ( int i = 0; i < costs.length; i = i + 1){
                if(this.getParent(i) != -1 || i == this.getRoot() ){
                    printPath(i);
                    System.out.println("(cost: " + costs[i] + ")");
                }
            }
        }   
    }
    
    public MST getMinimumSpanningTreeKruskal(){
        //assumes there is one connected component to get a tree
        //otherwise returns a forrest
        PriorityQueue<WeightedEdge> edgeList = new PriorityQueue<>();
        java.util.Set<Integer> vertexList = new java.util.HashSet();
        
        java.util.LinkedList<Integer>[] neighbors = new java.util.LinkedList[vertices.length];
        
        for(int i = 0; i < neighbors.length; i = i + 1){
            if(vertices[i] != null){
                neighbors[i] = new java.util.LinkedList<>();
            }
        }
        
        int[] parent = new int[vertices.length];
        
        for(int i = 0; i < parent.length; i = i + 1){
            parent[i] = -1;
        }
        
        int totalWeight = 0;
        
        java.util.Map<Integer, Integer> verticesToComponent = new java.util.HashMap<>();//to try to group vertices into components
        
        
        PriorityQueue<WeightedEdge>[] queues = deepClone(this.queues); 
        for(int i = 0; i < vertices.length; i = i + 1){
            if( vertices[i] != null){
                while( queues[i].size() > 0){
                    edgeList.offer( queues[i].poll() );
                }
            }
        }//after this edgeList has all the edges ordered by size from smallest to larges
        
        int root = -1;
        
        int islandCount = 0;
        while( edgeList.size() > 0 ){
            WeightedEdge smallestEdge = edgeList.poll();
            if( smallestEdge.v < smallestEdge.u){
                int temp = smallestEdge.u;
                smallestEdge.u = smallestEdge.v;
                smallestEdge.v = temp;
            }//make all edges from u to v
            
            if( vertexList.contains(smallestEdge.u) ){
                if( vertexList.contains( smallestEdge.v ) ){
                    //check for a cycle
                    
                    int root1 = verticesToComponent.get( smallestEdge.u );
                    int root2 = verticesToComponent.get( smallestEdge.v );
                    
                    while( root1 != verticesToComponent.get(root1).intValue()){
                        root1 = verticesToComponent.get(root1);
                    }
                    
                    while( root2 != verticesToComponent.get(root2).intValue()){
                        root2 = verticesToComponent.get(root2);
                    }
                    
                    if( root1 != root2 ) {
                        //not a cycle
                        root = Math.max(root1, root2) ;
                        neighbors[smallestEdge.u].add(smallestEdge.v);
                        neighbors[smallestEdge.v].add(smallestEdge.u);
                        
                        verticesToComponent.put( root1, root );
                        verticesToComponent.put( root2, root );
                        totalWeight = totalWeight + smallestEdge.weight;
                    }//else do nothing cycle detected
                    
                }else{//vertexList contains u but not v
                    vertexList.add( smallestEdge.v );
                    verticesToComponent.put( smallestEdge.v, verticesToComponent.get(smallestEdge.u) );
                    totalWeight = totalWeight + smallestEdge.weight;
                    neighbors[smallestEdge.u].add(smallestEdge.v);
                    neighbors[smallestEdge.v].add(smallestEdge.u);
                }
            }else{
                if( vertexList.contains ( smallestEdge.v ) ){
                    //contains v but not u
                    neighbors[smallestEdge.u].add(smallestEdge.v);
                    neighbors[smallestEdge.v].add(smallestEdge.u);
                    vertexList.add( smallestEdge.u );
                    verticesToComponent.put( smallestEdge.u, verticesToComponent.get( smallestEdge.v ) );
                    totalWeight = totalWeight + smallestEdge.weight;
                }else{
                    //need to create a new component
                    vertexList.add( smallestEdge.u);
                    vertexList.add( smallestEdge.v);
                    neighbors[smallestEdge.u].add(smallestEdge.v);
                    neighbors[smallestEdge.v].add(smallestEdge.u);
                    
                    verticesToComponent.put( smallestEdge.u, smallestEdge.v);
                    verticesToComponent.put( smallestEdge.v, smallestEdge.v );
                    
                    totalWeight = totalWeight + smallestEdge.weight;
                }
            }
            
        }
        
        java.util.LinkedList<Integer> queue = new java.util.LinkedList<>();
        
        queue.offer(root);
        
        while( queue.size() > 0 ){
            int fVal = queue.poll();
            
            while( neighbors[fVal].size() > 0){//breadth first search
                int tempV = neighbors[fVal].removeFirst();//get the child
                neighbors[tempV].remove(new Integer(fVal));//remove the link to the parent
                queue.offer(tempV);//put the child to be proccessed
                parent[ tempV ] = fVal;//record the parent
            }
        }
        
        return new MST(root, parent, totalWeight);
        
    }
    
    public MST getMinimumSpanningTreeMatrix(){
        for(int i = 0; i < vertices.length; i = i + 1){
            if( vertices[i] != null){
                return getMinimumSpanningTreeMatrix(i);
            }
        }
        return null;
    }
    
    public MST getMinimumSpanningTreeMatrix(int sourceVertex){
        int[] parent = new int[vertices.length];
        int totalWeight = 0;
        
        int[] indexFunction = new int[vertices.length];
        
        int rcVal = 0;
        
        java.util.HashSet<Integer> seenBefore = new java.util.HashSet<>();
        java.util.HashSet<Integer> notSeen = new java.util.HashSet<>();
        
        for(int i = 0; i < indexFunction.length; i = i + 1){
            if(vertices[i] != null){
                indexFunction[i] = rcVal;
                rcVal = rcVal + 1;
                notSeen.add(i);
            }else{
                indexFunction[i] = -1;
            }
            parent[i] = -1;
        }
        
        int[][] adjMatrix = getAdjacencyMatrix();
        
        notSeen.remove(new Integer(sourceVertex));
        seenBefore.add(new Integer(sourceVertex));
        
        while( notSeen.size() > 0 ){
            java.util.Iterator<Integer> iter = notSeen.iterator();
            int minVertex = -1;
            int minParent = -1;
            int minDistance = -1;
            while( iter.hasNext() && minParent < 0){//finds first vertex connected o current graph
                minVertex = iter.next();
                minParent = closestParent( seenBefore, minVertex, adjMatrix, indexFunction);
            }
            
            if(minParent < 0 ){
                break;
            }
            
            minDistance = adjMatrix[ indexFunction[minParent] ][ indexFunction[minVertex] ];
            
            while( iter.hasNext() ){//finds closest vertex connected to graph
                int tempVertex = iter.next();
                int tempParent = closestParent( seenBefore, tempVertex, adjMatrix, indexFunction);
                
                if( tempParent < 0 ){
                    continue;
                }
                
                int tempD = adjMatrix[ indexFunction[ tempParent] ][ indexFunction[ tempVertex ] ];
                
                if( tempD < minDistance && tempD > 0){
                    minVertex = tempVertex;
                    minDistance = tempD;
                    minParent = tempParent;
                }
            }
            
            notSeen.remove(minVertex);
            seenBefore.add(minVertex);
            totalWeight = totalWeight + minDistance;
            parent[minVertex] = minParent;
        }
        
        
        return new MST(sourceVertex ,parent, totalWeight);
    }
    
    public ShortestPathTree getShortestPathMatrix(int sourceVertex){
        int[] parent = new int[vertices.length];
        int[] costs = new int[vertices.length];
        
        java.util.HashSet<Integer> toBeProcessed = new java.util.HashSet<>();
        java.util.HashSet<Integer> seenBefore = new java.util.HashSet<>();
        
        for(int i = 0; i < vertices.length; i = i + 1){
            parent[i] = -1;
            costs[i] = -1;
        }
        
        int[] indexFunction = new int[vertices.length];
        
        int rcVal = 0;
        
        for(int i = 0; i < indexFunction.length; i = i + 1){
            if(vertices[i] != null){
                indexFunction[i] = rcVal;
                rcVal = rcVal + 1;
            }else{
                indexFunction[i] = -1;
            }
            parent[i] = -1;
        }
        
        int[][] adjMatrix = getAdjacencyMatrix();
        
        
        costs[sourceVertex] = 0;
        
        toBeProcessed.add(sourceVertex);
        
        while( toBeProcessed.size() > 0){
            java.util.Iterator<Integer> iter = toBeProcessed.iterator();
            int minVertex = iter.next();
            
            while( iter.hasNext() ){
                int tempV = iter.next();
                
                if( costs[ tempV ] < costs[minVertex] ){
                    minVertex = tempV;
                }
            }//now tempV has the vertex with the mimimal cost
            
            toBeProcessed.remove(minVertex);
            seenBefore.add(minVertex);
            
            for(int i = 0; i < vertices.length; i = i + 1){
                if( indexFunction[i] > -1 && !seenBefore.contains(i) && adjMatrix[ indexFunction[ minVertex ] ][ i ] > 0 ){
                    toBeProcessed.add(i);
                }
                
                
                
            }
            //need to update previous vertices
            
            for(Integer element: toBeProcessed ){
                if( adjMatrix[ indexFunction[ minVertex ] ][ element ] > 0 ){
                    //update the costs
                    if( costs[minVertex] + adjMatrix[ indexFunction[ minVertex ] ][ element ] < costs[ element ] || costs[ element ] < 0){
                        costs[element ] = costs[minVertex] + adjMatrix[ indexFunction[ minVertex ] ][ element ];
                        parent[ element ] = minVertex;
                    }
                }
            }
            
        }
        
        return new ShortestPathTree(sourceVertex, parent, costs);
    }
    
    private int closestParent( java.util.Set<Integer> set, int vertex, int[][] adjMatrix, int[] indexFunction){
        int parent = -1;
        int minD = Integer.MAX_VALUE;
        boolean returnValue = false;
        
        for(Integer element: set){
            if( 0 < adjMatrix[ indexFunction[element] ][ indexFunction[vertex] ] &&//must contain an edge
                    adjMatrix[ indexFunction[element] ][ indexFunction[vertex] ] < minD ){
                minD = adjMatrix[indexFunction[element]][indexFunction[vertex]];
                parent = element;
                returnValue = true;
            }
        }
        
        if(returnValue){
            return parent;
        }else{
            return -1;
        }
    }
}
