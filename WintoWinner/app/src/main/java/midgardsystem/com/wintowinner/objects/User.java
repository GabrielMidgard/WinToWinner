package midgardsystem.com.wintowinner.objects;

import java.util.List;

/**
 * Created by Gabriel on 22/08/2016.
 */
public class User{
    private int id;
    private int idUser;
    private String name;
    private String lastNames;
    private String email;
    private String psw;
    private String nickname;
    private String img;
    private int coins;
    private int balance;
    private Wallet wallet;
    private Historical historical;

    private List<Movement> listMovement;
    private List<Historical> listHistorical;

    public User() {}

    public User(int id, int idUser, String name, String lastNames, String email, String psw, String img, int coins) {
        this.id=id;
        this.idUser=idUser;
        this.name = name;
        this.lastNames = lastNames;
        this.email = email;
        this.psw = psw;
        this.img=img;
        this.coins=coins;
    }

    public User(int idUser, String name, String lastNames, String email, String psw, String img, int coins) {
        this.idUser=idUser;
        this.name = name;
        this.lastNames = lastNames;
        this.email = email;
        this.psw = psw;
        this.img=img;
        this.coins=coins;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLastNames() {
        return lastNames;
    }
    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPsw() {return psw;}
    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getImgUser() {
        return img;
    }
    public void setImgUser(String img) {
        this.img = img;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }

    public List<Movement> getListMovement() {
        return listMovement;
    }
    public void setListMovement(List<Movement> listMovement) {
        this.listMovement = listMovement;
    }

    public Wallet getWallet() {
        return wallet;
    }
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Historical getHistorical() {
        return historical;
    }
    public void setHistorical(Historical historical) {
        this.historical = historical;
    }


    public List<Historical> getListHistorical() {
        return listHistorical;
    }
    public void setListHistorical(List<Historical> listHistorical) {
        this.listHistorical = listHistorical;
    }
}