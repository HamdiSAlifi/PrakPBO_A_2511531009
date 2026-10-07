package miniBank;

public class RekeningTabungan extends Rekening{
	
	// Atribut spesifik milik Tabungan
	private double sukuBunga;
	
	// Contructor SubClass
	public RekeningTabungan(String nomor,String nama, double saldoAwal, String pinAwal, double sukuBunga) {
		// super()
		super(nomor, nama, saldoAwal, pinAwal);
		this.sukuBunga = sukuBunga;
	}
	
	public void tambahBungaAkhirBulan() {
		//
		// Bisa mengakses saldo secara langsung dari class RekeningTabungan karena telah memanggil constructor pada line 11
		double nominalBunga = saldo * (sukuBunga/100);
		saldo += nominalBunga;
		
		//
		String idTrx = "TRX-B-" + System.currentTimeMillis();
		riwayatTransaksi.add(new Transaksi(idTrx, "Bunga", nominalBunga));
		
		System.out.println("Bunga " + sukuBunga + "% berhasil ditambahkan: Rp" + nominalBunga);
	}
}
