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
import java.util.Hashtable;
import org.apache.commons.io.FileUtils;


public class ResponseReporter {
	private static String StrCmp="StrCmp"; //String comparison
	private static String NumEqu="NumCmp"; //Number equality
	private static String NumBGT="NumBGT";
	private static String NumBLT="NumBLT";
	private static String NumBGE="NumBGE";
	private static String NumBLE="NumBLE";
	private String fname;
	private Hashtable<Integer, String> vars = new Hashtable<Integer, String>();
	private Hashtable<String, String> types = new Hashtable<String, String>();
	private Hashtable<String, String> values = new Hashtable<String, String>();
	private String sname;
	public ResponseReporter(String name){
		fname="./lib/" + name;
		sname=name;
	}
	
	@SuppressWarnings("resource")
	public Hashtable<Integer, String> report() throws IOException{
	    BufferedReader br = new BufferedReader(new FileReader(fname));
	    String line = null;
	    int num=0;
	    try {
			while ((line = br.readLine()) != null){
				num++;
				if(line.contains(".responseText"))
				{
					line=line.trim();
					String[] s=line.split("=");
					String var=s[0];
					vars.put(num+1, var);	
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return vars;
	}
	public Hashtable<String, String> branchTypes() throws IOException{
	    
	    Integer[] lines=vars.keySet().toArray(new Integer[0]);	
	    ArrayList<String> obj=new ArrayList<String>();
		int i,j;
		for(i=0;i<lines.length;i++){
			j=lines[i];
			String var=vars.get(j);
			obj.add(var);
		}
		for(String variable : obj){
				BufferedReader br = new BufferedReader(new FileReader(fname));
			    String line = null;
			    try {
					while ((line = br.readLine()) != null){
						if(line.contains("if("+variable+"==\""))
						{
							types.put(variable, StrCmp);
							line=line.trim();
							String[] s=line.split("\"");
							String value=s[1];
							values.put(variable, value);
						}
						else if(line.contains("if("+variable+"=="))
						{
							types.put(variable, NumEqu);
							line=line.trim();
							String[] s=line.split("\"");
							String value=s[1];
							values.put(variable, value);
						}
						else if(line.contains("if("+variable+">="))
						{
							types.put(variable, NumBGE);
							line=line.trim();
							String[] s=line.split("\"");
							String value=s[1];
							values.put(variable, value);
						}
						else if(line.contains("if("+variable+"<="))
						{
							types.put(variable, NumBLE);
							line=line.trim();
							String[] s=line.split("\"");
							String value=s[1];
							values.put(variable, value);
						}
						else if(line.contains("if("+variable+">"))
						{
							types.put(variable, NumBGT);
							line=line.trim();
							String[] s=line.split("\"");
							String value=s[1];
							values.put(variable, value);
						}
						else if(line.contains("if("+variable+"<"))
						{
							types.put(variable, NumBLT);
							line=line.trim();
							String[] s=line.split("\"");
							String value=s[1];
							values.put(variable, value);
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		return types;
	}
	
	public Hashtable<String, String> getValues() {
		return values;
	}

	public void write(){
		if(vars!=null && !vars.isEmpty()){
			String report="";
			report=report.concat("=======Response File of "+sname+"=======\n");
			int l,s=report.length()-1;
			String dline="";
			for(l=0;l<s;l++){
				dline=dline.concat("=");
			}
			report=report.concat(dline+"\n");
			report=report.concat("Lines\tVariable\n");
			report=report.concat("=====\t"+ dline.substring(0, dline.length()-8) +"\n");
			Integer[] lines=vars.keySet().toArray(new Integer[0]);			
			int i,j;
			String var;
			for(i=0;i<lines.length;i++){
				j=lines[i];
				var=vars.get(j);
				report=report.concat(j+"\t"+var+"\n");
			}
			try{
				String file="ResponseOf"+sname+ new SimpleDateFormat("yyMMddHHmmss").format(new Date())+".brr";
				FileWriter writer = new FileWriter(file);
				BufferedWriter out =new BufferedWriter(writer);
				out.write(report);
				out.close();
				FileUtils.moveFileToDirectory(new File(file),new File("./brr"), false);
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}
}
