package graph;

import reasoning.EdgeType;

/**
 * 
 * @author node
 *
 * @param <E>
 *     The type of value that the edge can store. This type should
 *     should implement hashCode() and equals() to ensure uniqueness of Edge.
 */
public class Edge<E> {

	private int sourceVertexID;
	private int destVertexID;
	private E value;
	
	public Edge(int sourceVertexID,int destVertexID,E value)
	{
		this.sourceVertexID = sourceVertexID;
		this.destVertexID = destVertexID;
		this.value = value;
	}
	
	public int getSourceVertexID()
	{
		return this.sourceVertexID;
	}
	
	public int getDestVertexID()
	{
		return this.destVertexID;
	}
	
	public E getValue()
	{
		return this.value;
	}
	
	public void setValue(E value)
	{
		this.value = value;
	}
	public String toString()
	{
		return this.getSourceVertexID()+" -- "+this.getValue()+" --> "+this.getDestVertexID();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destVertexID;
		result = prime * result + sourceVertexID;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<E> other = (Edge<E>) obj;
		if (destVertexID != other.destVertexID)
			return false;
		if (sourceVertexID != other.sourceVertexID)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Edge<String> edge_1 = new Edge<String>(1,4,"123");
        Edge<String> edge_2 = new Edge<String>(1,4,"123");
        
        System.out.println(edge_1.equals(edge_2));
	}

}
