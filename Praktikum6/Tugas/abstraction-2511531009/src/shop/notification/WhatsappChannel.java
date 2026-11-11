package shop.notification;

public class WhatsappChannel extends AbstractChannel {

    @Override
    public String channelName() {
        return "WA";
    }

    @Override
    public boolean isValid(CustomerContact contact) {

        String phone = contact.getPhone();
        return (phone.startsWith("08")) || (phone.startsWith("+62")) && phone.length() >= 10;
    }

    @Override
    public String target(CustomerContact contact) {
        return "\n[" + channelName() + "] to " + contact.getPhone();
    }

    @Override
    public String msg(String msg) {
        return "[Toko UMKM] " + msg;
    }
}