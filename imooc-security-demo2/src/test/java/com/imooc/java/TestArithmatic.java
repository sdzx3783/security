package com.imooc.java;

public class TestArithmatic {
	public static void main(String[] args) {
		//func(1,2,23);
		System.out.println(1/2);
	}

	private static void func(int i, int j,int z, int k) {
		int divByten=k/10;
		int modByten=k%10;
		if(divByten>0) {//比10大
			if(modByten==0) {
				if(divByten<=i) {
					System.out.println("需要"+divByten+"张10元即可!");
				}else {
					int d=(divByten-i)*10;
					int divByfive=d/5;
					int modByfive=d%5;
					if(divByfive>0) {//差值比5大
						if(modByfive==0) {
							if(divByfive<=j) {
								System.out.println("需要"+i+"张10元 "+divByfive+"张5元即可");
							}else {
								int d1=k-10*i-j*5;
								if(d1<=z) {
									System.out.println("需要"+i+"张10元 "+j+"张5元即可 "+d1+"张1元");
								}else {
									System.out.println("没法凑齐！");
								}
							}
						}else {
						}
					}else{
						if(d<=z) {
							System.out.println("需要"+i+"张10元 "+"0张5元 "+d+"张1元");
						}else {
							System.out.println("没法凑齐！");
						}
					}
				}
			}else{
				
			}
			
		}
	}
}
