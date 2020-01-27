import java.util.Scanner;

public class Edge {
	private String color;
	private String head;
	private Node Node1;
	private Node Node2;
	private static Scanner sc;
	
	public Edge(){
		
	}
	
	public Edge (String c, String h, Node n1, Node n2){
		setColor(c);
		setHead(h);
		setNode1(n1);
		setNode2(n2);
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getHead() {
		return head;
	}
	
	public void setHead(String head) {
		this.head = head;
	}
	
	public Node getNode1() {
		return Node1;
	}
	public void setNode1(Node node1) {
		Node1 = node1;
	}
	public Node getNode2() {
		return Node2;
	}
	public void setNode2(Node node2) {
		Node2 = node2;
	}
	
	public void createNewEdge(){
		
		//#if Color
//@		System.out.print("> Please select a color [");
//@		
		//#if red
//@		System.out.print("r - red , ");
		//#endif
//@		
		//#if green
//@		System.out.print("g - green , ");
		//#endif
//@		
//@		System.out.print("n - none] :");
//@		
//@		String color = sc.nextLine();
//@		this.setColor(color);
//@		
		//#endif
		
		//#if edge
//@		System.out.print("> Please select a head [");
//@		
		//#if simpleLine
//@		System.out.print("l - simpleLine, ");
		//#endif
//@		
		//#if arrow
//@		System.out.print("a - arrow , ");
		//#endif
//@		
		//#if filledArrow
//@		System.out.print("fa - filledArrow ] :");
		//#endif
//@		
//@		String head = sc.nextLine();
//@		this.setHead(head);
		//#endif
		
	}
}
