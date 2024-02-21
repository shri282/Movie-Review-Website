package com.shri;

public class ratings {

	private String rating;
    private String comment;
    private String moviename;
    
    
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getMoviename() {
		return moviename;
	}
	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}
	@Override
	public String toString() {
		return "ratings [rating=" + rating + ", comment=" + comment + ", moviename=" + moviename + "]";
	}
	public ratings(String rating, String comment, String moviename) {
		super();
		this.rating = rating;
		this.comment = comment;
		this.moviename = moviename;
	}
	public ratings() {
		super();
	}
    
    
	
	
    
    
	
	
	
}
