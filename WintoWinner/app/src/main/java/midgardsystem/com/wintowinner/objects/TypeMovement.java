package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 08/10/2016.
 */
public class TypeMovement {

    private int id;
    private String typeMovement;

    public TypeMovement() {    }
    public TypeMovement(int id, String typeMovement) {
        this.id = id;
        this.typeMovement = typeMovement;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTypeMovement() {
        return typeMovement;
    }
    public void setTypeMovement(String typeMovement) {
        this.typeMovement = typeMovement;
    }
}
