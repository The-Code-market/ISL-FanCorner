package com.codemaster.fancorner;

public class Messages {
    private String message,type,ud,time,date,name;
    public Messages(String date,String message,String name,String time,String type,String ud){
        this.date=date;
        this.message=message;
        this.name=name;
        this.time=time;
        this.type=type;
        this.ud=ud;
    }

        public Messages(){

        }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUd() {
        return ud;
    }

    public void setUd(String ud) {
        this.ud = ud;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
