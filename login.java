import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {
 private EditText editTextMatricula;
 private EditText editTextSenha;
 private Button buttonLogin;
 private DatabaseHelper databaseHelper;
 private SQLiteDatabase db;
 public static boolean isUsuarioLogado() {
 return false;
 }
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_login);
 editTextMatricula = findViewById(R.id.editTextMatricula);
 editTextSenha = findViewById(R.id.editTextSenha);
 buttonLogin = findViewById(R.id.buttonLogin);
 // Criação do banco de dados e tabelas
 databaseHelper = new DatabaseHelper(this);
 db = databaseHelper.getWritableDatabase();
 buttonLogin.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 String matricula = editTextMatricula.getText().toString();
 String senha = editTextSenha.getText().toString();
 if (autenticar(matricula, senha)) {
 // Autenticação bem-sucedida, definir o status de login para true
 setUsuarioLogado(true);
 // Navegar para a próxima atividade
 Intent intent = new Intent(LoginActivity.this, LerQRActivity.class);
 startActivity(intent);
 } else {
 Toast.makeText(LoginActivity.this, "Matrícula ou senha inválidos",
Toast.LENGTH_SHORT).show();
 }
 }
 });
 }
 private static boolean isUsuarioLogado(Context context) {
 // Recupera o status de login armazenado no SharedPreferences
 SharedPreferences sharedPreferences = context.getSharedPreferences("login",
Context.MODE_PRIVATE);
 return sharedPreferences.getBoolean("logado", false);
 }
 private void setUsuarioLogado(boolean logado) {
 // Armazena o status de login no SharedPreferences
 SharedPreferences sharedPreferences = getSharedPreferences("login",
Context.MODE_PRIVATE);
 SharedPreferences.Editor editor = sharedPreferences.edit();
 editor.putBoolean("logado", logado);
 editor.apply();
 }
 private boolean autenticar(String matricula, String senha) {
 Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
 new String[]{DatabaseHelper.COLUMN_USER_MATRICULA},
 DatabaseHelper.COLUMN_USER_MATRICULA + "=? AND " +
DatabaseHelper.COLUMN_USER_SENHA + "=?",
 new String[]{matricula, senha},
 null, null, null);
 int count = cursor.getCount();
 cursor.close();
 return count > 0;
 }
 @Override
 protected void onDestroy() {
 super.onDestroy();
 // Fechar a conexão com o banco de dados
 db.close();
 }
 // Classe interna para ajudar na criação e atualização do banco de dados
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
 // Inserir alguns dados de exemplo (opcional)
 }
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