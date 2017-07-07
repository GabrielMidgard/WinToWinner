package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 19/08/2016.
 */
public class Match {
    int id;
    String localName;
    String localImg;
    String visitName;
    String visitImg;
    String hour;
    String date;

    int fkLeague;
    int fkTeamHome;
    int fkTeamVisit;
    int fkTypeMatch;
    int fkTypeDate;

    League objLeague;
    Team teamHome;
    Team teamVisit;
    TypeMatch typeMatch;
    TypeDate typeDate;


    String statusName;
    String round;
    String stadium;
    String countriesName;
    String city;

    public Match() {
    }

    //Preference
    public Match(int id, String date, String hour, String localName, String localImg, String visitName, String visitImg) {
        this.id = id;
        this.localName = localName;
        this.localImg = localImg;
        this.visitName = visitName;
        this.visitImg = visitImg;
        this.hour = hour;
        this.date = date;
    }

    public Match(int id, int fkLeague, int fkTeamHome, int fkTeamVisit, String date, String hour, int fkTypeMatch) {
        this.id = id;
        this.fkLeague = fkLeague;
        this.fkTeamHome = fkTeamHome;
        this.fkTeamVisit = fkTeamVisit;
        this.fkTypeMatch = fkTypeMatch;
        this.hour = hour;
        this.date = date;
    }

    public Match(int id, League objLeague, Team teamHome, Team teamVisit, String date, String hour, TypeMatch typeMatch) {
        this.id = id;
        this.objLeague = objLeague;
        this.teamHome = teamHome;
        this.teamVisit = teamVisit;
        this.hour = hour;
        this.date = date;
        this.typeMatch = typeMatch;
    }



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }
    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getVisitName() {
        return visitName;
    }
    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getLocalImg() {
        return localImg;
    }
    public void setLocalImg(String localImg) {
        this.localImg = localImg;
    }

    public String getVisitImg() {
        return visitImg;
    }
    public void setVisitImg(String visitImg) {
        this.visitImg = visitImg;
    }


    public int getFkLeague() {
        return fkLeague;
    }
    public void setFkLeague(int fkLeague) {
        this.fkLeague = fkLeague;
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

    public int getFkTypeMatch() {
        return fkTypeMatch;
    }
    public void setFkTypeMatch(int fkTypeMatch) {
        this.fkTypeMatch = fkTypeMatch;
    }

    public int getFkTypeDate() {
        return fkTypeDate;
    }
    public void setFkTypeDate(int fkTypeDate) {
        this.fkTypeDate = fkTypeDate;
    }


    public League getObjLeague() {
        return objLeague;
    }
    public void setObjLeague(League objLeague) {
        this.objLeague = objLeague;
    }

    public Team getTeamHome() {
        return teamHome;
    }
    public void setTeamHome(Team teamHome) {
        this.teamHome = teamHome;
    }

    public Team getTeamVisit() {
        return teamVisit;
    }
    public void setTeamVisit(Team teamVisit) {
        this.teamVisit = teamVisit;
    }

    public TypeMatch getTypeMatch() {
        return typeMatch;
    }
    public void setTypeMatch(TypeMatch typeMatch) {
        this.typeMatch = typeMatch;
    }


    public String getStatusName() {
        return statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRound() {
        return round;
    }
    public void setRound(String round) {
        this.round = round;
    }

    public String getStadium() {
        return stadium;
    }
    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getCountriesName() {
        return countriesName;
    }
    public void setCountriesName(String countriesName) {
        this.countriesName = countriesName;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}