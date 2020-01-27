package HMIN102.TP2;

public class Main {

	public static void main(String[] args) {
		Directory d = new Directory("dir");
		Directory d2 = new Directory("dir2");
		File f = new File("file1","ok");
		
		d.addElem(d2);
		d.addElem(f);
		
		System.out.println(f.absolueAdress());
	}

}
