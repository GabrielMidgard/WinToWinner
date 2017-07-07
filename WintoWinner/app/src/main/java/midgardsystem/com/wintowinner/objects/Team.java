package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 20/08/2016.
 */
public class Team {
    private int id;
    private String name;
    private String img;
    private int selected;

    public Team() {
    }
    public Team(int id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public Team(int id, String name, String img, int selected) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.selected=selected;
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

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public int isSelected() {
        return selected;
    }
    public void setSelected(int selected) {
        this.selected = selected;
    }
}
