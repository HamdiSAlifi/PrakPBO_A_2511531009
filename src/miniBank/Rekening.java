package miniBank;

import java.text.NumberFormat;
//import java.util.Locale; // hmdi init sendiri
import java.util.ArrayList;

public class Rekening {

	String nomorRekening;
	String namaPemilik;
	double saldo;
	double saldoAwal; // menyimpan saldo awal saat rekening dibuat, untuk challage pekan 2 soal 1
//	// custom: IDR to Rp
//	Locale idrLocale = Locale.of("id", "ID");
	NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	// Implementasi Asossiasi (1 to many)
	ArrayList<Transaksi> riwayatTransaksi;
	
	public Rekening(String nomor, String nama, double saldoAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;
		this.saldoAwal = saldoAwal;
		
		// Wajib menginisialisasi ArrayList di dalam constructor agar tidak NullPointerException
		this.riwayatTransaksi = new ArrayList<Transaksi>();
		
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + nf.format(saldo));
	}
	
	public void setorTunai(double nominal) { // min setor 10000
		if (nominal >= 10000) {
			saldo += nominal;
			// Merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
			riwayatTransaksi.add(trxBaru);
			
			System.out.println("Setor tunai Rp" + nf.format(nominal) + " berhasil. Saldo saat ini: Rp" + nf.format(saldo));
		} else {
			System.out.println("Gagal: nominal setor harus lebih dari 10000!");
		}
	}
	
	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir  : Rp" + nf.format(saldo));
		System.out.println("--------------------");
	}
	
	// Implementasi Fitur Tarik Tunai (minimal saldo ditarik: Rp10.000,00)
	public void tarikTunai(double nominal) {
		if (nominal >= 10000) {
			if (saldo < nominal) {
				System.out.println("Saldo Anda Tidak Cukup");
			} else {
				saldo -= nominal;
				// Integrasi Rekam Jejeak Penarikkan
				String idTrxt = "TRX-T-" + System.currentTimeMillis();
				Transaksi trxtBaru = new Transaksi(idTrxt, "Debit", nominal);
				
				riwayatTransaksi.add(trxtBaru);
				
				System.out.println("Tarik Tunai Rp" + nf.format(nominal) + " berhasil. Saldo saat ini: Rp" + nf.format(saldo));
			}
		} else {
			System.out.println("Gagal: nominal tarik harus minimal Rp10.000,00!");
		}
	}
	
	public void cetakMutasi() {
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Belum ada transaksi pada rekening ini");
		} else {
			for (Transaksi trx : riwayatTransaksi) {
				trx.cetakDetail();
			}
		}
	}
	
	public void akumulasi() {
		int totalSetor = 0;
        int totalTarik = 0;
        for (Transaksi trx : riwayatTransaksi) {
        	if (trx.getJenis().equals("Kredit")) {
        		totalSetor += trx.getNominal();
			} else if (trx.getJenis().equals("Debit")) {
				totalTarik += trx.getNominal();
			}
        }
        
        System.out.println("Total Setor: Rp" + nf.format(totalSetor));
        System.out.println("Total Tarik: Rp" + nf.format(totalTarik));
        System.out.println("Akumulasi: Rp" + nf.format(totalSetor - totalTarik));
        System.out.println("Saldo Sebelumnya: Rp" + nf.format(saldoAwal));
        System.out.println("Saldo Saat ini: Rp" + nf.format(saldo));
	}
}
