package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 25/10/2016.
 */
public class StatusRaffle {
    private int id;
    private String status;

    public StatusRaffle(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public StatusRaffle(){   }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
