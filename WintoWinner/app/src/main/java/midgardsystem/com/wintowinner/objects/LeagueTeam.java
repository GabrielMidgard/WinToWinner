package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 09/09/2016.
 */
public class LeagueTeam {
    private int fkLeague;
    private int fkTeam;

    public LeagueTeam(){    }
    public LeagueTeam(int fkLeague, int fkTeam) {
        this.fkLeague = fkLeague;
        this.fkTeam = fkTeam;
    }

    public int getFkLeague() {
        return fkLeague;
    }
    public void setFkLeague(int fkLeague) {
        this.fkLeague = fkLeague;
    }

    public int getFkTeam() {
        return fkTeam;
    }
    public void setFkTeam(int fkTeam) {
        this.fkTeam = fkTeam;
    }
}
