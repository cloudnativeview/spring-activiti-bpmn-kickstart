package com.cloudnativeview.workflows.service;

import org.activiti.engine.delegate.DelegateExecution;

import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;


/**
 * 
 * @author aamir
 *
 */
@Service
public class ServiceTask2 implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Service Task 2");
	}

	
}

