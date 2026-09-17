package miniBank;

import java.text.NumberFormat;

public class Rekening {

	String nomorRekening;
	String namaPemilik;
	double saldo;
	NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	public Rekening(String nomor, String nama, double saldoAwal) {
		nomorRekening = nomor;
		namaPemilik = nama;
		saldo = saldoAwal;
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + nf.format(saldo));
	}
	
	public void setorTunai(double nominal) { // min setor 10000
		if (nominal >= 10000) {
			saldo += nominal;
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
				System.out.println("Tarik Tunai Rp" + nf.format(nominal) + " berhasil. Saldo saat ini: Rp" + nf.format(saldo));
			}
		} else {
			System.out.println("Gagal: nominal tarik harus lebih dari 10000!");
		}
	}
}
