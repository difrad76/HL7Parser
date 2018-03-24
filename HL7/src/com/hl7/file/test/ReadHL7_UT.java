package com.hl7.file.test;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hl7.file.ReadHL7;

class ReadHL7_UT {

	@Test
	void test() {
		String filePath="D:\\Dima\\projects\\HL7\\HL7\\src\\test_data\\ADT_A01.hl7";
		String data;
		ReadHL7 reader=new ReadHL7(filePath);
		data=reader.readFile();
		System.out.println(data);
	}

}
