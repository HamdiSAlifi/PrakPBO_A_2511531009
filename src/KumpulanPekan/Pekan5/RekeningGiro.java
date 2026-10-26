package KumpulanPekan.Pekan5;

public class RekeningGiro extends Rekening{
	
	private double batasOverdraft;

	public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
		super(nomor, nama, saldoAwal, pinAwal);
		this.batasOverdraft = batasOverdraft;
	}
	
	public double getBatasOverdraft() {
		return batasOverdraft;
	}
	
	// Setelah refaktor metode tarik tunai pada kelas induk
	// Melakukan Overriding pada RekeningGiro.java
	@Override
	public boolean tarikTunai(double nominal) {
		if (nominal <= 0) {
			System.out.println("Gagal: Nominal tarik Giro harus lebih dari 0!");
			return false;
		}
		
		// Logika khusus Giro: Boleh ditarik asalkan nomial tidak melebihi (saldo + batasOverdraft)
		else if (nominal > (saldo + batasOverdraft)) {
			System.out.println("Transaksi Gagal: Melebihi batas overdraft! Maksimal penarikan: Rp" + (saldo + batasOverdraft));
			return false;
		}
		
		else {
			saldo -= nominal; // Saldo bisa bernilai negatif di sini (memakan overdraft)
			
			String idTrx = "TRX-G-" + System.currentTimeMillis();
			riwayatTransaksi.add(new Transaksi(idTrx, "Debit Giro", nominal));
			
			System.out.println("Tarik tunai Giro Rp" + nominal + "berhasil. Saldo saat ini: Rp" + saldo);
			return true;
		}
	}
	
}
