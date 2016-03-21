package example;

import graph.Edge;
import graph.Graph;
import graph.Message;
import graph.Vertex;

import java.util.Iterator;

public class PageRank {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        Graph<Double,Double,Double> graph = new Graph<Double,Double,Double>();
        
        graph.addVertex(new Page(1,true,0.0));
        graph.addVertex(new Page(2,true,0.0));
        graph.addVertex(new Page(3,true,0.0));
        graph.addVertex(new Page(4,true,0.0));
        
        graph.addEdge(new Edge<Double>(2,1,0.0));
        graph.addEdge(new Edge<Double>(3,1,0.0));
        graph.addEdge(new Edge<Double>(4,1,0.0));
        graph.addEdge(new Edge<Double>(2,3,0.0));
        graph.addEdge(new Edge<Double>(4,3,0.0));
        graph.addEdge(new Edge<Double>(4,2,0.0));
        
        graph.execute(3);
        graph.printGraph();
	}
}
class Page extends Vertex<Double,Double,Double>
{

	public Page(int vertexID, boolean active, Double value) {
		super(vertexID, active, value);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void compute(Iterator<Message<Double>> msgItr) {
		// TODO Auto-generated method stub
		if(superStep()==1)
			//rank = 1.0 / numOfTotalVertices();
			setValue(1.0);
		else
		{
	        double sum = 0;
	        while(msgItr.hasNext())
	        {
	        	sum += msgItr.next().getValue();
	        }
	        //rank = 0.15 / numOfTotalVertices() + 0.85 * sum;  
	        setValue(0.15 + 0.85 * sum); 
		}

        if(superStep()>30)
        	voltToHalt();
        else
        {
            Iterator<Edge<Double>> itr = getOutEdges();
    		while(itr.hasNext())
    		{
    			Edge<Double> edge = itr.next();
    			int targetVertexID = edge.getDestVertexID();
    			sendMessage(targetVertexID,getValue()/numOfOutEdges());
    		}
        }
	}	
}

