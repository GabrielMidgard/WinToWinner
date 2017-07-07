package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 22/08/2016.
 */
public class Betting {
    private int id;
    private int fkUser;
    private int fkTeam;
    private int fkMatch;
    private int betting;

    private Match match;
    private User user;
    private Team team;


    public Betting() {}

    public Betting(int id, int fkUser, int fkTeam, int fkMatch, int betting) {
        this.id = id;
        this.fkUser = fkUser;
        this.fkTeam = fkTeam;
        this.fkMatch = fkMatch;
        this.betting = betting;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getFkUser() {
        return fkUser;
    }
    public void setFkUser(int fkUser) {
        this.fkUser = fkUser;
    }

    public int getFkTeam() {
        return fkTeam;
    }
    public void setFkTeam(int fkTeam) {
        this.fkTeam = fkTeam;
    }

    public int getFkMatch() {
        return fkMatch;
    }
    public void setFkMatch(int fkMatch) {
        this.fkMatch = fkMatch;
    }

    public int getBetting() {
        return betting;
    }
    public void setBetting(int betting) {
        this.betting = betting;
    }


    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }

    public Match getMatch() {
        return match;
    }
    public void setMatch(Match match) {
        this.match = match;
    }
}

