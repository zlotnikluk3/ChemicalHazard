package com.example.kojot.factor.gas.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kojot.R;
import com.example.kojot.factor.listener.RecyclerItemClickListener;
import com.example.kojot.factor.model.Gas;
import com.example.kojot.factor.widget.LetterTile;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactHolder> {

    private List<Gas> gasList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public ContactListAdapter(Context context) {
        this.context = context;
        this.gasList = new ArrayList<>();
    }

    private void add(Gas item) {
        gasList.add(item);
        notifyItemInserted(gasList.size() - 1);
    }

    public void addAll(List<Gas> gasList) {
        for (Gas gas : gasList) {
            add(gas);
        }
    }

    public void remove(Gas item) {
        int position = gasList.indexOf(item);
        if (position > -1) {
            gasList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Gas getItem(int position) {
        return gasList.get(position);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact_gas, parent, false);

        final ContactHolder contactHolder = new ContactHolder(view);

        contactHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = contactHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, contactHolder.itemView);
                    }
                }
            }
        });

        return contactHolder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        final Gas gas = gasList.get(position);

        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

        LetterTile letterTile = new LetterTile(context);

        Bitmap letterBitmap = letterTile.getLetterTile(gas.getName(),
                String.valueOf(gas.getId()), tileSize, tileSize);

        holder.thumb.setImageBitmap(letterBitmap);
        holder.name.setText(gas.getName());
        holder.M.setText(gas.getM() + "[g/mol]");
        holder.Cp.setText("Cp: " + gas.getCp() + "[J/(mol·K)]");
        holder.Cv.setText("Cv: " + gas.getCv() + "[J/(mol·K)]");
        holder.pac1.setText("pac1: " + gas.getPac1() + " [ppm]");
        holder.pac2.setText("pac2: " + gas.getPac2() + " [ppm]");
        holder.pac3.setText("pac3: " + gas.getPac3() + " [ppm]");
    }

    @Override
    public int getItemCount() {
        return gasList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class ContactHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView name;
        TextView M;
        TextView Cp;
        TextView Cv;
        TextView pac1;
        TextView pac2;
        TextView pac3;

        public ContactHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            name = (TextView) itemView.findViewById(R.id.name);
            M = (TextView) itemView.findViewById(R.id.m);
            Cp = (TextView) itemView.findViewById(R.id.cp);
            Cv = (TextView) itemView.findViewById(R.id.cv);
            pac1 = (TextView) itemView.findViewById(R.id.pac1);
            pac2 = (TextView) itemView.findViewById(R.id.pac2);
            pac3 = (TextView) itemView.findViewById(R.id.pac3);
        }
    }
}
