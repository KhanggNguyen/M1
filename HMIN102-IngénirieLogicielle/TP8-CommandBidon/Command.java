package HMIN102.TP8;

abstract class Command {
	private Bidon bidon;
	private float volumeEauDeplace;
	
	public Command (Bidon b){
		bidon = b;
	}
	
	public Bidon getBidon() {
		return bidon;
	}

	public void setBidon(Bidon bidon) {
		this.bidon = bidon;
	}

	public float getVolumeEauDeplace() {
		return volumeEauDeplace;
	}

	public void setVolumeEauDeplace(float volumeEauDeplace) {
		this.volumeEauDeplace = volumeEauDeplace;
	}

	public void _do(){
		
	};
	
	public void undo(){
		
	};
}
