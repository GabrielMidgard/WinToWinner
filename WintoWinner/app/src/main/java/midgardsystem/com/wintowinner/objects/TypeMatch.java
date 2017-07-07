package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 07/09/2016.
 */
public class TypeMatch {
    private int id;
    private String name;

    public TypeMatch(){ }

    public TypeMatch(int id, String name) {
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
