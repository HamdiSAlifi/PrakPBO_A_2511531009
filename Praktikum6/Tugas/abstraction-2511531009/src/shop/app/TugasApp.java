package shop.app;

import java.util.List;
import shop.StudentInfo;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.CustomerContact;
import shop.notification.EmailChannel;
import shop.notification.NotificationChannel;
import shop.notification.NotificationService;
import shop.notification.SmsChannel;
import shop.notification.WhatsAppChannel;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.ShippingService;

public class TugasApp {
    public static void main(String[] args) {

        Product coffee = new PhysicalProduct("KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300);
        Product rendang = new PhysicalProduct("RDG-002", "Rendang Kemasan 250g", 120_000, 400);
        Product ebook = new DigitalProduct("EBK-003", "E-book Resep Kue Nusantara", 50_000,
        "https://toko-umkm.example/unduh/ebk-003");
        List<Product> cart = List.of(coffee, rendang, ebook);

        System.out.println("Tugas Oleh " + StudentInfo.NAME + " (" + StudentInfo.NIM + ") ");
        System.out.println("== T1: Total dengan biaya admin (Ekspres) ==");
        EWalletPayment eWallet = new EWalletPayment("Dompetku", 268000);
        CreditCardPayment kartuKredit = new CreditCardPayment("Siti Rahma", 1000000);
        BankTransferPayment bankTransfer = new BankTransferPayment("bank nagari");
        PaymentMethod[] payments = {eWallet, kartuKredit, bankTransfer};

        CheckoutService checkout = new CheckoutService();
        ShippingService shipping = new ExpressShipping();

        long total = checkout.total(cart, shipping);
        for (int i = 0; i < 3; i++) {
            PaymentMethod payment = payments[i];
            System.out.println(payment.getName() + ": Rp" + checkout.grandTotal(cart, shipping, payment) + " (admin Rp" + payment.adminFee(total) + ")");
        }

        System.out.println("\nKebijakan kartu kredit: " + kartuKredit.policy());
        System.out.println("Kebijakan e-wallet: " + eWallet.policy());

        System.out.println("== T2: Notifikasi Pesanan ==");
        NotificationService notificationSer = new NotificationService();
        notificationSer.register(new EmailChannel());
        notificationSer.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        notificationSer.register(new WhatsAppChannel());


        notificationSer.register(new NotificationChannel() {
            @Override public String channelName() { return "LOG"; }
            @Override public boolean send(CustomerContact contact, String message) {
                if (message.isBlank()) {
                    return false;
                }
                System.out.println("\n[LOG] " + contact.getName() + ": " + message); return true;
            }
        });

                
        String formatMessage = "Pesanan ORD-0005 sudah dikirim. Terima kasih telah berbelanja di toko UMKM kami!";

        CustomerContact[] contact = {
            new CustomerContact("Siti Rahma", "siti@example.com", "081234567890"),
            new CustomerContact("Hendra", "hendra-tanpa-email", "+6281298765432")
        };
        for (int i = 0; i < 2; i++) {
            CustomerContact c = contact[i];
            System.out.println("Terkirim ke " + c.getName() + " lewat " + notificationSer.broadcast(c, formatMessage) + " kanal\n");
        }
    }
}