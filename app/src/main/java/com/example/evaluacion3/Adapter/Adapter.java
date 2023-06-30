package com.example.evaluacion3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaluacion3.BD.Gasto;
import com.example.evaluacion3.Entidades.Gasto.viewGastoActivity;
import com.example.evaluacion3.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    public static ArrayList<Gasto> list;
    public static ArrayList<Gasto> originallist;


    public Adapter(Context context, ArrayList<Gasto> list) {
        this.context = context;
        Adapter.list = list;
        originallist = new ArrayList<>();
        originallist.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_element ,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String txtProducto = list.get(position).getProducto();
        int txtPrecio = list.get(position).getPrecio();
        String tipo = list.get(position).getTipo();

        holder.MostrarProducto.setText(txtProducto);
        holder.MostrarPrecio.setText(String.valueOf(txtPrecio));

        Context context = holder.itemView.getContext();
        if (tipo.equals(context.getString(R.string.category_1))) {
            holder.iconImageView.setImageResource(R.drawable.ic_ahorros);
        } else if (tipo.equals(context.getString(R.string.category_2))) {
            holder.iconImageView.setImageResource(R.drawable.ic_alimentacion);
        } else if (tipo.equals(context.getString(R.string.category_3))) {
            holder.iconImageView.setImageResource(R.drawable.ic_transporte);
        } else if (tipo.equals(context.getString(R.string.category_4))) {
            holder.iconImageView.setImageResource(R.drawable.ic_serviciosbasicos);
        } else if (tipo.equals(context.getString(R.string.category_5))) {
            holder.iconImageView.setImageResource(R.drawable.ic_education);
        } else if (tipo.equals(context.getString(R.string.category_6))) {
            holder.iconImageView.setImageResource(R.drawable.ic_gastos);
        } else if (tipo.equals(context.getString(R.string.category_7))) {
            holder.iconImageView.setImageResource(R.drawable.ic_ahorros);
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView MostrarProducto, MostrarPrecio;
        ImageView iconImageView;
        ViewHolder(View itemView){
            super(itemView);
            MostrarProducto=itemView.findViewById(R.id.gastoTextView);
            MostrarPrecio=itemView.findViewById(R.id.precioTextView);
            iconImageView=itemView.findViewById(R.id.iconImageView);

            itemView.setOnClickListener(view -> {
                Context context =view.getContext();
                Intent intent = new Intent(context, viewGastoActivity.class);
                intent.putExtra("ID",list.get(getAdapterPosition()).getId());
                context.startActivities(new Intent[]{intent});
            });
        }

    }

}
