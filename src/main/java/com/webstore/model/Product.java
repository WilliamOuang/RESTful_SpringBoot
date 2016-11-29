/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webstore.model;

import java.util.List;

/**
 *
 * @author WeiliangOuyang
 */
public class Product {
    private int productId;
    private String name;
    private double price;
    List<ProductComment> comment;
    
    public List<ProductComment> getComment() {
		return comment;
	}
	public void setComment(List<ProductComment> comment) {
		this.comment = comment;
	}

	
	
    public Product(int productId, String name, double price, List<ProductComment> comment) {
	
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.comment = comment;
	}
//	public Product(int productId, String name, double price,List<ProductComment> comment) {
//        this.productId = productId;
//        this.name = name;
//        this.price = price;
//        this.comment = comment;
//    }

    public int getProductId() {
        return productId;
    }

    public Product() {
		super();
	}
	public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.productId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }
   
    
}
