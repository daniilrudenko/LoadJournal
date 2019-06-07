package com.rudenko.models;

public class ServerAuthorizationData {

    private String url;
    public static final String DATABASE_NAME = "university";
    //------------------------

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    //------------------------
     public ServerAuthorizationData(){

    }

    public ServerAuthorizationData(String url, String port){
        this.url  = url;
    }
    //------------------------

}
