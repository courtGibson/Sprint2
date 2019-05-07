package fx.checkSave;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

import fx.homePageView.HomePageViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Language;
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
	String langTag;
	String propBund;
	Language l;
	
	@FXML
	Label unsaved;
	
	@FXML
	Label exiting;


	
	
	
	
	
	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
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

	public void setNewText()
	{
	
		unsaved.setText(l.getNewWord("unsaved.text"));
		exiting.setText(l.getNewWord("exiting.text"));
		checkSave.setText(l.getNewWord("save.text"));
		checkExit.setText(l.getNewWord("exit.text"));

	}
	
	public void exit() throws IOException
	{
		
		

			
			
			FXMLLoader loader = new FXMLLoader();
			Locale locale = Locale.forLanguageTag(langTag);
			
			ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

			loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
			loader.setResources(labels);
			BorderPane newMain = loader.load();
			
			HomePageViewController cont = loader.getController();
			cont.setLangTag(langTag);
			cont.setPropBund(propBund);
			cont.setLanguage(l);




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
