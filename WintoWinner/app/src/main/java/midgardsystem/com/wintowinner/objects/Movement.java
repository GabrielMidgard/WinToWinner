package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 08/10/2016.
 */
public class Movement {
    private int id;
    private int fk_wallet;
    private int fk_typeMovement;
    private String srtTypeMovement;
    private int fk_typeBalance;
    private int balance;
    private TypeMovement typeMovement;

    public Movement() {}
    public Movement(int id, int fk_wallet, int fk_typeMovement, int fk_typeBalance, int balance) {
        this.id = id;
        this.fk_wallet = fk_wallet;
        this.fk_typeMovement = fk_typeMovement;
        this.fk_typeBalance = fk_typeBalance;
        this.balance = balance;
    }

    public Movement( int fk_typeMovement, int fk_typeBalance, int balance) {
        this.fk_typeMovement = fk_typeMovement;
        this.fk_typeBalance = fk_typeBalance;
        this.balance = balance;
    }

    public Movement(int id, int balance, String srtTypeMovement) {
        this.id = id;
        this.srtTypeMovement = srtTypeMovement;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getFk_wallet() {
        return fk_wallet;
    }
    public void setFk_wallet(int fk_wallet) {
        this.fk_wallet = fk_wallet;
    }

    public int getFk_typeMovement() {
        return fk_typeMovement;
    }
    public void setFk_typeMovement(int fk_typeMovement) {
        this.fk_typeMovement = fk_typeMovement;
    }

    public int getFk_typeBalance() {
        return fk_typeBalance;
    }
    public void setFk_typeBalance(int fk_typeBalance) {
        this.fk_typeBalance = fk_typeBalance;
    }

    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public TypeMovement getTypeMovement() {
        return typeMovement;
    }
    public void setTypeMovement(TypeMovement typeMovement) {
        this.typeMovement = typeMovement;
    }

    public String getSrtTypeMovement() {
        return srtTypeMovement;
    }
    public void setSrtTypeMovement(String srtTypeMovement) {
        this.srtTypeMovement = srtTypeMovement;
    }
}
