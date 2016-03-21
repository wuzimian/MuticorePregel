package graph;
/**
 * 
 * @author node
 *
 * @param <M> 
 *     The type of the value that the message can hold.
 */
public class Message<M> {

	int targetVertex;
	M value;
	
	public Message(int targetVertex,M value)
	{
		this.targetVertex = targetVertex;
		this.value = value;
	}
	
	public M getValue()
	{
		return this.value;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
