package com.codemaster.fancorner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    HashMap<String, Integer> rankMap;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return rankMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank, team;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
