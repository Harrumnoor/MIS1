package my.vaadin.FirstActivitiGUI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import com.vaadin.ui.UI;

public class TasksExecuter
{ 
	public FormInput formInput=new FormInput();

	Object waitObj=new Object();
	public HashMap<String, String> fields;
	
	public HashMap<String, String> inputValues;
	public MyUI myUi;
	
	public boolean values_entered=false;
	
	//Executes all tasks (user and others too) given in activities list
	public void tasksExecutor(ProcessDefinition processDefinition, ProcessInstance processInstance, List<HistoricActivityInstance> activities, HashMap<String, String> fields)
	{
		//iterating over all tasks
		for (HistoricActivityInstance activity : activities) 
        {
        	if (activity.getActivityType() == "startEvent") 
        	{
        		System.out.println("BEGIN " + processDefinition.getName() 
                + " [" + processInstance.getProcessDefinitionKey()
                + "] " + activity.getStartTime());
        	}
        	
          
        	else 
            {
        		System.out.println("In Tasks Executor-- " + activity.getActivityName() 
                + " [" + activity.getActivityId() + "] "
                + activity.getDurationInMillis() + " ms");
        		
        		
        		
        		
        		
            }
        }
	}
	
	
	
	public void FormExecutor(HashMap<String, String> fields) throws InterruptedException
	{
		// TODO Auto-generated method stub
		/*Set set = fields.entrySet();
	    Iterator iterator = set.iterator();
	    while(iterator.hasNext())
	    {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
	         System.out.println(mentry.getValue());
	      }*/
		

		//formInput.inputFields=fields; //need to be stored for later use in get form input
		
		formInput.DisplayFormInput(fields);
		
		//System.out.println("BACK IN TASK EXECUTOR");
		//return formInput.formValues;
	
	
	}

	
}
