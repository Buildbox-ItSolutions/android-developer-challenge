package com.testebuildbox.testebuildbox.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by fjesus on 05/10/2018.
 */

public class User implements Parcelable {

    private String id;
    private String username;
    private String name;
    private String first_name;
    private String last_name;
    private String twitter_username;
    private String portfolio_url;
    private String bio;
    private String location;
    private ProfileImage profile_image;
    private String instagram_username;


    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        name = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        twitter_username = in.readString();
        portfolio_url = in.readString();
        bio = in.readString();
        location = in.readString();
        profile_image = in.readParcelable(ProfileImage.class.getClassLoader());
        instagram_username = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTwitter_username() {
        return twitter_username;
    }

    public void setTwitter_username(String twitter_username) {
        this.twitter_username = twitter_username;
    }

    public String getPortfolio_url() {
        return portfolio_url;
    }

    public void setPortfolio_url(String portfolio_url) {
        this.portfolio_url = portfolio_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProfileImage getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(ProfileImage profile_image) {
        this.profile_image = profile_image;
    }

    public String getInstagram_username() {
        return instagram_username;
    }

    public void setInstagram_username(String instagram_username) {
        this.instagram_username = instagram_username;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(name);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(twitter_username);
        parcel.writeString(portfolio_url);
        parcel.writeString(bio);
        parcel.writeString(location);
        parcel.writeParcelable(profile_image, i);
        parcel.writeString(instagram_username);
    }
}
