package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 26/10/2016.
 */
public class TypeNotification {
    private int id;
    private String typeNotification;

    public TypeNotification(int id, String typeNotification) {
        this.id = id;
        this.typeNotification = typeNotification;
    }

    public TypeNotification(){    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTypeNotification() {
        return typeNotification;
    }
    public void setTypeNotification(String typeNotification) {
        this.typeNotification = typeNotification;
    }
}
