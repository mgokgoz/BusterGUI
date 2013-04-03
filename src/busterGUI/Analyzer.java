package busterGUI;


import java.io.File;
import java.util.ArrayList;

public class Analyzer {

	
public ArrayList<String> getFileList(){
	  String path = "./lib"; 
	  String files;
	  File folder = new File(path);
	  File[] listOfFiles = folder.listFiles();
	  ArrayList<String> list = new ArrayList<String>();
	 
	  for (int i = 0; i < listOfFiles.length; i++) 
	  {
	 
		  	if (listOfFiles[i].isFile()) 
	   		{
		  		files = listOfFiles[i].getName();
		        if (files.endsWith(".js") || files.endsWith(".JS"))
		        {
		           list.add(files);
		        }
	   		}
	  }
	  return list;
}
}
