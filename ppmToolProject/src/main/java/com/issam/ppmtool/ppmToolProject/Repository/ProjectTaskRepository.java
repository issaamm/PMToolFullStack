package com.issam.ppmtool.ppmToolProject.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.issam.ppmtool.ppmToolProject.Domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long>{

	public List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
	
	public ProjectTask findByProjectSequence(String PTs);
	
	//public void deleteByProjectSequence(String PTs);
}
