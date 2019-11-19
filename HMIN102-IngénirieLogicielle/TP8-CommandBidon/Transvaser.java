package HMIN102.TP8;

public class Transvaser extends Command{
	private Bidon cible;

	public Transvaser(Bidon b, Bidon c) {
		super(b);
		this.cible = c;
		// TODO Auto-generated constructor stub
	}
	
	public Bidon getCible() {
		return cible;
	}

	public void setCible(Bidon cible) {
		this.cible = cible;
	}

	@Override
	public void _do() {
		// TODO Auto-generated method stub
		float volumeDeplace = this.getBidon().transvaser(cible);
		setVolumeEauDeplace(volumeDeplace);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		float volume_deplace = getVolumeEauDeplace();
		this.getBidon().setVolumeContenu(this.getBidon().getVolumeContenu() + this.getVolumeEauDeplace());
		getCible().setVolumeContenu(getCible().getVolumeContenu() - getVolumeEauDeplace());
		setVolumeEauDeplace(0);
		System.out.println(cible.getNomBidon() + " a re-transvaser " + volume_deplace + " à " + this.getBidon().getNomBidon());
	}
}
