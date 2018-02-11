package Activiti;
import my.vaadin.FirstActivitiGUI.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FormValue;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.form.FormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Test;

import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.EnumFormType;
import org.activiti.engine.impl.form.LongFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class MyProcess 
{
	public enum priority
	{
		low, medium, critical  
	}
	 
	public enum approveRequest
	{
		 
		Yes, No
	}
	
	public static int status=1;
	public static ProcessEngineConfiguration cfg; 
	public static ProcessEngine processEngine;
	public static RepositoryService repositoryService;
	public static RuntimeService runtimeService;
	public static IdentityService identityService;
	public static TaskService taskService;
	public static FormService formService;
	public static HistoryService historyService;
	public static Deployment deployment;
	public static ProcessDefinition processDefinition;
	public static ProcessInstance processInstance;
	public static int formCounter=0;
	
	public MyUI myUI;
	
	public void processConfiguration()
	{
		ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
			      .setJdbcUrl("jdbc:mysql://localhost:3306/activiti")
			      .setJdbcUsername("root")
			      .setJdbcPassword("sanashah2594")
			      .setJdbcDriver("com.mysql.jdbc.Driver")
			      .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		//Build process engine function
		processEngine = cfg.buildProcessEngine(); 
		repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();
		identityService = processEngine.getIdentityService();
		taskService = processEngine.getTaskService();
		formService = processEngine.getFormService();
	    historyService = processEngine.getHistoryService();
	    
	    //XML reader function
	    deployment = repositoryService.createDeployment().addClasspathResource("complex.bpmn20.xml").deploy();
		//deployment = repositoryService.createDeployment().addClasspathResource("Developer_Hiring_with_jpa.bpmn20.xml").deploy();
		//deployment = repositoryService.createDeployment().addClasspathResource("issueRequest.bpmn20.xml").deploy();
		//deployment = repositoryService.createDeployment().addClasspathResource("loan.bpmn20.xml").deploy();
        
        processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		processInstance = runtimeService.startProcessInstanceByKey("myProcess");
		

	}
	 
	
	
	public void execHistoricInstance()
	{

        //Historic Instances
        HistoricActivityInstance endActivity = null;
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
        		.processInstanceId(processInstance.getId()).finished()
            .orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance activity : activities) 
        {
        	
        	
        	if (activity.getActivityType() == "startEvent") 
        	{
        		System.out.println("BEGIN " + processDefinition.getName() 
                + " [" + processInstance.getProcessDefinitionKey()
                + "] " + activity.getStartTime());
        	}
        	
        	if (activity.getActivityType() == "endEvent") 
        	{
	            // Handle edge case where end step happens so fast that the end step
	            // and previous step(s) are sorted the same. So, cache the end step 
	            //and display it last to represent the logical sequence.
	            endActivity = activity;
        	} 
          
        	else 
            {
        		System.out.println("-- " + activity.getActivityName() 
                + " [" + activity.getActivityId() + "] "
                + activity.getDurationInMillis() + " ms");
            }
        }
        
        if (endActivity != null) 
        {
        	System.out.println("-- " + endActivity.getActivityName() 
                + " [" + endActivity.getActivityId() + "] "
                + endActivity.getDurationInMillis() + " ms");
        	System.out.println("COMPLETE " + processDefinition.getName() + " ["
                + processInstance.getProcessDefinitionKey() + "] " 
                + endActivity.getEndTime());
        }
        
        //Re-query the process instance, making sure the latest state is available
	    processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
	
	     

		
	}
	
	public void ActivitiWork() throws SQLException, InterruptedException 
	{
		System.out.println("HERE ID IS "+processInstance.getId());
		Scanner scanner = new Scanner(System.in);
		
		//System.out.println("Process Definition: "+processDefinition.getId());
		String processDef=processDefinition.getId();
		String name[]=processDef.split(":");
		
		//Database
		/*String dropSchema="Drop Schema if exists "+name[0];
		String query="CREATE DATABASE "+name[0];
		Connection con=null;
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","sanashah2594");
		Statement stmt=con.createStatement();
		stmt.executeUpdate(dropSchema);
		stmt.executeUpdate(query);*/
		
		//execute process
	    while (processInstance != null && !processInstance.isEnded()) 
	    {
		      //List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
	    	  
	    	  List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
		      System.out.println("Active outstanding tasks: [" + tasks.size() + "]");
		     
		      for (int i = 0; i < tasks.size(); i++) 
		      {
		    	  	HashMap<String, String> fields_names = new HashMap<String, String>(); // to store field name and its type
		    	  	HashMap<String, String> fields_ids = new HashMap<String, String>(); // to store field name and its type
			        Task task = tasks.get(i);
			        System.out.println("Processing Task [" + task.getName() + "]");
					
			        Map<String, Object> variables = new HashMap<String, Object>();
			        
			        //Getting type of task being executed
			        final Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
			        final BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
			        final org.activiti.bpmn.model.FlowElement flowElement = bpmnModel.getFlowElement(((DelegateExecution) execution).getCurrentActivityId());

			        System.out.println("----------Task type [" + flowElement.getClass().getSimpleName()+ "]-------------");
			        String taskType=flowElement.getClass().getSimpleName();
			        //fields_names.put("FormLabel",flowElement.getClass().getSimpleName());
			        
			        //Database
			        String createTable="";
			        String insert="";
			        String string1="";
			        String string2=" values (";
			        if (taskType.equals("UserTask"))
			        {
			        	String taskName=task.getName();
			        	String tableName=taskName.replaceAll(" ", "");
			        	createTable="Create table "+tableName +" (id integer primary key auto_increment not null, ";
			        	string1="Insert into "+tableName+" (";
			        	
			        }
			        
			      //get form input
			        FormData formData = formService.getTaskFormData(task.getId());			        
			        for (FormProperty formProperty : formData.getFormProperties()) 
			        {    
				          if (StringFormType.class.isInstance(formProperty.getType())) 
				          {
				        	  System.out.println(formProperty.getName() + "?");
				        	  fields_names.put(formProperty.getName(),"String");
				        	  fields_ids.put(formProperty.getName(),formProperty.getId());
				        	  /*String value = scanner.nextLine();
				        	  variables.put(formProperty.getId(), value);
				        	  createTable=createTable+formProperty.getId()+" varchar(255), ";
				        	  string2=string2+" '"+value+"' "+",";
				        	  string1=string1+formProperty.getId()+", ";*/
				          } 
			          
				          else if (LongFormType.class.isInstance(formProperty.getType())) 
				          {
				        	  System.out.println(formProperty.getName() + "? (Must be a whole number)");
				        	  fields_names.put(formProperty.getName(),"Long");
				        	  fields_ids.put(formProperty.getName(),formProperty.getId());
				        	  /*Long value = Long.valueOf(scanner.nextLine());
				        	  variables.put(formProperty.getId(), value);
				        	  createTable=createTable+formProperty.getId()+" long, ";
				        	  string2=string2+value+",";
				        	  string1=string1+formProperty.getId()+", ";*/
				          } 
			          
				          else if (DateFormType.class.isInstance(formProperty.getType())) 
				          {
				        	  System.out.println(formProperty.getName() + "? (Must be a date m/d/yy)");
				        	  
				        	  fields_names.put(formProperty.getName(),"Date");
				        	  fields_ids.put(formProperty.getName(),formProperty.getId());
				        	  
					          /*DateFormat dateFormat = new SimpleDateFormat("m/d/yy");
					          Date value = null;
								
					          try 
					          {
					        	  value = dateFormat.parse(scanner.nextLine());
					          } 
					          catch (ParseException e)    
					          {
					        	  e.printStackTrace();
					          }
					            
					          variables.put(formProperty.getId(), value);
					          createTable=createTable+formProperty.getId()+" varchar(255), ";
					          string2=string2+value+",";  
					          string1=string1+formProperty.getId()+", ";*/
				          } 
				          
				          else if (EnumFormType.class.isInstance(formProperty.getType())) 
				          { 	  
				        	  fields_names.put("EnumLabel",formProperty.getName());
				              fields_names.put("EnumId",formProperty.getId());
					        	  Map<String, String> values = (Map<String, String>) formProperty.getType().getInformation("values");
					        	    if (values != null) 
					        	    {
					        	      for (Entry<String, String> enumEntry : values.entrySet()) 
					        	      {
					        	        // Add value and label (if any)
					        	    	  System.out.println("Enum cb"+ enumEntry.getKey());
					        	        if (enumEntry.getValue() != null) 
					        	        {
						        	    	  System.out.println("Enum cb"+ enumEntry.getKey()+" value - "+enumEntry.getValue());
						        	    	  fields_names.put(enumEntry.getValue(),"Enum");
								        	  fields_ids.put(enumEntry.getKey(),enumEntry.getValue());
								        	  
					        	        }
					        	      }
					        	    }					        	  
					        	    System.out.println("IN ENUM IS :-> "+formProperty.getId());
					        	    System.out.println("IN ENUM IS :-> "+formProperty.getName());
					        	    
					        	    
				        		  
				        		
				        	  
				          }
				          
			        }
			         
			        //call tasks executor to execute tasks uptill now
			        
			       
			       
			        HashMap<String, String>formValues;
			        //SendMessageExecutionListener c=new SendMessageExecutionListener();
			        //c.myFields=fields;
			        Set set = fields_names.entrySet();
				    Iterator iterator = set.iterator();
				    int field_num=0;
				    System.out.println("NOWWWW");
				    while(iterator.hasNext())
				    {
				         Map.Entry mentry = (Map.Entry)iterator.next();
				         
				         String attr_name=(String) mentry.getKey();
				         String attr_type=(String) mentry.getValue(); 
				        
				       
				         
				         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
				         System.out.println(mentry.getValue());
				     }

				    
			        
			        	System.out.println("PLZ ALLAH PAK PLZ");
			        	//processInstance=null;
			        	TasksExecuter formExe=new TasksExecuter();
			        	//formExe.formInput.processInstance=processInstance;
			        	formExe.formInput.taskService=taskService;
			        	formExe.formInput.task=task;
			        	formExe.formInput.variables=variables;
			        	formExe.formInput.fields_ids=fields_ids;
					    formExe.FormExecutor(fields_names);
					  
					    return; 
				    
				    
				    //System.out.println("PUASING STARTED");
			        //System.out.println("ID IS "+processInstance.getId());
				    
				    /*try 
				    {
				    	 repositoryService.suspendProcessDefinitionById("myProcess");
					     System.out.println("EXECUTIMG FURTHER");
				    }
				    
				    catch (ActivitiException exc)
				    {
				    	
				    }*/
				    
			        
				    //System.out.println("HI! im back");
				     
				    
				   /* Set set = formValues.entrySet();
				    Iterator iterator = set.iterator();
				    int field_num=0;
				    System.out.println("RETURNED SAFELY");
				    while(iterator.hasNext())
				    {
				         Map.Entry mentry = (Map.Entry)iterator.next();
				         
				         String attr_name=(String) mentry.getKey();
				         String attr_type=(String) mentry.getValue(); 
				        
				       
				         
				         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
				         System.out.println(mentry.getValue());
				     }*/
				    
				    
				    
				    /*while(true)
			        {
			        	if (loop==false) break;
			        }*/
				    
			       // taskService.complete(task.getId(), variables);
			        
			        
			        
			        /*Set set2 = variables.entrySet();
				    Iterator iterator2 = set2.iterator();
				    int field_num2=0;
				    System.out.println("RETURNED SAFELY");
				    while(iterator2.hasNext())
				    {
				         Map.Entry mentry = (Map.Entry)iterator2.next();
				         
				         String attr_name=(String) mentry.getKey();
				         String attr_type=(String) mentry.getValue(); 
				        
				       
				         
				         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
				         System.out.println(mentry.getValue());
				     }
			        
				    ProcessInstance pi = runtimeService.startProcessInstanceByKey("myProcess");
				    Execution execution1 = runtimeService.createExecutionQuery()
				      .processInstanceId(pi.getId())
				      .activityId("receivetask1")
				      .singleResult();
				    assertNotNull(execution);

				    runtimeService.signal(execution1.getId());*/
			        
			        
			        //Database
			        /*createTable=createTable.substring(0, createTable.length()-2);
			        string1=string1.substring(0, string1.length()-2);
			        string2=string2.substring(0, string2.length()-1);
			        
			        createTable=createTable+")";
			        string1=string1+")";
			        string2=string2+")";
			        
			        //System.out.println("Query: "+createTable);
			        //System.out.println("Insert: "+string1 +string2);
			        
			        Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/"+name[0],"root","sanashah2594" );
			        Statement stmt2=conn.createStatement();
			        stmt2.executeUpdate(createTable);
			        stmt2.executeUpdate(string1+string2);
	        	   
	*/
				     
			        
			   }//for loops of tasks endss...
		      
			      //Re-query the process instance, making sure the latest state is available
			     // processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
			
			     
			      
	    }	//scanner.close();	      
	}
}


