package com.example.sadarik.futbol;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdaptadorPartido extends CursorAdapter {

    public AdaptadorPartido(Context context, Cursor c) {
        super(context, c, true);
    }

    public class ViewHolder{
        public TextView tv1,tv2,tv3;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.detalle_partido, parent, false);
        return v;
    }

    @Override
    public void bindView(View convertView, Context context, Cursor cursor) {

        ViewHolder vh = new ViewHolder();
        vh.tv1=(TextView) convertView.findViewById(R.id.tvIdJugador);
        vh.tv2=(TextView) convertView.findViewById(R.id.tvContrincante);
        vh.tv3=(TextView) convertView.findViewById(R.id.tvValoracion);
        Partido partido = GestorPartido.getRow(cursor);
        vh.tv1.setText(partido.getIdjugador()+"");
        vh.tv2.setText(partido.getContrincante());
        vh.tv3.setText(partido.getValoracion()+"");
    }
}
