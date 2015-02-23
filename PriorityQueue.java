package lab3;
/*
 @author: Adela Gao
 * Class project (cse241) for implementing Dijkstra's algorithm using Priority Queue.
 */
import java.util.*;

/**
 * A priority queue class supporting operations needed for Dijkstra's algorithm.
 */
class PriorityQueue<T> {

	/**
	 * Constructor
	 */
	// MAGICAL CONSTRUCTOR: parameterize PQNode
	public ArrayList<PQNode<T>> PQ;
	public PQNode<T> thisNode;
	public PQNode<T> getThisNode(int index) {
		return PQ.get(index);
	}

	public void setThisNode(int index, PQNode<T> thisNode) {
		PQ.set(index, thisNode);
	}

	public PriorityQueue() {
		ArrayList<PQNode<T>> PQ = new ArrayList<PQNode<T>>();
		this.PQ = PQ;
	}

	/**
	 * @return true iff the queue is empty.
	 */
	public boolean isEmpty() {
		//PQ.isEmpty()
		
		if (PQ.size() == 0){
			return true;
		}
		return false;

	}

	/**
	 * heapify, maintain heap property compare and possibly swap with parents
	 * you can do with a for loop or recursively heapify: takes care of swapping
	 * PQnode's positions in priority queue AND update the handles I use bubble
	 * up/down!
	 */
	public int bubbleUp(PQNode<T> node) {
		//int ans=0;
		//bubble up
		if (PQ.size() < 3) {
			if (PQ.size() < 2)
				return 0;
			else {//only two nodes
				if (node.getHandle().getKey() == 1
						&& PQ.get(0).getKey() > PQ.get(1).getKey()) { // new node too big
					swap(PQ.get(1), PQ.get(0));
//					System.out.println("swapped the only 2 nodes here");
					return 1;
				}
				return 0;
			}
		}
		int newIndex = node.getHandle().getKey();
		int parent = (newIndex -1) / 2;//will round towards zero; 2.5 --> 2
		while(PQ.get(newIndex).getKey()<PQ.get(parent).getKey()){
				swap(PQ.get(newIndex),PQ.get(parent));
				newIndex = parent;
				parent = (newIndex -1) / 2;
		}
		return newIndex;
	}

	/**
	 * swap two nodes: swap handle (retrieve handle based on int key, and swap
	 * the int keys of two handles swap the relative positions of these two
	 * nodes in the priority queue update the handle! key and value pair are
	 * always together; one PQNode is one node
	 */
	public void swap(PQNode<T> node1, PQNode<T> node2) {
		//now swap handle
		int old1 = node1.getHandle().getKey();
		int old2 = node2.getHandle().getKey();
				node1.getHandle().setKey(old2);//node1 has key2 now
				node2.getHandle().setKey(old1);//node2 has key1 now
		//swap in PQ
		PQ.set(old1, node2);//set node2 to node1's index
		PQ.set(old2, node1);//set node1 to node2's index		
	}

	/**
	 * Insert a (key, value) pair into the queue.
	 *
	 * @return a Handle to this pair so that we can find it later.
	 */
	Handle insert(int key, T value) {
		Handle newHandle = new Handle(PQ.size());// !!new edition
		// System.out.println("162: newHandle.key = "+newHandle.getKey());
		// System.out.println("163: PQ.size() = "+PQ.size());
		PQNode<T> newNode = new PQNode<T>(key, value, newHandle);
		PQ.add(newNode);// now newNode has index as the last one!
		//System.out.println("after insert, before heapify: "+PQ.toString());
		int correctIndex = bubbleUp(newNode);// ??? going to run heapify?
		//newNode.getHandle().setKey(correctIndex);
		//System.out.println("after insert, after heapify: "+PQ.toString());
		return newNode.getHandle();
	}
	/**
	 * @return the min key in the queue.
	 */
	public int min() {
		if (PQ.isEmpty())
			return 0;// should I ???
		PQNode<T> ans = PQ.get(0);
		return ans.getKey();
	}

