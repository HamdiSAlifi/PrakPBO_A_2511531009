package shop.notification;

import java.util.ArrayList;

public class NotificationService {
    private ArrayList<NotificationChannel> list = new ArrayList<>();

    public void register(NotificationChannel channel) {
        list.add(channel);
    }

    public int broadcast(CustomerContact contact, String message) {
        int success = 0;
        for (int i = 0; i < this.list.size(); i++) {
            if (list.get(i).send(contact, message)) {
                success += 1;
            }
        }
        return success;
    }
}