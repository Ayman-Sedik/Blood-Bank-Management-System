package com.dwidar.liveblood.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dwidar.liveblood.Model.Component.iBlood;
import com.dwidar.liveblood.Presenter.BloodsInfoActivityPresenter;
import com.dwidar.liveblood.R;

import java.util.ArrayList;

public class BloodAdapter extends RecyclerView.Adapter<BloodAdapter.Holder>
{
    private Context context;
    private ArrayList<iBlood> bloods;
    private BloodsInfoActivityPresenter presenter;

    public BloodAdapter(Context context, ArrayList<iBlood> bloods, BloodsInfoActivityPresenter p)
    {
        this.context = context;
        this.bloods = bloods;
        this.presenter = p;
    }


    @NonNull
    @Override
    public BloodAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.blood_item, parent, false);
        BloodAdapter.Holder holder = new BloodAdapter.Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BloodAdapter.Holder holder, final int position)
    {
        final String bloodName = this.bloods.get(position).getType();
        final int id = this.bloods.get(position).getId();

        String need = "Needed: " + this.bloods.get(position).getNeed();
        String avail = "Available: " + this.bloods.get(position).getAvailable();

        holder.bloodName.setText(bloodName);
        holder.need.setText(need);
        holder.avail.setText(avail);

        holder.btnDel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.deleteBlood(id);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return bloods.size();
    }


    public class Holder extends RecyclerView.ViewHolder
    {
        private TextView bloodName;
        private CardView bloodCard;
        private TextView avail;
        private TextView need;
        private Button btnDel;

        public Holder(@NonNull View itemView)
        {
            super(itemView);
            bloodName = itemView.findViewById(R.id.Lbl_BloodName);
            bloodCard = itemView.findViewById(R.id.BloodCard);
            btnDel = itemView.findViewById(R.id.BtnDel);
            avail = itemView.findViewById(R.id.Lbl_Avail);
            need = itemView.findViewById(R.id.Lbl_Need);
        }
    }

}
