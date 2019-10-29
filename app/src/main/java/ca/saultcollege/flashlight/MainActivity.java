package ca.saultcollege.flashlight;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 123;
    private static final String TAG = "Flashlight";

    boolean hasCameraFlash = false;

    Button btnFlashLight, btnBlinkFlashLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 1
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        1 */

        /* 2
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        2 */

        btnFlashLight = findViewById(R.id.btnFlashLightToggle);
        btnBlinkFlashLight = findViewById(R.id.btnBlinkFlashLight);

        btnFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change "text" to change the pop up message
                Toast.makeText(MainActivity.this, "You clicked the flashlight button", Toast.LENGTH_SHORT).show();

                /* 3
                if (hasCameraFlash) {
                    if (btnFlashLight.getText().toString().contains("ON")) {
                        btnFlashLight.setText(R.string.turn_light_off);
                        flashLightOn();
                    } else {
                        btnFlashLight.setText(R.string.turn_light_on);
                        flashLightOff();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No flash available on your device", Toast.LENGTH_SHORT).show();
                }
                3 */
            }
        });

        btnBlinkFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change "text" to change the pop up message
                Toast.makeText(MainActivity.this, "You clicked the blink button", Toast.LENGTH_SHORT).show();

                /* 4
                if(btnFlashLight.getText().toString().contains("OFF")) {
                    blinkFlash();
                } else{
                    Toast.makeText(MainActivity.this, "Turn on the flashlight first.", Toast.LENGTH_SHORT).show();
                }
                4 */
            }
        });
    }


    // You do not have to change anything below this line


    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void blinkFlash() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String myString = "0101010101";
        long blinkDelay = 50; //Delay in ms
        for (int i = 0; i < myString.length(); i++) {
            if (myString.charAt(i) == '0') {
                try {
                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, true);
                } catch (CameraAccessException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            } else {
                try {
                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, false);
                } catch (CameraAccessException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }

            try {
                Thread.sleep(blinkDelay);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                } else {
                    btnFlashLight.setEnabled(false);
                    btnBlinkFlashLight.setEnabled(false);
                    Toast.makeText(MainActivity.this, "Permission denied to use the camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
