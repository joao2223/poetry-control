package com.api.rifas.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_home_page")
public class HomePage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String imgHomePage;
	private String imgLogo;
	
	public HomePage() {
	}

	public HomePage(Long id, String imgHomePage, String imgLogo) {
		this.id = id;
		this.imgHomePage = imgHomePage;
		this.imgLogo = imgLogo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImgHomePage() {
		return imgHomePage;
	}

	public void setImgHomePage(String imgHomePage) {
		this.imgHomePage = imgHomePage;
	}

	public String getImgLogo() {
		return imgLogo;
	}

	public void setImgLogo(String imgLogo) {
		this.imgLogo = imgLogo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HomePage other = (HomePage) obj;
		return Objects.equals(id, other.id);
	}
}
