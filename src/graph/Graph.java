package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
public class Graph<V,E,M> {
	
    Map<Integer,Vertex<V,E,M>> vertices = new HashMap<Integer,Vertex<V,E,M>>();
    int superStep = 0;

    
    public Graph()
    {
    	
    }
    public void execute(int numOfThreads)
    {
    	/*
    	 * before iteration, that is, in superStep 0, the state of a vertex:
    	 * active: up to how the user program initialize the vertex
    	 * messages: empty
    	 * messagesOfNextSuperStep: empty
    	 */
    	while(true)
    	{
    		superStep ++;
    		/*
    		 * a super step begins.
    		 * collect all the active vertices, prepare the messages for the vertex.
    		 * if a vertex is inactive but it was messaged during the last super step, set the vertex active.
    		 */
    		ArrayList<VertexProgram<V,E,M>> vp_list = new ArrayList<VertexProgram<V,E,M>>(numOfThreads);
    		for(int i=0;i<numOfThreads;i++)
    			vp_list.add( new VertexProgram<V,E,M>() );
    		
    		int counter = 0;
    		Iterator<Integer> itr = vertices.keySet().iterator();
    		while(itr.hasNext())
    		{
    			int vertexID = itr.next();
    			Vertex<V, E, M> v = vertices.get(vertexID);
    			if(v.isActive())
    			{
    				v.swapMessage();
    				vp_list.get(counter++ % numOfThreads).addElement(v);
    			}
    			else if(v.hasUnprocessedMessage())
    			{
    				v.setActive(true);
    				v.swapMessage();
    				vp_list.get(counter++ % numOfThreads).addElement(v);
    			}
    		}
    		if(counter==0)
    			break;
    		
    		ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
    		for(int i=0;i<numOfThreads;i++)
    			executor.execute(vp_list.get(i));
    		executor.shutdown();
        	try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//System.out.println("superstep "+superStep+" ends.");
//        	if( (superStep-1)%3==1 )
//        	{
//        		System.out.println("iteration: "+(superStep-1)/3);
//        		printGraph2();
//        	}
        		
    	}
    }

    
    public Iterator<Vertex<V, E, M>> getVertexIterator()
    {
    	return this.vertices.values().iterator();
    }
    
    Vertex<V, E, M> getVertex(int vertexID)
    {
    	if(vertices.containsKey(vertexID))
    		return vertices.get(vertexID);
    	return null;
    }
    public int numOfVertexes()
    {
    	return this.vertices.size();
    }
    public void printGraph()
    {
    	Iterator<Integer> itr = vertices.keySet().iterator();
    	while(itr.hasNext())
    	{
    		Vertex<V, E, M> v = vertices.get(itr.next());
    		System.out.println(v.getVertexID()+" "+v.getValue());
    	}
    }
    public void printGraph2()
    {
    	Iterator<Integer> itr = vertices.keySet().iterator();
    	while(itr.hasNext())
    	{
    		Vertex<V, E, M> v = vertices.get(itr.next());
    		System.out.println(v.getVertexID());
    		Iterator<Edge<E>> edgeItr =  v.getOutEdges();
    		while(edgeItr.hasNext())
    		{
    			Edge<E> edge = edgeItr.next();
    			System.out.println("\t"+edge.getValue()+" --> "+edge.getDestVertexID());
    		}
    	}
    }
    
    int superStep()
    {
    	return this.superStep;
    }
    
    /*
     * The uniqueness of Vertex is determined by the field Vertex.vertexID.
     * So you will never find two vertexes with the same vertexID in the graph.
     */
    public boolean containsVertex(int vertexID)
    {
    	return vertices.containsKey(vertexID);
    }
    public void addVertex(Vertex<V, E, M> v)
    {
    	v.setGraphHandler(this);
    	if( ! vertices.containsKey(v.getVertexID()))
    	{
    		vertices.put(v.getVertexID(), v);
    	}    
    }
    
    /*
     * The uniqueness of Edge is determined by the equals() and hashCode() function.
     * So, user-defined subClasses of Edge should must implement equals() and hashCode() function.
     */
    public boolean containsEdge(Edge<E> edge)
    {
    	if(vertices.containsKey(edge.getSourceVertexID()))
    		return vertices.get(edge.getSourceVertexID()).containsEdge(edge);
        return false;
    }
    public void addEdge(Edge<E> edge) throws Exception
    {
    	if( ! vertices.containsKey(edge.getSourceVertexID()))
    		throw new Exception("There is no such a vertex in the garph whose ID is:" + edge.getSourceVertexID());
    	else if( ! vertices.containsKey(edge.getDestVertexID()))
    		throw new Exception("There is no such a vertex in the garph whose ID is:" + edge.getDestVertexID());
    	else
    	    vertices.get(edge.getSourceVertexID()).addEdge(edge);
    }
    

    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
