package com.example.adivinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.adivinanumero.MESSAGE";
    private static EditText nameRanking;
    private  String name = "vacio";
    public int tries = 0;
    private AlertDialog adRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareAlertDialog();
        final Button btn = findViewById(R.id.btnEnviar);
        final TextView etTexto = findViewById(R.id.etTexto);
        etTexto.setInputType(InputType.TYPE_CLASS_NUMBER);
        final int numGuess = numRandom();
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                int numUser = Integer.parseInt(etTexto.getText().toString());
                if (numUser > numGuess) {
                    Toast.makeText(MainActivity.this, "El numero que has introducido es mas grande", Toast.LENGTH_SHORT).show();
                    etTexto.setText("");
                    tries++;
                } else if (numUser < numGuess) {
                    Toast.makeText(MainActivity.this, "El numero que has introducido es mas pequeño", Toast.LENGTH_SHORT).show();
                    etTexto.setText("");
                    tries++;
                } else if (numUser == numGuess) {
                    tries++;
                    Toast.makeText(MainActivity.this, "Correcto. Has acertado en " + tries + " intentos", Toast.LENGTH_SHORT).show();
                    etTexto.setText("");
                    tries = 0;
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
                String value = nameRanking.getText().toString();
                Intent intent = new Intent(getApplicationContext(), HallOfFame.class);
                intent.putExtra(EXTRA_MESSAGE, value);
                startActivity(intent);
            }
        });
    }


    public static int numRandom(){
        int numGuess = (int) (Math.random() * 100 + 1);
        return numGuess;
    }
}