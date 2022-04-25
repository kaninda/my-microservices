package kani.customer.tools;

public enum NotificationType {
    CUSTOMER("customer"),
    FRAUD("fraud");
    public final String label;

    private NotificationType(String label) {
        this.label = label;
    }
}
