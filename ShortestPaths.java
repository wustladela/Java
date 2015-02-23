package lab3;
/*
 @author: Adela Gao
 * A class project (cse241, algorithms) implementing Dijkstra's Algorithm
 */
import java.util.ArrayList;


import lab3.Vertex.EdgeIterator;

/**
 * Compute shortest paths in a graph.
 *
 * Constructor computes the actual shortest paths and
 * maintain all the information needed to reconstruct them.  The
 * returnPath() function uses this information to return the
 * appropriate path of edge ID's from the start to the given end.
 *
 * Note that the start and end ID's should be mapped to vertices using
 * the graph's get() function.
 */
class ShortestPaths {
    
    /**
     * Constructor
     */
	private int startId;
	private Multigraph G;
	Integer INF = Integer.MAX_VALUE;
	ArrayList<Edge> prev = new ArrayList<Edge>();
    public ShortestPaths(Multigraph G, int startId) {
    	ArrayList<Handle> staticIndices = new ArrayList<Handle>(G.nVertices());

    	for (int i=0; i<G.nVertices(); i++){
    		staticIndices.add(null);
    		prev.add(null);
    	}
    	this.startId=startId;
    	this.G=G;
    	
    	PriorityQueue<Vertex> vPQ = new PriorityQueue<Vertex>();//PQ of vertex, with everything aligned like handles.
    	boolean scanned[] = new boolean[G.nVertices()];
    	for (int i=0; i<G.nVertices();i++){
    		if(i!=startId){
    			Handle cHandle = vPQ.insert(INF, G.get(i));
    			//staticIndices.set(i, cHandle.getKey());//???????....this will update your static Handles automatically. 
    			staticIndices.set(i, cHandle);
    		}
    		else{
    			Handle cHandle = vPQ.insert(0, G.get(startId));
    			staticIndices.set(i, cHandle);
    			//staticIndices.set(startId, cHandle.getKey());
    		}
    	}
    	//Relaxation
    	while(vPQ.isEmpty()==false){
    		int minKey = vPQ.min();
    		Vertex min = vPQ.extractMin();

    		scanned[min.id()]=true;
    		EdgeIterator adjEdges = G.get(min.id()).adj();//WHAT IS IT DOING HERE?
    		while(adjEdges.hasNext()){//loop thru all neighbor edges of min
    			Edge nextEdge = adjEdges.next();
    			Vertex neighbor = nextEdge.to();//loop thru all neighbors, one by one
    			int weight = nextEdge.weight();//new edge's weight
    			if(scanned[neighbor.id()]==false){
    				int newWeight = minKey+weight;//potentially a new key for this neighbor	

    					int neighborIndex = staticIndices.get(neighbor.id()).getKey(); //
    					boolean decreasedKey = vPQ.decreaseKey(vPQ.getThisNode(neighborIndex).getHandle(), newWeight);//????
    					if(decreasedKey){
    						prev.set(neighbor.id(), nextEdge);
    					}
    				
    			}
    		}
    	}

    }    
 
	/**
     * Calculates the list of edge ID's forming a shortest path from the start
     * vertex to the specified end vertex.
     *
     * @return the array
     */
    public int[] returnPath(int endId) { 

    	if(endId == this.getStartId())return new int[0];
    	//int counter = 0;
    	ArrayList<Integer> ans = new ArrayList<Integer>();
    	
    	Edge fromEdge = prev.get(endId);
    	Vertex fromVertex = fromEdge.from();
    	for (int i=0; i<G.nVertices(); i++){	
    		ans.add(fromEdge.id());

    		if(prev.get(fromVertex.id())!=null){
    			fromEdge = prev.get(fromVertex.id());
        		fromVertex = fromEdge.from();
    		}

    		else{
    			break;

    		}

    	}
    	int[] finalAns = new int[ans.size()];
    	for (int i=0; i<ans.size(); i++){
    		finalAns[ans.size()-i-1]=ans.get(i);
    	}
       return finalAns;
    }
    
    
    public int getStartId() {
 		return startId;
 	}
 	public void setStartId(int startId) {
 		this.startId = startId;
 	}
 	public Multigraph getG() {
 		return G;
 	}
 	public void setG(Multigraph g) {
 		G = g;
 	}
    
}
