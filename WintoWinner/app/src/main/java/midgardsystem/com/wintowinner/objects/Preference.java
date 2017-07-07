package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 27/12/2016.
 */
public class Preference {
    private int id;
    private int fkTeam;

    public Preference(){    }

    public Preference(int fkTeam) {
        this.fkTeam = fkTeam;
    }

    public Preference(int id, int fkTeam) {
        this.id = id;
        this.fkTeam = fkTeam;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getFkTeam() {
        return fkTeam;
    }
    public void setFkTeam(int fkTeam) {
        this.fkTeam = fkTeam;
    }
}
