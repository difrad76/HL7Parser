package com.hl7.parser;


import java.util.List;

import com.hl7.file.ReadHL7;
import com.hl7.util.StringUtils;


public class Parser<T> {

	private String fileName=null;
	private String message=null;
	private String SEGMENT_SEPARATOR="\n";
	private String ESCAPE_CHAR=null;
	private String FIELD_SEPARATOR=null;
	private String SUB_FIELD_SEPARATOR=null;
	private String SUB_COMPONET_SEPARATOR=null;
	private String REPEATING_CHAR=null;
	
	private Node<T> tree=new Node<>("ROOT");
	
	private String fieldDataValue;
	
	public Parser(String fileName) {
		this.fileName=fileName;
		readMessage();
	}
	
	public Parser(){
		
	}
	
	public void parse() {
		setSeparatorStrings(message);
		String[] segments=message.split(SEGMENT_SEPARATOR);		
		for (String segment : segments) {
			parseSegment(segment, tree);
		}
	}
	
	public void readMessage() {
		ReadHL7 reader=new ReadHL7(fileName);
		message=reader.readFile();
	}
	
	public void parseSegment(String segment, Node<T> parent){
		int counter=1;
		String fields[]=segment.split(StringUtils.escapeRegex(ESCAPE_CHAR, FIELD_SEPARATOR));
		String segName=fields[0];
		Node<T> field=null;
		Node<T> segmentHead=new Node<T>(segName, parent);
		if (segName.equals("MSH")) {
			field=new Node<T>(segName+"."+counter++,"|",segmentHead);
			segmentHead.addChild(field);
		}
		for (int i=1;i<fields.length;i++) {
			field=new Node<>(segName+"."+(counter++),segmentHead);
				if (field.getName().equals("MSH.2")) {
					field.setValue(fields[i]);
					segmentHead.addChild(field);
					continue;
				}
			String[] repeatedField=fields[i].split(StringUtils.escapeRegex(ESCAPE_CHAR,REPEATING_CHAR));		
			if (repeatedField.length>1) {
						parseRepeation(i, repeatedField, field);
				}else {
						parseField(fields[i], field);
				}
			segmentHead.addChild(field);		
		}
		tree.addChild(segmentHead);
	}
	
	public void parseRepeation(int fieldCount, String[] repeat, Node<T> parent) {
		String subFieldName=parent.getName();
		for (int j=0;j<repeat.length;j++) {
			Node<T> repeatingField=new Node<T>(subFieldName+".r"+(j+1),  parent );
			parseField(repeat[j], repeatingField);
			parent.addChild(repeatingField);	
		}
	}
	
	public void parseField(String fields, Node<T> parent) {
		String subFields[]=fields.split(StringUtils.escapeRegex(ESCAPE_CHAR,SUB_FIELD_SEPARATOR));
		if (subFields.length==1) {
			parent.setValue(fields);
			return;
		}
		String subFieldName=parent.getName()+".";
		for (int i=0;i<subFields.length; i++) {			
			if (subFields.length>0) {
				Node<T> subField=new Node<T>(subFieldName+(i+1), subFields[i],  parent );
				String[] subComponents=subField.getValue().split(StringUtils.escapeRegex(ESCAPE_CHAR,SUB_COMPONET_SEPARATOR));
				if (subFields.length>1) {
					parseSubField(subComponents, subField);
				}
				parent.addChild(subField);
			}
		}
	}
	
	public void parseSubField(String[] subComponents, Node<T> parent ) {
		String subFieldName=parent.getName()+".";
		for (int i=0;i<subComponents.length;i++) {
			if (subComponents.length>0) {
				parent.setValue(null);
				Node<T> subComponent=new Node<T>(subFieldName+(i+1), subComponents[i],  parent );	
				parent.addChild(subComponent);
			}
		}
	}
	
	private void setSeparatorStrings(String message) {
		String msgStartChar = new String(new byte[] {(byte) 0x0b});
		String msgEndChars= new String(new byte[] {(byte) 0x1c, (byte) 0x0d});
		if (message.startsWith(msgStartChar)) {
			message=message.substring(1);
		}
		if (message.endsWith(msgEndChars)) {
			message=message.substring(0, message.length()-2);
		}
		FIELD_SEPARATOR=String.valueOf(message.charAt(3));
		SUB_FIELD_SEPARATOR=String.valueOf(message.charAt(4));
		REPEATING_CHAR=String.valueOf(message.charAt(5));
		ESCAPE_CHAR=(message.charAt(6)=='\\'?"\\":String.valueOf(message.charAt(6)));
		SUB_COMPONET_SEPARATOR=String.valueOf(message.charAt(7));
	}
	
	public String getFieldValue(String fieldName) {
		String segName=fieldName.substring(0, fieldName.indexOf("."));
		List<Node<T>> list=tree.getNodes();
		for (Node<T> node : list) {
			if (node.getName()!=null & node.getName().equals(segName)) {
				getFieldValue(fieldName, node);
				break;
			}
		}
		return fieldDataValue;
	}
	
	private void getFieldValue(String fieldName, Node<T> field) {
		for (Node<T> node : field.getNodes()) {
			if (fieldName.equals(node.getName())) {
				this.fieldDataValue=node.getValue();
			}else {
				getFieldValue(fieldName,node);
			}
		}

	}
	
	public Node<T> getNode(String fieldName) {
		return null;
	}
	
	public Node<T> getTree(){
		return tree;
	}
	
}
