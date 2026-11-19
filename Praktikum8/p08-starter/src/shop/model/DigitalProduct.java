package shop.model;

/** Produk digital (e-book, template) yang diunduh pembeli. */
public class DigitalProduct extends Product {
    private final String downloadLink;

    public DigitalProduct(String sku, String name, long price, String downloadLink) {
        super(sku, name, price);
        this.downloadLink = downloadLink;
    }

    // TODO L4: produk digital dipaksa punya "berat". Siapa yang salah: class ini atau interface-nya?
    @Override
    public int getWeightGram() {
        throw new UnsupportedOperationException("Produk digital tidak punya berat");
    }

    @Override
    public String getDownloadLink() {
        return downloadLink;
    }
}
