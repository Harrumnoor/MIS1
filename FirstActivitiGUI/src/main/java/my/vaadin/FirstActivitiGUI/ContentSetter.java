package my.vaadin.FirstActivitiGUI;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ContentSetter implements Runnable
{
	public UI myUi;
	VerticalLayout v;

	@Override
	public void run() 
	{
		System.out.println("haan SANA agaya hun tayto khilao");
		// TODO Auto-generated method stub
		myUi.setContent(v);
		
	}
	

}
