package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 13/08/2016.
 */
public class Countries {
    private int id;
    private String nameCountries;
    private int nLeagues;

    public Countries() {
    }
    public Countries(String nameCountries) {
        this.nameCountries = nameCountries;
    }
    public Countries(int id, String nameCountries) {
        this.id=id;
        this.nameCountries = nameCountries;
    }
    public Countries(int id, String nameCountries, int nLeagues) {
        this.id=id;
        this.nameCountries = nameCountries;
        this.nLeagues=nLeagues;
    }

    public Countries(String nameContinent, String nameCountries) {
        this.nameCountries = nameCountries;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNameCountries() {
        return nameCountries;
    }
    public void setNameCountries(String nameCountries) {
        this.nameCountries = nameCountries;
    }

    public int getnLeagues() {
        return nLeagues;
    }
    public void setnLeagues(int nLeagues) {
        this.nLeagues = nLeagues;
    }
}
