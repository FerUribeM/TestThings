package com.ferbajoo.testthings.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ferbajoo.annotation.Foo;
import com.ferbajoo.testthings.R;
import com.ferbajoo.testthings.Utilities;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by
 *          amplemind on 6/27/18.
 */
@Foo(name = "SignatureActivity",value = "Firma electronica canvas",drawable = R.drawable.asignature_image)
public class SignatureActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_clear, btn_save_signature;

    private SignaturePad mSignature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_signature);

        btn_clear = findViewById(R.id.btn_clear);
        btn_save_signature = findViewById(R.id.btn_save_signature);

        btn_clear.setOnClickListener(this);
        btn_save_signature.setOnClickListener(this);

        Utilities.verifyHavePermissions(this,Utilities.PERMITION);

        mSignature = findViewById(R.id.view_signature);

        mSignature.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Log.e("OnStart","Signature");
            }

            @Override
            public void onSigned() {
                btn_save_signature.setEnabled(true);
                btn_clear.setEnabled(true);
            }

            @Override
            public void onClear() {
                btn_save_signature.setEnabled(false);
                btn_clear.setEnabled(false);
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_clear:
                mSignature.clear();
                break;
            case R.id.btn_save_signature:
                Bitmap signatureBitmap = mSignature.getSignatureBitmap();
                if (addJpgSignatureToGallery(signatureBitmap)){
                    Toast.makeText(this, "Firma guardada correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error al guardar la firma", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir(), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    public File getAlbumStorageDir() {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SignaturePad");
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

}
