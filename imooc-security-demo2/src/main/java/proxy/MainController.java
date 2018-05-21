package proxy;

public class MainController {
	/**
	 * JDK动态代理 只能对实现接口的方法进行增强
	 * @param args
	 */
	public static void main(String[] args) {
		AfterAdvice afterAdvice=new AfterAdvice() {
			@Override
			public void afterHandle() {
				System.out.println("后置切面方法Run.......");
				
			}
		};
		BeforeAdvice beforeAdvice=new BeforeAdvice() {
			@Override
			public void beforeHandle() {
				System.out.println("前置切面方法Run.......");				
			}
		};
		SimpleObject simpleObject = new SimpleObject();
		ProxyFactory pro=new ProxyFactory(beforeAdvice, afterAdvice,simpleObject);
		SimpleInterface createProxyObject = (SimpleInterface) pro.createProxyObject();
		String a = createProxyObject.A();
		System.out.println(a);
		String b = ((SimpleObject) createProxyObject).B();
		System.out.println(b);
	}
	
}
