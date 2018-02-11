package my.vaadin.FirstActivitiGUI;

import com.vaadin.ui.FormLayout;

import java.awt.Graphics;
import java.awt.Shape;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.Position.Bias;
import javax.swing.text.View;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.vaadin.data.Binder;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import com.vaadin.ui.VerticalLayout;

import Activiti.MyProcess;

 
public class FormInput
{
		public static boolean isComplete=false;
		Button formSubmitBtn=new Button("Submit");
		public TaskService taskService;
		public Task task;
		boolean isEnum=false;
		boolean isEnumInit=false;
		public Map<String, Object> variables;
		MyUI myUi;
	int labelCount=0;
		String enumId;
	    HashMap<String, String> inputFields;
	    List <TextField> textFields=new ArrayList<TextField>();
	    List<String> EnumList= new ArrayList();
	    
	    String selected;
	   RadioButtonGroup EnumFields = new RadioButtonGroup();
	  
	   Label lbl;
	    public boolean valuesEntered=false;
		HashMap<String, String> formValues=new HashMap<String,String>();
	    Object waitObj;
	    public HashMap<String, String> fields_ids=new HashMap<String,String>();    
	    
		public FormInput() 
		{
			
			// TODO Auto-generated constructor stub
			formSubmitBtn.addClickListener(e -> this.getFormInput());
			EnumFields.addValueChangeListener(e -> {
				System.out.println("clicked: " + e.getSource().getValue());
				selected=e.getSource().getValue().toString();
			});
			
		}
	
		
		
		//get form input
		public void DisplayFormInput(HashMap<String, String> fields)
		{
			
			System.out.println("IDHARRRRRRRRRRRRR HUN MAIN");
			
			
			final VerticalLayout pageLayout=new VerticalLayout(); 
			
			
			Set set = fields.entrySet();
		    Iterator iterator = set.iterator();
		    int field_num=0;
		    while(iterator.hasNext())
		    {
		         Map.Entry mentry = (Map.Entry)iterator.next();
		         
		         String attr_name=(String) mentry.getKey();
		         String attr_type=(String) mentry.getValue(); 
		         
		         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
		         System.out.println(mentry.getValue());
		         
		         if(attr_type.equals("Enum"))
		         {
		        	 EnumList.add(attr_name);
		        	 isEnum=true;	        	 
		         }
		         
		         if ((labelCount==0) && (mentry.getKey().equals("EnumLabel")))
		         {
		        	 lbl=new Label(mentry.getValue()+"");
		        	 
		        	 labelCount=1;
		         }
		         
		         if (mentry.getKey().equals("EnumId"))
		         {
		        	 enumId=mentry.getValue().toString();
		        	 System.out.println("ENUMIDDDDDDD: "+enumId);
		         }
		         
		        
		        	 
		         else 
		         {
		        	    if(!attr_type.equals("Enum") && (!attr_name.equals("EnumLabel")))
		        	    {
					         TextField attr_field=new TextField();
					         attr_field.setId(attr_name);
					         textFields.add(attr_field); //for later use
					         
					         Label lbl=new Label(attr_name);
					         
					         pageLayout.addComponents(lbl,attr_field); 
		        	    }     
		         }
		     }
				
		   if (isEnum) 
		   { 
			   
			   if (isEnumInit==false)
					   {
				  
				   isEnumInit=true;
					   }
			   
			   EnumFields.setItems(EnumList);
			   
			 
			  
			   pageLayout.addComponents(lbl,EnumFields);
			   EnumFields.setId(fields.get("EnumId"));
		   }
			   
		   
		   
		   
		   pageLayout.addComponents(formSubmitBtn);
		   

		   
		   
		   //now display all
		   System.out.println("Form input maiiii");
		   UI.getCurrent().setContent(pageLayout);
		   	   
	}
		
	
	public void getFormInput()
	{

		// System.out.println(EnumFields.getSelectedItem().toString() + "IS THE SELCETC");
	
		
	   System.out.println("get form input");
	   for (TextField textfield: textFields)
	   {
		   System.out.println(textfield.getId()+ textfield.getValue());
		   //formValues.put(textfield.getId(),textfield.getValue());
		   variables.put(this.fields_ids.get(textfield.getId()), textfield.getValue());
	   }
	   
	   if (isEnumInit)
	   {
		   String enumKey="";
		    Set set = this.fields_ids.entrySet();
		    Iterator iterator = set.iterator();
		    int field_num=0;
		    while(iterator.hasNext())
		    {
		         Map.Entry mentry = (Map.Entry)iterator.next();
		         if (mentry.getValue().equals(selected))
		         {
		        	 enumKey=mentry.getKey().toString();
		         }
		         
	        }
		    
		    System.out.println("Button click mai key is: "+enumId+" and selected value is: "+selected);
		    variables.put(enumId,selected);
	   }
	   
	   	taskService.complete(task.getId(), variables);
	   	MyProcess process=new MyProcess();
   		
   		
		try {
			 
			System.out.println("BLA BLA");
			process.execHistoricInstance();
			System.out.println("new new");
			if (process.processInstance!=null)
				process.ActivitiWork();
			else
			{
				System.out.println("DISSABLING");
				
			}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	   
	   
	   
	   
	  
	}

		
}
