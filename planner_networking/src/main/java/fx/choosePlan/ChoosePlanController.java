package fx.choosePlan;

import java.io.IOException;
import javafx.scene.control.ComboBox;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import fx.compare.CompareViewController;
import fx.planView.PlanViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import software_masters.planner_networking.Language;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Plan;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;


public class ChoosePlanController
{
	public PlanFile plan;
	public Client testClient;
	String langTag;
	String propBund;
	Language l;

	
	
	Stage primaryStage;
	// to commit
	public void setTestClient(Client testClient)
	{
		this.testClient = testClient;
		
	}
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
	
	
	// make the FXML stuff
	
	@FXML
	public RadioButton viewPlanRBtn;
	
	@FXML
	public RadioButton newPlanRBtn;
	
	@FXML
	public TextField newPlanYearText;
	
	BorderPane mainView;
	
	@FXML
	public Button planSubBtn;
	
	@FXML
	public Label viewPlanLabel;
	
	@FXML
	public Label newPlanLabel;
	
	@FXML
	public Label planYearLabel;
	@FXML
	public Label compare;
	@FXML
	public RadioButton compareBtn;
	
	PlanFile comparePlan;
	
	int count = 0;

	String user;
	

	String dept;
	@FXML
	Label selectedPlan;
	@FXML 
	public ComboBox<String> compareMenu;
	
	public void setDept(String deptName)
	{
		this.dept = deptName;
	}
	
	
	public void setUser(String userName)
	{
		this.user = userName;
	}
	
	
	public void setNewText()
	{
	
		selectedPlan.setText(l.getNewWord("selectedPlan.text"));
		viewPlanLabel.setText(l.getNewWord("viewPlan.text"));
		newPlanLabel.setText(l.getNewWord("makeNewPlan.text"));
		planYearLabel.setText(l.getNewWord("newPlanYear.text"));
		planSubBtn.setText(l.getNewWord("submit.text"));
		compare.setText(l.getNewWord("compareTo.text"));
		compareMenu.setPromptText(l.getNewWord("selectComparePlan.text"));

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
		 
	
            
		if (count==0)
		{
			 compareMenu.setItems(thisArray);
			 count ++;
		}

	
	}
	
	public void selectPlan() throws IllegalArgumentException, RemoteException
	{
		
		testClient.getPlan(compareMenu.getValue());
		
		comparePlan = testClient.getCurrPlanFile();
		
		
	}
	
	public void choosePlanType() throws IOException
	{
		

		
		
		if(viewPlanRBtn.isSelected())
		{
			
			System.out.println("hello");
			FXMLLoader loader = new FXMLLoader();
			Locale locale = Locale.forLanguageTag(langTag);
			
			ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

			loader.setLocation(Main.class.getResource("/fx/planView/planView.fxml"));
			loader.setResources(labels);
			BorderPane newMain = loader.load();
			
			PlanViewController cont = loader.getController();
			cont.setLangTag(langTag);
			cont.setPropBund(propBund);
			cont.setLanguage(l);

			cont.setTestClient(testClient);
			
			cont.setPrimaryStage(primaryStage);
			

	
			cont.setDept(dept);
		
			
			cont.setUser(user);
		
			
			primaryStage.getScene().setRoot(newMain);
			
			

			
			
		}
		else if(newPlanRBtn.isSelected())// newPlanButton selected
		{
			String planYear = newPlanYearText.getText();
			System.out.println(planYear);
			testClient.getCurrPlanFile().setYear(planYear);
			
			FXMLLoader loader = new FXMLLoader();
			Locale locale = Locale.forLanguageTag(langTag);
			
			ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

			loader.setLocation(Main.class.getResource("/fx/planView/planView.fxml"));
			loader.setResources(labels);

			this.mainView = loader.load();
			
			PlanViewController cont = loader.getController();
			cont.setLangTag(langTag);
			cont.setPropBund(propBund);
			cont.setLanguage(l);

			cont.setTestClient(testClient);
			
			
			cont.setDept(dept);
		
			
			cont.setUser(user);
			
			cont.setPrimaryStage(primaryStage);
			
		
			
		
			
			primaryStage.getScene().setRoot(mainView);
	
		}
		else if(compareBtn.isSelected())
		{
			FXMLLoader loader = new FXMLLoader();
			Locale locale = Locale.forLanguageTag(langTag);
			
			ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

			loader.setLocation(Main.class.getResource("/fx/compare/compareView.fxml"));
			loader.setResources(labels);
			BorderPane newMain = loader.load();
			
			CompareViewController cont = loader.getController();
			cont.setLangTag(langTag);
			cont.setPropBund(propBund);
			cont.setLanguage(l);
			cont.setPlans(plan, comparePlan);
			cont.setDept(dept);
			cont.setUser(user);

			cont.setTestClient(testClient);
			
			cont.setPrimaryStage(primaryStage);

			
			primaryStage.getScene().setRoot(newMain);
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