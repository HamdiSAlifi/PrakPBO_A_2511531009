package miniBank;

public class RekeningGiro extends Rekening{
	
	private double batasOverdraft;

	public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
		// super()
		super(nomor, nama, saldoAwal, pinAwal);
		this.batasOverdraft = batasOverdraft;
	}
	
	// Getter
	public double getBatasOverdraft() {
		return batasOverdraft;
	}
	
	// dianjutkan pada modul 5
}
