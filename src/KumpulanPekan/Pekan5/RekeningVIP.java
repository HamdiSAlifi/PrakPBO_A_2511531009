package KumpulanPekan.Pekan5;

import java.text.NumberFormat;

public class RekeningVIP extends Rekening {

    private static final double BONUS_PEMBUKAAN = 100_000;

    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    public RekeningVIP(String nomor, String nama, double saldoAwal, String pinAwal) {
        super(nomor, nama, saldoAwal + BONUS_PEMBUKAAN, pinAwal);

        String idTrx = "TRX-VIP-" + System.currentTimeMillis();
        riwayatTransaksi.add(new Transaksi(idTrx, "Bonus VIP", BONUS_PEMBUKAAN));

        System.out.println("Selamat! Rekening VIP Anda mendapatkan bonus pembukaan sebesar "
                + nf.format(BONUS_PEMBUKAAN) + ".");
    }
}
