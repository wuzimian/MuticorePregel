package graph;

import java.util.ArrayList;

public class VertexProgram<V,E,M> implements Runnable{

	private ArrayList<Vertex<V,E,M>> v_list = new ArrayList<Vertex<V,E,M>>();
	
	public VertexProgram()
	{
	}
	public void addElement(Vertex<V,E,M> v)
	{
		v_list.add(v);
	}
	@Override
	public void run() {
		for(Vertex<V,E,M> v : v_list)
		    v.compute(v.getMessages());
	}
}



