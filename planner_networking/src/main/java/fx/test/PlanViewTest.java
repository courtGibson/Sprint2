package fx.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loginView.LoginViewController;
import serverView.ServerViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loginView.LoginViewController;
import serverView.ServerViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;


import static org.junit.jupiter.api.Assertions.*;


public class PlanViewTest extends ApplicationTest{
	static Server testServer;
	static Client testClient;
	static Server actualServer;
	static Registry registry;
	private Stage primaryStage;
	BorderPane mainView;
	LoginViewController cont;
	
	@Override
	public void start(Stage primaryStage) throws IOException, NotBoundException
	{
		this.primaryStage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/serverView/serverView.fxml"));
		mainView = loader.load();

		
		ServerViewController cont = loader.getController();
		cont.setMainView(mainView);
		cont.setPrimaryStage(primaryStage);
		
		Scene s = new Scene(mainView);
		primaryStage.setScene(s);
		primaryStage.show();
		
		registry = LocateRegistry.createRegistry(1077);
		
		ServerImplementation server = ServerImplementation.load();
		
		actualServer = server;
		Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
		registry.rebind("PlannerServer", stub);
		
		this.testServer = (Server) registry.lookup("PlannerServer");
		this.testClient = new Client(testServer);
		
		this.testClient = testClient;
		
		FXMLLoader loader2 = new FXMLLoader();
		loader2.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		this.mainView = loader2.load();
		assertThat(mainView!=null);
		
		LoginViewController cont2 = loader2.getController();
		cont2.setTestClient(testClient);
		cont2.setPrimaryStage(primaryStage);
		cont2.setMainView(mainView);
		this.cont = cont2;
		
		primaryStage.getScene().setRoot(mainView);
		primaryStage.show();
		
	}
	
	@After
	public void tearDown () throws Exception
	{
		registry.unbind("PlannerServer");
		UnicastRemoteObject.unexportObject(registry, true);
		System.out.println("Closing RMI Server");
	}

	@Test
	public void testMakingSureTreeLoads()
	{
		//Want to show the last node of a standard plan exists
		navigateToPage();
		
		
		clickOn("Mission");
		doubleClickOn("Mission");
		doubleClickOn("Goal");
		doubleClickOn("Learning Objective");
		doubleClickOn("Assessment Process");
		clickOn("Results");
		
		assertEquals(getTextLabel("#nodeLabel"), "Results");
		
		
		//Checking changing content
		clickOn("#contents");
		write("Add some good content here");
		clickOn("#tree");
		clickOn("Mission");
		clickOn("Goal");
		clickOn("Learning Objective");
		clickOn("Assessment Process");
		clickOn("Results");
		
		assertEquals(getTextTextArea("#contents"), "Add some good content here");
		
		//Check add branch
		TreeView thisTree = (TreeView) lookup("#tree").query();
		assertEquals(thisTree.getRoot().getChildren().size(), 1);
		clickOn("Goal");
		clickOn("#addBtn");
		
		clickOn("#tree");
		clickOn("Mission");
		doubleClickOn("Mission");
		
	
		assertEquals(thisTree.getRoot().getChildren().size(), 2);
		
		//Check remove branch
		assertEquals(thisTree.getRoot().getChildren().size(), 2);
		
		clickOn("Goal");
		clickOn("#removeBtn");
		clickOn("#tree");
		clickOn("Mission");
		doubleClickOn("Mission");
		assertEquals(thisTree.getRoot().getChildren().size(), 1);
		
		
		
		//Check homepage button
		

		doubleClickOn("Goal");
		doubleClickOn("Learning Objective");
		doubleClickOn("Assessment Process");
		clickOn("Results");
		clickOn("#contents");
		write("Add some good content here");
		
		assertEquals(getTextTextArea("#contents"), "Add some good content here");
		clickOn("#homepageButton");
		clickOn("#checkSave");
		
		//Check that save worked by looking for text about good content
		clickOn("#menu");
		clickOn("2019");
		clickOn("#submit");
		clickOn("#viewPlanRBtn");
		clickOn("#planSubBtn");
		clickOn("#tree");
		clickOn("Mission");
		doubleClickOn("Mission");
		doubleClickOn("Goal");
		doubleClickOn("Learning Objective");
		doubleClickOn("Assessment Process");
		
		clickOn("Results");
		
		
		assertEquals(getTextTextArea("#contents"), "Add some good content here");
		
		
		//Check save button
		clickOn("Mission");
		clickOn("#contents");
		write("Check save");
		clickOn("#saveBtn");
		clickOn("#homepageButton");
		
		clickOn("#menu");
		clickOn("2019");
		clickOn("#submit");
		clickOn("#viewPlanRBtn");
		clickOn("#planSubBtn");
		clickOn("#tree");
		clickOn("Mission");
		clickOn("#contents");
		
		assertEquals(getTextTextArea("#contents"), "Check save");
		
		clickOn("#logoutButton");
		
		
		Button thisButton = (Button) lookup("#LoginSubmitButton").query();
		assertEquals(thisButton.getText(), "Submit");
		
		navigateToPage();
		
		doubleClickOn("Mission");
		
		
		clickOn("#contents");
		write(" Exit");
		
		clickOn("#homepageButton");
		clickOn("#checkExit");
		
		clickOn("#menu");
		clickOn("2019");
		clickOn("#submit");
		clickOn("#viewPlanRBtn");
		clickOn("#planSubBtn");
		clickOn("#tree");
		clickOn("Mission");
		
		assertEquals(getTextTextArea("#contents"), "Check save");
		
	}
	


	public void navigateToPage()
	{
		clickOn("#UsernameTextField");
		write("user");
		clickOn("#PasswordTextField");
		write("user");
		clickOn("#LoginSubmitButton");
		clickOn("#menu");
		clickOn("2019");
		clickOn("#submit");
		clickOn("#viewPlanRBtn");
		clickOn("#planSubBtn");
		clickOn("#tree");
		
		
	}

	@SuppressWarnings("unchecked")
	public String getTextLabel(String label)
	{

		Label thisLabel = (Label) lookup(label).query();
		return thisLabel.textProperty().get();

	}
	
	@SuppressWarnings("unchecked")
	public String getTextTextArea(String label)
	{

		TextArea thisLabel = (TextArea) lookup(label).query();
		return thisLabel.textProperty().get();

	}
	
	@Test
	public void testComments()
	{
		navigateToPage();
		
		clickOn("Mission");
		clickOn("#comment");
		
		clickOn("#addComment");
		
		clickOn("#commentText");
		
		write("This is a Comment");
		
		clickOn("#submitButton");
		clickOn("Mission");
		clickOn("#comment");
		
		clickOn("#addComment");
		
		clickOn("#commentText");
		
		write("This is a second Comment");
		
		clickOn("#submitButton");
		
		clickOn("Mission");
		assertEquals(testClient.getCurrNode().getComments().size(), 2);
		clickOn("#comment");
		
		clickOn("#commentMenu");
		
		clickOn("This is a Comment");
		
		clickOn("#displayButton");
		
		assertEquals(getTextLabel("#commentLabel"), "This is a Comment");
		
		clickOn("#removeComment");
		
		clickOn("#submitButton");
		clickOn("Mission");
		assertEquals(testClient.getCurrNode().getComments().size(), 1);
		
		
	}


}
