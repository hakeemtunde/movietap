package com.gudacity.scholar.movietap.database.model;

public class MovieTrailer {

    private String id;
    private String name;
    private String key;
    private String site;
    private String type;
    private int size;

    public MovieTrailer(String id, String name, String key, String site, String type, int size) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.site = site;
        this.type = type;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "MovieTrailer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", site='" + site + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                '}';
    }
}
