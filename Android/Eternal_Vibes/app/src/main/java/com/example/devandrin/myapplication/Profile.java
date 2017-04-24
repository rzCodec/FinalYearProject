package com.example.devandrin.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Devandrin on 2017/04/19.
 */

public class Profile {
    private int id;
    private String firstname;
    private String surname;
    private String email;
    private String alias;
    private int genre_id;
    private String token;
    private String song_link;
    private int latitude;
    private int longitude;
    private int pardons;
    private int search_distance;
    private int join_timestamp;
    private int is_banned;
    private int last_login_timestamp;
    private String avatar_url;
    private String permalink_url;
    private String permalink;

    public Profile() {
    }

    private Profile(JSONObject item) throws JSONException {
        this.id = item.getInt("id");
        this.firstname = item.getString("firstname");
        this.surname = item.getString("surname");
        this.email = item.getString("email");
        this.alias = item.getString("alias");
        this.genre_id = item.getInt("genre_id");
        this.token = item.getString("token");
        this.song_link = item.getString("song_link");
        this.latitude = item.getInt("latitude");
        this.longitude = item.getInt("longitude");
        this.pardons = item.getInt("pardons");
        this.search_distance = item.getInt("search_distance");
        try
        {
            this.join_timestamp = item.getInt("join_timestamp");
        }
        catch(JSONException e)
        {
            this.join_timestamp=-1;
        }

        this.is_banned = item.getInt("is_banned");
        this.last_login_timestamp = item.getInt("last_login_timestamp");
        this.avatar_url = item.getString("avatar_url");
        this.permalink_url = item.getString("permalink_url");
        this.permalink = item.getString("permalink");
    }

    public static ArrayList<Profile> fromJSONArray(JSONArray arr) throws JSONException {
        ArrayList<Profile> values = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            values.add(new Profile(arr.getJSONObject(i)));
        }
        return (values.size() <= 0) ? null : values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSong_link() {
        return song_link;
    }

    public void setSong_link(String song_link) {
        this.song_link = song_link;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getIs_banned() {
        return is_banned;
    }

    public void setIs_banned(int is_banned) {
        this.is_banned = is_banned;
    }

    public int getPardons() {
        return pardons;
    }

    public void setPardons(int pardons) {
        this.pardons = pardons;
    }

    public int getSearch_distance() {
        return search_distance;
    }

    public void setSearch_distance(int search_distance) {
        this.search_distance = search_distance;
    }

    public int getJoin_timestamp() {
        return join_timestamp;
    }

    public void setJoin_timestamp(int join_timestamp) {
        this.join_timestamp = join_timestamp;
    }

    public int is_banned() {
        return is_banned;
    }

    public int getLast_login_timestamp() {
        return last_login_timestamp;
    }

    public void setLast_login_timestamp(int last_login_timestamp) {
        this.last_login_timestamp = last_login_timestamp;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getPermalink_url() {
        return permalink_url;
    }

    public void setPermalink_url(String permalink_url) {
        this.permalink_url = permalink_url;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    @Override
    public String toString() {
        return id + "\n" + firstname + "\n" + surname + "\n" + email + "\n" + alias + "\n" + genre_id + "\n" + token + "\n" + song_link + "\n" + latitude + "\n"
                + longitude + "\n" + pardons + "\n" + search_distance + "\n" + join_timestamp + "\n" + is_banned + "\n"
                + last_login_timestamp + "\n" + avatar_url + "\n" + permalink_url + "\n" + permalink;
    }
}
