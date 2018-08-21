package com.gudacity.scholar.movietap.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private long id;
    private String title;
    private double voteAverage;
    private String posterPath;
    private String synopsis;
    private String date;

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {

                public Movie createFromParcel(Parcel parcel) {
                    return new Movie(parcel);
                }

                public Movie[] newArray(int size) {
                    return new Movie[size];
                }

            };

    public Movie(Parcel parcel) {
        this.id = parcel.readLong();
        this.title = parcel.readString();
        this.voteAverage = parcel.readDouble();
        this.posterPath = parcel.readString();
        this.synopsis = parcel.readString();
        this.date = parcel.readString();
    }


    public Movie(long id, String title, double voteAverage, String posterPath, String synopsis, String date) {
        this.id = id;
        this.title = title;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.synopsis = synopsis;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", voteAverage=" + voteAverage +
                ", posterPath='" + posterPath + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(this.id);
        parcel.writeString(this.title);
        parcel.writeDouble(this.voteAverage);
        parcel.writeString(this.posterPath);
        parcel.writeString(this.synopsis);
        parcel.writeString(this.date);
    }
}
