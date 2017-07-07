package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 31/12/2016.
 */
public class TypeDate {
    private int id;
    private String name;

    public TypeDate(){ }

    public TypeDate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
