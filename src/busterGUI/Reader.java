package busterGUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	private String fname;
	public Reader(String s){
		fname=s;
	}
	@SuppressWarnings("resource")
	public String read() throws IOException{
		String all="",line="";
	    BufferedReader br = new BufferedReader(new FileReader(fname));
	    try {
			while ((line = br.readLine()) != null){
				all=all+line+"\n";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    return all;
	}
}
