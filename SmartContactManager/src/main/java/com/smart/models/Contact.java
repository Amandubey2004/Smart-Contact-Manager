package com.smart.models;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact 
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	private String cname;
	private String secondName;
	private String work;
	private String email;
	private String phone;
	
	@Column(length = 5000)
	private String description;
	
	@ManyToOne
	private User user;

	
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Contact(int cid, String cname, String secondName, String work, String email, String phone,
			 String description, User user) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.secondName = secondName;
		this.work = work;
		this.email = email;
		this.phone = phone;
         
		this.description = description;
		this.user = user;
	}

	


	public int getCid() {
		return cid;
	}


	public void setCid(int cid) {
		this.cid = cid;
	}


	public String getCname() {
		return cname;
	}


	public void setCname(String cname) {
		this.cname = cname;
	}


	public String getSecondName() {
		return secondName;
	}


	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}


	public String getWork() {
		return work;
	}


	public void setWork(String work) {
		this.work = work;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	



	




	
//
//	@Override
//	public String toString() {
//		return "Contact [cid=" + cid + ", cname=" + cname + ", secondName=" + secondName + ", work=" + work + ", email="
//				+ email + ", phone=" + phone + ", description=" + description + ", user=" + user + "]";
//	}


	
	

}
