package com.codemaster.fancorner;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codemaster.fancorner.model.TeamRank;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    List<TeamRank> teamRanks;
    int rankCurr = 1;

    public RankAdapter(List<TeamRank> teamRank) {
        this.teamRanks = teamRank;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rank.setText(String.valueOf(rankCurr));
        rankCurr++;
        String countAndTeam = teamRanks.get(position).getTeam() + "(" + teamRanks.get(position).getRank() + ")";
        holder.team.setText(countAndTeam);

        String teamName = teamRanks.get(position).getTeam();
        switch (teamName) {
            case "Kerala Blasters":
                holder.circleImageView.setImageResource(R.drawable.kerala);
                break;
            case "ATK Mohun Bagan":
                holder.circleImageView.setImageResource(R.drawable.atkmb);
                break;
            case "Odisha":
                holder.circleImageView.setImageResource(R.drawable.odisha);
                break;
            case "Hyderbad":
                holder.circleImageView.setImageResource(R.drawable.hyderbad);
                break;
            case "Bengaluru":
                holder.circleImageView.setImageResource(R.drawable.bengaluru);
                break;
            case "Goa":
                holder.circleImageView.setImageResource(R.drawable.goa);

                break;
            case "East Bengal":
                holder.circleImageView.setImageResource(R.drawable.eastbengal);

                break;
            case "Jamshedpur":
                holder.circleImageView.setImageResource(R.drawable.jamshedpur);
                break;
            case "Chennayin":
                holder.circleImageView.setImageResource(R.drawable.chennai);
                break;
            case "Mumbai City":
                holder.circleImageView.setImageResource(R.drawable.mumbai);
                break;
            case "NorthEast United":
                holder.circleImageView.setImageResource(R.drawable.northeast);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return teamRanks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank, team;
        CircleImageView circleImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rankText);
            team = itemView.findViewById(R.id.teamText);
            circleImageView = itemView.findViewById(R.id.jcImage);
        }
    }
}
