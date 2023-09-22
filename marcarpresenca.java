import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
public class MarcarPresencaActivity extends AppCompatActivity {
 private DatabaseHelper databaseHelper;
 private static final String SCHEME = "atadigital";
 private static final String HOST = "activity_marcar_presenca";
 private TextView qrCodeTextView;
 private TextView matriculaTextView;
 private TextView dateTextView;
 private TextView timeTextView;
 private TextView disciplineTextView;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_marcar_presenca);
 databaseHelper = new DatabaseHelper(this);
 qrCodeTextView = findViewById(R.id.qr_code_textview);
 matriculaTextView = findViewById(R.id.matricula_textview);
 dateTextView = findViewById(R.id.date_textview);
 timeTextView = findViewById(R.id.time_textview);
 disciplineTextView = findViewById(R.id.discipline_textview);
 // Ver se o usuário está logado
 if (!isUsuarioLogado()) {
 Toast.makeText(this, "Usuário não está logado", Toast.LENGTH_SHORT).show();
 finish(); // Finaliza a atividade se o usuário não estiver logado
 return;
 }
 Intent intent = getIntent();
 Uri data = intent.getData();

 }
 // pegando os dados do BD
 String userMatricula = databaseHelper.getMatriculaUsuario();
 String currentDate = new SimpleDateFormat("yyyy-MM-dd",
Locale.getDefault()).format(new Date());
 String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new
Date());
 String discipline = getDisciplineForCurrentDay();
 // Atualiza as informações na tela
 matriculaTextView.setText("Matrícula: " + userMatricula);
 dateTextView.setText("Data: " + currentDate);
 timeTextView.setText("Hora: " + currentTime);
 disciplineTextView.setText("Disciplina: " + discipline);
 }
 private boolean isUsuarioLogado() {
 SharedPreferences sharedPreferences = getSharedPreferences("login",
Context.MODE_PRIVATE);
 return sharedPreferences.getBoolean("logado", false);
 }
 private String getDisciplineForCurrentDay() {
 int currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
 switch (currentDayOfWeek) {
 case Calendar.MONDAY:
 return "Projeto de Banco de Dados";
 case Calendar.TUESDAY:
 return "Back-end Framework";
 case Calendar.WEDNESDAY:
 return "Desenvolvimento para Dispositivo Móvel";
 case Calendar.THURSDAY:
 return "Organização e Arquitetura de Computadores";
 case Calendar.FRIDAY:
 return "Redes de Computadores";
 default:
 return "";
 }
 }
}
