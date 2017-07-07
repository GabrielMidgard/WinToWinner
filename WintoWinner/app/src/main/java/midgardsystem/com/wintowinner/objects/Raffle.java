package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 25/10/2016.
 */
public class Raffle {
    private int fkMatch;
    private StatusRaffle statusRaffle;
    private int balanceCoins;
    private int balanceMoney;
    private Prize prize;
    private String prizeImg;
    private String prizeName;

    public Raffle(int fkMatch, StatusRaffle statusRaffle) {
        this.fkMatch = fkMatch;
        this.statusRaffle = statusRaffle;
    }

    public Raffle(int fkMatch, StatusRaffle statusRaffle, int balanceCoins, int balanceMoney, String prizeImg, String prizeName) {
        this.fkMatch = fkMatch;
        this.statusRaffle = statusRaffle;
        this.balanceCoins = balanceCoins;
        this.balanceMoney = balanceMoney;
        this.prizeImg = prizeImg;
        this.prizeName = prizeName;
    }

    public Raffle() {  }

    public int getFkMatch() {
        return fkMatch;
    }
    public void setFkMatch(int fkMatch) {
        this.fkMatch = fkMatch;
    }

    public StatusRaffle getStatusRaffle() {
        return statusRaffle;
    }
    public void setStatusRaffle(StatusRaffle statusRaffle) {
        this.statusRaffle = statusRaffle;
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

    public Prize getPrize() {
        return prize;
    }
    public void setPrize(Prize prize) {
        this.prize = prize;
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
}
