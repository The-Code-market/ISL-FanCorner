package com.codemaster.fancorner.model;

public class TeamRank implements Comparable<TeamRank>{
    String team;
    int rank;

    public TeamRank(String team, int rank) {
        this.team = team;
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int compareTo(TeamRank o) {
        int compareAmount = ((TeamRank) o).getRank();
        return (int) (compareAmount - (this.rank));
    }
}
