package graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;


/**
 * 
 * @author node
 *
 * @param <V>
 *     The type of the value that a vertex can store.
 * @param <E>
 *     The type of the value that an edge can store.
 * @param <M>
 *     The type of the value that a message can store.
 *     
 */
public abstract class Vertex<V,E,M> {

	///////////////////////////data of a vertex/////////////////////////////////////////////////
	/*
	 * the graph that this vertex belongs to.
	 */
	private Graph<V,E,M> graph = null;
	/*
	 * The uniqueness of Vertex is determined by the field: vertexID.
	 */
	private int vertexID;
	/*
	 * The data carried by this vertex.
	 */
	private V value;
	/*
	 * outEdges store all the out-going edges of this vertex.
	 * use Hash structure to ensure uniqueness of a edge, but the user-defined E type
	 * should override the equals() and hashCode() function to ensure uniqueness.
	 */
	private Set<Edge<E>> outEdges = new HashSet<Edge<E>>();
    ///////////////////////////data of a vertex/////////////////////////////////////////////////
	
	
    ///////////////////////////state of a vertex/////////////////////////////////////////////////
	/*
	 * the state of a vertex. active or inactive.
	 */
	private boolean active;
    /*
     * The input messages needed when a super step starts. Duplication is allowed.
     */
	private List<Message<M>> messages = new Vector<Message<M>>();
	

	/*
	 * Messages send to this vertex during a super step. should be accessed by the vertex in the next super step.
	 * Before every super step, we assign messagesOfNextSuperStep to message and make messagesOfNextSuperStep empty.
	 */
	private List<Message<M>> messagesOfNextSuperStep = new Vector<Message<M>>(); 
	
    ///////////////////////////state of a vertex/////////////////////////////////////////////////

	
	public Vertex(int vertexID,boolean active,V value)
	{
	    this.vertexID = vertexID;	
	    this.active = active;
	    this.value = value;
	}
	
	void setGraphHandler(Graph<V,E,M> graph)
	{
		this.graph = graph;
	}
	
	boolean addEdge(Edge<E> edge)
	{
		if(!outEdges.contains(edge))
		{
			outEdges.add(edge);
			return true;
		}
		return false;
	}
	
	boolean containsEdge(Edge<E> edge)
	{
		return outEdges.contains(edge);
	}
	
	boolean isActive()
	{
		return active;
	}
	
	void setActive(boolean activeOrNot)
	{
		this.active = activeOrNot;
	}
	
	boolean hasUnprocessedMessage()
	{
		return !messagesOfNextSuperStep.isEmpty();
	}
	
	void swapMessage()
	{
		messages = messagesOfNextSuperStep;
		messagesOfNextSuperStep = new Vector<Message<M>>();
	}
	
	Iterator<Message<M>> getMessages()
	{
		return messages.iterator();
	}
	
	public int getVertexID()
	{
		return this.vertexID;
	}
	//not safe
	public V getVertexValue()
	{
		return this.value;
	}
	//not safe
	public Iterator<Edge<E>> outEdges()
	{
		return outEdges.iterator();
	}

	public int numOfOutEdges()
	{
		return this.outEdges.size();
	}
	

	
	
	abstract protected void compute(Iterator<Message<M>> msgItr);
	protected final V getValue()
	{
		return this.value;
	}
	protected final void setValue(V value)
	{
		this.value = value;
	}
	protected final boolean addNewEdge(int destVertexID,E value)
	{
		return this.addEdge(new Edge<E>(this.vertexID,destVertexID,value));
	}
	protected final Iterator<Edge<E>> getOutEdges()
	{
		return outEdges.iterator();
	}

	protected final void sendMessage(int targetVertex,M messageValue)
	{
		Message<M> message = new Message<M>(targetVertex,messageValue);
		Vertex<V,E,M> v = graph.getVertex(targetVertex);
		v.messagesOfNextSuperStep.add(message);
	}
	protected final void broadcast(M messageValue)
	{
		Map<Integer,Vertex<V,E,M>> vertices = graph.vertices;
		Set<Entry<Integer,Vertex<V,E,M>>> entries = vertices.entrySet();
		for(Entry<Integer,Vertex<V,E,M>> entry: entries)
			entry.getValue().messagesOfNextSuperStep.add(new Message<M>(entry.getKey(),messageValue));
	}
	protected final void voltToHalt()
	{
		active = false;
	}	
	protected final int superStep()
	{
		return graph.superStep();
	}
	protected final int numOfTotalVertices()
	{
		return graph.vertices.size();
	}
	
	
}
