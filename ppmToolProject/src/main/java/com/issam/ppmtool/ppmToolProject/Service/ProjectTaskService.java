package com.issam.ppmtool.ppmToolProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issam.ppmtool.ppmToolProject.Domain.Backlog;
import com.issam.ppmtool.ppmToolProject.Domain.Project;
import com.issam.ppmtool.ppmToolProject.Domain.ProjectTask;
import com.issam.ppmtool.ppmToolProject.Exceptions.ProjectIdException;
import com.issam.ppmtool.ppmToolProject.Exceptions.ProjectNotFoundException;
import com.issam.ppmtool.ppmToolProject.Repository.BacklogRepository;
import com.issam.ppmtool.ppmToolProject.Repository.ProjectRepository;
import com.issam.ppmtool.ppmToolProject.Repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
		
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask,String username){
		
		//find the backlog where the PT will be include
		
			Backlog backlog =  projectService.findProjectByProjectIdentifier(projectIdentifier, username).getBacklog();//backlogRepository.findByProjectIdentifier(projectIdentifier);
			//set the bl to the pt
			projectTask.setBacklog(backlog);
			
			//get the value of the backlog PTsequence , and increment it , 
			//so we could set the new value of the projectSequence in our PT object
			Integer BacklogSequence = backlog.getPTSequence();
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);
			projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
			
			//set the projectIdentifier in our PT object
			projectTask.setProjectIdentifier(projectIdentifier);
			//priority
			if(projectTask.getPriority()==null) {
				projectTask.setPriority(3);
			}
			//status
			if(projectTask.getStatus()==null||projectTask.getStatus()=="") {
				projectTask.setStatus("TO_DO");
			}
			
			return projectTaskRepository.save(projectTask);
		
		
	}


	public Iterable<ProjectTask> findBacklogById(String backlog_id , String username) {
		
		projectService.findProjectByProjectIdentifier(backlog_id, username);
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
	
	public ProjectTask findProjectTaskByPTs(String backlog_id, String PTs , String username) {
		
		projectService.findProjectByProjectIdentifier(backlog_id, username);
		
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(PTs);
		if(projectTask==null) {
			throw new ProjectNotFoundException("Project Task with ID :'"+PTs+"' not found");
		}
		
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task with ID :'"+PTs+"' does not exist in project :"+backlog_id);
		}
		return projectTask;
	}
	
	public ProjectTask updatePT(ProjectTask updatedProjectTask,String backlog_id,String PTs,String username) {
		
		ProjectTask projectTask = findProjectTaskByPTs(backlog_id, PTs,username);
		projectTask = updatedProjectTask;
		
		return projectTaskRepository.save(projectTask);
	}
	
	public void deleteProjectTask(String backlog_id, String PTs,String username) {
		ProjectTask projectTask = findProjectTaskByPTs(backlog_id, PTs,username);
		projectTaskRepository.delete(projectTask);
	}

}