	/**
	 * Find and remove the smallest (key, value) pair in the queue.
	 *
	 * @return the value associated with the smallest key
	 */
	public T extractMin() {
		if (PQ.isEmpty())
			return null;
		else {
			PQNode<T> minNode = PQ.get(0); 
			T ans = minNode.getValue();
			swap(PQ.get(0), PQ.get(PQ.size()-1));
			//PQ.set(0, PQ.get(PQ.size() - 1));// set the root to be the last node
			//PQ.get(PQ.size() - 1).setHandle(null);// delete Handle
			
			PQ.remove(PQ.size() - 1);// remove the last node
			//System.out.println("134 from PQ: PQ.size = "+PQ.size());
			if(PQ.size()>0 && PQ.get(0)!=null) {
				bubbleDown(PQ.get(0));
			}
			
			// heapify and get the correct index of last//?????do I need to have an int here?
			//System.out.println("correctIndex = "+correctIndex);
			//System.out.println("after bubbleDown: this node key: "+PQ.get(correctIndex).getHandle().getKey()+" size"+PQ.size());
			return ans;
		}
	}
	
	public int bubbleDown(PQNode<T> node){
		int newIndex = node.getHandle().getKey();// Handle's key, the index
		//System.out.println("45 newIndex = "+newIndex);
		if(PQ.size()<3){
			if(PQ.size()<2)return 0;
			if(PQ.get(0).getKey()>PQ.get(1).getKey()){
				swap(PQ.get(0), PQ.get(1));
				//System.out.println("138: swapped only 2 nodes");
				return 1;
			}
		}
		//normal bubble down:
		int left = newIndex * 2 + 1;// left child
		int right = newIndex * 2 + 2;// right child
		int smallest = 0;
		//System.out.println("left = "+left + " right = "+ right +" size = "+PQ.size());
		if ((left < PQ.size())//if newNode is bigger than left child
		&& PQ.get(left).getKey() < PQ.get(newIndex).getKey()) {
		smallest = left;
		}else{
		smallest = newIndex;
		}
		if ((right < PQ.size())//if newNode is bigger than right child
		&& PQ.get(right).getKey() < PQ.get(smallest).getKey()) {
		smallest = right;
		}
		if (smallest != newIndex) {
		//System.out.println("in heapify 112: smallest = " + smallest);
		//System.out.println("in heapify 113: newIndex = " + newIndex);
		swap(PQ.get(newIndex), PQ.get(smallest));
		//System.out.println("114 now smallest = " + smallest);
		bubbleDown(PQ.get(smallest));
		}
		return smallest;
	}

	/**
	 * Decrease the key to the newKey associated with the Handle.
	 *
	 * If the pair is no longer in the queue, or if its key <= newKey, do
	 * nothing and return false. Otherwise, replace the key with newkey, fix the
	 * ordering of the queue, and return true.
	 *
	 * @return true if we decreased the key, false otherwise.
	 */
	public boolean decreaseKey(Handle h, int newKey) {
		if (PQ.isEmpty())
			return false;
		else {		
			int oldKey = PQ.get(h.getKey()).getKey();
			if(newKey > oldKey){
				return false;
			}
			PQ.get(h.getKey()).setKey(newKey);
			if(newKey < oldKey){
				bubbleUp(PQ.get(h.getKey()));
				return true;
			}	
		}
		return false;
	}

	/**
	 * @return the key associated with the Handle.
	 */
	public int handleGetKey(Handle h) {
		if (h.getKey() < PQ.size()) {
			PQNode<T> theNode = PQ.get(h.getKey());
			return theNode.getKey();
		} else
			return -1;// ????
	}

	/**
	 * @return the value associated with the Handle.
	 */
	public T handleGetValue(Handle h) {
		PQNode<T> theNode = PQ.get(h.getKey());
		if (theNode.getValue() != null)
			return theNode.getValue();
		else
			return null;
	}

	/**
	 * Print every element of the queue in the order in which it appears (i.e.
	 * the array representing the heap)
	 * 
	 * @param pqNode
	 */
	public String toString() {
		String ans = "";
		for(int i = 0; i<PQ.size(); i++){
			ans+=PQ.get(i).toString()+", ";
		}
		//String ans = PQ.toString();// ????
		return ans;
	}

	public int size() {
		// TODO Auto-generated method stub
		return PQ.size();
	}
}
