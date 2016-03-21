package example;

import java.util.Iterator;
import java.util.Vector;

import graph.Edge;
import graph.Graph;
import graph.Message;
import graph.Vertex;

public class SSSP {

	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        Graph<Integer,Integer,Integer> graph = new Graph<Integer,Integer,Integer>();
        
        graph.addVertex(new Location(1,true,0));
        graph.addVertex(new Location(2,false,Integer.MAX_VALUE));
        graph.addVertex(new Location(3,false,Integer.MAX_VALUE));
        graph.addVertex(new Location(4,false,Integer.MAX_VALUE));
        graph.addVertex(new Location(5,false,Integer.MAX_VALUE));
        
        graph.addEdge(new Edge<Integer>(1,2,3));
        graph.addEdge(new Edge<Integer>(1,3,6));
        graph.addEdge(new Edge<Integer>(2,5,5));
        graph.addEdge(new Edge<Integer>(2,4,3));
        graph.addEdge(new Edge<Integer>(2,3,1));
        graph.addEdge(new Edge<Integer>(3,5,3));
        graph.addEdge(new Edge<Integer>(3,4,1));
        graph.addEdge(new Edge<Integer>(4,5,2));
        
        graph.execute(4);
        graph.printGraph();
    
	}

}

class Location extends Vertex<Integer,Integer,Integer>
{


	public Location(int vertexID, boolean active, Integer value) {
		super(vertexID, active, value);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void compute(Iterator<Message<Integer>> msgItr) {
		// TODO Auto-generated method stub
		if(superStep()==1)
		{
        	Iterator<Edge<Integer>> itr = getOutEdges();
    		while(itr.hasNext())
    		{
    			Edge<Integer> edge = itr.next();
    			int targetVertexID = edge.getDestVertexID();
    			int pathCost = getValue()+edge.getValue();
    			sendMessage(targetVertexID,pathCost);
    		}
    		return;
		}
		
		int min = Integer.MAX_VALUE;
		
		while(msgItr.hasNext())
		{
			min = Math.min(min, msgItr.next().getValue() );
		}
        if(min < getValue())
        {
        	setValue(min);

        	Iterator<Edge<Integer>> itr = getOutEdges();
    		while(itr.hasNext())
    		{
    			Edge<Integer> edge = itr.next();
    			int targetVertexID = edge.getDestVertexID();
    			int pathCost = getValue()+edge.getValue();
    			sendMessage(targetVertexID,pathCost);
    		}
        }
		voltToHalt();
	}	
}

