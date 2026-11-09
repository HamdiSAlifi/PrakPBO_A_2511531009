package shop.payment;

public interface PaymentMethod {
    String getName();

    boolean pay(long amount);

    default long adminFee(long amount) {
        return 0;
    };
}
