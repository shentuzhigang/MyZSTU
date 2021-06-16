package cn.edu.zstu;

@SuppressWarnings("serial")
public class ZstuHelperException extends Exception{
	public ZstuHelperException(Object err) {
		super(err.toString());
	}
}
