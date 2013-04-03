package busterGUI;

import java.util.ArrayList;

public class FuncDivider {
	private String code;
	public FuncDivider(String s){
		code=s;
	}
	public ArrayList<String> divide(){
		ArrayList<String> func=new ArrayList<String>();
		String[] div = code.split("function");
		int l=div.length;
		int i;
		for (i=1;i<l;i++){
			//System.out.print(div[i]+"\n");
			String[] f = div[i].split("\\(", 2);
			//System.out.print("\n-------------------------------------\n");
			//System.out.print(f[0]);
			//System.out.print("\n-------------------------------------\n");
			f[0]=f[0].replaceAll(" ", "");
			if(!f[0].equals(""))func.add(f[0]);
		}
		System.out.println(func);
		return func;
	}
}
