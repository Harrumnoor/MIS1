package Activiti;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


public class ResumeService implements JavaDelegate
{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		 System.out.println("Storing resume ...");
		// TODO Auto-generated method stub
		
	}

}