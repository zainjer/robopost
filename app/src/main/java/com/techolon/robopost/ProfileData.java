package com.techolon.robopost;

import android.net.Uri;

public class ProfileData {

   private static String display_name = "development";
   private static String account_email;
   private static Uri display_picture_url;
   private static boolean LOGGED_IN;


    public static String getDisplay_name() {
        return display_name;
    }

    public static void setDisplay_name(String display_name) {
        ProfileData.display_name = display_name;
    }

    public static String getAccount_email() {
        return account_email;
    }

    public static void setAccount_email(String account_email) {
        ProfileData.account_email = account_email;
    }

    public static Uri getDisplay_picture_url() {
        return display_picture_url;
    }

    public static void setDisplay_picture_url(Uri display_picture_url) {
        ProfileData.display_picture_url = display_picture_url;
    }

    public static void setLoggedIn(Boolean loggedIn){
        ProfileData.LOGGED_IN  = loggedIn;
    }
    public static boolean getLoggedIn(){
        return LOGGED_IN;
    }
}
