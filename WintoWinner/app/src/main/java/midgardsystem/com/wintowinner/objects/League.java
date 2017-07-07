package midgardsystem.com.wintowinner.objects;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 19/08/2016.
 */
public class League {
    private int id;
    private String nameLeague;
    private String imgLeague;
    List<Match> matchItem = new ArrayList<Match>();
    private String imgLeagueId;
    private int fkSport;

    public League() {}

    public League(int id, String nameLeague) {
        this.id = id;
        this.nameLeague = nameLeague;
    }

    public League(int id, String nameLeague, int fkSport) {
        this.id = id;
        this.nameLeague = nameLeague;
        this.fkSport=fkSport;
    }

    public League(int id, String nameLeague, String imgLeagueId) {
        this.id = id;
        this.nameLeague = nameLeague;
        this.imgLeagueId=imgLeagueId;
    }
    public League(String nameContinent, String nameCountries, String sportName, String sportImg, String nameLeague, String imgLeague) {
        this.nameLeague = nameLeague;
        this.imgLeague = imgLeague;
    }
    public League(String nameContinent, String sportName, String sportImg, String nameLeague, String imgLeague) {
        this.nameLeague = nameLeague;
        this.imgLeague = imgLeague;
    }
    public League(String nameContinent, String sportName, String sportImg, String nameLeague) {
        this.nameLeague = nameLeague;
    }
    public League(String nameContinent, String sportName, String sportImg, String nameLeague, ArrayList<Match> matchItem) {
        this.nameLeague = nameLeague;
        this.matchItem = matchItem;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNameLeague() {
        return nameLeague;
    }
    public void setNameLeague(String nameLeague) {
        this.nameLeague = nameLeague;
    }

    public String getImgLeague() {
        return imgLeague;
    }
    public void setImgLeague(String imgLeague) {
        this.imgLeague = imgLeague;
    }

    public List<Match> getMatchItem() {
        return matchItem;
    }
    public void setMatchItem(List<Match> matchItem) {
        this.matchItem = matchItem;
    }

    public String getImgLeagueId() {
        return imgLeagueId;
    }
    public void setImgLeagueId(String imgLeagueId) {
        this.imgLeagueId = imgLeagueId;
    }

    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(this.imgLeagueId, "drawable", context.getPackageName());
    }

    public int getFkSport() {
        return fkSport;
    }
    public void setFkSport(int fkSport) {
        this.fkSport = fkSport;
    }
}

