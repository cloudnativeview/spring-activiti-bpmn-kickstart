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
public class ServiceTask3 implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Service Task 3");
		Integer counter = getIntegerValue(execution.getVariable("counter"));
		System.out.println("Counter value is: "+ counter);
		counter ++;
		execution.setVariable("counter", counter);
	}

	/**Utility method to get Integer value
	 * 
	 * @param counter
	 * @return
	 */
	private Integer getIntegerValue(Object counter) {
		if(counter instanceof Integer) {
			return (Integer) counter;
		}else if(counter instanceof Long) {
			return ((Long) counter).intValue();
		}else if(counter instanceof String) {
			return Integer.valueOf((String) counter);
		}
		return null;
	}



}




