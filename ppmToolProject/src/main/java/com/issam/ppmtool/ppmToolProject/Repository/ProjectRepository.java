package com.issam.ppmtool.ppmToolProject.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.issam.ppmtool.ppmToolProject.Domain.Project;


@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{

	public Project findProjectByprojectIdentifier(String projectIdentifier);
	
	public Iterable<Project> findAllByProjectLeader(String projectLeader);
}
 