package shop.payment;

public class BankTransferPayment implements PaymentMethod{
    private String bankName;
    // private long balance;

    public BankTransferPayment(String bankName) {
        if (bankName == null || bankName.isBlank()) {
            throw new IllegalArgumentException("Bank name must not be blank");
        }
        // if (balance < 0) {
        //     throw new IllegalArgumentException("Balance must not be negative");
        // }

        this.bankName = bankName;
        // this.balance = balance;
    }

    @Override
    public String getName() {
        return "Transfer " + bankName;
    }

    // public long getBalance() {
    //     return balance;
    // }

    @Override
    public boolean pay(long amount) {
        return amount > 0;
    }
}
