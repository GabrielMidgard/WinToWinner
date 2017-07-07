package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 08/10/2016.
 */
public class Wallet {
    private int currentBalance;
    private int currentBalanceCoins;

    public Wallet(int currentBalance, int currentBalanceCoins) {
        this.currentBalance = currentBalance;
        this.currentBalanceCoins = currentBalanceCoins;
    }

    public Wallet(){ }

    public int getCurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getCurrentBalanceCoins() {
        return currentBalanceCoins;
    }
    public void setCurrentBalanceCoins(int currentBalanceCoins) {
        this.currentBalanceCoins = currentBalanceCoins;
    }
}
