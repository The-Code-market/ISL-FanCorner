package com.codemaster.fancorner.model;

public class RankPredictModel {
    String name,date,team,score1,score2,time,ud;

    public RankPredictModel(String name,String score1,String score2,String team,String date,String time,String ud){

        this.date=date;
        this.team=team;
        this.time=time;
        this.ud=ud;
        this.name=name;
        this.score1=score1;
        this.score2=score2;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUd() {
        return ud;
    }

    public void setUd(String ud) {
        this.ud = ud;
    }

    public RankPredictModel(){

    }
}
