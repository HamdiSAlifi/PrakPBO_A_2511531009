package miniBank;

import java.text.NumberFormat;

//import java.util.Locale;
import java.util.ArrayList;

public class Rekening {
	// 2026-09-24 | private
	private String nomorRekening;
	private String namaPemilik;
	private double saldo;
	private double saldoAwal; // menyimpan saldo awal saat rekening dibuat, untuk challage pekan 2 soal 1
	private String pin;
	
	// 2026-09-24 | Challenge 1 Attempt: additional variable for challenge
	private int percobaan = 0;
	private boolean akunTerblokir = false;
	
	private NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	// Implementasi Asossiasi (1 to many)
	private ArrayList<Transaksi> riwayatTransaksi;
	
	// 2026-09-24 | Constructor Modification to receive pinAwal
	public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;
		this.saldoAwal = saldoAwal;
		
		// 2026-09-24 | PIN Validation
		if (pinAwal.length() == 6) {
			this.pin = pinAwal;
		} else {
			System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
			this.pin = "123456";
		}
		
		// Wajib menginisialisasi ArrayList di dalam constructor agar tidak NullPointerException
		this.riwayatTransaksi = new ArrayList<Transaksi>();
		
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + nf.format(saldo));
	}
	
	// 2026-09-24 | Getter
	public String getNomorRekening() {
		return nomorRekening;
	}
	public String getNamaPemilik() {
		return namaPemilik;
	}
	
	//
	public boolean otentikasi(String inputPin) {
		// 2026-09-24 | Challenge 1 Attempt : blocked account display
		if (akunTerblokir) {
			System.out.println("Akun Terblokir: Akun Anda telah diblokir akibat tiga kali salah Memasukkan PIN!");
			return false;
		}
		if (this.pin.equals(inputPin) ) { // PIN Validation
			percobaan = 0; // reset if inputed the correct PIN/ Credentials
			return true;
		} else {
			percobaan++;
			if (percobaan >= 3) {
				akunTerblokir = true;
				System.out.println("Akun Terblokir: Akun Anda telah diblokir akibat tiga kali salah Memasukkan PIN!");
			} else {
				System.out.println("Akses Ditolak: PIN yang Anda masukkan salah! Sisa percobaan: " + (3 - percobaan));
			}
			return false;
		}
	}
	// 2026-09-24 | Challenge 1 Attempt: Block Status Getter
	public boolean isTerblokir() {
		return akunTerblokir;
	}
	
	// 2026-09-24 | Challenge 2 Attempt: new method, gantiPin()
	public boolean gantiPin(String pinLama, String pinBaru) {
		if (akunTerblokir) {
			System.out.println("Akun Terblokir: Akun Anda telah diblokir akibat tiga kali salah Memasukkan PIN!");
			return false;
		}
		if (!this.pin.equals(pinLama)) {
			percobaan++;
			if (percobaan >= 3) {
				System.out.println("Akun Terblokir: Akun Anda telah diblokir akibat tiga kali salah Memasukkan PIN!");
			} else {
				System.out.println("Akses Ditolak: PIN yang Anda masukkan salah! Sisa percobaan: " + (3 - percobaan));
			}
			return false;
		}
		if (pinBaru.equals(pinLama)) {
			System.out.println("Gagal: PIN baru tidak boleh sama dengan PIN lama!");
			return false;
		}
		if (pinBaru.length() != 6) {
			System.out.println("Gagal: PIN baru harus 6 digit!");
			return false;
		}
		this.pin = pinBaru;
		percobaan = 0; // reset the attempt counter after successfully changing the PIN 
		System.out.println("PIN berhasil diubah!");
		return true;
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
