/**
 * 
 * @author Jerry Wu
 *
 * @param <T1> Object 1
 * @param <T2> Object 2
 */
public class Pair<T1, T2> {
	private Object T1;
	private Object T2;
	
	/**
	 * creates new Pair
	 * @param T1 object 1
	 * @param T2 object 2
	 */
	public Pair(Object T1, Object T2) {
		this.T1= T1;
		this.T2 = T2;
	}
	
	/**
	 * gets the first object
	 * @return returns the first object
	 */
	public Object getKey() {
		return this.T1;
	}
	
	/**
	 * gets the second object
	 * @return returns the second object
	 */
	public Object getValue() {
		return this.T2;
	}

}
