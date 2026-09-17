package miniBank;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening> daftarRekening = new ArrayList<>();
		Rekening akunAktif =  null;
		boolean isRunning = true;
		
		System.out.println("=== SISTEM PERBANKAN MINI ===");
		
		while (isRunning) {
			System.out.println("\nMenu Utama:");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Akun Aktif");
			System.out.println("6. Cetak Mutasi (Riwayat)");
			System.out.println("7. Akumulasi Tarik-Setor");
			System.out.println("0. Keluar");
			System.out.print("Pilih Menu: ");
			
			int pilihan = input.nextInt();
			input.nextLine(); //clear buffer
			
			switch (pilihan) {
			case 1:
				System.out.print("Masukkan No Rekening: ");
				String no = input.nextLine();
				System.out.print("Masukkan Nama Pemilik: ");
				String nama = input.nextLine();
				System.out.print("Masukkan Saldo Awal: ");
				double saldo = input.nextDouble();
				if (saldo < 50000) {
					System.out.println("Saldo Awal Tidak Mencukupi. Masukkan Saldo Minimal Rp50.000,00");
				} else {
					//Manggil Constructor
					akunAktif = new Rekening(no, nama, saldo);
					daftarRekening.add(akunAktif);
				}
				break; 					
					
				
			case 2:
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening");
				} else {
					System.out.print("Masukkan nominal setor: ");
					double setor = input.nextDouble();
					if (setor < 10000) {
						System.out.print("Minimal Setor Rp10.000,00");
					} else {
						akunAktif.setorTunai(setor); //Behavior/Method
					}
				}
				break;
				
			case 3:
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening");
				} else {
					System.out.print("Masukkan nominal tarik: ");
					double tarik = input.nextDouble();
					akunAktif.tarikTunai(tarik); //Behavior/Method
				}
				break;
				
			case 4:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				} else {
					akunAktif.cekInformasi();
				}
				break;
				
			case 5:
				if (daftarRekening.isEmpty()) {
					System.out.println("Belum Ada Rekening yang Terdaftar!");
				} else {
					System.out.println("Daftar Rekening Terdaftar");
					for (int i = 0; i < daftarRekening.size(); i++) {
						System.out.println("Pilihan Ke-" + (i + 1) + ":");
						daftarRekening.get(i).cekInformasi();
						System.out.println("==================================");
					}
					
					System.out.print("Pilih Nomor Index Rekening yang Ingin Diaktifkan (1 - " + daftarRekening.size() + "): ");
					int indeksPilihan = input.nextInt();
					
					if (indeksPilihan >= 1 && indeksPilihan <= daftarRekening.size()) {
						akunAktif = daftarRekening.get(indeksPilihan - 1);
						System.out.println("Berhasil Berganti Akun!");
					} else {
						System.out.println("Nomor Pilihan Tidak Valid!");
					}
				}
				break;
				
			case 6:
			    if (akunAktif == null) {
			        System.out.println("Error: Anda belum membuka rekening!");
			    } else {
			        akunAktif.cetakMutasi();
			    }
			    break;
			    
			case 7: 
				if (akunAktif == null) {
			        System.out.println("Error: Anda belum membuka rekening!");
			    } else {
			    	akunAktif.akumulasi();
		        }
			    break;
			    
			case 0:
				isRunning = false;
				System.out.println("Sistem ditutup. Terima kasih!");
				break;
				
			default:
				System.out.println("Pilihan tidak valid");
			}
			
		}
		input.close();
	}

}
