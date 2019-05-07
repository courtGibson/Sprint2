package fx.homePageView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
import software_masters.planner_networking.Language;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;

public class HomePageViewController
{
	String langTag;
	String propBund;
	Language l;


	public Client testClient;
	
	@FXML 
	public Button planSubmitButton;
	
	@FXML 
	public Button logoutButton;

	Stage primaryStage;
	BorderPane mainView;
	
	PlanFile selectedPlan;
	
	@FXML
	public ComboBox<String> menu;
	
	int count = 0;
	
	
	@FXML
	Label user;
	
	@FXML
	Label dept;
	@FXML
	Label deptText;
	@FXML
	Label userText;
	@FXML
	Label homePage;
	@FXML
	Label deptPlans;
	
	
	public void setLangTag(String tag)
	{
		langTag = tag;
	}
	
	public void setPropBund(String bund)
	{
		propBund = bund;
	}
	
	public void setLanguage(Language lan)
	{
		l = lan;
		setNewText();
	}
	
	
	
	public void setDept(String deptName)
	{
		dept.setText(deptName);
	}
	
	
	public void setUser(String userName)
	{
		user.setText(userName);
	}
	
	
	public void setNewText()
	{
	
		planSubmitButton.setText(l.getNewWord("submitSelection.text"));
		userText.setText(l.getNewWord("username.text"));
		menu.setPromptText(l.getNewWord("selectedPlan.text"));
		planSubmitButton.setText(l.getNewWord("submit.text"));
		deptText.setText(l.getNewWord("department.text"));
		logoutButton.setText(l.getNewWord("logout.text"));
		homePage.setText(l.getNewWord("homePage.text"));
		deptPlans.setText(l.getNewWord("deptPlans.text"));

	}
	
	public void makeMenu() throws RemoteException
	{	
		//
		
		//ObservableList<String> thisArray = new ObservableList<String>();

		// Use Java Collections to create the List.
        List<String> list = new ArrayList<String>();
        
        ArrayList<PlanFile> plans = testClient.getPlans();
        
       /* for (PlanFile p :plans)
        {
        	list.add(p.getYear());
        }*/
 
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
	
	public void logout() throws IOException 
	{
		System.out.println("logout");
		
		FXMLLoader loader = new FXMLLoader();
		Locale locale = Locale.forLanguageTag(langTag);
		
		ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		loader.setResources(labels);
		BorderPane newMain = loader.load();
		
		LoginViewController cont = loader.getController();
		cont.setLangTag(langTag);
		cont.setPropBund(propBund);
		cont.setLanguage(l);

		cont.setTestClient(this.testClient);
		cont.setPrimaryStage(primaryStage);
		
		primaryStage.getScene().setRoot(newMain);
	}
	
	
	public void selectPlan() throws IllegalArgumentException, RemoteException
	{
		
		testClient.getPlan(menu.getValue());
		
		selectedPlan = testClient.getCurrPlanFile();
		
		
	}
	
	public void submit() throws IOException
	{
		// do stuff to set up next view
		
		System.out.println("button click");
		
		FXMLLoader loader = new FXMLLoader();
		Locale locale = Locale.forLanguageTag(langTag);
		
		ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

		loader.setLocation(Main.class.getResource("/fx/choosePlan/choosePlan.fxml"));
		loader.setResources(labels);

		this.mainView = loader.load();
		
		ChoosePlanController cont = loader.getController();
		cont.setLangTag(langTag);
		System.out.println("\n\nlangTag: "+langTag);
		cont.setPropBund(propBund);
		cont.setLanguage(l);

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