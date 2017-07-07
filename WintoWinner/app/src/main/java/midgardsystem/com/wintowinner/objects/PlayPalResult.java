package midgardsystem.com.wintowinner.objects;

/**
 * Created by TELMEX on 03/05/2017.
 */

public class PlayPalResult {
    private boolean success;
    private int money;
    private int coins;

    public PlayPalResult() {

    }

    public PlayPalResult(int money, int coins) {
        this.money = money;
        this.coins = coins;
    }

    public PlayPalResult(boolean success, int money, int coins) {
        this.success = success;
        this.money = money;
        this.coins = coins;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }
}
