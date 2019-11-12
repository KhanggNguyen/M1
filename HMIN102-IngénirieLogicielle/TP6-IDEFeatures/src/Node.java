import java.util.Scanner;

public class Node {
	private String label;
	private String color;
	private String shape;
	private static Scanner sc;
	
	public Node(){
		
	}
	
	public Node(String l, String c, String s){
		setLabel(l);
		setColor(c);
		setShape(s);
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		if(color.equals("r") || color.equals("g")){
			this.color = color;
		}else{
			this.color = "n";
		}
		
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	
	public void createNewNode(){
		
		System.out.print("> Please give a label");
		String label = sc.nextLine();
		this.setLabel(label);
		
		//#if color
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
	}
}
