
 class Person {
	protected String name="sun";
	
	public String getName() {
		System.out.println("获取Person name.......");
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class Student extends Person{
	private String name="xxx";
	public void func() {
		System.out.println("赋值前 子类name: "+name);
		System.out.println("赋值前 父类name: "+super.name);
		setName("dong");//是给父类 name属性赋值
		String name2 = super.getName();
		System.out.println(name2);
		
		System.out.println("赋值后 子类name: "+name);
		System.out.println("赋值后 父类name: "+super.name);
	}
	
}
public class Test{
	public static void main(String[] args) {
		Student s=new Student();
		s.func();
	}
}
