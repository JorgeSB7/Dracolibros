package com.dracolibros.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dracolibros.R;
import com.dracolibros.model.BookEntity;

import java.util.ArrayList;

public class BookAdapter
    extends RecyclerView.Adapter<BookAdapter.BookViewHolder>
    implements View.OnClickListener {

        private ArrayList<BookEntity> items;
        private View.OnClickListener listener;

        // Clase interna:
        // Se implementa el ViewHolder que se encargará
        // de almacenar la vista del elemento y sus datos
        public static class BookViewHolder extends RecyclerView.ViewHolder {
            private ImageView imgbook;
            private TextView Nameb;
            private TextView Authorb;

            public BookViewHolder(View itemView) {
                super(itemView);
                imgbook = (ImageView) itemView.findViewById(R.id.imgbook);
                Nameb = (TextView) itemView.findViewById(R.id.Nameb);
                Authorb = (TextView) itemView.findViewById(R.id.Authorb);
            }

            public void BookBind(BookEntity item) {
                byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgbook.setImageBitmap(decodedByte);
                Nameb.setText(item.getName());
                Authorb.setText(item.getAuthor());
            }
        }

        // Contruye el objeto adaptador recibiendo la lista de datos
        public BookAdapter(@NonNull ArrayList <BookEntity> items) {
        this.items = items;
        }

        // Se encarga de crear los nuevos objetos ViewHolder necesarios
        // para los elementos de la colección.
        // Infla la vista del layout, crea y devuelve el objeto ViewHolder
        @Override
        public BookViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_row, parent, false);
        row.setOnClickListener(this);

        BookViewHolder bookViewHolder = new BookViewHolder(row);
        return bookViewHolder;
        }

        // Se encarga de actualizar los datos de un ViewHolder ya existente.
        @Override
        public void onBindViewHolder (BookViewHolder viewHolder,int position){
        BookEntity item = items.get(position);
        viewHolder.BookBind(item);
        }

        // Indica el número de elementos de la colección de datos.
        @Override
        public int getItemCount () {
        return items.size();
        }

        // Asigna un listener al elemento
        public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;
        }

        @Override
        public void onClick (View view){
        if (listener != null)
            listener.onClick(view);
        }

    }

