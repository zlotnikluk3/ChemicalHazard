package com.example.kojot.factor.liquid.adapter;

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
import com.example.kojot.factor.model.Liquid;
import com.example.kojot.factor.widget.LetterTile;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactHolder> {

    private List<Liquid> liquidList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public ContactListAdapter(Context context) {
        this.context = context;
        this.liquidList = new ArrayList<>();
    }

    private void add(Liquid item) {
        liquidList.add(item);
        notifyItemInserted(liquidList.size() - 1);
    }

    public void addAll(List<Liquid> liquidList) {
        for (Liquid liquid : liquidList) {
            add(liquid);
        }
    }

    public void remove(Liquid item) {
        int position = liquidList.indexOf(item);
        if (position > -1) {
            liquidList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Liquid getItem(int position) {
        return liquidList.get(position);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact_liq, parent, false);

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
        final Liquid liquid = liquidList.get(position);

        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

        LetterTile letterTile = new LetterTile(context);

        Bitmap letterBitmap = letterTile.getLetterTile(liquid.getName(),
                String.valueOf(liquid.getId()), tileSize, tileSize);

        holder.thumb.setImageBitmap(letterBitmap);
        holder.name.setText(liquid.getName());
        holder.M.setText(liquid.getM() + "[g/mol]");
        holder.Cp.setText("Cp: " + liquid.getCp() + "[J/(mol·K)]");
        holder.pac1.setText("pac1: " + liquid.getPac1() + " [ppm]");
        holder.pac2.setText("pac2: " + liquid.getPac2() + " [ppm]");
        holder.pac3.setText("pac3: " + liquid.getPac3() + " [ppm]");
    }

    @Override
    public int getItemCount() {
        return liquidList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class ContactHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView name;
        TextView M;
        TextView Cp;
        TextView pac1;
        TextView pac2;
        TextView pac3;

        public ContactHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            name = (TextView) itemView.findViewById(R.id.name);
            M = (TextView) itemView.findViewById(R.id.m);
            Cp = (TextView) itemView.findViewById(R.id.cp);
            pac1 = (TextView) itemView.findViewById(R.id.pac1);
            pac2 = (TextView) itemView.findViewById(R.id.pac2);
            pac3 = (TextView) itemView.findViewById(R.id.pac3);
        }
    }
}
