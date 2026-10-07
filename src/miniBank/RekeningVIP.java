package miniBank;

import java.text.NumberFormat;

public class RekeningVIP extends Rekening {

    private static final double BONUS_PEMBUKAAN = 100_000;

    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    /**
     * Saat rekening VIP dibuka, nasabah otomatis mendapat bonus 100.000
     * yang ditambahkan ke saldo awal.
     */
    public RekeningVIP(String nomor, String nama, double saldoAwal, String pinAwal) {
        // Panggil constructor Rekening dengan saldo awal + bonus
        super(nomor, nama, saldoAwal + BONUS_PEMBUKAAN, pinAwal);

        // Catat bonus sebagai transaksi kredit
        String idTrx = "TRX-VIP-" + System.currentTimeMillis();
        riwayatTransaksi.add(new Transaksi(idTrx, "Bonus VIP", BONUS_PEMBUKAAN));

        System.out.println("Selamat! Rekening VIP Anda mendapatkan bonus pembukaan sebesar "
                + nf.format(BONUS_PEMBUKAAN) + ".");
    }
}
