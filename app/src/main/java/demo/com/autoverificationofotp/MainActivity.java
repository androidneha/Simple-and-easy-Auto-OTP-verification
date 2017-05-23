package demo.com.autoverificationofotp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Context context;
    EditText edt;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;
        edt =(EditText)findViewById(R.id.edt);
        if(Build.VERSION.SDK_INT < 23)
        {

        }
        else
        {
            requestContactPermission();
        }
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.d("Text",messageText);
                Toast.makeText(MainActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();
                edt.setText(messageText.split(" ")[1]);
            }
        });
    }
    private void requestContactPermission() {

        int hasContactPermission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS);

        if(hasContactPermission != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]   {Manifest.permission.RECEIVE_SMS}, 123);
        }
        else
        {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                // Check if the only required permission has been granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", "Contact permission has now been granted. Showing result.");
                    Toast.makeText(this,"Contact Permission is Granted",Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Permission", "Contact permission was NOT granted.");
                }
                break;
        }
    }

}
