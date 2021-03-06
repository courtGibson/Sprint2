package fx.checkSave;

import java.io.IOException;
import java.rmi.RemoteException;

import fx.homePageView.HomePageViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;

public class CheckSaveController
{

	
	public Client testClient;
	
	Stage primaryStage;
	
	BorderPane mainView;
	
	@FXML
	private Button checkSave;
	
	@FXML
	private Button checkExit;	
	
	boolean send;
	
	
	String user;
	String dept;
	
	
	
	
	
	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	/**
	 * @return the dept
	 */
	public String getDept()
	{
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public void exit() throws IOException
	{
		
		

			
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
			//this.mainView = loader.load();
			BorderPane newMain = loader.load();
			
			HomePageViewController cont = loader.getController();



			cont.setTestClient(testClient);
	
			
			
			cont.setPrimaryStage(primaryStage);
			

			cont.setDept(dept);
			cont.setUser(user);
			
			primaryStage.setWidth(800);
			primaryStage.getScene().setRoot(newMain);
			

		
	}
	
	public void save() throws IllegalArgumentException, IOException
	{
		
	
		this.testClient.pushPlan(testClient.getCurrPlanFile());
		exit();
		
	}

	/**
	 * @return the testClient
	 */
	public Client getTestClient()
	{
		return testClient;
	}

	/**
	 * @param testClient the testClient to set
	 */
	public void setTestClient(Client testClient)
	{
		this.testClient = testClient;
	}

	/**
	 * @return the primaryStage
	 */
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

	/**
	 * @param primaryStage the primaryStage to set
	 */
	public void setPrimaryStage(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
	}


	
	
	
}
