package com.hl7.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadHL7 {
	
	private String fileName=null;
	private Charset charSet=StandardCharsets.UTF_8;
	
	public ReadHL7() {
		
	}
	
	public ReadHL7(String fileName, Charset set) {
		this.fileName=fileName;
		this.charSet=set;
	}
	
	public ReadHL7(String fileName) {
		this.fileName=fileName;
	}


	public String readFile() {
	   StringBuilder contentBuilder = new StringBuilder();
	   try (Stream<String> stream = Files.lines( Paths.get(fileName), charSet))
		    {
		        stream.forEach(s -> contentBuilder.append(s).append("\n"));
		    }
		    catch (IOException e)
		    {
		        e.printStackTrace();
		    }
	   return contentBuilder.toString();		
	}
	
	

}
