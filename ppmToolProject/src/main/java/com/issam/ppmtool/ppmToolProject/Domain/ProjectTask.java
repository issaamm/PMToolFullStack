package com.issam.ppmtool.ppmToolProject.Domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@Column(updatable = false)
	private String projectSequence;
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	private String status;
	
	@NotBlank(message = "please include a Project Task Summray")
	private String summray;
	
	private String acceptanceCritria;
	private Integer priority;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date dueDate;
	
	
	private Date created_At;
	private Date updated_At;
	
	//ManToOne with Backlog
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Backlog backlog; 
	
	
	
	public ProjectTask() {
	}
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getProjectSequence() {
		return projectSequence;
	}



	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}



	public String getProjectIdentifier() {
		return projectIdentifier;
	}



	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getSummray() {
		return summray;
	}



	public void setSummray(String summray) {
		this.summray = summray;
	}



	public String getAcceptanceCritria() {
		return acceptanceCritria;
	}



	public void setAcceptanceCritria(String acceptanceCritria) {
		this.acceptanceCritria = acceptanceCritria;
	}



	public Integer getPriority() {
		return priority;
	}



	public void setPriority(Integer priority) {
		this.priority = priority;
	}



	public Date getDueDate() {
		return dueDate;
	}



	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}



	public Date getCreated_At() {
		return created_At;
	}



	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}



	public Date getUpdated_At() {
		return updated_At;
	}



	public void setUpdated_At(Date updated_At) {
		this.updated_At = updated_At;
	}


	

	public Backlog getBacklog() {
		return backlog;
	}



	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}



	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_At = new Date();
	}



	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", projectIdentifier="
				+ projectIdentifier + ", status=" + status + ", summray=" + summray + ", acceptanceCritria="
				+ acceptanceCritria + ", priority=" + priority + ", dueDate=" + dueDate + ", created_At=" + created_At
				+ ", updated_At=" + updated_At + "]";
	}
	
	
}
