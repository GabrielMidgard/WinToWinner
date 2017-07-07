package midgardsystem.com.wintowinner.dateSource;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import midgardsystem.com.wintowinner.objects.Award;
import midgardsystem.com.wintowinner.objects.Continent;
import midgardsystem.com.wintowinner.objects.ContinentsCountries;
import midgardsystem.com.wintowinner.objects.CountrieLeagues;
import midgardsystem.com.wintowinner.objects.Countries;
import midgardsystem.com.wintowinner.objects.Historical;
import midgardsystem.com.wintowinner.objects.League;
import midgardsystem.com.wintowinner.objects.LeagueTeam;
import midgardsystem.com.wintowinner.objects.Match;
import midgardsystem.com.wintowinner.objects.Movement;
import midgardsystem.com.wintowinner.objects.Notification;
import midgardsystem.com.wintowinner.objects.Preference;
import midgardsystem.com.wintowinner.objects.Sport;
import midgardsystem.com.wintowinner.objects.Team;
import midgardsystem.com.wintowinner.objects.TypeBalance;
import midgardsystem.com.wintowinner.objects.TypeMatch;
import midgardsystem.com.wintowinner.objects.TypeMovement;
import midgardsystem.com.wintowinner.objects.TypeNotification;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.objects.Wallet;

/**
 * Created by Gabriel on 13/08/2016.
 */
