package proxy;

public class SimpleObject implements SimpleInterface{
	public String A() {
		System.out.println("A方法。。。");
		return "ASUCCESS";
	}
	public String B() {
		System.out.println("B方法。。。");
		return "BSUCCESS";
	}
}
