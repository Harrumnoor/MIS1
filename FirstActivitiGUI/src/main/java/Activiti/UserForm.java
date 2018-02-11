package Activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

public class UserForm implements JavaDelegate
{
		//Expression to, from, subject, bcc, text, cc;
		
		@Override
		public void execute(DelegateExecution execution) throws Exception
		{
			System.out.println("in form class");
		}

}
