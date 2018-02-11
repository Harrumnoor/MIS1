package Activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

public class RiskEvaluation implements JavaDelegate
{
	Expression salary, risk;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception
	{
		//String Salary=(String) salary.getValue(execution);
		String Risk = (String) execution.getVariable("risk");
		
		//if (Integer.parseInt(Salary) >= 10000)
		//{
			execution.setVariable("risk","false");
		//}
		
	}
}
