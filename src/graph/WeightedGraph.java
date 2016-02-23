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
        return getMinimumSpanningTree(0);
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
            copiedQueues[i] = new PriorityQueue<WeightedEdge>();
            
            for(WeightedEdge e: queues[i]){
                copiedQueues[i].add(e);
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
                printPath(i);
                System.out.println("(cost: " + costs[i] + ")");
            }
        }   
    }
}
