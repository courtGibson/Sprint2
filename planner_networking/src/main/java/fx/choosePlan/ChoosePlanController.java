package fx.choosePlan;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import fx.contentCompare.contCompareController;
import fx.planView.PlanViewController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import software_masters.planner_networking.PlanNode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import software_masters.planner_networking.Centre;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Plan;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;


public class ChoosePlanController
{
	public PlanFile plan;
	public Client testClient;
	
	
	
	
	Stage primaryStage;
	// to commit
	public void setTestClient(Client testClient)
	{
		this.testClient = testClient;
		
	}
	
	
	// make the FXML stuff
	
	@FXML
	private RadioButton viewPlanRBtn;
	
	@FXML
	private RadioButton newPlanRBtn;
	
	@FXML
	private TextField newPlanYearText;
	
	BorderPane mainView;
	
	@FXML
	private Button planSubBtn;
	
	@FXML
	private Label viewPlanLabel;
	
	@FXML
	private Label newPlanLabel;
	
	@FXML
	private Label planYearLabel;
	
	@FXML
	private RadioButton compButton;
	
	
	String user;
	

	String dept;
	
	public void setDept(String deptName)
	{
		this.dept = deptName;
	}
	
	
	public void setUser(String userName)
	{
		this.user = userName;
	}
	
	public void choosePlanType() throws IOException
	{
		

		
		
		if(viewPlanRBtn.isSelected())
		{
			
			System.out.println("hello");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fx/planView/planView.fxml"));
			//this.mainView = loader.load();
			BorderPane newMain = loader.load();
			
			PlanViewController cont = loader.getController();
			cont.setTestClient(testClient);
			
			cont.setPrimaryStage(primaryStage);
			

	
			cont.setDept(dept);
		
			
			cont.setUser(user);
		
			
			primaryStage.getScene().setRoot(newMain);
			
			

			
			
		}
		else if (newPlanRBtn.isSelected())// newPlanButton selected
		{
			String planYear = newPlanYearText.getText();
			System.out.println(planYear);
			testClient.getCurrPlanFile().setYear(planYear);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fx/planView/planView.fxml"));
			this.mainView = loader.load();
			
			PlanViewController cont = loader.getController();
			cont.setTestClient(testClient);
			
			
			cont.setDept(dept);
		
			
			cont.setUser(user);
			
			cont.setPrimaryStage(primaryStage);
			
		
			
		
			
			primaryStage.getScene().setRoot(mainView);
			

			
			
			
		}
		
		else
		{
			String planYear = newPlanYearText.getText();
			System.out.println(planYear);
			testClient.getCurrPlanFile().setYear(planYear);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fx/contentCompare/contentChange.fxml"));
			this.mainView = loader.load();
			
			contCompareController cont = loader.getController();
			
			
			cont.setPrimaryStage(primaryStage);
			
		
			
		
			
			primaryStage.getScene().setRoot(mainView);
			
			
		}
		
		
	}



	/**
	 * @return the plan
	 */
	public PlanFile getPlan()
	{
		return plan;
	}



	/**
	 * @param plan the plan to set
	 */
	public void setPlan(PlanFile plan)
	{
		this.plan = plan;
	}



	public void setPrimaryStage(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		
	}
	
	
}