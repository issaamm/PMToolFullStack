package com.issam.ppmtool.ppmToolProject.Domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Backlog {
	
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer PTSequence = 0;
	private String projectIdentifier;
	
	//OneToOne 
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "projectId" , nullable = false)
	@JsonIgnore
	private Project project;

	//OneToMany with projecttasks
	@OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.REFRESH , mappedBy = "backlog" , orphanRemoval = true)
	List<ProjectTask> projectTasks = new ArrayList<>();
	
	
	
	public Backlog() {
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getPTSequence() {
		return PTSequence;
	}



	public void setPTSequence(Integer pTSequence) {
		PTSequence = pTSequence;
	}



	public String getProjectIdentifier() {
		return projectIdentifier;
	}



	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	


	public Project getProject() {
		return project;
	}



	public void setProject(Project project) {
		this.project = project;
	}
	
	



	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}



	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}



	@Override
	public String toString() {
		return "Backlog [id=" + id + ", PTSequence=" + PTSequence + ", projectIdentifier=" + projectIdentifier + "]";
	}
	
	
	
	
	

}