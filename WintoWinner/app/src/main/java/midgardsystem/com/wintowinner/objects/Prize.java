package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 27/10/2016.
 */
public class Prize {
    private String prizesImg;
    private String prizesName;

    public Prize(String prizesImg, String prizesName) {
        this.prizesImg = prizesImg;
        this.prizesName = prizesName;
    }

    public Prize(){ }

    public String getPrizesImg() {
        return prizesImg;
    }
    public void setPrizesImg(String prizesImg) {
        this.prizesImg = prizesImg;
    }

    public String getPrizesName() {
        return prizesName;
    }
    public void setPrizesName(String prizesName) {
        this.prizesName = prizesName;
    }
}
