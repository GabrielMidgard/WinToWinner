package midgardsystem.com.wintowinner.utils;

/**
 * Created by Gabriel on 13/08/2016.
 */
public class AppConfig {
    static String URL="http://www.wintowinner.com.mx/api/";
    static String URL_IMG="http://www.wintowinner.com.mx/_img/";

    public static String URL_IMG_USER = URL_IMG+"/_users/";

    // Server user login url
    public static String URL_GET_CONFIG = URL+"config.php?log_in=0";
    public static String URL_GET_CONFIG_LOG_IN = URL+"config.php?log_in=1&idUser=";

    public static String URL_GET_BETTING_BY_MATCH = URL+"betting.php?get=1&idMatch=";
    public static String URL_CREATE_BETTING = URL+"betting.php?new=1&idUser=";
    public static String URL_ACCEPTED_BETTING = URL+"betting.php?acepted=1";

    public static String URL_CREATE_ACCOUNT = URL+"account.php?new=1&name=";
    public static String URL_LOGIN = URL+"account.php?log_in=1&email=";
    public static String URL_LOGIN_FB = URL + "account.php?fb=1&name=";

    public static String URL_GET_NEWS = URL + "new.php?get=1";

    public static String URL_GET_PRIZE = URL + "prize.php?get=1";
    public static String URL_SET_PRIZE = URL + "prize.php?set=1";


    public static String URL_GET_ALL_AWARD_BY_ID = "http://midgardsystems.mx/services/wintowin/Services.php?opt=21&idUser=";

    public static String URL_GET_ALL_PREFERENCES = URL + "preferences.php?get=1&idUser=";
    public static String URL_DELETE_PREFERENCES = URL + "preferences.php?del=1&idUser=";
    public static String URL_ADD_PREFERENCES = URL + "preferences.php??new=1&idUser=";

    public static String URL_GET_TEAMS = URL + "team.php?get=1&idLeague=";

    public static String URL_SET_SALES="sales.php?set=1&idUser=";
}
