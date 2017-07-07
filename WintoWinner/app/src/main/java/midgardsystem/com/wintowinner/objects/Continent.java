package midgardsystem.com.wintowinner.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 13/08/2016.
 */
public class Continent {
    private int id;
    private String nameContinent;
    List<Countries> countriesItem = new ArrayList<Countries>();

    public Continent(String nameContinent) {
        this.nameContinent = nameContinent;
    }
    public Continent(int id, String nameContinent) {
        this.id=id;
        this.nameContinent = nameContinent;
    }
    public Continent(String nameContinent, ArrayList<Countries> countriesItem) {
        this.nameContinent = nameContinent;
        this.countriesItem = countriesItem;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nameContinent;
    }
    public void setName(String nameContinent) {
        this.nameContinent = nameContinent;
    }


    public List<Countries> getCountrieItem() {
        return countriesItem;
    }
    public void setCountrieItem(List<Countries> countriesItem) {
        this.countriesItem = countriesItem;
    }

}
