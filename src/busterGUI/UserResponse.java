package busterGUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserResponse {
	private String name="";
	private ArrayList<ResponseObject> responses=new ArrayList<ResponseObject>();
	
	public UserResponse(String fname){
		name=fname;
		try {
			readResponseFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ResponseObject> getResponse(){
		return responses;
	}

	public String getType(String function) {
		String type="";
		for(ResponseObject ro : responses){
			if(ro.funcName.equals(function)){
				type=ro.type;
			}
		}
		return type;
	}

	public String getResponse(String function) {
		String response="";
		for(ResponseObject ro : responses){
			if(ro.funcName.equals(function)){
				response=ro.response;
			}
		}
		return response;
	}
	
	private void readResponseFile() throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader(name));
	    String line = null;
	    String[] r;
	    try {
			while ((line = br.readLine()) != null){
				r=line.split("\t");
				ResponseObject ro = new ResponseObject(r[0],r[1],r[2]);
				responses.add(ro);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
