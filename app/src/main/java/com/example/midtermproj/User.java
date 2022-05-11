package com.example.midtermproj;

public class User {
    String name;
    String nickname;
    String user_id;
    String user_pw;


    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }
}
