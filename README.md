# Graph
A java interface for graphs along with an abstract class.  Will include an implementation of unweighted and weighted graphs.
Note that edges must be addes symmetrically to get an undirected graph for the abstract class.  
Note adding the same edge twice is possible but will increase space used and make implementation slower.

The following the Graph interface methods

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




This was an exercise from the book, Introduction to Java Programming Comprehensive Edition by Y. Daniel Liang
