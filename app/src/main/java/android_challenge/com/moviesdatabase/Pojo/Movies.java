package android_challenge.com.moviesdatabase.Pojo;

/**
 * Created by Fabio_2 on 10/12/2018.
 */

public class Movies {

  public String title;
  public int year;
  public String imdbID;
  public String type;
  public String poster;

  public Movies(String title, int year, String imdbID, String type, String poster){
    this.title = title;
    this.year = year;
    this.imdbID = imdbID;
    this.type = type;
    this.poster = poster;

  }

  public String getTitle(){ return this.title; }

  public int getYear(){ return this.year; }

  public String getImdbID(){ return this.imdbID; }

  public String getType(){ return this.type; }

  public String getPoster(){ return this.poster; }

}
