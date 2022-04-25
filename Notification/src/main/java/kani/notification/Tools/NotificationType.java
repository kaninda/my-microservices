package kani.notification.Tools;

public enum NotificationType {
    CUSTOMER("customer"),
    FRAUD("fraud");
    public final String label;

    private NotificationType(String label) {
        this.label = label;
    }
}