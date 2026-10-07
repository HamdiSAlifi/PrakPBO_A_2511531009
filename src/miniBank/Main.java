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
			System.out.println("7. Simulasi Akhir Bulan");
			System.out.println("8. Akumulasi Tarik-Setor");
			System.out.println("9. Ganti PIN");
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
				input.nextLine(); // clear buffer
				
				// validasi saldo sebelum membuat objek
				if (saldo < 50000) {
					System.out.println("Gagal: Saldo Awal Tidak Mencukupi. Masukkan Saldo Minimal Rp50.000,00");
					break;
				}
				
				System.out.print("Masukkan PIN Awal (6 Digit): ");
				String pin = input.nextLine();
				
				// Tugas 1.1 | Opsi Produk
				System.out.print("Pilih Produk: \n1. Tabungan Umum \n2. Giro Bisnis \n\nPilihan Anda: ");
				String pilihanRekening = input.nextLine();
				
				// Tugas 1.2 | Meminta input sukuBunga (%) jika Tabungan, instansiasi objek RekeningTabungan
				if (pilihanRekening.equals("1")) {
					System.out.print("Masukkan Suku Bunga (%): ");
					double sukuBunga = input.nextInt();
					akunAktif = new RekeningTabungan(no, nama, saldo, pin, sukuBunga);
					
				// Tugas 1.3 | Meminta input batasOverdraft (limit pinjaman) jika Giro, instansiasi objek RekeningGiro
				} else if (pilihanRekening.equals("2")) {
					System.out.print("Masukkan Batas Overdraft (limit pinjaman): ");
					double batasOverdraft = input.nextInt();
					akunAktif = new RekeningGiro(no, nama, saldo, pin, batasOverdraft);
				} else {
					System.out.println("Pilihan produk tidak valid. Rekeninng tidak dibuat.");
					break;
				}
				
				// Tugas 1.4 | Menyimpan ke daftar rekening (Upcasting: subclass dikenali sebagai rekening)
				daftarRekening.add(akunAktif);
				System.out.println("Rekening berhasil ditambahkan ke daftar.");
				break;
				 					
			case 2: // setor-tunai
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening");
				} else {
					System.out.print("Masukkan nominal setor: ");
					double setor = input.nextDouble();
					input.nextLine();
					if (setor < 10000) {
						System.out.print("Minimal Setor Rp10.000,00");
					} else {
						akunAktif.setorTunai(setor); //Behavior/Method
					}
				}
				break;
				
			case 3: // tarik-tunai
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening");
				} else {
					System.out.print("Masukkan PIN: ");
					String tryPIN = input.nextLine();
					if (akunAktif.otentikasi(tryPIN)) {
						System.out.print("Masukkan nominal tarik: ");
						double tarik = input.nextDouble();
						input.nextLine();
						akunAktif.tarikTunai(tarik); //Behavior/Method
					} else {
						System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
					}
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
				
			case 6: //cetak-mutasi
			    if (akunAktif == null) {
			        System.out.println("Error: Anda belum membuka rekening!");
			    } else {
			    	System.out.print("Masukkan PIN: ");
					String tryPIN = input.nextLine();
					if (akunAktif.otentikasi(tryPIN)) {
						akunAktif.cetakMutasi();
					} else {
						System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
					}
			    }
			    break;
			    
		    // Tugas 2 | Simulasi Akhir Bulan
			case 7: 
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				// Tugas 2.2 | Periksa apakah akunAktif adalah Rek
				} else if (akunAktif instanceof RekeningTabungan) {
					// Tugas 2.3 | Downcast lalu panggil method tambahBungaAkhirBulan()
					RekeningTabungan tabungan = (RekeningTabungan) akunAktif;
					tabungan.tambahBungaAkhirBulan();
				} else {
					// Tugas 2.4 | Tolak jika selain Rek. Tabungan
					System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
				}
				break;
			    
			case 8: // akumulasi
				if (akunAktif == null) {
			        System.out.println("Error: Anda belum membuka rekening!");
			    } else {
			    	akunAktif.akumulasi();
		        }
			    break;
			    
			case 9:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				} else {
					System.out.println("Masukkan PIN Lama: ");
					String pinLama = input.nextLine();
					System.out.println("Masukkan PIN Baru (6 Digit): ");
					String pinBaru = input.nextLine();
					System.out.println("Konfirmasi PIN Baru: ");
					String konfirmasiPin = input.nextLine();
					
					if (!pinBaru.equals(konfirmasiPin)) {
						System.out.println("Gagal: Konfirmasi PIN tidak cocok!");
					} else {
						akunAktif.gantiPin(pinLama, pinBaru);
					}
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
