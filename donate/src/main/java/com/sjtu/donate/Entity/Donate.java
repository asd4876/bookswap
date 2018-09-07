package com.sjtu.donate.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sjtu.donate.Common.Utils;

@Entity
public class Donate {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    private String createTime;
    
    private String title;
    
    @Column(length=500)
    private String description;
    
    private int userId;
    
    private String address;
    
    private String telephone;
    
    private String name;
    
    private int price;
    
    private String filePath;
    
    private String fileName;
    
    private String writtenBy;
    
    public Donate() {
    	
    }
    
    public Donate(int userId, String title, String description) {
    	this.userId = userId;
    	this.title = title;
    	this.description = description;
    	this.createTime = Utils.convertToString(new Date(System.currentTimeMillis()));
    	this.address = "";
    	this.telephone = "";
    	this.name = "";
    	this.price = 0;
    	this.fileName = "";
    	this.filePath = "";
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getWrittenBy() {
		return writtenBy;
	}

	public void setWrittenBy(String writtenBy) {
		this.writtenBy = writtenBy;
	}
    
    
    
    
    
}
