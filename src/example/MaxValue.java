package example;

import java.util.Iterator;
import java.util.Vector;

import graph.Edge;
import graph.Graph;
import graph.Message;
import graph.Vertex;

public class MaxValue {

	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        
		
		Graph<Integer,Integer,Integer> graph = new Graph<Integer,Integer,Integer>();		
        graph.addVertex(new SimpleNode(1,true,3));
        graph.addVertex(new SimpleNode(2,true,6));
        graph.addVertex(new SimpleNode(3,true,2));
        graph.addVertex(new SimpleNode(4,true,1));
        
        graph.addEdge(new Edge<Integer>(1, 2, null));
        graph.addEdge(new Edge<Integer>(2, 1, null));
        graph.addEdge(new Edge<Integer>(2, 4, null));
        graph.addEdge(new Edge<Integer>(3, 2, null));
        graph.addEdge(new Edge<Integer>(3, 4, null));
        graph.addEdge(new Edge<Integer>(4, 3, null));
      
        graph.execute(4);
        
        graph.printGraph();

        
	}

}

class SimpleNode extends Vertex<Integer,Integer,Integer>
{
    public SimpleNode(int vertexID, boolean active, Integer value) {
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
				int targetVertexID = itr.next().getDestVertexID();
				sendMessage(targetVertexID,getValue());
			}
			return;
		}
		int max = 0;
		while(msgItr.hasNext())
		{
			max = Math.max(max, msgItr.next().getValue() );
		}
		if(getValue() < max)
		{
			setValue(Math.max(max, getValue()));
			Iterator<Edge<Integer>> itr = getOutEdges();
			while(itr.hasNext())
			{
				int targetVertexID = itr.next().getDestVertexID();
				sendMessage(targetVertexID,getValue());
			}
		}
		voltToHalt();
	}	
}
