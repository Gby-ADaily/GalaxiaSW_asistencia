package com.example.toshiba_glx.galaxiasw_asistencia;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    TextClock tclTime;
    static TextView txtDate,txtEstado;
    ImageButton mgbRegistrar;
    ListView lstRegistroHora;
    ArrayList<String> listaAsistencia = new ArrayList<String>();
    String[] estado= new String[]{"Entrada Mañana","Salida Mañana","Entrada Tarde","Salida Tarde"};
    int estadoOrden=0,am_pm;
    Boolean primero =true;
    public final Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tclTime=(TextClock) findViewById(R.id.tclTiempo);
        txtDate=(TextView) findViewById(R.id.txtFecha);
        txtEstado=(TextView) findViewById(R.id.txtEstadoAsistencia);
        mgbRegistrar = (ImageButton) findViewById(R.id.mgbRegistrar);
        lstRegistroHora = (ListView) findViewById(R.id.lstRegistroHora);

        sdf.setTimeZone(cal.getTimeZone());
        txtDate.setText(sdf.format(cal.getTime()));



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Calendar calen = Calendar.getInstance();
                txtEstado.setText(""+calen.get(Calendar.MINUTE)+"-"+calen.get(Calendar.SECOND)+"_"+calen.get(Calendar.AM_PM));
                am_pm = calen.get(Calendar.AM_PM);
                if(primero){
                    primero=false;
                    if(0 == am_pm){ //AM
                        estadoOrden=0;
                    }else if(1== am_pm){//PM
                        estadoOrden=2;
                    }
                }
                ponerEstado(false);
                handler.postDelayed(this, 900);
            }
        },900);

        mgbRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaAsistencia.add("- "+tclTime.getText()+"  --  "+estado[estadoOrden]);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        listaAsistencia );
                ponerEstado(true);
                lstRegistroHora.setAdapter(arrayAdapter);
            }
        });
    }
    private void ponerEstado(boolean click){
        if(am_pm == 0){
            if(estadoOrden==0 && click){
                estadoOrden=1;
            }else if(estadoOrden==1 && click){
                estadoOrden=0;
            }else if(estadoOrden==2 || estadoOrden==3){
                estadoOrden=estadoOrden-2;
            }
        }else if(am_pm == 1){
            if(estadoOrden==2 && click){
                estadoOrden=3;
            }else if(estadoOrden==3 && click){
                estadoOrden=2;
            }else if(estadoOrden==0 || estadoOrden==1){
                estadoOrden=estadoOrden+2;
            }
        }
        txtEstado.setText(estado[estadoOrden]);

    }
}
