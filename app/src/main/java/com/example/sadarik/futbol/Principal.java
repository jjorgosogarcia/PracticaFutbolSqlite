package com.example.sadarik.futbol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Principal extends Activity {

    private GestorJugador gj;
    private Adaptador ad;
    private final int ACTIVIDADAGREGARJUGADOR=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        gj=new GestorJugador(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_aniadir) {
            Intent i = new Intent(Principal.this,AgregarJugador.class);
            i.setType("agregar");
            startActivityForResult(i,ACTIVIDADAGREGARJUGADOR);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gj.open();
        Cursor c = gj.getCursor();
        ad = new Adaptador(this,c);
        final ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(ad);
        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Principal.this,ActiAgregarPartido.class);
                intent.putExtra("id",id);
                startActivity(intent);
                }

        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Principal.this);
                alert.setTitle(R.string.borrar_jugador);
                alert.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Cursor cursor = (Cursor) lv.getItemAtPosition(position);
                                Jugador j = GestorJugador.getRow(cursor);
                                gj.delete(j);
                                ad.changeCursor(gj.getCursor());
                            }
                        });
                alert.setNegativeButton(android.R.string.no, null);
                alert.show();
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        gj.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Jugador ju;
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACTIVIDADAGREGARJUGADOR:
                    gj.open();
                    ju=(Jugador)data.getExtras().getSerializable("Agregarjugador");
                    gj.insert(ju);
                    gj.getCursor().close();
                    ad.changeCursor(gj.getCursor());
                    Toast.makeText(this, R.string.jugador_insertado, Toast.LENGTH_LONG).show();
            }
        }
    }
}
