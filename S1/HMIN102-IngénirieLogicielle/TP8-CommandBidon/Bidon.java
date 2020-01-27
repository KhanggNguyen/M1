package HMIN102.TP8;

public class Bidon {
	private float capaciteMax;
	private float volumeContenu;
	private String nomBidon;
	
	public Bidon(float capaciteMax, float volumeContenu, String nomBidon) {
		super();
		this.capaciteMax = capaciteMax;
		this.volumeContenu = volumeContenu;
		this.nomBidon = nomBidon;
	}

	public float getCapaciteMax() {
		return capaciteMax;
	}

	public void setCapaciteMax(float capaciteMax) {
		this.capaciteMax = capaciteMax;
	}

	public float getVolumeContenu() {
		return volumeContenu;
	}

	public void setVolumeContenu(float volumeContenu) {
		this.volumeContenu = volumeContenu;
	}

	public String getNomBidon() {
		return nomBidon;
	}

	public void setNomBidon(String nomBidon) {
		this.nomBidon = nomBidon;
	}

	public void vider(){
		setVolumeContenu(0);
		System.out.println(getNomBidon() + " : est Vidé");
	}
	
	public void remplir(){
		setVolumeContenu(getCapaciteMax());
		System.out.println(getNomBidon() + " : est Rempli");
	}
	
	public float transvaser(Bidon b){
		if(b.getVolumeContenu() + this.getVolumeContenu() <= b.getCapaciteMax()){
			float volumeADeplacer = this.getVolumeContenu();
			this.setVolumeContenu(0);
			b.setVolumeContenu(b.getVolumeContenu() + this.getVolumeContenu());
			System.out.println(this.getNomBidon() + " a transvasé " + volumeADeplacer + " à " + b.getNomBidon());
			return volumeADeplacer;
		}else{
			float volume_possible = b.getCapaciteMax() - b.getVolumeContenu();
			this.setVolumeContenu(this.getVolumeContenu() - volume_possible);
			b.setVolumeContenu(b.getVolumeContenu() + volume_possible);
			System.out.println(this.getNomBidon() + " a transvasé " + volume_possible + " à " + b.getNomBidon());
			return volume_possible;
		}
	}
	
	
}
