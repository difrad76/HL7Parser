package com.hl7.parser.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hl7.parser.Node;

class Node_UT {

	@Test
	void test() {
		Node<?> tree=createTree();
		tree.printTreeDepth("|");

	}
	
	private Node<?> createTree() {
		Node<?> root=new Node<>("root");
		Node <?> node1=root.addChild(new Node<>("node1"));
		
		node1.addChild(new Node<>("node 1 child 1"));
		node1.addChild(new Node<>("node 1 child 2"));
		Node<?> node2=root.addChild(new Node<>("node2"));
		node2.addChild(new Node<>("node 2 child 1"));
		node2.addChild(new Node<>("node 2 child 2"));
		root.addChild(new Node<>("node3"));


		return root;
	}

}
