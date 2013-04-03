package busterGUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class Isolator {

private String fname="";
private String tname="";
private String oname="";
private String resname="";
private boolean mode; //true for urf's, false for arf's
private ArrayList<ResponseObject> responses=new ArrayList<ResponseObject>();
	
	public Isolator(String name,boolean m){
		fname="./lib/" + name;
		tname="./test/backup/"+ name.split("\\.")[0] +"-test-" + new SimpleDateFormat("yyMMddHHmmss").format(new Date())   +".js";
		oname="./test/"+ name.split("\\.")[0] +"-test.js";
		resname="./resfiles/"+ name.split("\\.")[0] +".urf";
		mode=m;
	}
	@SuppressWarnings("resource")
	public ArrayList<ResponseObject> isolate() throws IOException{
		String method=null;
		String url=null;
		String function=null;
		
	    String btest="";
	    BufferedReader br = new BufferedReader(new FileReader(fname));
	    BufferedReader br2 = new BufferedReader(new FileReader("btest.js"));
	    String line = null;
	    String[] s=br.readLine().split(" ");
	    int i;
	    String a;
	    for(i=0;i<s.length;i++){
	    	a = s[i];
	    	if(a.equals("function"))
	    		function=s[i+1];
	    }
	    function=function.replaceFirst("\\([^\\(]*\\)", "");
	    try {
			while ((line = br.readLine()) != null){
				if(line.contains(".open"))
				{
					line=line.trim();
					line=line.substring(line.indexOf("(")+1,line.lastIndexOf(")")-1);
					s=line.split(",");
					method=s[0];
					url=s[1];
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
	    String line2 = null;
	    try {
			while ((line2 = br2.readLine()) != null){
				btest=btest+line2+"\n";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
	    String all=new Reader(fname).read();
	    FuncDivider fd=new FuncDivider(all);
	    fd.divide();
	    btest=btest.replaceAll("REQUEST_METHOD", method);

	    btest=btest.replaceAll("SERVER_URL", url);

	    btest=btest.replaceAll("FUNCTION_NAME", function);
	    if(mode==true){
	    	String type=new String();
	    	String response=new String();
	    	UserResponse ur = new UserResponse(resname);
	    	type=ur.getType(function);
	    	response=ur.getResponse(function);
	    	btest=btest.replaceAll("CONTENTTYPE", type);
	    	btest=btest.replaceAll("RESPONSE", response);
	    	responses=ur.getResponse();
	    }
	    backup();
	    write(btest);
		return responses;
	}
	private void backup() throws IOException{
		FileUtils.copyFile(new File(oname), new File(tname));
	}
	private void write(String s){
		try{
			FileWriter writer = new FileWriter(oname);
			BufferedWriter out =new BufferedWriter(writer);
			out.write(s);
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
