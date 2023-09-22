private static class DatabaseHelper extends SQLiteOpenHelper {
 private static final String DATABASE_NAME = "AtaDigitalDB";
 private static final int DATABASE_VERSION = 1;
 private static final String TABLE_USERS = "users";
 private static final String COLUMN_USER_ID = "id";
 private static final String COLUMN_USER_MATRICULA = "matricula";
 private static final String COLUMN_USER_SENHA = "senha";
 private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
 + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
 + COLUMN_USER_MATRICULA + " TEXT,"
 + COLUMN_USER_SENHA + " TEXT"
 + ")";
 private Context context;
 public DatabaseHelper(Context context) {
 super(context, DATABASE_NAME, null, DATABASE_VERSION);
 this.context = context;
 }
 @Override
 public void onCreate(SQLiteDatabase db) {
 db.execSQL(CREATE_TABLE_USERS);
 
 @Override
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
 onCreate(db);
 }
 private void insertUserData(SQLiteDatabase db, String matricula, String senha) {
 ContentValues values = new ContentValues();
 values.put(COLUMN_USER_MATRICULA, matricula);
 values.put(COLUMN_USER_SENHA, senha);
 long id = db.insert(TABLE_USERS, null, values);
 if (id == -1) {
 Toast.makeText(context, "Erro ao inserir dados de exemplo",
Toast.LENGTH_SHORT).show();
 }
 }
 }
}