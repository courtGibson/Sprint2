package fx.homePageView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import fx.choosePlan.ChoosePlanController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import loginView.LoginViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;

public class HomePageViewController
{
	
	public Client testClient;
	
	@FXML 
	private Button planSubmitButton;
	
	@FXML 
	private Button logoutButton;

	Stage primaryStage;
	BorderPane mainView;
	
	//PlanFile selectedPlan;
	
	@FXML
	private ComboBox<String> menu;
	
	int count = 0;
	
	
	@FXML
	Label user;
	
	@FXML
	Label dept;
	
	public void setDept(String deptName)
	{
		dept.setText(deptName);
	}
	
	
	public void setUser(String userName)
	{
		user.setText(userName);
	}
	
	
	public void makeMenu() throws RemoteException
	{	
		//
		
		//ObservableList<String> thisArray = new ObservableList<String>();

		// Use Java Collections to create the List.
        List<String> list = new ArrayList<String>();
        
        ArrayList<PlanFile> plans = testClient.getPlans();
        

 
        // Now add observability by wrapping it with ObservableList.
        ObservableList<String> thisArray = FXCollections.observableList(list);
		
        //System.out.println("we are here");
        for (PlanFile p : plans)
        {
        	thisArray.add(p.getYear());
        }
		 
		//menu.getItems().addAll(thisArray);
		 
		//Label selected = new Label("Select plan");

        //selected.setText(menu.getValue().getPlan().getName()+menu.getValue().getYear()); 
            
	if (count==0)
	{
		 menu.setItems(thisArray);
		 count ++;
	}
      
		 
		 
  
	
	}
	
	public void logout() throws IOException {
		System.out.println("logout");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		//this.mainView = loader.load();
		BorderPane newMain = loader.load();
		
		LoginViewController cont = loader.getController();
		cont.setTestClient(this.testClient);
		cont.setPrimaryStage(primaryStage);
		
		primaryStage.getScene().setRoot(newMain);
	}
	
	
	public void selectPlan() throws IllegalArgumentException, RemoteException
	{
		
		testClient.getPlan(menu.getValue());
		
		//selectedPlan = testClient.getCurrPlanFile();
		
		
	}
	
	public void submit() throws IOException
	{
		// do stuff to set up next view
		
		System.out.println("button click");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/fx/choosePlan/choosePlan.fxml"));
		this.mainView = loader.load();
		
		ChoosePlanController cont = loader.getController();
		cont.setTestClient(this.testClient);
		cont.setPrimaryStage(primaryStage);


		cont.setDept(dept.getText());
		cont.setTestClient(testClient);
		
		cont.setUser(user.getText());
		
		primaryStage.getScene().setRoot(mainView);
	}
	 
	
	
	
	
	

	
	public Client getTestClient()
	{
	
		return testClient;
	}

	public void setTestClient(Client testClient)
	{
	
		this.testClient = testClient;
	}

	public Stage getPrimaryStage()
	{
	
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage)
	{
	
		this.primaryStage = primaryStage;
	}

	public BorderPane getMainView()
	{
	
		return mainView;
	}

	public void setMainView(BorderPane mainView)
	{
	
		this.mainView = mainView;
	}

	
	

	
	
}