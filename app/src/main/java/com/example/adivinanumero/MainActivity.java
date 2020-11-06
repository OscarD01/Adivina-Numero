package com.example.adivinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
    private static EditText nameRanking;
    public int tries = 0;
    private AlertDialog adRanking;
    private String value;
    private int numGuess = 0;
    private TextView txtTimer;
    private Chronometer chronometer;
    private long pauseOffset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareAlertDialog();
        final Button btn = findViewById(R.id.btnEnviar);
        final TextView etTexto = findViewById(R.id.etTexto);
        txtTimer = findViewById(R.id.txtTimer);
        txtTimer.setText("");
        etTexto.setInputType(InputType.TYPE_CLASS_NUMBER);
        chronometer = findViewById(R.id.chrono);
        numRandom();
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                int numUser = Integer.parseInt(etTexto.getText().toString());
                if (numUser > numGuess) {
                    Toast.makeText(MainActivity.this, "El numero que has introducido es mas grande", Toast.LENGTH_SHORT).show();
                    etTexto.setText("");
                    tries++;
                    System.out.println(tries);
                } else if (numUser < numGuess) {
                    Toast.makeText(MainActivity.this, "El numero que has introducido es mas pequeño", Toast.LENGTH_SHORT).show();
                    etTexto.setText("");
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
                } else if(numUser > 100 || numUser < 0){
                    Toast.makeText(MainActivity.this, "El numero no se encuentra entre 0 y 100", Toast.LENGTH_SHORT).show();
                }
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
                value = nameRanking.getText().toString();
                System.out.println(tries);
                openRanking();
            }
        });
    }

    public void openRanking(){
        String message = value + "," + tries + "," + pauseOffset/1000;
        //Reset
        numRandom();
        Intent intent = new Intent(getApplicationContext(), HallOfFame.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    public void  numRandom(){
        numGuess = (int) (Math.random() * 100 + 1);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        numGuess = 20;
        tries = 0;
    }
}