package com.yun.jt808.common;

import java.util.ArrayList;
import java.util.List;
/**
* @Description: TODO 
* @author James
* @date 2021年10月15日 下午12:31:34
 */
public class RecursionTest {

	class Node{
		public Integer id;
		public String name;
		public Integer pid;
		public List<Node> clist = new ArrayList<Node>();
	}
	
	
	public List<Node> init(){
		List<Node> data = new ArrayList<Node>();
		
		Node p1 = new Node();
		p1.id = 1;
		p1.name = "父1";
		
		Node p2 = new Node();
		p2.id = 2;
		p2.name = "父2";
		
		Node p3 = new Node();
		p3.id = 3;
		p3.name = "父3";
		
		Node c1 = new Node();
		c1.id = 4;
		c1.name = "C4";
		c1.pid = 1;
		
		Node c2 = new Node();
		c2.id = 5;
		c2.name = "C5";
		c2.pid = 4;
		
		Node c3 = new Node();
		c3.id = 6;
		c3.name = "C6";
		c3.pid = 4;
		
		data.add(p1);
		data.add(p2);
		data.add(p3);
		data.add(c1);
		data.add(c2);
		data.add(c3);
		
		return data;
	}
	
	public List<Node> buildTree(List<Node> data){
		List<Node> parents = new ArrayList<Node>();
		for(Node n : data){
			if(n.pid == null){
				parents.add(n);
			}
		}
		
		for(Node n : parents){
			sub(n, data);
		}
		return parents;
	}

	private void sub(Node n, List<Node> cs) {
		for(Node c : cs){
			if(n.id.equals(c.pid)){
				n.clist.add(c);
				sub(c, cs);
			}
		}
	}
}
