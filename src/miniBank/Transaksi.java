package miniBank;

public class Transaksi {
	// 2026-09-24 | private
	private String idTransaksi;
	private String jenis;
	private double nominal;
	
	// Constructor
	public Transaksi (String id, String jenis, double nominal) {
		this.idTransaksi = id;
		this.jenis       = jenis;
		this.nominal     = nominal;
	}
	
	// Getter
	public String getIdTransaksi() {
		return idTransaksi;
	}
	
	public String getJenis() {
		return jenis;
	}
	
	public double getNominal() {
		return nominal;
	}
	
	public void cetakDetail() {
		System.out.println("ID: " + idTransaksi + " | Jenis: " + jenis + " | Nominal: Rp" + nominal);
	}
}

