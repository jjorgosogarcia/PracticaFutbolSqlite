package com.example.sadarik.futbol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class AgregarPartido extends Activity {

    EditText et1,et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_agregar_partido);
        et1 = (EditText) findViewById(R.id.etContrincante);
        et2 = (EditText) findViewById(R.id.etValoracion);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void agregarPartido(View view){
        Intent i = getIntent();
        String contricante;
        int valoracion;
        contricante = et1.getText().toString();
        valoracion= Integer.valueOf(et2.getText().toString());
        Partido p = new Partido(0,contricante,valoracion);
        i.putExtra("Agregarpartido",p);
        setResult(RESULT_OK,i);
        finish();
    }
}
