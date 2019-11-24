package com.issam.ppmtool.ppmToolProject.Service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issam.ppmtool.ppmToolProject.Domain.Backlog;
import com.issam.ppmtool.ppmToolProject.Domain.Project;
import com.issam.ppmtool.ppmToolProject.Domain.User;
import com.issam.ppmtool.ppmToolProject.Exceptions.ProjectIdException;
import com.issam.ppmtool.ppmToolProject.Exceptions.ProjectNotFoundException;
import com.issam.ppmtool.ppmToolProject.Repository.BacklogRepository;
import com.issam.ppmtool.ppmToolProject.Repository.ProjectRepository;
import com.issam.ppmtool.ppmToolProject.Repository.UserRepository;

@Service
public class ProjectService {

	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void saveOrUpdateProject(Project project, String username) {
				
		try {
			User user = userRepository.findByUsername(username);
			project.setUser(user);
						
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				project.setProjectLeader(user.getUsername());
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			if(project.getId()!=null) {
				if(!project.getProjectLeader().equals(username)) {
					throw new ProjectNotFoundException("Project Not found in your account");
				}
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
				
			}
			 projectRepository.save(project);			
		} catch (Exception e) {
			throw new ProjectIdException("This Project ID :'"+project.getProjectIdentifier()+"' already exist");
		}
	}
	
	
	public Project findProjectByProjectIdentifier(String projectIdentifier , String username) {
		Project project = projectRepository.findProjectByprojectIdentifier(projectIdentifier);
		if(project == null) {
			throw new ProjectIdException("There's no project with this ID : '"+projectIdentifier+"'");
		}
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("this project doesn't belong to you");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(String username){
		return projectRepository.findAllByProjectLeader(username);
	}
	
	public void deleteProjectById(String projectIdentifier , String username) {
		
		projectRepository.delete(findProjectByProjectIdentifier(projectIdentifier, username));
	}
}
