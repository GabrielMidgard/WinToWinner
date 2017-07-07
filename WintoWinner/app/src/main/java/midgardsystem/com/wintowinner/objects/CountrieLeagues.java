package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 19/08/2016.
 */
public class CountrieLeagues {
    private int fkCountrie;
    private int fkLeague;

    public CountrieLeagues(int fkCountrie, int fkLeague) {
        this.fkCountrie = fkCountrie;
        this.fkLeague = fkLeague;
    }
    public CountrieLeagues(){    }

    public int getFkCountrie() {
        return fkCountrie;
    }
    public void setFkCountrie(int fkCountrie) {
        this.fkCountrie = fkCountrie;
    }

    public int getFkLeague() {
        return fkLeague;
    }
    public void setFkLeague(int fkLeague) {
        this.fkLeague = fkLeague;
    }
}
