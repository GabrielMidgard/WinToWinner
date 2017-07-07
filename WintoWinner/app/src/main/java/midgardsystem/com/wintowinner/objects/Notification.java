package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 26/10/2016.
 */
public class Notification {
    private int fkTypeNotification;
    private int balanceCoins;
    private int balanceMoney;
    private String prizeImg;
    private String prizeName;
    private Prize prize;
    private String infoHeader;
    private String infoBody;
    private TypeNotification typeNotification;

    public Notification(int fkTypeNotification, int balanceCoins, int balanceMoney, String prizeImg, String prizeName, String infoHeader, String infoBody) {
        this.fkTypeNotification = fkTypeNotification;
        this.balanceCoins = balanceCoins;
        this.balanceMoney = balanceMoney;
        this.prizeImg = prizeImg;
        this.prizeName = prizeName;
        this.infoHeader = infoHeader;
        this.infoBody = infoBody;
    }

    public Notification(int fkTypeNotification, int balanceCoins, int balanceMoney, String prizeImg, String prizeName, String infoHeader, String infoBody, TypeNotification typeNotification) {
        this.balanceCoins = balanceCoins;
        this.balanceMoney = balanceMoney;
        this.prizeImg = prizeImg;
        this.prizeName = prizeName;
        this.infoHeader = infoHeader;
        this.infoBody = infoBody;
        this.typeNotification = typeNotification;
    }

    public Notification(){    }

    public int getFkTypeNotification() {
        return fkTypeNotification;
    }
    public void setFkTypeNotification(int fkTypeNotification) {
        this.fkTypeNotification = fkTypeNotification;
    }

    public int getBalanceCoins() {
        return balanceCoins;
    }
    public void setBalanceCoins(int balanceCoins) {
        this.balanceCoins = balanceCoins;
    }

    public int getBalanceMoney() {
        return balanceMoney;
    }
    public void setBalanceMoney(int balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public String getPrizeImg() {
        return prizeImg;
    }
    public void setPrizeImg(String prizeImg) {
        this.prizeImg = prizeImg;
    }

    public String getPrizeName() {
        return prizeName;
    }
    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Prize getPrize() {
        return prize;
    }
    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public String getInfoHeader() {
        return infoHeader;
    }
    public void setInfoHeader(String infoHeader) {
        this.infoHeader = infoHeader;
    }

    public String getInfoBody() {
        return infoBody;
    }
    public void setInfoBody(String infoBody) {
        this.infoBody = infoBody;
    }

    public TypeNotification getTypeNotification() {
        return typeNotification;
    }
    public void setTypeNotification(TypeNotification typeNotification) {
        this.typeNotification = typeNotification;
    }
}
