package com.example.zxc;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Normalizer;
import java.util.ArrayList;

//import android.support.v7.app.AppCompatActivity;


public class MainActivity<respuestas> extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 2;
    private static final int RECONOCEDOR_VOZ = 7;
    private TextView escuchando;
    private TextView respuesta;
    private ArrayList <Respuestas> respuestas;
    private TextToSpeech leer;
    private Object TextView;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        inicializar();
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == RESULT_OK && requestCode == RECONOCEDOR_VOZ) {
            ArrayList <String> reconocido =
                    data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
            String escuchado = reconocido.get( 0 );
            escuchando.setText( escuchado );
            prepararRespuesta( escuchado );
        }
    }

    private void prepararRespuesta ( String escuchado ) {
        String normalizar = Normalizer.normalize( escuchado, Normalizer.Form.NFD );
        String sintilde = normalizar.replaceAll( "[^\\p{ASCII}]", "" );

        //    int resultado;
        //  String respuesta = respuest.get(0).getRespuestas();
        for (int i = 0; i < respuestas.size(); i++) {
            int resultado = sintilde.toLowerCase().indexOf( respuestas.get( i ).getCuestion() );
            if (resultado != -1) {
                //         respuesta = respuestas.get(i);
                responder( respuestas.get( i ) );
                return;
            }
        }

    }

    private void responder ( Respuestas respuesta ) {
        startActivity( respuesta.getIntent() );

        // respuesta.setText(respuestita);
  /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            leer.speak(respuestita, TextToSpeech.QUEUE_FLUSH, null, null);
        }else {
            leer.speak(respuestita, TextToSpeech.QUEUE_FLUSH, null);
        }*/
    }



    private void inicializar () {
        escuchando = ( TextView ) findViewById( R.id.textView );

        respuesta = ( TextView ) findViewById( R.id.tvRespuesta );
        respuestas = proveerDatos();

        leer = new TextToSpeech( this, this );
    }

    private ArrayList <Respuestas> proveerDatos () {
        ArrayList <Respuestas> respuestas = new ArrayList <>();
        respuestas.add( new Respuestas( "chiste", " toc toc quien es ",new Intent(this,MainActivity2.class)));
             /*   respuestas.add( new Respuestas( "adios", "good luck" ) );
        respuestas.add( new Respuestas( "como estas", "con ganas de ayudaret " ) );
        respuestas.add( new Respuestas( "nombre", "hola me llamo magtronic 2.0" ) );
        respuestas.add( new Respuestas( " hi", "estoyocupado buscamemas tarde " ) );*/
        return respuestas;
    }


    public void hablar ( View v ) {
        Intent hablar = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        hablar.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX" );
        startActivityForResult( hablar, RECONOCEDOR_VOZ );
    }


    @Override
    public void onInit ( int status ) {

    }
}