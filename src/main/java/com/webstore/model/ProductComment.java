package com.webstore.model;
import com.fasterxml.jackson.annotation.JsonProperty;
public class ProductComment {
	private int id;
	private String comment1;
	
	public ProductComment(@JsonProperty("comment1") String comment) {
		
		this.comment1 = comment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return comment1;
	}
	public void setComment(String comment) {
		this.comment1 = comment;
	}
	public ProductComment(int id, String comment) {
		super();
		this.id = id;
		this.comment1 = comment;
	}
	
}
