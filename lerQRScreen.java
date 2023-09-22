import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
public class LerQRActivity extends AppCompatActivity implements BarcodeCallback {
 private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
 private boolean hasCameraPermission;
 private DecoratedBarcodeView barcodeView;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_ler_qractivity);
 barcodeView = findViewById(R.id.barcode_view);
 hasCameraPermission = ContextCompat.checkSelfPermission(this,
Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
 if (!hasCameraPermission) {
 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
CAMERA_PERMISSION_REQUEST_CODE);
 } else {
 setupBarcodeScanner();
 }
 }
 private void setupBarcodeScanner() {
 Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE);
 barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
 barcodeView.decodeContinuous(this);
 }
 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
@NonNull int[] grantResults) {
 super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
 if (grantResults.length > 0 && grantResults[0] ==
PackageManager.PERMISSION_GRANTED) {
 hasCameraPermission = true;
 setupBarcodeScanner();
 } else {
 Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show();
 finish();
 }
 }
 }
 @Override
 public void barcodeResult(BarcodeResult result) {
 String qrCodeValue = result.getText();
 Toast.makeText(this, qrCodeValue, Toast.LENGTH_SHORT).show();
 if (qrCodeValue.equals("atadigital://activity_marcar_presenca")) {
 Intent intent = new Intent(this, MarcarPresencaActivity.class);
 startActivity(intent);
 }
 }
 @Override
 public void possibleResultPoints(List<ResultPoint> resultPoints) {
 // Método vazio, não é necessário implementar.
 }
 @Override
 protected void onResume() {
 super.onResume();
 barcodeView.resume();
 }
 @Override
 protected void onPause() {
 super.onPause();
 barcodeView.pause();
 }
}