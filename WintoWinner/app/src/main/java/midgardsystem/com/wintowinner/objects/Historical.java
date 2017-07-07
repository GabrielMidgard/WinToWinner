package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 08/10/2016.
 */
public class Historical {
    private int idBetting;
    private int betting;
    private int active;
    private int idMatch;
    private String date;
    private String hour;
    private String typeMatchName;
    private String status;
    private int fkTeamHome;
    private int fkTeamVisit;
    private int fkTeamWin;

    private int fkUserTeam;
    private int fkUserChallengingTeam;
    private String round;


    public Historical() {     }

    public Historical(int idBetting, int betting, int active, int idMatch, String date, String hour, String typeMatchName, String status, int fkTeamHome, int fkTeamVisit, int fkTeamWin) {
        this.idBetting = idBetting;
        this.betting = betting;
        this.active = active;
        this.idMatch = idMatch;
        this.date = date;
        this.hour = hour;
        this.typeMatchName = typeMatchName;
        this.status = status;
        this.fkTeamHome = fkTeamHome;
        this.fkTeamVisit = fkTeamVisit;
        this.fkTeamWin = fkTeamWin;
    }

    public Historical(int betting, int active, int idMatch, String date, String hour, String typeMatchName, String status,int fkTeamHome, int fkTeamVisit, int fkTeamWin){
        //SELECT id, betting, active   , fk_match    ,       data,        hour,           fk_matches,        status,  fk_team_home,   fk_team_visit,   fk_team_win FROM bettings_history
        this.betting=betting;
        this.active=active;
        this.idMatch=idMatch;
        this.date=date;
        this.hour=hour;
        this.typeMatchName=typeMatchName;
        this.status=status;
        this.fkTeamHome=fkTeamHome;
        this.fkTeamVisit=fkTeamVisit;
        this.fkTeamWin=fkTeamWin;
    }

    public Historical(int betting, int active, int idMatch, String date, String hour, String typeMatchName, String status,int fkTeamHome, int fkTeamVisit, int fkTeamWin, int fkUserTeam, int fkUserChallengingTeam, String round){
        //SELECT id, betting, active   , fk_match    ,       data,        hour,           fk_matches,        status,  fk_team_home,   fk_team_visit,   fk_team_win FROM bettings_history
        this.betting=betting;
        this.active=active;
        this.idMatch=idMatch;
        this.date=date;
        this.hour=hour;
        this.typeMatchName=typeMatchName;
        this.status=status;
        this.fkTeamHome=fkTeamHome;
        this.fkTeamVisit=fkTeamVisit;
        this.fkTeamWin=fkTeamWin;

        this.fkUserTeam=fkUserTeam;
        this.fkUserChallengingTeam=fkUserChallengingTeam;
        this.round=round;
    }

    public int getIdBetting() {
        return idBetting;
    }
    public void setIdBetting(int idBetting) {
        this.idBetting = idBetting;
    }

    public int getBetting() {
        return betting;
    }
    public void setBetting(int betting) {
        this.betting = betting;
    }

    public int getActive() {
        return active;
    }
    public void setActive(int active) {
        this.active = active;
    }

    public int getIdMatch() {
        return idMatch;
    }
    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTypeMatchName() {
        return typeMatchName;
    }
    public void setTypeMatchName(String typeMatchName) {
        this.typeMatchName = typeMatchName;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getFkTeamHome() {
        return fkTeamHome;
    }
    public void setFkTeamHome(int fkTeamHome) {
        this.fkTeamHome = fkTeamHome;
    }

    public int getFkTeamVisit() {
        return fkTeamVisit;
    }
    public void setFkTeamVisit(int fkTeamVisit) {
        this.fkTeamVisit = fkTeamVisit;
    }

    public int getFkTeamWin() {
        return fkTeamWin;
    }
    public void setFkTeamWin(int fkTeamWin) {
        this.fkTeamWin = fkTeamWin;
    }

    public int getFkUserTeam() {
        return fkUserTeam;
    }
    public void setFkUserTeam(int fkUserTeam) {
        this.fkUserTeam = fkUserTeam;
    }

    public int getFkUserChallengingTeam() {
        return fkUserChallengingTeam;
    }
    public void setFkUserChallengingTeam(int fkUserChallengingTeam) {
        this.fkUserChallengingTeam = fkUserChallengingTeam;
    }

    public String getRound() {
        return round;
    }
    public void setRound(String round) {
        this.round = round;
    }
}
