package review;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import shop.exception.CatalogLoadException;
import shop.exception.PaymentFailedException;
import shop.payment.PaymentMethod;

/**
 * Latihan review kode: versi "fragile" yang lama menelan exception
 * sehingga kegagalan tersembunyi dari pemanggil. Versi ini sudah diperbaiki
 * — exception dibiarkan terlempar ke pemanggil agar bisa ditangani dengan benar.
 */
public class FragileShop {

    /**
     * Membayar sejumlah {@code amount} menggunakan {@code payment}.
     * Tidak lagi menelan kegagalan bayar — PaymentFailedException dibiarkan
     * terlempar ke pemanggil.
     */
    public static void payOrder(PaymentMethod payment, long amount) throws PaymentFailedException {
        payment.pay(amount);
    }

    /**
     * Membaca baris pertama (header) katalog.
     * IOException asli disertakan sebagai cause CatalogLoadException
     * agar penyebab kegagalan tidak hilang.
     */
    public static void readCatalogHeader(Path file) throws CatalogLoadException {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            reader.readLine();
        } catch (IOException e) {
            throw new CatalogLoadException("Gagal membaca header katalog: " + file, e);
        }
    }
}
