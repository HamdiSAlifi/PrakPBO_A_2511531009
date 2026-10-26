package KumpulanPekan.Pekan5;

import java.text.NumberFormat;

public class KartuDebit extends RekeningTabungan {

    private static final double LIMIT_TARIK = 500_000;

    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    public KartuDebit(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
        super(nomor, nama, saldoAwal, pinAwal, sukuBunga);
        System.out.println("Kartu Debit berhasil diterbitkan untuk rekening " + nomor
                + " dengan limit tarik " + nf.format(LIMIT_TARIK) + "/transaksi.");
    }

    @Override
    public boolean tarikTunai(double nominal) {
        if (nominal > LIMIT_TARIK) {
            System.out.println("Gagal: Penarikan melalui Kartu Debit tidak boleh melebihi "
                    + nf.format(LIMIT_TARIK) + " per transaksi.");
            return false;
        }
        return super.tarikTunai(nominal);
    }

    public double getLimitTarik() {
        return LIMIT_TARIK;
    }
}
