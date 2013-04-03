package busterGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
	private String fname="";

	Parser(String name){
		fname=name;
	}
	String Parse(){
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("node esparse "+ fname);
			p.waitFor();
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null, result = "";
        try {
			while ((line = br.readLine()) != null){
					result=result+line+"\n";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return result;
	}

}
