package com.hl7.parser.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hl7.parser.Node;
import com.hl7.parser.Parser;

class Parser_UT<T> {
	
	private static final String DATA_FILE="D:\\Dima\\projects\\HL7\\HL7\\src\\test_data\\ADT_A01.hl7";

	@Test
	public void test() {
//		Parser<T> parser=new Parser<T>("D:\\\\Dima\\\\projects\\\\HL7\\\\HL7\\\\src\\\\test_data\\\\ADT_A01.hl7");
		Parser<T> parser=new Parser<T>(DATA_FILE);
		parser.parse();
		Node<T> node=parser.getTree();
		node.printTreeDepth("|");
	}

	@Test
	public void testGetFieldValue() {
		String fieldValue=null;
		String fieldName="PID.5.r1.1.1";
		Parser<T> parser=new Parser<T>(DATA_FILE);
		parser.parse();
		//parser.getTree();
		fieldValue=parser.getFieldValue(fieldName);
		System.out.println("The value of field "+fieldName+" is "+fieldValue);
	}
}
