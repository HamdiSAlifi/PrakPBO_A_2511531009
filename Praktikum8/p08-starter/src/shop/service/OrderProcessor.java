package shop.service;

import shop.exception.PaymentFailedException;
import shop.model.Order;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.util.Rupiah;

/**
 * Memproses satu pesanan dari awal sampai selesai.
 * Program ini BERJALAN BENAR, tetapi sulit diubah. Tugas Anda: membenahinya.
 */
public class OrderProcessor {
    private final long ratePerKg;
    private final int goldPercent;

    public OrderProcessor(long ratePerKg, int goldPercent) {
        this.ratePerKg = ratePerKg;
        this.goldPercent = goldPercent;
    }

    // TODO L1  : tandai setiap kelompok baris di method ini dengan // [R1] ..., // [R2] ..., dst.
    // TODO L2  : pecah menjadi Bill, OrderCalculator, InvoicePrinter, Notifier; ganti nama menjadi CheckoutService
    // TODO L3  : hapus switch dan new; terima DiscountStrategy dan PaymentMethod dari luar
    public long process(Order order, String memberLevel, String payCode, long funds)
            throws PaymentFailedException {
        long subtotal = 0;
        for (Order.Line line : order.lines()) {
            subtotal += line.total();
        }

        long discount = 0;
        if (memberLevel.equals("GOLD")) {
            discount = subtotal * goldPercent / 100;
        }

        int grams = 0;
        for (Order.Line line : order.lines()) {
            grams += line.product().getWeightGram() * line.qty();
        }
        long shipping = grams == 0 ? 0 : ((grams + 999) / 1000) * ratePerKg;
        long total = subtotal - discount + shipping;

        PaymentMethod payment = switch (payCode) {
            case "EWALLET" -> new EWalletPayment(funds);
            case "CARD" -> new CreditCardPayment(funds);
            default -> throw new IllegalArgumentException("Metode bayar tidak dikenal: " + payCode);
        };
        payment.pay(total);

        System.out.println("--- INVOICE " + order.getOrderId() + " ---");
        System.out.println("Kirim ke : " + order.getAddress().getRecipient()
                + ", " + order.getAddress().getCity());
        for (Order.Line line : order.lines()) {
            System.out.println(line.product().getName() + " x" + line.qty()
                    + " = " + Rupiah.format(line.total()));
        }
        System.out.println("Subtotal : " + Rupiah.format(subtotal));
        System.out.println("Diskon   : " + Rupiah.format(discount));
        System.out.println("Ongkir   : " + Rupiah.format(shipping));
        System.out.println("Total    : " + Rupiah.format(total));
        System.out.println("Bayar    : " + payment.name());

        System.out.println("[SMS] " + order.getAddress().getRecipient() + ": pesanan "
                + order.getOrderId() + " sudah dibayar (" + Rupiah.format(total) + ")");
        return total;
    }
}
