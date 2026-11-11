package shop.notification;

public class SmsChannel extends AbstractChannel {
    private int maxLength;
    public SmsChannel(int maxLength){
        if (maxLength < 4) {
            throw new IllegalArgumentException("maxLength must be at least 4");
        }        
        this.maxLength = maxLength;
    }

    @Override
    public String channelName() {
        return "SMS";
    }

    @Override
    public String target(CustomerContact contact) {
        return "\n[" + channelName() + "] to " + contact.getPhone();
    }

    @Override
    public String msg(String msg) {
        if (msg.length() <= maxLength){
            return msg;
        }

        String slice = msg.substring(0, this.maxLength - 3);
        slice += "...";
        return slice;
    }

    @Override
    public boolean isValid(CustomerContact contact) {
        return contact.getPhone().startsWith("08");
    }
}