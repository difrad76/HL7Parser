package com.hl7.parser;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	
	private String name;
	private String value;
	
	private List<Node<T>> nodes=new ArrayList<Node<T>>();
	private Node<T> parent;
	
	public Node() {
		
	}
	
	public Node(String name) {
		this.name=name;
	}
	
	public Node(String name, String value) {
		this.name=name;
		this.value=value;
	}
	
	public Node(String name, Node<T> parent) {
		this.name=name;
		this.parent=parent;
	}
	
	
	public Node(String name, String value, Node<T> parent) {
		this.name=name;
		this.value=value;
		this.parent=parent;
	}
	
	public Node<T> addChild(String name, String value) {
		Node<T> child=new Node<T>(name, value, this);
		addChild(child);
		return child;
	}
	
	public Node<T> addChild(Node<T> child) {
		nodes.add(child);
		return child;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<Node<T>> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node<T>> nodes) {
		this.nodes = nodes;
	}
	public Node<T> getParent() {
		return parent;
	}
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	
	public void printTreeDepth(String appender) {
		  printTreeDepth(this,  appender);
	 }
	
	public void printTreeDepth(Node<T> node, String appender) {
		  System.out.println(appender + (node.getValue()==null? node.name:node.name+": "+node.getValue()));
		  
		  node.nodes.forEach(each ->  printTreeDepth(each, appender + appender));
	 }

}
