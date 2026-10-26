package miniBank;

import java.text.NumberFormat;

import java.util.ArrayList;

public class Rekening {
	private String nomorRekening;
	private String namaPemilik;
	private String pin;
	
	protected double saldo;
	protected ArrayList<Transaksi> riwayatTransaksi;

	private double saldoAwal; 
	private NumberFormat nf = NumberFormat.getCurrencyInstance();
	private int percobaan = 0;
	private boolean akunTerblokir = false;
	
	public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;
		this.saldoAwal = saldoAwal;
		
		if (pinAwal.length() == 6) {
			this.pin = pinAwal;
		} else {
			System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
			this.pin = "123456";
		}
		
		this.riwayatTransaksi = new ArrayList<Transaksi>();
		
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + nf.format(saldo));
	}
	
	public String getNomorRekening() {
		return nomorRekening;
	}
	public String getNamaPemilik() {
		return namaPemilik;
	}
	
	public boolean otentikasi(String inputPin) {
		if (akunTerblokir) {
			System.out.println("Akun Terblokir: Akun Anda telah diblokir akibat tiga kali salah Memasukkan PIN!");
			return false;
		}
		if (this.pin.equals(inputPin) ) {
			percobaan = 0;
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
	public boolean isTerblokir() {
		return akunTerblokir;
	}
	
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
		percobaan = 0;
		System.out.println("PIN berhasil diubah!");
		return true;
	}
	
	public void setorTunai(double nominal) {
		if (nominal >= 10000) {
			saldo += nominal;
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
	
	// Refaktor Method tarikTunai | mengubah tipe data tarikTunai void menjadi boolean
	public boolean tarikTunai(double nominal) {
		if (nominal <= 0) {
			System.out.println("Gagal: Nominal tarik harus lebih dari 0!");
			return false;
		} else if (nominal > saldo) {
			System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
			return false;
		} else {
			saldo -= nominal;
			
			String idTrx = "TRX-T-" + System.currentTimeMillis();
			riwayatTransaksi.add(new Transaksi(idTrx, "Debit", nominal));
			
			System.out.println("tarik tunai Rp" + nominal + "berhasil. Sisa saldo: Rp" + saldo);
			return true; // Indikasi sukses
		}
	}
	
	// Setelah melakukan Overriding pada RekeningGiro.java
	// Tugas 1.5 | Transfer Antar Rekening (Polymorphism)
	// Parameter 'tujuan' bertipe Rekening, sehingga bisa menerima Tabungan, Giro, VIP, atau KartuDebit
	public void transfer(Rekening tujuan, double nominal, String pinInput) {
		// 1. Autentikasi PIN pengirim. JIka gagal, batalkan.
		if (!otentikasi(pinInput)) {
			System.out.println("Transfer Dibatalkan: Otentikasi PIN gagal.");
			return;
		}
		
		// Memvalidasi tambahan: tidak boleh transfer ke rekening sendiri
		if (tujuan == this) {
			System.out.println("Transfer Gagal: Tidak dapat transfer ke rekening yang sama");
			return;
		}

		// Validasi tambahan: setorTunai() hanya menerima minimal < Rp.10.000,00
		if (nominal < 10000) {
			System.out.println("Transfer Gagal: Minimal transfer Rp10.000,00");
			return;
		}
		
		// 	2. Tarik tunai dari rekening ini (Overriding: Giro karena aturan Overdraft)
		boolean berhasilTarik = this.tarikTunai(nominal);
		
		// 3. Periksa value
		if (berhasilTarik) {
			tujuan.setorTunai(nominal);
			System.out.println(
					"Transfer Rp" + nf.format(nominal) + " dari " + this.nomorRekening + 
					" ke " + tujuan.getNomorRekening() + 
					" (" + tujuan.getNamaPemilik() + ") berhasil"
					);
		} else {
			System.out.println("Transfer Gagal: Penarikan dari rekening asal tidak berhasil");
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
