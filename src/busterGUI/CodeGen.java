package busterGUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;



public class CodeGen {

	private String fname="";
	
	CodeGen(String name){
		fname=name;
	}
	@SuppressWarnings("resource")
	String GenerateJS() throws FileNotFoundException{
	    String bcodegen="";
	    BufferedReader br = new BufferedReader(new FileReader(fname));
	    BufferedReader br2 = new BufferedReader(new FileReader("bcodegen.js"));
	    String line = null, result = "";
	    try {
			while ((line = br.readLine()) != null){
					result=result+line+"\n";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    String line2 = null;
	    try {
			while ((line2 = br2.readLine()) != null){
				bcodegen=bcodegen+line2+"\n";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		bcodegen=bcodegen.replaceAll("AAAAAAAAAAAAAAA", result);
		try{
			FileWriter writer = new FileWriter("newcodegen.js");
			BufferedWriter out =new BufferedWriter(writer);
			out.write(bcodegen);
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("node newcodegen");
			p.waitFor();
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}
		BufferedReader br3 = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line3 = null, js = "";
        try {
			while ((line3 = br3.readLine()) != null){
					js=js+line3+"\n";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return js;
	}
}
