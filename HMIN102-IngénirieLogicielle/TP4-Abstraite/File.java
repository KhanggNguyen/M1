package HMIN102.TP2;

public class File extends ElementAtomique{
	private String contenue;
	
	public File(String name, String c){
		setContenue(c);
		setNom(name);
	}

	public String getContenue() {
		return contenue;
	}

	public void setContenue(String contenue) {
		this.contenue = contenue;
	}

	@Override
	public void cat() {
		System.out.println(contenue);
		
	}
	
	public int nbElem(){
		return contenue.length();
	}
	
}
