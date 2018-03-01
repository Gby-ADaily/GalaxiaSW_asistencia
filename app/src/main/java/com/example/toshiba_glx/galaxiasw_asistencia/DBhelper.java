package com.example.toshiba_glx.galaxiasw_asistencia;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhelper extends SQLiteOpenHelper {

    // TABLA USER
    public static final String TABLE_USER= "TBusuario";
    public static final String USER_ID = "_id";
    public static final String USER_DNI = "usuarioDNI";
    public static final String USER_NAME = "usuarioNombre";
    public static final String CUSER_EMAIL = "usuarioEmail";
    public static final String USER_PHONE = "usuarioTelefono";

    private static final String CREATE_TABLEUSER = "create table "
            + TABLE_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_DNI + " TEXT NOT NULL, "
            + USER_NAME + " TEXT NOT NULL, "
            + CUSER_EMAIL + " TEXT NOT NULL, "
            + USER_PHONE + " TEXT NOT NULL"+");";
    // TABLA ASISTENCIA
    public static final String TABLE_ASSISTANCE = "TBasistencia";
    public static final String ASSISTANCE_ID = "_id";
    public static final String ASSISTANCE_IDUSER = "asistencia_IDusuario";
    public static final String ASSISTANCE_STATE = "asistenciaEstado";
    public static final String ASSISTANCE_TIME = "asistenciaTiempo";
    public static final String ASSISTANCE_DATE = "asistenciaFecha";

    private static final String CREATE_TABLEASSISTANCE = "create table "
            + TABLE_ASSISTANCE + "("
            + ASSISTANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ASSISTANCE_IDUSER + " INTEGER NOT NULL, "
            + ASSISTANCE_STATE + " TEXT NOT NULL, "
            + ASSISTANCE_TIME + " TEXT NOT NULL,"
            + ASSISTANCE_DATE + " TEXT NOT NULL,"
            + " FOREIGN KEY("+ASSISTANCE_IDUSER+") REFERENCES "+TABLE_USER+"("+USER_ID+") "
            +");";

    // informacion del a base de datos
    static final String DB_NAME = "DBPlazaGuia";
    static  String direccion="";
    static final int DB_VERSION = 1;

    // Informacion de la base de datos

    //, FOREIGN KEY ("+ DEBERES_IDCURSO +") REFERENCES "+ TABLE_MEMBER +"(" + MIEMBRO_ID + "));";


    public DBhelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        direccion=db.getPath();
        db.execSQL(CREATE_TABLEUSER);
        db.execSQL(CREATE_TABLEASSISTANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);   //1
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSISTANCE);//2
        onCreate(db);
    }
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(direccion,
                    null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            Log.e("Error", "No existe la base de datos " + e.getMessage());
        }
        return checkDB != null;
    }

}
