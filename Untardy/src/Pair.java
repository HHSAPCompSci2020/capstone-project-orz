public class Pair<T1, T2> {
	private Object T1;
	private Object T2;
	public Pair(Object T1, Object T2) {
		this.T1= T1;
		this.T2 = T2;
	}
	
	public Object getKey() {
		return this.T1;
	}
	
	public Object getValue() {
		return this.T2;
	}

}
