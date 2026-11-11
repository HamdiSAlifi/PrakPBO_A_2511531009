package shop.notification;

public final class CustomerContact {
    private final String name;
    final String email;
    private final String phone;
    
    public CustomerContact(String name, String email, String phone) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        this.name =  name;
        
        if (email.isBlank()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        this.email = email;
        
        if (phone.isBlank()) {
            throw new IllegalArgumentException("Phone number must not be empty");
        }
        this.phone = phone;

    }     
        
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
