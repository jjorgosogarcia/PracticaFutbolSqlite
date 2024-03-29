package com.example.sadarik.futbol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AgregarJugador extends Activity {

    private EditText et1,et2,et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_agregar);
        et1=(EditText)findViewById(R.id.etNombre);
        et2=(EditText)findViewById(R.id.etTelef);
        et3=(EditText)findViewById(R.id.etFnac);
    }

    public void agregarJugador(View view){
        Intent i = getIntent();
        String nombre,telefono,fecha;
        nombre = et1.getText().toString();
        telefono= et2.getText().toString();
        fecha=et3.getText().toString();
        Jugador j = new Jugador(nombre,telefono,fecha);
        i.putExtra("Agregarjugador",j);
        setResult(RESULT_OK,i);
        finish();
    }
}
