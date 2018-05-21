package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.util.Assert;

public class ProxyFactory {
	private Object target;
	private BeforeAdvice beforeAdvice;
	private AfterAdvice afterAdvice;
	
	public ProxyFactory(BeforeAdvice beforeAdvice, AfterAdvice afterAdvice,Object target) {
		super();
		this.beforeAdvice = beforeAdvice;
		this.afterAdvice = afterAdvice;
		this.target=target;
	}
	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public BeforeAdvice getBeforeAdvice() {
		return beforeAdvice;
	}

	public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
		this.beforeAdvice = beforeAdvice;
	}

	public AfterAdvice getAfterAdvice() {
		return afterAdvice;
	}

	public void setAfterAdvice(AfterAdvice afterAdvice) {
		this.afterAdvice = afterAdvice;
	}

	public  Object createProxyObject() {
		Class<?>[] interfaces = target.getClass().getInterfaces();
		Assert.notEmpty(interfaces,"代理目标类必须实现某个接口");
		InvocationHandler handle=new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if(beforeAdvice!=null) {
					beforeAdvice.beforeHandle();
				}
				Object result = method.invoke(target, args);
				if(afterAdvice!=null) {
					afterAdvice.afterHandle();
				}
				return result;
			}
		};
		return Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(),interfaces , handle);
	}
}
