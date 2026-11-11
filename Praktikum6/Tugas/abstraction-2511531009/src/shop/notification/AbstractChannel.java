package shop.notification;

public abstract class AbstractChannel implements NotificationChannel{

    private int sendCount = 0;
    private String last = "";

    public abstract boolean isValid(CustomerContact contact);
    public abstract String msg(String msg);
    public abstract String target(CustomerContact contact);

    @Override
    public boolean send(CustomerContact contact, String message) {
        if (message.isBlank()) {
            return false;
        }

        if (!isValid(contact)) {
            return false;
        }

        sendCount += 1;
        last = msg(message);
        System.out.println(target(contact) + " " + last);
            
        return true;
    }

    public int getSendCount() {
        return this.sendCount;
    }

    public String getLast() {
        return this.last;
    }
}
