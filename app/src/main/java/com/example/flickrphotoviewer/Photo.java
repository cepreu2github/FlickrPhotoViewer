package com.example.flickrphotoviewer;

/**
 * Created by cepreu on 29.08.17.
 */

public class Photo {
    private String id;
    private String server;
    private int farm;
    private String secret;
    private String originalsecret;
    private String originalformat;

    public String getOriginalsecret() {
        return originalsecret;
    }

    public void setOriginalsecret(String originalsecret) {
        this.originalsecret = originalsecret;
    }

    public String getOriginalformat() {
        return originalformat;
    }

    public void setOriginalformat(String originalformat) {
        this.originalformat = originalformat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String url(){
        if (originalsecret == null || originalformat == null || !originalformat.equals("jpg")){
            return String.format("https://farm%d.staticflickr.com/" +
                    "%s/%s_%s.jpg", farm, server, id, secret);
        }
        return String.format("https://farm%d.staticflickr.com/" +
                "%s/%s_%s_o.jpg", farm, server, id, originalsecret);
    }

    public String previewUrl(){
        return String.format("https://farm%d.staticflickr.com/" +
                "%s/%s_%s_q.jpg", farm, server, id, secret);
    }
}
