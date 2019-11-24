package com.issam.ppmtool.ppmToolProject.Web;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issam.ppmtool.ppmToolProject.Domain.ProjectTask;
import com.issam.ppmtool.ppmToolProject.Service.ProjectTaskService;
import com.issam.ppmtool.ppmToolProject.Service.ValidationService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
	
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private ValidationService validationService;
	
	
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTask(@Valid @RequestBody ProjectTask projectTask , 
			                                BindingResult result ,@PathVariable String backlog_id , Principal principal ){
		ResponseEntity<?> errorMap = validationService.getErrorsForProject(result);
		if(errorMap != null) {
			return validationService.getErrorsForProject(result);
		}
		projectTaskService.addProjectTask(backlog_id, projectTask , principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.CREATED);
	}
	
	//get the backlog based on Backlog Id

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog (@PathVariable String backlog_id ,Principal principal){
		return projectTaskService.findBacklogById(backlog_id, principal.getName());
	}
	
	//get projectTask by PTs 
	@GetMapping("/{backlog_id}/{PTs}")
	public ResponseEntity<?> getProjectTaskByPTs(@PathVariable String backlog_id,@PathVariable String PTs,Principal principal) {
		ProjectTask projectTask = projectTaskService.findProjectTaskByPTs(backlog_id,PTs,principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
	
	//update project task 
	
	@PatchMapping("/{backlog_id}/{PTs}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedProjectTask , BindingResult result,
			                          @PathVariable String backlog_id,@PathVariable String PTs ,Principal principal) {
		ResponseEntity<?> errorMap = validationService.getErrorsForProject(result);
		if(errorMap != null) {
			return validationService.getErrorsForProject(result);
		}
		
		ProjectTask projectTask = projectTaskService.updatePT(updatedProjectTask, backlog_id, PTs,principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
	
	@DeleteMapping("/{backlog_id}/{PTs}")
	public ResponseEntity<?> deletePTs(@PathVariable String backlog_id,@PathVariable String PTs ,Principal principal) {
		projectTaskService.deleteProjectTask(backlog_id, PTs, principal.getName());
		return new ResponseEntity<String>("Project Task with ID : '"+PTs+"'was deleted successfuly ",HttpStatus.OK);
	}
	
	
}
