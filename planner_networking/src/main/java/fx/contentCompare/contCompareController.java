package fx.contentCompare;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
public class contCompareController
{
	@FXML
	private Button okButton;
	
	@FXML
	private Label compDisplay;
	
	Stage primaryStage;
	BorderPane mainView;
	
	public contCompareController()
	{
		// TODO Auto-generated constructor stub
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
