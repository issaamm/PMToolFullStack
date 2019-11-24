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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issam.ppmtool.ppmToolProject.Domain.Project;
import com.issam.ppmtool.ppmToolProject.Service.ProjectService;
import com.issam.ppmtool.ppmToolProject.Service.ValidationService;

@RestController
@RequestMapping("api/project")
@CrossOrigin
public class ProjectController {
	
	

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ValidationService validationService;
	
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result , Principal principal){
		
		ResponseEntity<?> errorMap = validationService.getErrorsForProject(result);
		if(errorMap!= null) 
			return validationService.getErrorsForProject(result);
		
		projectService.saveOrUpdateProject(project,principal.getName());
		
		return new ResponseEntity<Project>(project,HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public Iterable<Project> findAllPts(Principal principal){
		return projectService.findAllProjects(principal.getName());
	}
	
	
	@GetMapping("/{pt_id}")
	public ResponseEntity<?> findPrrojectByPID(@PathVariable String pt_id,Principal principal){
		Project project = projectService.findProjectByProjectIdentifier(pt_id.toUpperCase(),principal.getName());
		return new ResponseEntity<Project>(project , HttpStatus.OK);
	}
	
	@DeleteMapping("/{pt_id}")
	public ResponseEntity<?> deleteProjectById(@PathVariable String pt_id, Principal principal){
		projectService.deleteProjectById(pt_id,principal.getName());
		
		return new ResponseEntity<String>("The Project With ID : '"+pt_id+"' was deleted successfuly",HttpStatus.OK);
	}
}
