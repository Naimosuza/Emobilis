package com.example.doctorappointment;

import java.io.Serializable;

public class UserLoginDetail implements Serializable {

    @SerializedName("data")
    public Data data;

    public static class Data implements Serializable {
        @SerializedName("Password")
        public String password;
        @SerializedName("Email")
        public String email;
    }



}
