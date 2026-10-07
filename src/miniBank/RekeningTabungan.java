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
	
	/**
	 * Tambah bunga akhir tahun — hanya aktif jika saldo saat ini > 10.000.000.
	 * Nominal bunga dihitung dari sukuBunga yang sama dengan bunga bulanan.
	 */
	public void tambahBungaAkhirTahun() {
		final double SALDO_MINIMUM_BUNGA = 10_000_000;
		if (saldo <= SALDO_MINIMUM_BUNGA) {
			System.out.println("Gagal: Bunga akhir tahun hanya diberikan jika saldo lebih dari Rp10.000.000,00. "
					+ "Saldo Anda saat ini: Rp" + saldo);
			return;
		}
		double nominalBunga = saldo * (sukuBunga / 100);
		saldo += nominalBunga;
		
		String idTrx = "TRX-BT-" + System.currentTimeMillis();
		riwayatTransaksi.add(new Transaksi(idTrx, "Bunga Akhir Tahun", nominalBunga));
		
		System.out.println("Bunga akhir tahun " + sukuBunga + "% berhasil ditambahkan sebesar Rp" + nominalBunga
				+ ". Saldo saat ini: Rp" + saldo);
	}
}
