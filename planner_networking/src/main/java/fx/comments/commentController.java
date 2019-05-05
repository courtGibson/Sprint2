package fx.comments;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.VBox;
import fx.checkSave.CheckSaveController;
import fx.homePageView.HomePageViewController;
import fx.planView.PlanViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import loginView.LoginViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.FXTreeView;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.PlanNode;

public class commentController
{

	@FXML
	private Label commentLabel;
	
	@FXML
	private Button displayButton;
	
	@FXML
	private ComboBox<String> commentMenu;
	
	
	@FXML
	private RadioButton addComment;
	
	
	@FXML
	private RadioButton removeComment;
	
	@FXML
	private TextField commentText;
	
	@FXML
	private Button submitButton;
	
	
	/**
	 * 
	 */
	PlanViewController current;
	
	/**
	 * @return the current
	 */
	public PlanViewController getCurrent()
	{
		return current;
	}
	/**
	 * @param current the current to set
	 */
	public void setCurrent(PlanViewController current)
	{
		this.current = current;
	}


	BorderPane mainView;

	Stage primaryStage;
	
	
	
	int count = 0;

	
	public Client testClient;

	
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
	
	public void makeMenu() throws RemoteException
	{	
		//
		
		//ObservableList<String> thisArray = new ObservableList<String>();

		// Use Java Collections to create the List.
        List<String> list = new ArrayList<String>();
        
        
        
        PlanNode node = current.getTestClient().getCurrNode();
        
        ArrayList<String> comment = node.getComments();
 
        // Now add observability by wrapping it with ObservableList.
        ObservableList<String> thisArray = FXCollections.observableList(list);
		
        //System.out.println("we are here");
        for (String p : comment)
        {
        	thisArray.add(p);
        }
		 
		//menu.getItems().addAll(thisArray);
		 
		//Label selected = new Label("Select plan");

        //selected.setText(menu.getValue().getPlan().getName()+menu.getValue().getYear()); 
            
        if (count==0)
        {
		
        	commentMenu.setItems(thisArray);
        	count ++;
        }
      
		 
		 
  
	
	}
	
	public void submit() throws RemoteException
	{
		
		if(addComment.isSelected())
		{
			add();
			
			
		}
		else if (removeComment.isSelected())
		{
			remove();
			
			
		}
		else
		{
			
			System.out.println("u did nothing");
			
		}
		primaryStage.close();
	}
	
	
	public void add() throws RemoteException
	{
		current.getTestClient().getCurrNode().addNote(commentText.getText());;
		System.out.println(current.getTestClient().getCurrNode().getComments().get(0));
	}
	
	public void remove() throws RemoteException
	{
		current.getTestClient().getCurrNode().removeNote(commentMenu.getValue());;
		
	}
	
	public void setLabel()
	{
		commentLabel.setText(commentMenu.getValue());
		
	}
}
