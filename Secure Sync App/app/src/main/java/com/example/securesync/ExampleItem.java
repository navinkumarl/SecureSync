package com.example.securesync;

public class ExampleItem {
    private String user_name,mobile_no,email;

    public ExampleItem(String uname,String mobno,String mail) {
        user_name=uname;
        mobile_no=mobno;
        email=mail;
    }
    public String getUser_name() {
        return user_name;
    }
    public String getMobile_no() {
        return mobile_no;
    }
    public String getEmail() {
        return email;
    }
}
