package HMIN102.TP8;

public class Vider extends Command {

	public Vider(Bidon b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void _do() {
		// TODO Auto-generated method stub
		this.getBidon().vider();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		this.getBidon().remplir();
	}
	
}
