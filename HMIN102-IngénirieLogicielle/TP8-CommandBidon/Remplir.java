package HMIN102.TP8;

public class Remplir extends Command{

	public Remplir(Bidon b) {
		super(b);
	}

	@Override
	public void _do() {
		// TODO Auto-generated method stub
		this.getBidon().remplir();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		this.getBidon().vider();
	}
	
	
}
