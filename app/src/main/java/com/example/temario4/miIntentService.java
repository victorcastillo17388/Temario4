package com.example.temario4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class miIntentService extends AppCompatActivity {
    private EditText entrada;
    public static TextView salida;
    public static ProgressBar miprogress;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_intent_service);
        entrada = (EditText) findViewById(R.id.entrada);
        salida = (TextView) findViewById(R.id.salida);
        miprogress=  findViewById(R.id.miprogress);

        IntentFilter filtro = new IntentFilter(ReceptorOperacion.ACTION_RESP);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(new ReceptorOperacion(), filtro);
    }
    public void calcularOperacion(View view) {
        double n = Double.parseDouble(entrada.getText().toString());
        salida.append(n +"^2 = ");
        miprogress.setVisibility(View.VISIBLE);
      /*Intent i = new Intent(this, IntentServiceOperacion.class);
        i.putExtra("numero", n);
        startService(i);*/
        Intent i = new Intent();
        i.putExtra("numero", n);
        JobIntentServiceOperacion.enqueueWork(this,i);
    }

    public class ReceptorOperacion extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "com.example.intentservice.intent.action.RESPUESTA_OPERACION";
        @Override
        public void onReceive(Context context, Intent intent) {
            Double res = intent.getDoubleExtra("resultado", 0.0);
            salida.append(" " + res + "\n");
            miprogress.setVisibility(View.GONE);
        }
    }
}