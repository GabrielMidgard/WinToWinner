package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 14/08/2016.
 */
public class ContinentsCountries {
    private int fkContinent;
    private int fkCountrie ;

    public ContinentsCountries() {}

    public ContinentsCountries(int fkCountrie, int fkContinent) {
        this.fkCountrie = fkCountrie;
        this.fkContinent = fkContinent;
    }

    public int getFkContinent() {
        return fkContinent;
    }
    public void setFkContinent(int fkContinent) {
        this.fkContinent = fkContinent;
    }

    public int getFkCountrie() {
        return fkCountrie;
    }
    public void setFkCountrie(int fkCountrie) {
        this.fkCountrie = fkCountrie;
    }
}
