package review.fixed;

import java.util.ArrayList;
import java.util.List;

interface Payable {
    boolean pay(long amount);
}

interface Refundable {
    boolean refund(long amount);
}

interface Shippable {
    long ship(int weightGrams);
}

interface Invoiceable {
    String printInvoice();
}

class GiftVoucher implements Payable, Invoiceable {
    private long balance = 100_000L;

    @Override
    public boolean pay(long amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "Voucher, remaining balance " + balance;
    }
}

class StoreCredit implements Payable, Refundable, Invoiceable {
    private long balance;

    StoreCredit(long initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean pay(long amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public boolean refund(long amount) {
        balance += amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "Store credit, remaining balance " + balance;
    }
}

abstract class Courier {
    protected abstract long ratePerKg();

    public final long cost(int kilograms) {
        return ratePerKg() * kilograms;
    }
}

class RegularCourier extends Courier {
    @Override
    protected long ratePerKg() {
        return 10_000L;
    }
}

class ExpressCourier extends Courier {
    @Override
    protected long ratePerKg() {
        return 18_000L;
    }
}

class SameDayCourier extends Courier {
    @Override
    protected long ratePerKg() {
        return 30_000L;
    }
}

class Cart {
    private final List<Item> items = new ArrayList<>();

    static class Item {
        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        long subtotal() {
            return price * quantity;
        }

        String getName() {
            return name;
        }
    }

    void add(Item item) {
        items.add(item);
    }

    long total() {
        long total = 0;
        for (Item item : items) {
            total += item.subtotal();
        }
        return total;
    }
}

final class Address {
    private final String street;
    private final String city;

    Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

class SimpleOrder {
    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        this.id = id;
        this.address = address;
    }

    Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return id + " -> " + address;
    }
}

interface Discount {
    int percentage();

    default long apply(long price) {
        return price - price * percentage() / 100;
    }
}

class MemberDiscount implements Discount {
    @Override
    public int percentage() {
        return 5;
    }
}

class FlashSaleDiscount implements Discount {
    @Override
    public int percentage() {
        return 30;
    }
}

public class BadShopRefactored {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));
        System.out.println("Total cart: " + cart.total());

        Courier express = new ExpressCourier();
        System.out.println("Ongkir EXPRESS 2 kg: " + express.cost(2));

        Address home = new Address("Jl. Sudirman 10", "Padang");
        SimpleOrder first = new SimpleOrder("ORD-1", home);
        SimpleOrder second = new SimpleOrder("ORD-2",
                new Address("Jl. Sudirman 10", "Bukittinggi"));
        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount();
        System.out.println("Harga flash sale: " + flashSale.apply(100_000));

        GiftVoucher voucher = new GiftVoucher();
        System.out.println(voucher.pay(30_000) + " | " + voucher.printInvoice());
    }
}
