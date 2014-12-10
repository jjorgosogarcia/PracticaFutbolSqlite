package com.example.sadarik.futbol;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class ActiAgregarPartido extends Activity {

    private long id;
    private ListView lv;
    private GestorPartido gp;
    private AdaptadorPartido adp;
    private final int ACTIVIDADAGREGARPARTIDO=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_actiagregarpartido);
        gp= new GestorPartido(this);
        Intent i = getIntent();
        id=i.getLongExtra("id",0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actiagregarpartido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(ActiAgregarPartido.this,AgregarPartido.class);
            i.setType("agregar");
            startActivityForResult(i,ACTIVIDADAGREGARPARTIDO);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gp.open();
        Cursor c = gp.getCursor(null,null,null,id);
        adp = new AdaptadorPartido(this,c);
        lv = (ListView) findViewById(R.id.listView2);
        lv.setAdapter(adp);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gp.close();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Partido partido;
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACTIVIDADAGREGARPARTIDO:
                    gp.open();
                    partido=(Partido)data.getExtras().getSerializable("Agregarpartido");
                    partido.setIdjugador((int) id);
                    gp.insert(partido);
                    gp.getCursor().close();
                    adp.changeCursor(gp.getCursor());
                    Toast.makeText(this, R.string.partido_insertado, Toast.LENGTH_LONG).show();
            }
        }
    }
}
