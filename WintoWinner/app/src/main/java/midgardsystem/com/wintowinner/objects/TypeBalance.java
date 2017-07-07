package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 08/10/2016.
 */
public class TypeBalance {
    private int id;
    private String typeBalance;

    public TypeBalance(){}

    public TypeBalance(int id, String typeBalance) {
        this.id = id;
        this.typeBalance = typeBalance;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTypeBalance() {
        return typeBalance;
    }
    public void setTypeBalance(String typeBalance) {
        this.typeBalance = typeBalance;
    }
}