public class DatabaseHelper  extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;
    private static final String LOG = "";
    private static SQLiteDatabase db = null;

    // Database name
    public static final String DATABASE_NAME  = "wintowin.db";

    // Database version number
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_CONFING = "config";
    public static final String TABLE_CONTINENTS = "continents";
    public static final String TABLE_COUNTRIES = "countries";
    public static final String TABLE_CONTINENT_COUNTRIES = "continent_countries";
    public static final String TABLE_COUNTRIE_LEAGUES = "countrie_leagues";
    public static final String TABLE_SPORTS = "sports";
    public static final String TABLE_LEAGUES = "leagues";
    public static final String TABLE_LEAGUE_TEAMS="league_teams";
    public static final String TABLE_TEAMS = "teams";
    public static final String TABLE_MATCHES = "matches";
    public static final String TABLE_BETTINGS = "bettings";
    public static final String TABLE_BETTINGS_HISTORY = "bettings_history";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_TYPE_MATCHES = "typeMatch";
    public static final String TABLE_WALLET = "wallet";
    public static final String TABLE_MOVEMENTS = "movements";
    public static final String TABLE_TYPE_BALANCE = "typeBalance";
    public static final String TABLE_TYPE_MOVEMENTS = "typeMovements";
    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String TABLE_TYPE_NOTIFICATIONS = "typeNotifications";
    public static final String TABLE_PRIZES = "prizes";
    public static final String TABLE_STATUS_PRIZES = "statusPrizes";
    public static final String TABLE_PREFERENCES = "preferences";

    // Columns names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_USER = "idUser";
    public static final String COLUMN_ID_BETTING = "idBetting";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_FK_CONTINENT = "fk_continent";
    public static final String COLUMN_FK_COUNTRIE = "fk_countrie";
    public static final String COLUMN_FK_SPORT = "fk_sport";
    public static final String COLUMN_FK_LEAGUE = "fk_league";
    public static final String COLUMN_FK_TEAM_HOME = "fk_team_home";
    public static final String COLUMN_FK_TEAM_VISIT = "fk_team_visit";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_FK_DATE = "fk_date";
    public static final String COLUMN_FK_HOUR = "fk_hour";
    public static final String COLUMN_FK_TYPE_MATCH = "fk_type_match";
    public static final String COLUMN_FK_TYPE_DATE = "fk_type_Date";


    public static final String COLUMN_FK_USER = "fk_user";
    public static final String COLUMN_FK_MATCH = "fk_match";
    public static final String COLUMN_FK_TEAM = "fk_team";
    public static final String COLUMN_BETTING = "betting";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PSW = "password";
    public static final String COLUMN_NEW_INSTALATION = "newInstalation";
    public static final String COLUMN_COINTS = "coints";
    public static final String COLUMN_FK_USER_CHALLENGING="fk_user_challenging";
    public static final String COLUMN_FK_TEAM_CHALLENGING="fk_team_challenging";
    public static final String COLUMN_DATA="data";
    public static final String COLUMN_STATUS="status";
    public static final String COLUMN_FK_BETTING="fk_betting";
    public static final String COLUMN_STATUS_NAME="statusName";
    public static final String COLUMN_ROUND="round";
    public static final String COLUMN_STADIUM="stadium";
    public static final String COLUMN_COUNTRIES_NAME="countriesName";
    public static final String COLUMN_CITY="city";
    public static final String COLUMN_CURRENT_BALANCE="currentBalance";
    public static final String COLUMN_CURRENT_COINTS="currentBalanceCoins";
    public static final String COLUMN_FK_TYPE_MOVEMENT="fk_typeMovement";
    public static final String COLUMN_FK_TYPE_BALANCE="fk_typeBalance";
    public static final String COLUMN_BALANCE="balance";
    public static final String COLUMN_TYPE_BALANCE ="typeBalance";
    public static final String COLUMN_TYPE_MOVEMENT = "typeMovement";
    public static final String COLUMN_ACTIVE="active";
    public static final String COLUMN_FK_TEAM_WIN="fk_team_win";
    public static final String COLUMN_TYPE_MATCHES="fk_matches";
    public static final String COLUMN_FK_USER_TEAM="fk_user_team";
    public static final String COLUMN_FK_USER_CHALLENGING_TEAM="fk_user_challenging_team";
    public static final String COLUMN_FK_TYPE_NOTIFICATION="fk_typeNotification";
    public static final String COLUMN_BALANCE_COINS="balanceCoins";
    public static final String COLUMN_BALANCE_MONEY="balanceMoney";
    public static final String COLUMN_PRIZE_IMG="prizeImg";
    public static final String COLUMN_PRIZE_NAME="prizeName";
    public static final String COLUMN_INFO_HEADER="infoHeader";
    public static final String COLUMN_INFO_BODY="infoBody";
    public static final String COLUMN_NOTIFICATION="notification";
    public static final String COLUMN_CANT="cant";
    public static final String COLUMN_FK_STATUS_PRIZE="fkStatusPrice";
    public static final String COLUMN_ID_PRIZE="idPrize";
    public static final String COLUMN_REQUEST_CREATION_DATE="requestCreationDate";



    public static final String COLUMNS_CONFING = COLUMN_NEW_INSTALATION+" INTEGER NOT NULL";
    public static final String COLUMNS_CONTINENTS = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME+" varchar(255) NOT NULL";
    public static final String COLUMNS_COUNTRIES =  COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME+" varchar(255) NOT NULL";
    public static final String COLUMNS_CONTINENT_COUNTRIES = COLUMN_FK_CONTINENT+" INTEGER NOT NULL, "+ COLUMN_FK_COUNTRIE+" INTEGER NOT NULL";
    public static final String COLUMNS_SPORTS = COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+" varchar(255) NOT NULL,  "+COLUMN_IMG+" varchar(255) NOT NULL";
    public static final String COLUMNS_LEAGUES= COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+"  varchar(255) NOT NULL, "+COLUMN_FK_SPORT+" INTEGER NOT NULL,  "+COLUMN_IMG+"  varchar(255)";
    public static final String COLUMNS_COUNTRIE_LEAGUES=COLUMN_FK_COUNTRIE+" INTEGER NOT NULL, "+ COLUMN_FK_LEAGUE+" INTEGER NOT NULL";
    public static final String COLUMNS_TEAMS=COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+"  varchar(255) NOT NULL, "+COLUMN_IMG+" varchar(255) NOT NULL";
    //public static final String COLUMNS_MATCHES = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_FK_LEAGUE+" INTEGER NOT NULL, "+ COLUMN_FK_TEAM_HOME+" INTEGER NOT NULL, "+COLUMN_FK_TEAM_VISIT+" INTEGER NOT NULL, "+COLUMN_DATE+" varchar(255) NOT NULL, "+COLUMN_HOUR+"  varchar(255) NOT NULL, "+COLUMN_FK_TYPE_MATCH+" INTEGER NOT NULL, " + COLUMN_FK_TYPE_DATE+" INTEGER NOT NULL, "+COLUMN_STATUS_NAME + " varchar(255) NOT NULL, " + COLUMN_ROUND + " varchar(255) NOT NULL, " + COLUMN_STADIUM + " varchar(255) NOT NULL, " +  COLUMN_COUNTRIES_NAME + " varchar(255) NOT NULL, " + COLUMN_CITY + " varchar(255) NOT NULL";
    public static final String COLUMNS_MATCHES = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_FK_LEAGUE+" INTEGER NOT NULL, "+ COLUMN_FK_TEAM_HOME+" INTEGER NOT NULL, "+COLUMN_FK_TEAM_VISIT+" INTEGER NOT NULL, "+COLUMN_DATE+" varchar(255) NOT NULL, "+COLUMN_HOUR+"  varchar(255) NOT NULL, "+COLUMN_FK_TYPE_MATCH+" INTEGER NOT NULL, " + COLUMN_FK_TYPE_DATE+" INTEGER NOT NULL, " + COLUMN_ROUND + " varchar(255) NOT NULL, " + COLUMN_STADIUM + " varchar(255) NOT NULL, " +  COLUMN_COUNTRIES_NAME + " varchar(255) NOT NULL, " + COLUMN_CITY + " varchar(255) NOT NULL";

    public static final String COLUMNS_BETTINGS = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_FK_USER+" INTEGER NOT NULL, "+COLUMN_FK_MATCH+" INTEGER NOT NULL, "+COLUMN_FK_TEAM+" INTEGER NOT NULL, "+COLUMN_BETTING+" INTEGER NOT NULL";
    public static final String COLUMNS_BETTING_HISTORY = COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +COLUMN_ID_BETTING +" INTEGER, "+ COLUMN_BETTING+" INTEGER, " + COLUMN_ACTIVE + " INTEGER, " + COLUMN_FK_MATCH + " INTEGER, " + COLUMN_DATA + "  varchar(255), " + COLUMN_HOUR + "  varchar(255), " + COLUMN_TYPE_MATCHES + "  varchar(255), " + COLUMN_STATUS + "  varchar(255), " + COLUMN_FK_TEAM_HOME + " INTEGER, " + COLUMN_FK_TEAM_VISIT + " INTEGER, " + COLUMN_FK_TEAM_WIN + " INTEGER ,"+COLUMN_FK_USER_TEAM+" INTEGER, " +COLUMN_FK_USER_CHALLENGING_TEAM+" INTEGER, "+COLUMN_ROUND+"  varchar(255)";
    public static final String COLUMNS_USERS = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_ID_USER+"  INTEGER, "+COLUMN_NAME+" varchar(255) NOT NULL,"+COLUMN_LASTNAME+" varchar(255), "+COLUMN_EMAIL+" varchar(255) NOT NULL, "+COLUMN_PSW+" varchar(255), "+COLUMN_IMG+" varchar(255), "+COLUMN_COINTS+" INTEGER NOT NULL";
    public static final String COLUMNS_LEAGUE_TEAMS = COLUMN_FK_LEAGUE+" INTEGER, "+COLUMN_FK_TEAM+" INTEGER";
    public static final String COLUMNS_TYPE_MATCHES = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+" varchar(255) NOT NULL";
    public static final String COLUMNS_WALLET = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_CURRENT_BALANCE+" INTEGER, "+COLUMN_CURRENT_COINTS+" INTEGER";
    public static final String COLUMNS_MOVEMENTS = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_FK_TYPE_MOVEMENT+" INTEGER, "+COLUMN_FK_TYPE_BALANCE+" INTEGER, "+COLUMN_BALANCE+" INTEGER";
    public static final String COLUMNS_TYPE_BALANCE = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_TYPE_BALANCE+" INTEGER";
    public static final String COLUMNS_TYPE_MOVEMENTS = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_TYPE_MOVEMENT+" INTEGER";
    public static final String COLUMNS_NOTIFICATIONS = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_FK_TYPE_NOTIFICATION+" INTEGER, " + COLUMN_BALANCE_COINS+" INTEGER, " + COLUMN_BALANCE_MONEY+" INTEGER, " + COLUMN_PRIZE_IMG+"  varchar(255), " + COLUMN_PRIZE_NAME+"  varchar(255), " + COLUMN_INFO_HEADER+" varchar(255), " + COLUMN_INFO_BODY+"  varchar(255) ";
    public static final String COLUMNS_TYPE_NOTIFICATIONS = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NOTIFICATION+"  varchar(255)";
    public static final String COLUMNS_PRIZES =        COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_ID_PRIZE +" INTEGER, " + COLUMN_NAME+" varchar(255), "+ COLUMN_PRIZE_IMG+" varchar(255), "+ COLUMN_CANT+" INTEGER, "+COLUMN_FK_STATUS_PRIZE+" INTEGER, "+COLUMN_REQUEST_CREATION_DATE+" DATETIME DEFAULT CURRENT_TIMESTAMP";
    public static final String COLUMNS_STATUS_PRIZES = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_STATUS+" varchar(255)";
    public static final String COLUMNS_PREFERENCES = COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_FK_TEAM+" INTEGER";

    // table creation
    private static final String CREATE_CONFING  = "CREATE TABLE IF NOT EXISTS " + TABLE_CONFING + "("+COLUMNS_CONFING+")";
    private static final String CREATE_CONTINENTS = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTINENTS + "("+COLUMNS_CONTINENTS+")";
    private static final String CREATE_COUNTRIES = "CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRIES + "("+COLUMNS_COUNTRIES+")";
    public static final String CREATE_CONTINENT_COUNTRIES = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTINENT_COUNTRIES + "("+COLUMNS_CONTINENT_COUNTRIES+")";
    public static final String CREATE_SPORT= "CREATE TABLE IF NOT EXISTS " + TABLE_SPORTS + "("+COLUMNS_SPORTS+")";
    public static final String CREATE_LEAGUES = "CREATE TABLE IF NOT EXISTS " + TABLE_LEAGUES + "("+COLUMNS_LEAGUES+")";
    public static final String CREATE_COUNTRIE_LEAGUES = "CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRIE_LEAGUES + "("+COLUMNS_COUNTRIE_LEAGUES+")";
    public static final String CREATE_TEAMS = "CREATE TABLE IF NOT EXISTS " + TABLE_TEAMS + "("+COLUMNS_TEAMS+")";
    public static final String CREATE_MATCHES = "CREATE TABLE IF NOT EXISTS " + TABLE_MATCHES + "("+COLUMNS_MATCHES+")";
    public static final String CREATE_BETTINGS = "CREATE TABLE IF NOT EXISTS " + TABLE_BETTINGS + "("+COLUMNS_BETTINGS+")";
    public static final String CREATE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "("+COLUMNS_USERS+")";
    public static final String CREATE_BETTINGS_HISTORY = "CREATE TABLE IF NOT EXISTS " + TABLE_BETTINGS_HISTORY + "("+COLUMNS_BETTING_HISTORY+")";
    public static final String CREATE_LEAGUE_TEAMS = "CREATE TABLE IF NOT EXISTS " + TABLE_LEAGUE_TEAMS + "("+COLUMNS_LEAGUE_TEAMS+")";
    public static final String CREATE_TYPE_MATCHES = "CREATE TABLE IF NOT EXISTS " + TABLE_TYPE_MATCHES + "("+COLUMNS_TYPE_MATCHES+")";
    public static final String CREATE_WALLET = "CREATE TABLE IF NOT EXISTS " + TABLE_WALLET + "("+COLUMNS_WALLET+")";
    public static final String CREATE_MOVEMENTS = "CREATE TABLE IF NOT EXISTS " + TABLE_MOVEMENTS + "("+COLUMNS_MOVEMENTS+")";
    public static final String CREATE_TYPE_BALANCE = "CREATE TABLE IF NOT EXISTS " + TABLE_TYPE_BALANCE + "("+COLUMNS_TYPE_BALANCE+")";
    public static final String CREATE_TYPE_MOVEMENTS  = "CREATE TABLE IF NOT EXISTS " + TABLE_TYPE_MOVEMENTS + "("+COLUMNS_TYPE_MOVEMENTS+")";
    public static final String CREATE_NOTIFICATIONS  = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATIONS + "("+COLUMNS_NOTIFICATIONS+")";
    public static final String CREATE_TYPE_NOTIFICATIONS  = "CREATE TABLE IF NOT EXISTS " + TABLE_TYPE_NOTIFICATIONS + "("+COLUMNS_TYPE_NOTIFICATIONS+")";
    public static final String CREATE_PRIZES  = "CREATE TABLE IF NOT EXISTS " + TABLE_PRIZES + "("+COLUMNS_PRIZES+")";
    public static final String CREATE_STATUS_PRIZES  = "CREATE TABLE IF NOT EXISTS " + TABLE_STATUS_PRIZES + "("+COLUMNS_STATUS_PRIZES+")";
    public static final String CREATE_PREFERENCES  = "CREATE TABLE IF NOT EXISTS " + TABLE_PREFERENCES + "("+COLUMNS_PREFERENCES+")";

    // columns Inserts
    private String[] INSERT_CONTINENTS = { COLUMN_ID, COLUMN_NAME };
    private String[] INSERT_COUNTRIES = { COLUMN_ID, COLUMN_NAME };
    private String[] INSERT_CONTINENT_COUNTRIES  = { COLUMN_FK_CONTINENT, COLUMN_FK_COUNTRIE };
    private String[] INSERT_SPORTS = { COLUMN_ID, COLUMN_NAME, COLUMN_IMG };
    private String[] INSERT_LEAGUES = { COLUMN_ID, COLUMN_NAME, COLUMN_FK_SPORT, COLUMN_IMG };
    private String[] INSERT_COUNTRIE_LEAGUES = {  COLUMN_FK_COUNTRIE, COLUMN_FK_LEAGUE };
    private String[] INSERT_TEAMS = { COLUMN_ID, COLUMN_NAME, COLUMN_IMG };
    private String[] INSERT_MATCHES = { COLUMN_ID, COLUMN_FK_LEAGUE, COLUMN_FK_TEAM_HOME, COLUMN_FK_TEAM_VISIT, COLUMN_FK_DATE, COLUMN_FK_HOUR,COLUMN_FK_TYPE_MATCH, COLUMN_STATUS_NAME, COLUMN_ROUND, COLUMN_STADIUM, COLUMN_COUNTRIES_NAME, COLUMN_CITY};
    private String[] INSERT_BETTINGS = { COLUMN_ID, COLUMN_FK_USER, COLUMN_FK_MATCH, COLUMN_FK_TEAM, COLUMN_BETTING  };
    private String[] INSERT_BETTING_HISTORY = {  COLUMN_ID, COLUMN_BETTING, COLUMN_ACTIVE, COLUMN_FK_MATCH, COLUMN_DATA,
            COLUMN_HOUR, COLUMNS_TYPE_MATCHES, COLUMN_STATUS, COLUMN_FK_TEAM_HOME, COLUMN_FK_TEAM_VISIT, COLUMN_FK_TEAM_WIN,
            COLUMN_FK_USER_TEAM, COLUMN_FK_USER_CHALLENGING_TEAM, COLUMN_ROUND  };
    private String[] INSERT_USERS = { COLUMN_ID,COLUMN_NAME, COLUMN_LASTNAME, COLUMN_EMAIL, COLUMN_PSW, COLUMN_IMG, COLUMN_COINTS};
    private String[] INSERT_LEAGUE_TEAMS = { COLUMN_FK_LEAGUE, COLUMN_FK_TEAM};
    private String[] INSERT_TYPE_MATCHES = {  COLUMN_ID,COLUMN_NAME  };
    private String[] INSERT_WALLET = {  COLUMN_ID,COLUMN_CURRENT_BALANCE, COLUMN_CURRENT_COINTS  };
    private String[] INSERT_MOVEMENTS = {  COLUMN_ID,COLUMN_FK_TYPE_MOVEMENT, COLUMN_FK_TYPE_BALANCE, COLUMN_BALANCE };
    private String[] INSERT_TYPE_BALANCE = {  COLUMN_ID, COLUMN_TYPE_BALANCE };
    private String[] INSERT_TYPE_MOVEMENTS = {  COLUMN_ID, COLUMN_TYPE_MOVEMENT };
    private String[] INSERT_NOTIFICATIONS = { COLUMN_ID, COLUMN_FK_TYPE_NOTIFICATION, COLUMN_BALANCE_COINS, COLUMN_BALANCE_MONEY,
    COLUMN_PRIZE_IMG, COLUMN_PRIZE_NAME,  COLUMN_INFO_HEADER, COLUMN_INFO_BODY };
    private String[] INSERT_TYPE_NOTIFICATION = { COLUMN_ID, COLUMN_NOTIFICATION };
    private String[] INSERT_PRIZES  = { COLUMN_ID, COLUMN_NAME, COLUMN_PRIZE_IMG, COLUMN_CANT, COLUMN_CANT};
    private String[] INSERT_STATUS_PRIZES = { COLUMN_ID, COLUMN_STATUS };
    private String[] INSERT_PREFERENCES = { COLUMN_ID, COLUMN_FK_TEAM };


    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public synchronized void close() {
        if (sInstance != null)
            db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_CONFING);
            db.execSQL(CREATE_CONTINENTS);
            db.execSQL(CREATE_SPORT);
            db.execSQL(CREATE_COUNTRIES);
            db.execSQL(CREATE_CONTINENT_COUNTRIES);
            db.execSQL(CREATE_COUNTRIE_LEAGUES);
            db.execSQL(CREATE_LEAGUES);
            db.execSQL(CREATE_LEAGUE_TEAMS);
            db.execSQL(CREATE_TEAMS);
            db.execSQL(CREATE_MATCHES);
            db.execSQL(CREATE_TYPE_MATCHES);
            db.execSQL(CREATE_SPORT);
            db.execSQL(CREATE_USERS);
            db.execSQL(CREATE_BETTINGS_HISTORY);

            db.execSQL(CREATE_WALLET);
            db.execSQL(CREATE_MOVEMENTS);
            db.execSQL(CREATE_TYPE_BALANCE);
            db.execSQL(CREATE_TYPE_MOVEMENTS);

            db.execSQL(CREATE_NOTIFICATIONS);
            db.execSQL(CREATE_TYPE_NOTIFICATIONS);
            db.execSQL(CREATE_PRIZES);
            db.execSQL(CREATE_STATUS_PRIZES);

            db.execSQL(CREATE_PREFERENCES);


            /* db.execSQL(CREATE_BETTINGS);*/

            insertRowConfig(db);
            //insertContinents(db);
            //insertSports(db);
            insertTypeMatchs(db);

            insertTypeBalance(db);
            insertTypeMovements(db);
            insertTypeNotifications(db);
            insertStatusPrize(db);


        }catch(Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP IF TABLE EXISTS " + TABLE_CONFIG);

        // Create tables again
        onCreate(db);
    }

    // INSERT
    public static boolean insertRowConfig(SQLiteDatabase db) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NEW_INSTALATION, 0);

            createSuccessful = db.insertWithOnConflict(TABLE_CONFING, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    public static void insertContinents(SQLiteDatabase db){

        ArrayList<Continent> valueClauses = new ArrayList<Continent>();
        /*1*/  valueClauses.add(new Continent(1, "América"));
        /*2*/  valueClauses.add(new Continent(2, "Europa"));
        /*3*/  valueClauses.add(new Continent(3, "Internacional"));
        /*4*/  valueClauses.add(new Continent(4, "África"));
        /*5*/  valueClauses.add(new Continent(5, "Oceanía"));
        /*6*/  valueClauses.add(new Continent(6, "Asia"));

        for(int i=0; i< valueClauses.size();i++){
            insertRowContinents(db, valueClauses.get(i));
        }

    }
    public static boolean insertRowContinents(SQLiteDatabase db, Continent objContinent) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objContinent.getId());
            values.put(COLUMN_NAME, objContinent.getName());

            createSuccessful = db.insertWithOnConflict(TABLE_CONTINENTS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;

        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    public static void insertSports(SQLiteDatabase db){

        ArrayList<Sport> valueSports = new ArrayList<Sport>();
        /*1*/  valueSports.add(new Sport(1, "Soccer", "ic_soccer"));
        /*2*/  valueSports.add(new Sport(2, "Básquetbol", "ic_basketball"));
        /*3*/  valueSports.add(new Sport(3, "Béisbol", "ic_baseball"));
        /*4*/  valueSports.add(new Sport(4, "Fútbol Americano", "ic_football"));
        /*5*/  valueSports.add(new Sport(5, "Box", "ic_box"));
        /*6*/  valueSports.add(new Sport(6, "Tenis", "ic_tennis"));
        /*7*/  valueSports.add(new Sport(7, "Hockey", "ic_hockey"));
        /*8*/  valueSports.add(new Sport(8, "Voleibol", "ic_boleyball"));

        /*9*/  valueSports.add(new Sport(9, "CPMX8", "ic_cpmx"));
        /*10*/  valueSports.add(new Sport(10, "UFC", "ic_ufc"));


        for(int i=0; i< valueSports.size();i++){
            insertRowSports(db, valueSports.get(i));
        }

    }
    public static boolean insertRowSports(SQLiteDatabase db, Sport objSport) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objSport.getIdSport());
            values.put(COLUMN_NAME, objSport.getSportName());
            values.put(COLUMN_IMG, objSport.getSportImg());

            createSuccessful = db.insertWithOnConflict(TABLE_SPORTS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    public static void insertTypeMatchs(SQLiteDatabase db){

        ArrayList<TypeMatch> valueTypeMatchs = new ArrayList<TypeMatch>();
        /*1*/  valueTypeMatchs.add(new TypeMatch(1, "Liguilla"));
        /*2*/  valueTypeMatchs.add(new TypeMatch(2, "Amistoso"));
        /*3*/  valueTypeMatchs.add(new TypeMatch(3, "1/4 de final"));
        /*4*/  valueTypeMatchs.add(new TypeMatch(4, "1/8 de final"));
        /*5*/  valueTypeMatchs.add(new TypeMatch(5, "Semifinal"));
        /*6*/  valueTypeMatchs.add(new TypeMatch(6, "Final"));

        for(int i=0; i< valueTypeMatchs.size();i++){
            insertRowTypeMatchs(db, valueTypeMatchs.get(i));
        }

    }
    public static boolean insertRowTypeMatchs(SQLiteDatabase db, TypeMatch objTypeMatch) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objTypeMatch.getId());
            values.put(COLUMN_NAME, objTypeMatch.getName());

            createSuccessful = db.insertWithOnConflict(TABLE_TYPE_MATCHES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    public static void insertTypeBalance(SQLiteDatabase db){

        ArrayList<TypeBalance> valueTypeBalance = new ArrayList<TypeBalance>();
        /*1*/  valueTypeBalance.add(new TypeBalance(1, "Coins"));
        /*2*/  valueTypeBalance.add(new TypeBalance(2, "Money"));

        for(int i=0; i< valueTypeBalance.size();i++){
            insertRowTypeBalance(db, valueTypeBalance.get(i));
        }

    }
    public static boolean insertRowTypeBalance(SQLiteDatabase db, TypeBalance objTypeBalance) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objTypeBalance.getId());
            values.put(COLUMN_TYPE_BALANCE, objTypeBalance.getTypeBalance());


            createSuccessful = db.insertWithOnConflict(TABLE_TYPE_BALANCE, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    public static void insertTypeMovements(SQLiteDatabase db){

        ArrayList<TypeMovement> valueTypeMovement = new ArrayList<TypeMovement>();
        /*1*/  valueTypeMovement.add(new TypeMovement(1, "Retiro"));
        /*2*/  valueTypeMovement.add(new TypeMovement(2, "Añadido"));
        /*3*/  valueTypeMovement.add(new TypeMovement(3, "Consuelo"));
        /*4*/  valueTypeMovement.add(new TypeMovement(4, "Bonus"));

        for(int i=0; i< valueTypeMovement.size();i++){
            insertRowTypeMovements(db, valueTypeMovement.get(i));
        }

    }
    public static boolean insertRowTypeMovements(SQLiteDatabase db, TypeMovement objTypeMovement) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objTypeMovement.getId());
            values.put(COLUMN_TYPE_MOVEMENT, objTypeMovement.getTypeMovement());

            createSuccessful = db.insertWithOnConflict(TABLE_TYPE_MOVEMENTS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    public static void insertTypeNotifications(SQLiteDatabase db){

        ArrayList<TypeNotification> valueTypeNotification = new ArrayList<TypeNotification>();
        /*1*/  valueTypeNotification.add(new TypeNotification(1, "Bonificacion"));
        /*2*/  valueTypeNotification.add(new TypeNotification(2, "Rifa"));
        /*3*/  valueTypeNotification.add(new TypeNotification(3, "Info"));
        /*3*/  valueTypeNotification.add(new TypeNotification(4, "Ganador Rifa"));

        for(int i=0; i< valueTypeNotification.size();i++) {
            insertRowTypeNotifications(db, valueTypeNotification.get(i));
        }

    }
    public static boolean insertRowTypeNotifications(SQLiteDatabase db, TypeNotification objTypeNotification) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objTypeNotification.getId());
            values.put(COLUMN_NOTIFICATION, objTypeNotification.getTypeNotification());

            createSuccessful = db.insertWithOnConflict(TABLE_TYPE_NOTIFICATIONS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    public static boolean insertNotification(SQLiteDatabase db, Notification objNotification) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            //values.put(COLUMN_ID, objNotification.getI());
            values.put(COLUMN_FK_TYPE_NOTIFICATION, objNotification.getTypeNotification().getId());

            switch (objNotification.getTypeNotification().getId()){
                //bonificacion
                case 1:
                    values.put(COLUMN_BALANCE_COINS, objNotification.getBalanceCoins());
                    values.put(COLUMN_BALANCE_MONEY, objNotification.getBalanceMoney());
                break;

                //rifa
                case 2:
                    //if(objNotification.getPrize().equals(null)){
                    if(objNotification.getPrize()==null){
                        values.put(COLUMN_PRIZE_IMG, "");
                        values.put(COLUMN_PRIZE_NAME, "");
                    }else {
                        values.put(COLUMN_PRIZE_IMG, objNotification.getPrize().getPrizesImg());
                        values.put(COLUMN_PRIZE_NAME, objNotification.getPrize().getPrizesName());
                    }
                break;

                //info
                case 3:
                    values.put(COLUMN_INFO_HEADER, objNotification.getInfoHeader());
                    values.put(COLUMN_INFO_BODY, objNotification.getInfoBody());
                break;
            }

            createSuccessful = db.insertWithOnConflict(TABLE_NOTIFICATIONS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    public static void insertStatusPrize(SQLiteDatabase db){

        ArrayList<TypeNotification> valueTypeNotification = new ArrayList<TypeNotification>();
        /*1*/  valueTypeNotification.add(new TypeNotification(1, "Pendiente, contacto"));
        /*2*/  valueTypeNotification.add(new TypeNotification(2, "Pendiente entrega"));
        /*3*/  valueTypeNotification.add(new TypeNotification(3, "Entregado"));

        for(int i=0; i< valueTypeNotification.size();i++) {
            insertRowStatusPrize(db, valueTypeNotification.get(i));
        }

    }
    public static boolean insertRowStatusPrize(SQLiteDatabase db, TypeNotification objTypeNotification) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objTypeNotification.getId());
            values.put(COLUMN_STATUS, objTypeNotification.getTypeNotification());

            createSuccessful = db.insertWithOnConflict(TABLE_STATUS_PRIZES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    public static boolean insertRowCountries(SQLiteDatabase db, Countries objCountries) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objCountries.getId());
            values.put(COLUMN_NAME, objCountries.getNameCountries());

            createSuccessful = db.insertWithOnConflict(TABLE_COUNTRIES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean insertRowContinentsCountries(SQLiteDatabase db, ContinentsCountries objCC) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FK_CONTINENT, objCC.getFkContinent());
            values.put(COLUMN_FK_COUNTRIE, objCC.getFkCountrie());

            createSuccessful = db.insertWithOnConflict(TABLE_CONTINENT_COUNTRIES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean insertRowLeagues(SQLiteDatabase db, League objLeague) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objLeague.getId());
            values.put(COLUMN_NAME, objLeague.getNameLeague());
            values.put(COLUMN_IMG, objLeague.getImgLeague());
            values.put(COLUMN_FK_SPORT, objLeague.getFkSport());


            createSuccessful = db.insertWithOnConflict(TABLE_LEAGUES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean insertRowCountrieLeagues(SQLiteDatabase db, CountrieLeagues objContrieLeague) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FK_COUNTRIE, objContrieLeague.getFkCountrie());
            values.put(COLUMN_FK_LEAGUE, objContrieLeague.getFkLeague());


            createSuccessful = db.insertWithOnConflict(TABLE_COUNTRIE_LEAGUES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean insertRowTeam(SQLiteDatabase db, Team objTeam) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, objTeam.getId());
            values.put(COLUMN_NAME, objTeam.getName());
            values.put(COLUMN_IMG, objTeam.getImg());

            createSuccessful = db.insertWithOnConflict(TABLE_TEAMS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean insertRowMatch(SQLiteDatabase db, Match objMatch) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, objMatch.getId());
            values.put(COLUMN_FK_LEAGUE, objMatch.getFkLeague());
            values.put(COLUMN_FK_TEAM_HOME, objMatch.getFkTeamHome());
            values.put(COLUMN_FK_TEAM_VISIT, objMatch.getFkTeamVisit());
            values.put(COLUMN_DATE, objMatch.getDate());
            values.put(COLUMN_HOUR, objMatch.getHour());

            values.put(COLUMN_FK_TYPE_MATCH, objMatch.getFkTypeMatch());
            values.put(COLUMN_FK_TYPE_DATE, objMatch.getFkTypeDate());

            //values.put(COLUMN_STATUS_NAME, objMatch.getStatusName());
            values.put(COLUMN_ROUND, objMatch.getRound());
            values.put(COLUMN_STADIUM, objMatch.getStadium());
            values.put(COLUMN_COUNTRIES_NAME, objMatch.getCountriesName());
            values.put(COLUMN_CITY, objMatch.getCity());


            createSuccessful = db.insertWithOnConflict(TABLE_MATCHES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean insertRowLeaguesTeam(SQLiteDatabase db, LeagueTeam objLeagueTeam) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FK_TEAM, objLeagueTeam.getFkTeam());
            values.put(COLUMN_FK_LEAGUE, objLeagueTeam.getFkLeague());

            createSuccessful = db.insertWithOnConflict(TABLE_LEAGUE_TEAMS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean insertRowSportLeagues(SQLiteDatabase db, CountrieLeagues objContrieLeague) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FK_COUNTRIE, objContrieLeague.getFkCountrie());
            values.put(COLUMN_FK_LEAGUE, objContrieLeague.getFkLeague());


            createSuccessful = db.insertWithOnConflict(TABLE_COUNTRIE_LEAGUES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean insertUser(SQLiteDatabase db, User user, boolean newUser)  {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, user.getId());
            values.put(COLUMN_ID_USER, user.getId());
            values.put(COLUMN_NAME, user.getName());
            values.put(COLUMN_LASTNAME, user.getLastNames());
            values.put(COLUMN_EMAIL, user.getEmail());
            values.put(COLUMN_PSW, user.getPsw());
            values.put(COLUMN_IMG, user.getImgUser());

            if(newUser==true){
                values.put(COLUMN_COINTS, user.getCoins());
            }else{
                values.put(COLUMN_COINTS, user.getCoins());
            }

            createSuccessful = db.insertWithOnConflict(TABLE_USERS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;


            if( ! (user.getListMovement().equals(null))) {
                for (int i = 0; i < user.getListMovement().size(); i++) {
                    insertMovement(db, user.getListMovement().get(i));
                }
            }



            if( user.getWallet() != null) {
               insertWallet(db, user.getWallet());
            }

            if( user.getListHistorical() != null) {
                for (int i = 0; i < user.getListHistorical().size(); i++) {
                    insertBettingHistory(db, user.getListHistorical().get(i));
                }
            }

        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
            return false;
        }

        return createSuccessful;
    }
    public static boolean insertUserFB(SQLiteDatabase db, User user, boolean newUser)  {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_USER, user.getIdUser());
            values.put(COLUMN_NAME, user.getName());
            values.put(COLUMN_LASTNAME, user.getLastNames());
            values.put(COLUMN_EMAIL, user.getEmail());
            //values.put(COLUMN_PSW, user.getPsw());
            values.put(COLUMN_IMG, user.getImgUser());

            if(newUser==true){
                values.put(COLUMN_COINTS, user.getCoins());
            }else{
                values.put(COLUMN_COINTS, user.getCoins());
            }

            createSuccessful = db.insertWithOnConflict(TABLE_USERS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;


            if( ! (user.getListMovement().equals(null))) {
                for (int i = 0; i < user.getListMovement().size(); i++) {
                    insertMovement(db, user.getListMovement().get(i));
                }
            }

            if( ! (user.getWallet().equals(null))) {
                insertWallet(db, user.getWallet());
            }

            if( ! (user.getListHistorical().equals(null))) {
                for (int i = 0; i < user.getListHistorical().size(); i++) {
                    insertBettingHistory(db, user.getListHistorical().get(i));
                }
            }

        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }

        return createSuccessful;
    }

    public static boolean insertMovement(SQLiteDatabase db, Movement movement)  {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            //values.put(COLUMN_ID, movement.getId());
            values.put(COLUMN_FK_TYPE_MOVEMENT, movement.getFk_typeMovement());
            values.put(COLUMN_FK_TYPE_BALANCE, movement.getFk_typeBalance());
            values.put(COLUMN_BALANCE, movement.getBalance());

            createSuccessful = db.insertWithOnConflict(TABLE_MOVEMENTS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;

        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }

        return createSuccessful;
    }
    public static boolean insertWallet(SQLiteDatabase db, Wallet wallet)  {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            //values.put(COLUMN_ID, movement.getId());

            values.put(COLUMN_CURRENT_BALANCE, wallet.getCurrentBalance());
            values.put(COLUMN_CURRENT_COINTS, wallet.getCurrentBalanceCoins());

            createSuccessful = db.insertWithOnConflict(TABLE_WALLET, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;


        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }

        return createSuccessful;
    }
    public static boolean insertBettingHistory(SQLiteDatabase db, Historical historical)  {
        boolean createSuccessful = false;
        try {
            //String COLUMNS_BETTING_HISTORY = COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +COLUMN_ID_BETTING +" INTEGER, "+ COLUMN_BETTING+" INTEGER, " + COLUMN_ACTIVE + " INTEGER, " + COLUMN_FK_MATCH + " INTEGER, " + COLUMN_DATA + "  varchar(255), " + COLUMN_HOUR + "  varchar(255), " + COLUMN_TYPE_MATCHES + "  varchar(255), " + COLUMN_STATUS + "  varchar(255), " + COLUMN_FK_TEAM_HOME + " INTEGER, " + COLUMN_FK_TEAM_VISIT + " INTEGER, " + COLUMN_FK_TEAM_WIN + " INTEGER ,"+COLUMN_FK_USER_TEAM+" INTEGER, " +COLUMN_FK_USER_CHALLENGING_TEAM+" INTEGER";
            ContentValues values = new ContentValues();
            //values.put(COLUMN_ID, historical.getIdBetting());
            values.put(COLUMN_ID_BETTING, historical.getIdBetting());
            values.put(COLUMN_BETTING, historical.getBetting());
            values.put(COLUMN_ACTIVE, historical.getActive());
            values.put(COLUMN_FK_MATCH, historical.getIdMatch());
            values.put(COLUMN_DATA, historical.getDate());
            values.put(COLUMN_HOUR, historical.getHour());

            values.put(COLUMN_TYPE_MATCHES, historical.getTypeMatchName());
            values.put(COLUMN_STATUS, historical.getStatus());

            values.put(COLUMN_FK_TEAM_HOME, historical.getFkTeamHome());
            values.put(COLUMN_FK_TEAM_VISIT, historical.getFkTeamVisit());
            values.put(COLUMN_FK_TEAM_WIN, historical.getFkTeamWin());

            values.put(COLUMN_FK_USER_TEAM, historical.getFkUserTeam());
            values.put(COLUMN_FK_USER_CHALLENGING_TEAM, historical.getFkUserChallengingTeam());
            values.put(COLUMN_ROUND, historical.getRound());

            //values.put(COLUMN_FK_BETTING, betting.getId());

            createSuccessful = db.insertWithOnConflict(TABLE_BETTINGS_HISTORY, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }

        return createSuccessful;
    }
    public static boolean insertNotifications(SQLiteDatabase db, Notification notification)  {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
            //values.put(COLUMN_ID, movement.getId());
            values.put(COLUMN_FK_TYPE_NOTIFICATION, notification.getTypeNotification().getId());
            values.put(COLUMN_BALANCE_COINS, notification.getBalanceCoins());
            values.put(COLUMN_BALANCE_MONEY, notification.getBalanceMoney());
            values.put(COLUMN_PRIZE_IMG, notification.getPrizeImg());
            values.put(COLUMN_PRIZE_NAME, notification.getPrizeName());
            values.put(COLUMN_INFO_HEADER, notification.getInfoHeader());
            values.put(COLUMN_INFO_BODY, notification.getInfoBody());

            createSuccessful = db.insertWithOnConflict(TABLE_NOTIFICATIONS, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;


        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }

        return createSuccessful;
    }
    public static boolean insertPrize(SQLiteDatabase db, Award award, int opt)  {
        boolean createSuccessful = false;

        //Inserting the data includes this:
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_PRIZE, award.getId());
            values.put(COLUMN_NAME, award.getName());
            values.put(COLUMN_PRIZE_IMG, award.getImg());
            values.put(COLUMN_CANT, 1);


            if(opt==1){
                values.put(COLUMN_REQUEST_CREATION_DATE, award.getRequestCreationDate());
                values.put(COLUMN_FK_STATUS_PRIZE, award.getFkStatusPrize());
            }else{
                values.put(COLUMN_REQUEST_CREATION_DATE, getDateTime());
                values.put(COLUMN_FK_STATUS_PRIZE, 1);
            }


            createSuccessful = db.insertWithOnConflict(TABLE_PRIZES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;

        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }

        return createSuccessful;
    }

    public static boolean insertRowPreferences(SQLiteDatabase db, Preference objPreference) {
        boolean createSuccessful = false;
        try {
            ContentValues values = new ContentValues();
           // values.put(COLUMN_ID, objPreference.getId());
            values.put(COLUMN_FK_TEAM, objPreference.getFkTeam());

            createSuccessful = db.insertWithOnConflict(TABLE_PREFERENCES, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE) > 0;
        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return createSuccessful;
    }

    // UPDATE
    public static boolean updateConfig(SQLiteDatabase db) {
        boolean createSuccessful = false;
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NEW_INSTALATION, 1);

            String where = ""; // La cláusula where para identificar las columnas que desea actualizar.
            String[] value = {   }; // El valor de la cláusula where.

            db.update(TABLE_CONFING, contentValues, where, value);
            createSuccessful=true;

        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        } finally {
            db.close();
        }
        return createSuccessful;
    }
    public static boolean updateCointsUser(SQLiteDatabase db, int coins) {
        boolean createSuccessful = false;
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_COINTS, coins);

            String where = ""; // La cláusula where para identificar las columnas que desea actualizar.
            String[] value = {   }; // El valor de la cláusula where.

            db.update(TABLE_USERS, contentValues, where, value);
            createSuccessful=true;

        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean updateWallet(SQLiteDatabase db, int coins) {
        boolean createSuccessful = false;
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_CURRENT_COINTS, coins);

            String where = ""; // La cláusula where para identificar las columnas que desea actualizar.
            String[] value = {   }; // El valor de la cláusula where.

            db.update(TABLE_WALLET, contentValues, where, value);
            createSuccessful=true;

        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return createSuccessful;
    }
    public static boolean updatePrize(SQLiteDatabase db, Award award){
        boolean createSuccessful = false;
        try {

            String query = "SELECT " +COLUMN_ID+" FROM "+TABLE_PRIZES+" " +
                    " WHERE "+COLUMN_ID_PRIZE+"="+award.getId()+" AND fkStatusPrice < "+award.getFkStatusPrize()+" ORDER BY "+COLUMN_REQUEST_CREATION_DATE+" ASC LIMIT 1";
/*
String query = "UPDATE "+TABLE_PRIZES +
                    " SET " +COLUMN_FK_STATUS_PRIZE+"=" +award.getFkStatusPrize()+
                    " WHERE "+ COLUMN_ID + "= " +
                    "(SELECT " +COLUMN_ID+" FROM "+TABLE_PRIZES+" " +
                    " WHERE "+COLUMN_ID_PRIZE+"="+award.getId()+" AND fkStatusPrice < "+award.getFkStatusPrize()+" ORDER BY "+COLUMN_REQUEST_CREATION_DATE+" ASC LIMIT 1)";

            */

           // db.rawQuery(query, null);
            Cursor cr = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    int id=cr.getInt(0);
                    query = "UPDATE "+TABLE_PRIZES +
                            " SET " +COLUMN_FK_STATUS_PRIZE+"=" +award.getFkStatusPrize()+
                            " WHERE "+ COLUMN_ID + "= " + id;
                    cr = db.rawQuery(query, null);
                    createSuccessful=true;

                } while(cr.moveToNext());
            }

        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return createSuccessful;

    }


    // DELETE
    public boolean deteleSports(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_MATCHES, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deteleContinents(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_MATCHES, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deteleContinentsCountry(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_MATCHES, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deteleCountry(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_MATCHES, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deteleCountryLeagues(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_MATCHES, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deteleLeagues(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_MATCHES, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deleteLeagueTeams(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_LEAGUE_TEAMS, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deleteTeams(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_TEAMS, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }


    public boolean deteleAllMatch(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_MATCHES, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deteleUser(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_USERS, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }

    public boolean deteleWallet(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_WALLET, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deteleMovement(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_MOVEMENTS, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deteleHistorical(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_BETTINGS_HISTORY, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean detelePrizes(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_PRIZES, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean deteleNotifications(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_NOTIFICATIONS, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean detelePreferences(SQLiteDatabase db) {
        boolean deleteSuccessful = false;
        try {
            String[] value = {  }; // El valor de la cláusula where.
            String where="";

            db.delete(TABLE_PREFERENCES, where, value);
            deleteSuccessful=true;

        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return deleteSuccessful;
    }
    public boolean detelePreferencesByIdTeam(SQLiteDatabase db, int idTeam) {
        return db.delete(TABLE_PREFERENCES, COLUMN_FK_TEAM + "=" + idTeam, null) > 0;
    }


    public static Boolean isNewInstalation(SQLiteDatabase db) {
        String query = "SELECT * FROM "+TABLE_CONFING;
        Boolean isNewInstalation=false;
        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    if(cr.getInt(0)==1){
                        isNewInstalation=false;
                    }else{
                        isNewInstalation=true;
                    }
                } while(cr.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        finally {
            cr.close();
        }
        return isNewInstalation;
    }
    public static int isTeamPreference(SQLiteDatabase db, int idTeam) {
        String query = "SELECT * FROM "+TABLE_PREFERENCES +
                        " WHERE "+COLUMN_FK_TEAM+"="+idTeam;
        int isTeamPreference=0;
        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    isTeamPreference=1;

                } while(cr.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return isTeamPreference;
    }

    public List<Continent> getAllContinent(SQLiteDatabase db){
        String query = "SELECT * FROM "+TABLE_CONTINENTS;

        /*
        String query = "SELECT countries.id, countries.name FROM " +TABLE_COUNTRIES+
                " INNER JOIN "+TABLE_CONTINENT_COUNTRIES+" ON continent_countries.fk_countrie=countries.id " +
                " INNER JOIN "+TABLE_CONTINENTS+" ON continents.id=continent_countries.fk_continent  " +
                " INNER JOIN "+TABLE_COUNTRIE_LEAGUES+" ON countrie_leagues.fk_countrie=countries.id " +
                " INNER JOIN "+TABLE_LEAGUES+" ON leagues.id=countrie_leagues.fk_league " +
                " INNER JOIN "+TABLE_SPORTS+" ON sports.id=leagues.fk_sport " +
                " WHERE continents.id="+idContinent +
                " AND sports.id=" +idSport+
                " GROUP BY countries.id";
*/

        List<Continent> lstContinents = new ArrayList<>();

        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    lstContinents.add(new Continent(cr.getInt(0), cr.getString(1)));
                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        finally {
            cr.close();
        }
        return lstContinents;
    }
    public List<Continent> getAllContinent(SQLiteDatabase db, int idSport){

        String query = "SELECT "+TABLE_CONTINENTS+".id, "+TABLE_CONTINENTS+"."+COLUMN_NAME+
                " FROM "+TABLE_CONTINENTS+
                " INNER JOIN "+TABLE_CONTINENT_COUNTRIES+" ON continent_countries.fk_continent=" +TABLE_CONTINENTS+".id "+
                " INNER JOIN "+TABLE_COUNTRIES+" ON "+ TABLE_COUNTRIES+".id=continent_countries.fk_countrie  " +

                " INNER JOIN "+TABLE_COUNTRIE_LEAGUES+" ON countrie_leagues.fk_countrie=countries.id "+
                " INNER JOIN "+TABLE_LEAGUES+" ON leagues.id=countrie_leagues.fk_league "+
                " INNER JOIN "+TABLE_MATCHES+" ON matches.fk_league = leagues.id"+
                //" INNER JOIN "+TABLE_SPORTS+" ON sports.id=leagues.fk_sport "+
                //" WHERE continents.id="+1 + " AND leagues.fk_sport="+idSport +*/
                " WHERE "+TABLE_LEAGUES+"."+COLUMN_FK_SPORT+" = "+idSport+
               // " WHERE "+TABLE_SPORTS+"."+COLUMN_ID+"="+idSport ;
               " GROUP BY "+TABLE_CONTINENTS+".id";

        List<Continent> lstContinents = new ArrayList<>();

        Cursor cr=null;
        try{
            cr = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    lstContinents.add(new Continent(cr.getInt(0), cr.getString(1)));

                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }finally{
            cr.close();
        }
        return lstContinents;
    }
    public List<Countries> getAllCountriesByIdContinent(SQLiteDatabase db, int idContinent, int idSport) {
        List<Countries> lstCountries = new ArrayList<>();
        String query = "SELECT countries.id, countries.name FROM " +TABLE_COUNTRIES+
                " INNER JOIN "+TABLE_CONTINENT_COUNTRIES+" ON continent_countries.fk_countrie=countries.id " +
                " INNER JOIN "+TABLE_CONTINENTS+" ON continents.id=continent_countries.fk_continent  " +
                " INNER JOIN "+TABLE_COUNTRIE_LEAGUES+" ON countrie_leagues.fk_countrie=countries.id " +
                " INNER JOIN "+TABLE_LEAGUES+" ON leagues.id=countrie_leagues.fk_league " +
                " INNER JOIN "+TABLE_SPORTS+" ON sports.id=leagues.fk_sport " +
                " WHERE continents.id="+idContinent +
                " AND sports.id=" +idSport+
                " GROUP BY countries.id";

        try{
            Cursor cr = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    int noLeagues= getNoLeaguesByCountriesId(db, cr.getInt(0), idSport);
                    if(noLeagues>0) {
                        lstCountries.add(new Countries(cr.getInt(0), cr.getString(1), noLeagues));
                    }
                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return lstCountries;
    }
    public List<Sport> getAllSports(SQLiteDatabase db) {
        List<Sport> lstSports = new ArrayList<>();
        String query = "SELECT * FROM " +TABLE_SPORTS;
        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    lstSports.add(new Sport(cr.getInt(0), cr.getString(1), cr.getString(2)));

                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return lstSports;
    }
    public List<League> getLeaguesBySportId(SQLiteDatabase db, int idSport) {
        List<League> lstLeagues = new ArrayList<>();
        String query = "SELECT "+TABLE_LEAGUES+"."+COLUMN_ID+", "+TABLE_LEAGUES+"."+COLUMN_NAME+", "+TABLE_LEAGUES+"."+COLUMN_IMG
                + " FROM " +TABLE_LEAGUES
                + " WHERE "+TABLE_LEAGUES+"."+COLUMN_FK_SPORT+"="+idSport;



        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String img=cr.getString(2);
                    if(img==null || img.equals("")){
                        img="league_1";
                    }

                    lstLeagues.add(new League(cr.getInt(0), cr.getString(1),img));

                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return lstLeagues;
    }
    public List<Team> getAllTeamsByIdLeague(SQLiteDatabase db, int idLeague) {
        List<Team> lstTeams = new ArrayList<>();
        String query = "SELECT "+TABLE_TEAMS+"."+COLUMN_ID+", " + TABLE_TEAMS+"."+COLUMN_NAME +", "+ TABLE_TEAMS+"."+COLUMN_IMG
                + " FROM " +TABLE_TEAMS+
                " INNER JOIN "+TABLE_LEAGUE_TEAMS+" ON "+TABLE_LEAGUE_TEAMS+"."+COLUMN_FK_TEAM+" = "+TABLE_TEAMS+"."+COLUMN_ID +
                " INNER JOIN "+TABLE_LEAGUES+" ON "+TABLE_LEAGUES+"."+COLUMN_ID+" ="+TABLE_LEAGUE_TEAMS+"."+COLUMN_FK_LEAGUE+
                " WHERE "+TABLE_TEAMS+"."+COLUMN_ID+"="+idLeague;


        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {

                    lstTeams.add(new Team(cr.getInt(0), cr.getString(1), cr.getString(2)));

                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return lstTeams;
    }


    public int getNoLeaguesByCountriesId(SQLiteDatabase db, int IdCountrie, int idSport) {
        int NoLeagues=0;
        /*String query = "SELECT COUNT(*)AS no FROM "+TABLE_COUNTRIES +
                " INNER JOIN "+TABLE_COUNTRIE_LEAGUES+" ON countries.id =countrie_leagues.fk_countrie " +
                " INNER JOIN "+TABLE_LEAGUES+" ON leagues.id =countrie_leagues.fk_league " +
                " WHERE countries.id=" +IdCountrie+" AND leagues.fk_sport="+idSport +
                " GROUP BY  leagues.id";
                */

        String query = "SELECT leagues.id, leagues.name FROM " + TABLE_LEAGUES +
                " INNER JOIN "+TABLE_COUNTRIE_LEAGUES+" ON leagues.id=countrie_leagues.fk_league" +
                " INNER JOIN "+TABLE_COUNTRIES+" ON countrie_leagues.fk_countrie=countries.id" +
                " INNER JOIN "+TABLE_MATCHES+" ON leagues.id=matches.fk_league" +
                " WHERE countries.id="+IdCountrie+" AND leagues.fk_sport="+idSport +
                " GROUP BY leagues.id";


        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    //NoLeagues=cr.getInt(0);
                    NoLeagues++;
                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return NoLeagues;
    }

    public List<League> getLeaguesByCountriesId(SQLiteDatabase db, int idCountrie, int idSport) {
        List<League> lstLeagues = new ArrayList<>();
        String query = "SELECT leagues.id, leagues.name FROM leagues " +
                " INNER JOIN "+TABLE_COUNTRIE_LEAGUES+" ON leagues.id=countrie_leagues.fk_league " +
                " INNER JOIN "+TABLE_COUNTRIES+" ON countrie_leagues.fk_countrie=countries.id " +
                " INNER JOIN "+TABLE_MATCHES+" ON leagues.id=matches.fk_league "+
                " WHERE countries.id=" +idCountrie+
                " AND leagues.fk_sport="+idSport+
                " LIMIT 1";
        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    lstLeagues.add(new League(cr.getInt(0), cr.getString(1)));
                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return lstLeagues;
    }
    public League getLeagueById(SQLiteDatabase db, int idLeague) {
        League objLeagues = null;
        String query = "SELECT leagues.id, leagues.name FROM " + TABLE_LEAGUES +
                " WHERE leagues.id=" +idLeague;
        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    objLeagues = new League(cr.getInt(0), cr.getString(1));
                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return objLeagues;
    }

    public List<Match> getMatchByLeagueId(SQLiteDatabase db, int idLeague){
        List<Match> lstMatchs = new ArrayList<>();
        String query = "SELECT matches.id, matches.date, matches.hour, leagues.id AS idLeague, leagues.name AS nameLeague, tHome.name AS homeName, tHome.img AS homeImg, tVisit.name AS visitName, tVisit.img AS visitImg  FROM matches " +
                "INNER JOIN "+TABLE_LEAGUES+" ON matches.fk_league= leagues.id " +
                "INNER JOIN "+TABLE_TEAMS+" tHome ON matches.fk_team_home=tHome.id " +
                "INNER JOIN "+TABLE_TEAMS+" tVisit ON matches.fk_team_visit=tVisit.id " +
                "WHERE leagues.id="+idLeague;
        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    lstMatchs.add(new Match(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(5), cr.getString(6), cr.getString(7), cr.getString(8)));
                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return lstMatchs;
    }
    public List<Match> getMatchDatePreference(SQLiteDatabase db, int fkTypeData){
        List<Match> lstMatchs = new ArrayList<>();
        String where="";
        if(fkTypeData==1){
            where=" WHERE matches.fk_type_Date=1 OR matches.fk_type_Date=3";
        }else{
            where=" WHERE matches.fk_type_Date="+fkTypeData;
        }

        String query = "SELECT matches.id, matches.date, matches.hour, tHome.name AS homeName, tHome.img AS homeImg, tVisit.name AS visitName, tVisit.img AS visitImg, matches.fk_type_Date, tHome.id AS homeId,  tVisit.id AS visitId" +
                " FROM matches " +
                "INNER JOIN "+TABLE_TEAMS+" tHome ON matches.fk_team_home=tHome.id " +
                "INNER JOIN "+TABLE_TEAMS+" tVisit ON matches.fk_team_visit=tVisit.id " +
                where;

        Cursor cr=null;
        try{
            cr = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {

                    if(isTeamPreference(db, cr.getInt(7))==1){
                        lstMatchs.add(new Match(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5), cr.getString(6)));
                    }else if(isTeamPreference(db, cr.getInt(8))==1){
                        lstMatchs.add(new Match(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5), cr.getString(6)));
                    }
                    else{
                        Log.e(null, "no esta en favoritos");
                    }

                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        finally {
            if(cr!=null){
                cr.close();
            }
        }
        return lstMatchs;
    }

    public Match getMatchById(SQLiteDatabase db, int idMatch) {
        Match match = null;
        String query = "SELECT *  FROM " + TABLE_MATCHES+
                " WHERE matches.id="+idMatch;
        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    match = new Match(cr.getInt(0), cr.getInt(1), cr.getInt(2), cr.getInt(3), cr.getString(4), cr.getString(5), cr.getInt(6));

                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return match;
    }

    /*
    public List<Match> getMatchTodayPreference(SQLiteDatabase db){

        return lstMatchs;
    }
*/

    public List<Movement> getMovements(SQLiteDatabase db){

        List<Movement> lstMovements = new ArrayList<>();
        String query = "SELECT movements.id, movements.balance, typeMovements.typeMovement " +
                "FROM " + TABLE_MOVEMENTS +
                " INNER JOIN typeMovements ON movements.fk_typeMovement=typeMovements.id";

        Cursor cr = null;
        try{
            cr = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    lstMovements.add(new Movement(cr.getInt(0), cr.getInt(1), cr.getString(2)));
                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        } finally {
            if(cr!=null){
                cr.close();
            }

        }
        return lstMovements;
    }
    public List<Historical> getHistoricals(SQLiteDatabase db){

        List<Historical> lstHistoricals = new ArrayList<>();
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_BETTING+", " + COLUMN_ACTIVE + ", " + COLUMN_FK_MATCH + ", " + COLUMN_DATA + ", " + COLUMN_HOUR + ", " + COLUMN_TYPE_MATCHES + ", " + COLUMN_STATUS + ", " + COLUMN_FK_TEAM_HOME + ", " + COLUMN_FK_TEAM_VISIT + ", " + COLUMN_FK_TEAM_WIN +  ", " + COLUMN_FK_USER_TEAM+  ", " + COLUMN_FK_USER_CHALLENGING_TEAM +", " + COLUMN_ROUND
                + " FROM "+ TABLE_BETTINGS_HISTORY;

        Cursor cr=null;
        try{
            cr = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    lstHistoricals.add( new Historical( cr.getInt(1), cr.getInt(2),cr.getInt(3), cr.getString(4), cr.getString(5), cr.getString(6), cr.getString(7), cr.getInt(8), cr.getInt(9), cr.getInt(10), cr.getInt(11), cr.getInt(12), cr.getString(13) ) );
                    //Id cr.getInt(0),
                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        finally {
            if(cr!=null){
                cr.close();
            }
        }
        return lstHistoricals;
    }
    public List<Notification> getNotifications(SQLiteDatabase db){

        List<Notification> lstNotifications = new ArrayList<>();

        String query = "SELECT " + TABLE_NOTIFICATIONS + "." + COLUMN_ID + ", " + TABLE_NOTIFICATIONS + "." +  COLUMN_FK_TYPE_NOTIFICATION + ", " + TABLE_NOTIFICATIONS + "." + COLUMN_BALANCE_COINS + ", " + TABLE_NOTIFICATIONS + "." +  COLUMN_BALANCE_MONEY + ", " + TABLE_NOTIFICATIONS + "." + COLUMN_PRIZE_IMG + ", " + TABLE_NOTIFICATIONS + "." + COLUMN_PRIZE_NAME + ", " + TABLE_NOTIFICATIONS + "." + COLUMN_INFO_HEADER + ", " + TABLE_NOTIFICATIONS + "." +  COLUMN_INFO_BODY+ ", "+TABLE_TYPE_NOTIFICATIONS+"."+COLUMN_ID+", "+TABLE_TYPE_NOTIFICATIONS+"."+COLUMN_NOTIFICATION
                    + " FROM "+ TABLE_NOTIFICATIONS
                    + " INNER JOIN "+TABLE_TYPE_NOTIFICATIONS
                    + " ON " + TABLE_NOTIFICATIONS + "." + COLUMN_FK_TYPE_NOTIFICATION + "=" + TABLE_TYPE_NOTIFICATIONS+"."+COLUMN_ID;

        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    TypeNotification typeNotification = new TypeNotification();
                    typeNotification.setId(cr.getInt(8));
                    typeNotification.setTypeNotification(cr.getString(9));

                    lstNotifications.add(new Notification(cr.getInt(1), cr.getInt(2), cr.getInt(3), cr.getString(4), cr.getString(5), cr.getString(6), cr.getString(7), typeNotification));


                    //Id cr.getInt(0),
                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return lstNotifications;
    }
    public List<Award> getAwards(SQLiteDatabase db){
        List<Award> lstAwards = new ArrayList<>();
       /* String query = "SELECT " + TABLE_PRIZES+"."+COLUMN_ID+", " + TABLE_PRIZES+"."+COLUMN_NAME+", " + TABLE_PRIZES+"."+COLUMN_PRIZE_IMG+", "+TABLE_PRIZES+"."+COLUMN_CANT+", " + TABLE_STATUS_PRIZES+"."+COLUMN_STATUS+", "+TABLE_PRIZES+"."+COLUMN_REQUEST_CREATION_DATE+
                " FROM " + TABLE_PRIZES +
                " INNER JOIN "+TABLE_STATUS_PRIZES+" ON "+TABLE_PRIZES+"."+COLUMN_FK_STATUS_PRIZE+"="+TABLE_STATUS_PRIZES+"."+COLUMN_ID;
*/
        String query = "SELECT " + TABLE_PRIZES+"."+COLUMN_ID_PRIZE+", " + TABLE_PRIZES+"."+COLUMN_NAME+", " + TABLE_PRIZES+"."+COLUMN_PRIZE_IMG+", " + TABLE_STATUS_PRIZES+"."+COLUMN_STATUS+", "+TABLE_PRIZES+"."+COLUMN_REQUEST_CREATION_DATE+ ", "
                +TABLE_STATUS_PRIZES+"."+COLUMN_ID+", "+TABLE_PRIZES+"."+COLUMN_FK_STATUS_PRIZE+
                " FROM " + TABLE_PRIZES +
                " INNER JOIN "+TABLE_STATUS_PRIZES+" ON "+TABLE_PRIZES+"."+COLUMN_FK_STATUS_PRIZE+"="+TABLE_STATUS_PRIZES+"."+COLUMN_ID;


        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    //lstAwards.add(new Award(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getInt(3), cr.getString(4),cr.getString(5)));
                    lstAwards.add(new Award(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3),cr.getString(4),cr.getInt(5),cr.getInt(6)));

                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return lstAwards;
    }

    /*
    public TypeMatch getTypeMatchById(SQLiteDatabase db, int idTypeMatch) {
        Match TypeMatch = null;
        String query = "SELECT *  FROM " + TABLE_TYPE_MATCHES+
                " WHERE matches.id="+idMatch;
        Cursor cr = db.rawQuery(query, null);
        try{
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    match = new Match(cr.getInt(0), cr.getInt(1), cr.getInt(2), cr.getInt(3), cr.getString(4), cr.getString(5), cr.getInt(6));
                    League league = getLeagueById(db, cr.getInt(1));
                    Team teamHome = getTeamById(db, cr.getInt(2));
                    Team teamVisit = getTeamById(db, cr.getInt(3));
                    TypeMatch typeMatch = getTypeMatchById(db, cr.getInt(6));

                    match = new Match(cr.getInt(0), league, teamHome, teamVisit, cr.getString(4), cr.getString(5), typeMatch);

                } while(cr.moveToNext());
            }
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return typeMatch;
    }
*/
    public Team getTeamById(SQLiteDatabase db, int idTeam) {
        Team team= new Team();
        String query = "SELECT * FROM " +TABLE_TEAMS+
                " WHERE id="+idTeam;
        Cursor cr = db.rawQuery(query, null);
        try {
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                team = new Team(cr.getInt(0), cr.getString(1), cr.getString(2));
            }
            while (cr.moveToNext()) ;
        }

        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return team;
    }
    public User getUser(SQLiteDatabase db) {
        User user=null;
        String query = "SELECT "+COLUMN_ID+", "+COLUMN_ID_USER+", "+COLUMN_NAME+","+COLUMN_LASTNAME+", "+COLUMN_EMAIL+", "+COLUMN_PSW+", "+COLUMN_IMG+", "+COLUMN_COINTS+" FROM " +TABLE_USERS;;

        Cursor cr=null;
        try {
            cr = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                user = new User(cr.getInt(0), cr.getInt(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5),cr.getString(6), getCoinsWallet(db));
            }

            while (cr.moveToNext()) ;
        }

        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }finally {
            if(cr!=null){
                cr.close();
            }
        }
        //user.setWallet(getCoinsWallet());
        return user;
    }
    public int getCoinsWallet(SQLiteDatabase db) {
        int  coins=0;
        String query = "SELECT * FROM " +TABLE_WALLET;
        Cursor cr = null;
        try {
            cr = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            if (cr.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                coins = cr.getInt(2);
            }
            while (cr.moveToNext()) ;
        }

        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }finally {
            if(cr!=null){
                cr.close();
            }
        }
        return coins;
    }


    //Functions
    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }
    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}

