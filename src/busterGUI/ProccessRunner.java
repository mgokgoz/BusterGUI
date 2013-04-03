package busterGUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JTextArea;

public class ProccessRunner implements Runnable{
	private String results="";
	private JTextArea j=null;
	private ProcessBuilder p;
	public ProccessRunner(String cmd,String arg,File dir) {
		p=new ProcessBuilder(cmd,arg);
		p.directory(dir);
	}
	public ProccessRunner(String cmd,String arg,File dir,JTextArea result) {
		p=new ProcessBuilder(cmd,arg);
		p.directory(dir);
		j=result;
	}
	@Override
	public void run() {
		Process r = null;
		try {
			r = p.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedReader br = new BufferedReader(new InputStreamReader(r.getInputStream()));
        String line = null, previous = null;
        try {
			while ((line = br.readLine()) != null)
			    if (!line.equals(previous)) {
			        previous = line;
			        if(line.contains("test cases")){
			        	int i=line.indexOf("test cases");
			        	int j=line.indexOf("timeouts");
			        	line=line.substring(i-2,j+8);
			        }
			        else if(line.contains("Firefox")){
			        	int i=line.indexOf("Firefox");
			        	line=line.substring(i);
			        }
			        else if(line.contains("Chro")){
			        	int i=line.indexOf("Chro");
			        	line=line.substring(i);
			        }
			        if(j!=null)
			        	j.setText(j.getText()+line+"\n");
			    }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public String getResults() {
		return results;
	}

}
