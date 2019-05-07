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
	
	@FXML
	public ComboBox<String> compareMenu;
	
	@FXML
	public Button homePage;
	
	@FXML
	public Button logout;
	
	@FXML
	public Text comparePlans;
	
	
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
	
	public void setNewText()
	{
	
		compareMenu.setPromptText(l.getNewWord("selectedPlan.text"));
		homePage.setText(l.getNewWord("viewPlan.text"));
		logout.setText(l.getNewWord("makeNewPlan.text"));
		comparePlans.setText(l.getNewWord("newPlanYear.text"));
	
	}

}
