package fx.compare;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Language;
import software_masters.planner_networking.PlanFile;

public class CompareViewController 
{

	public CompareViewController() 
	{
		
	}
	
	public PlanFile plan;
	public Client testClient;
	String langTag;
	String propBund;
	Language l;
	Stage primaryStage;
	PlanFile originalPlan;
	PlanFile comparePlan;
	
	@FXML
	public Text planA;
	
	@FXML
	public Text planB;
	
	@FXML
	public Button homePage;
	
	@FXML
	public Button logout;
	
	@FXML
	public Text comparePlans;
	
	public void setPlans(PlanFile pA, PlanFile pB)
	{
		originalPlan = pA;
		comparePlan = pB;
		planA.setText(pA.getYear());
		planB.setText(pB.getYear());
	}
	
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
	
	public void setPrimaryStage(Stage primaryStage)
	{

		this.primaryStage = primaryStage;
	}

	public void setNewText()
	{
	
		homePage.setText(l.getNewWord("homePage.text"));
		logout.setText(l.getNewWord("logout.text"));
		comparePlans.setText(l.getNewWord("comparePlan.text"));

	
	}

}
