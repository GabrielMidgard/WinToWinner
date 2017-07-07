package midgardsystem.com.wintowinner.objects;

/**
 * Created by Gabriel on 04/09/2016.
 */
public class Data {
    private String name;
    private String type;
    private String error;
    private int maxLength;
    private int minLength;
    // enum type {LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO};

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = name;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = name;
    }

    public int getMaxLength(){ return maxLength;}
    public void setMaxLength(int maxLength) { this.maxLength = maxLength;}

    public int getMinLength() { return minLength;}
    public void setMinLength(int minLength) { this.minLength = minLength;}
}
