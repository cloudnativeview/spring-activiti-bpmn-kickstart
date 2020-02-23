package com.cloudnativeview.workflows.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author aamir
 *
 */
@RestController
@RequestMapping("/api/workflow")
public class ActivitiController {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;

	/**Start process instance for the given process definition
	 * 
	 * @param processDefinitionId
	 * @param businessKey
	 * @return
	 */
	@GetMapping(value = "/trigger/{processDefinitionId}/{businessKey}")
	public ResponseEntity<Boolean> triggerActivitiWorkflow(@PathVariable String processDefinitionId, @PathVariable String businessKey) {
		Map<String, Object> variableMap = new HashMap<>();
		variableMap.put("counter", 0);
		runtimeService.startProcessInstanceByKey(processDefinitionId, businessKey, variableMap);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}


	/**Get workflow status
	 * 
	 * @param processDefinitionId
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/status/{processDefinitionId}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getProcessDiagramWithActivitiStateMarked(@PathVariable String processDefinitionId) throws IOException {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(processDefinitionId).active().singleResult();
		
		if (processInstance != null) {
			BpmnModel model = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
			if (model != null && model.getLocationMap().size() > 0) {
				ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
				InputStream generateDiagram = generator.generateDiagram(model, "png",
						runtimeService.getActiveActivityIds(processInstance.getId()));
				return new ResponseEntity<>(IOUtils.toByteArray(generateDiagram), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

}
