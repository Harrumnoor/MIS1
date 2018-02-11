package my.vaadin.FirstActivitiGUI;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import org.springframework.core.task.TaskExecutor;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import Activiti.MyProcess;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI 
{
	
	
	
	
	/*UI ui;
	
	public UI getUI()
	{
		return this.ui;
		
	}*/
	
	
    @Override
    protected void init(VaadinRequest vaadinRequest) 
    {  
    	
        try 
        {
        	
        	//System.out.println("UI IN INIT IS"+UI.getCurrent());
        	//ui=UI.getCurrent();
        	
            MyProcess process=new MyProcess();
        	process.myUI=this;
        	System.out.println("Cureeent UI: "+UI.getCurrent());
        	process.processConfiguration();
			process.ActivitiWork();	
			
			
		} 
        
        catch (Exception e1) 
        {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet 
    {
    	
    	
    }
}
