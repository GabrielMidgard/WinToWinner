package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 05/10/2016.
 */
public class News {
    private int id;
    private String header;
    private String subeader;
    private String content;
    private String img;
    private String created;
    private int idSport;

    public News(){   }

    public News(int id, String header, String subeader, String content, String img, String created, int idSport) {
        this.id = id;
        this.header = header;
        this.subeader = subeader;
        this.content = content;
        this.img = img;
        this.created = created;
        this.idSport = idSport;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }

    public String getSubeader() {
        return subeader;
    }
    public void setSubeader(String subeader) {
        this.subeader = subeader;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }

    public int getIdSport() {
        return idSport;
    }
    public void setIdSport(int idSport) {
        this.idSport = idSport;
    }
}
