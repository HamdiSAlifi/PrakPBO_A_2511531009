package miniBank;

import java.text.NumberFormat;

public class KartuDebit extends RekeningTabungan {

    // Limit sekali tarik menggunakan kartu debit
    private static final double LIMIT_TARIK = 500_000;

    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    // Constructor KartuDebit mengikuti constructor RekeningTabungan
    public KartuDebit(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
        super(nomor, nama, saldoAwal, pinAwal, sukuBunga);
        System.out.println("Kartu Debit berhasil diterbitkan untuk rekening " + nomor
                + " dengan limit tarik " + nf.format(LIMIT_TARIK) + "/transaksi.");
    }

    /**
     * Override tarikTunai() dari Rekening, tambahkan pengecekan limit 500.000.
     * Saldo kartu debit bersifat real-time sama dengan rekening tabungannya
     * karena KartuDebit extends RekeningTabungan dan mengakses field saldo yang sama.
     */
    @Override
    public void tarikTunai(double nominal) {
        if (nominal > LIMIT_TARIK) {
            System.out.println("Gagal: Penarikan melalui Kartu Debit tidak boleh melebihi "
                    + nf.format(LIMIT_TARIK) + " per transaksi.");
            return;
        }
        // Delegasikan ke parent yang sudah menangani validasi saldo dll.
        super.tarikTunai(nominal);
    }

    public double getLimitTarik() {
        return LIMIT_TARIK;
    }
}
