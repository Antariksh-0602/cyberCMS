package com.cyberCMS.cyberCMS.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ITService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Lob
    private String description;
    private String SecurityProvisions;
    private String Dos;
    private String Donts;

    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

	public String getSecurityProvisions() {
		return SecurityProvisions;
	}

	public void setSecurityProvisions(String securityProvisions) {
		SecurityProvisions = securityProvisions;
	}

	public String getDos() {
		return Dos;
	}

	public void setDos(String dos) {
		Dos = dos;
	}

	public String getDonts() {
		return Donts;
	}

	public void setDonts(String donts) {
		Donts = donts;
	}
}