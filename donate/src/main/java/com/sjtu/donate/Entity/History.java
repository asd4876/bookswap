package com.sjtu.donate.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sjtu.donate.Common.Utils;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    private int userId;
    
    private int productId;
    
    private String createTime;
    
    private int price;
    
    private String productName;
    
    private String photoUrl;
    
    @Column(length=500)
    private String description;
    
    public History() {
    	
    }
    
    public History(int userId, int productId, int price, String productName, String photoUrl, String description) {
    	this.userId = userId;
    	this.price = price;
    	this.productId = productId;
    	this.productName = productName;
    	this.photoUrl = photoUrl;
    	this.description = description;
    	this.createTime = Utils.convertToString(new Date(System.currentTimeMillis()));
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
