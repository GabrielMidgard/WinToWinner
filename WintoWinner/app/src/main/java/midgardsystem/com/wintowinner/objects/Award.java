package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 10/10/2016.
 */
public class Award {
    int id;
    private String name;
    private String img;
    private int priceCoins;
    private int fkStatusPrize;
    private int idStatusPrize;
    private String statusPrize;
    private String requestCreationDate;

    public Award(){    }
    public Award(int id, String name, String img, int priceCoins, int fkStatusPrize) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.priceCoins=priceCoins;
        this.fkStatusPrize=fkStatusPrize;
    }
    public Award(int id, String name, String img, int priceCoins, String statusPrize) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.priceCoins=priceCoins;
        this.statusPrize=statusPrize;
    }

    public Award(int id, String name, String img, int priceCoins, String statusPrize, String requestCreationDate) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.priceCoins=priceCoins;
        this.statusPrize=statusPrize;
        this.requestCreationDate=requestCreationDate;
    }




    public Award(int id, String name, String img, String statusPrize, String requestCreationDate,int fkStatusPrize, int idStatusPrize) {
      //        prizes.id, prizes.name, prizes.prizeImg, statusPrizes.status, prizes.requestCreationDate, statusPrizes.id, prizes.fkStatusPrice
        this.id = id;
        this.name = name;
        this.img = img;
        this.statusPrize=statusPrize;
        this.requestCreationDate=requestCreationDate;
        this.fkStatusPrize=fkStatusPrize;
        this.idStatusPrize=idStatusPrize;
    }
    //SELECT prizes.id, prizes.name, prizes.prizeImg, prizes.cant, statusPrizes.status, prizes.requestCreationDate, statusPrizes.id, prizes.fkStatusPrice FROM prizes INNER JOIN statusPrizes ON prizes.fkStatusPrice=statusPrizes.id

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public int getPriceCoins() {
        return priceCoins;
    }
    public void setPriceCoins(int priceCoins) {
        this.priceCoins = priceCoins;
    }

    public int getFkStatusPrize() {
        return fkStatusPrize;
    }
    public void setFkStatusPrize(int fkStatusPrize) {
        this.fkStatusPrize = fkStatusPrize;
    }

    public String getStatusPrize() {
        return statusPrize;
    }
    public void setStatusPrize(String statusPrize) {
        this.statusPrize = statusPrize;
    }

    public String getRequestCreationDate() {
        return requestCreationDate;
    }
    public void setRequestCreationDate(String requestCreationDate) {
        this.requestCreationDate = requestCreationDate;
    }
}
