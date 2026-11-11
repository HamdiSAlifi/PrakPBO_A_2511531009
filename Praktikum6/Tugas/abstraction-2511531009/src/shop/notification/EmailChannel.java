package shop.notification;

public class EmailChannel extends AbstractChannel {

    @Override
    public String channelName() {
        return "EMAIL";
    }

    @Override
    public boolean isValid(CustomerContact contact) {
        return contact.getEmail().contains("@"); 
    }

    @Override
    public String msg(String msg) {
        return msg;
    }

    @Override
    public String target(CustomerContact contact) {
        return "\n[" + channelName() + "] to " + contact.getEmail(); 
    }

}
