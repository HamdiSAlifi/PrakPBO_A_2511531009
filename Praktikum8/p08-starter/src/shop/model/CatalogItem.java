package shop.model;

/** Semua barang di katalog toko. TODO L4: apakah semua barang butuh kedua method di bawah? */
public interface CatalogItem {
    String getSku();

    String getName();

    long getPrice();

    /** Berat dalam gram, untuk menghitung ongkir. */
    int getWeightGram();

    /** Tautan unduh, untuk produk digital. */
    String getDownloadLink();
}
