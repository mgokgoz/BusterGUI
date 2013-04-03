package busterGUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Scanner {

public ArrayList<String> scan(ArrayList<String> files) throws FileNotFoundException{
	int i;
	ArrayList<String> list = new ArrayList<String>();
    for(i=0;i<files.size();i++){
    	String file="";
    	BufferedReader br = new BufferedReader(new FileReader("lib/"+files.get(i)));
    	String line = null;
    	try {
    		while ((line = br.readLine()) != null){
    			file=file+line+"\n";
    		}
    	} catch (IOException e1) {
    		e1.printStackTrace();
    	}
    	if(file.contains("XMLHttpRequest")){
    		list.add(files.get(i));
    	}
    }
	return list;
}

}
