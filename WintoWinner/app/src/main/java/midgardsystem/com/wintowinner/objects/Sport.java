package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 13/08/2016.
 */
public class Sport {
    private int idSport;
    public String sportName;
    public String sportImg;

    public Sport(int idSport, String sportName, String sportImg) {
        this.idSport=idSport;
        this.sportName = sportName;
        this.sportImg = sportImg;
    }
    public Sport(String nameContinent, String sportName, String sportImg) {
        this.sportName = sportName;
        this.sportImg = sportImg;

    }
    public Sport() {

    }
    public int getIdSport() {
        return idSport;
    }
    public void setIdSport(int idSport) {
        this.idSport = idSport;
    }

    public String getSportImg() {
        return sportImg;
    }
    public void setSportImg(String sportImg) {
        this.sportImg = sportImg;
    }

    public String getSportName() {
        return sportName;
    }
    public void setSportName(String sportName) {
        this.sportName = sportName;
    }
}

