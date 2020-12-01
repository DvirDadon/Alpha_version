package com.example.alpha_version;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrCode_reader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_reader);
    }


    /**
     * @since 23/11/2020
     * @param view
     */
    public void Scan(View view) {
        ScanCode();
    }

    /**
     * @since 23/11/2020
     * The method Scan the qr code
     */
    private void ScanCode() {
        IntentIntegrator ig = new IntentIntegrator(this);
        ig.setCaptureActivity(CaptureAct.class);
        ig.setOrientationLocked(false);
        ig.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        ig.setPrompt("Scanning Code");
        ig.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!= null){
            if(result.getContents() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Scanning Result");
                builder.setPositiveButton("Scan again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ScanCode();
                    }
                }).setNegativeButton("Finish", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                Toast.makeText(this, "No Results", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    /**
     * @since 1/12/2020
     * This method create option menu
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    /**
     * @since  1/12/2020
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuStorage){
            Intent si = new Intent(this,Storage.class);
            startActivity(si);
        }
        else if(id == R.id.menuGPS){
            Intent si = new Intent (this , GPS.class);
            startActivity(si);

        }
        else{
            Intent si = new Intent(this , QrCode_reader.class);
            startActivity(si);
        }
        return true;
    }
}