package com.example.adivinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.adivinanumero.MESSAGE";
    public static final String EXTRA_BITMAP  = "com.example.adivinanumero.BITMAP";
    Button btn;
    Button btnReset;
    private static EditText nameRanking;
    public int tries = 0;
    private AlertDialog adRanking;
    private String name;
    private int numGuess = 0;
    private TextView textView;
    private Chronometer chronometer;
    private long pauseOffset;
    private int min = 0, max = 100;
    private Bitmap rankingBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareAlertDialog();
        btn = findViewById(R.id.btnEnviar);
        btnReset = findViewById(R.id.btnReset);
        final TextView etTexto = findViewById(R.id.etTexto);
        textView = findViewById(R.id.textView);
        etTexto.setInputType(InputType.TYPE_CLASS_NUMBER);
        chronometer = findViewById(R.id.chrono);
        numRandom();
        textView.setText("Introduce un Numero entre " + min +" y " + max);
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(!etTexto.getText().toString().isEmpty()){
                    int numUser = Integer.parseInt(etTexto.getText().toString());
                    if (numUser > 100 || numUser < 0){
                        Toast.makeText(MainActivity.this, "El numero no se encuentra entre 0 y 100", Toast.LENGTH_SHORT).show();
                        tries++;
                        etTexto.setText("");
                    }
                    else if (numUser > numGuess) {
                        Toast.makeText(MainActivity.this, "El numero que has introducido es mas grande", Toast.LENGTH_SHORT).show();
                        etTexto.setText("");
                        max = numUser;
                        textView.setText("Introduce un Numero entre " + min +" y " + max);
                        tries++;
                        System.out.println(tries);
                    } else if (numUser < numGuess) {
                        Toast.makeText(MainActivity.this, "El numero que has introducido es mas pequeño", Toast.LENGTH_SHORT).show();
                        etTexto.setText("");
                        min = numUser;
                        textView.setText("Introduce un Numero entre " + min +" y " + max);
                        tries++;
                        System.out.println(tries);

                    } else if (numUser == numGuess) {
                        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                        chronometer.stop();
                        tries++;
                        Toast.makeText(MainActivity.this, "Correcto. Has acertado en " + tries + " intentos", Toast.LENGTH_SHORT).show();
                        etTexto.setText("");
                        System.out.println(pauseOffset/1000);
                        showRankingDialog();
                    }
                }
                else{
                    tries++;
                    Toast.makeText(MainActivity.this, "Se ha sumado un fallo por no introducir ningun numero", Toast.LENGTH_SHORT).show();
                }

            }

        });

        etTexto.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){ //|| (keyCode == KeyEvent.FLAG_EDITOR_ACTION)){
                    btn.performClick();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numRandom();
            }
        });

    }
    public void prepareAlertDialog(){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("¿Quieres guardar tu puntuación en el Ranking?");
        adb.setMessage("Introduce tu nombre: ");
        nameRanking = new EditText(this);
        adb.setView(nameRanking);

        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adRanking = adb.create();
        adRanking.setCanceledOnTouchOutside(false);
    }

    public void showRankingDialog(){
        adRanking.show();
        adRanking.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                name = nameRanking.getText().toString();
                nameRanking.setText("");
                System.out.println(tries);
                getCameraFoto();
                adRanking.dismiss();
            }
        });

        nameRanking.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    adRanking.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    public void openRanking(){


        String message = name + "," + tries + "," + (int)pauseOffset/1000;
        numRandom();
        Intent intent = new Intent(getApplicationContext(), HallOfFame.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_BITMAP, rankingBitmap);
        startActivity(intent);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            rankingBitmap = (Bitmap) extras.get("data");
            openRanking();
        }
    }

    public void  numRandom(){
        numGuess = 50; //(int) (Math.random() * 100 + 1);
        min = 0;
        max = 100;
        textView.setText("Introduce un Numero entre " + min +" y " + max);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        System.out.println(numGuess);
        tries = 0;
    }

    private void getCameraFoto() {
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 1);
    }
}