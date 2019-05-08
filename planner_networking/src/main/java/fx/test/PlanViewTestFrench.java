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
import java.util.Locale;
import java.util.ResourceBundle;

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
import software_masters.planner_networking.LangEnglish;
import software_masters.planner_networking.LangFrench;
import software_masters.planner_networking.LangSpanish;
import software_masters.planner_networking.Language;
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


public class PlanViewTestFrench extends ApplicationTest{
	static Server testServer;
	static Client testClient;
	static Server actualServer;
	static Registry registry;
	private Stage primaryStage;
	BorderPane mainView;
	LoginViewController cont;
	String langTag = "fr-FR";
	String propBund = "prop/fr";
	Language l = new LangFrench();
	
	
	@Override
	public void start(Stage primaryStage) throws IOException, NotBoundException
	{
		this.primaryStage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		Locale locale = Locale.forLanguageTag(langTag);
		
		ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);
		loader.setResources(labels);
		
		loader.setLocation(Main.class.getResource("/serverView/serverView.fxml"));
		mainView = loader.load();

		
		ServerViewController cont = loader.getController();
		cont.setMainView(mainView);
		cont.setPrimaryStage(primaryStage);
		cont.setLangTag(langTag);
		cont.setPropBund(propBund);
		cont.setLanguage(l);
		
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
		Locale locale2 = Locale.forLanguageTag(langTag);
		
		ResourceBundle labels2 = ResourceBundle.getBundle(propBund, locale2);
		loader2.setResources(labels2);
		
		loader2.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		this.mainView = loader2.load();
		assertThat(mainView!=null);
		
		LoginViewController cont2 = loader2.getController();
		cont2.setLangTag(langTag);
		cont2.setPropBund(propBund);
		cont2.setLanguage(l);
		cont2.setTestClient(testClient);
		cont2.setPrimaryStage(primaryStage);
		cont2.setMainView(mainView);
		this.cont = cont2;
		
		
		FXMLLoader loader3 = new FXMLLoader();
		Locale locale3 = Locale.forLanguageTag(langTag);
		
		ResourceBundle labels3 = ResourceBundle.getBundle(propBund, locale3);
		loader3.setResources(labels3);
		
		loader3.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		this.mainView = loader3.load();
		assertThat(mainView!=null);
		
		LoginViewController cont3 = loader3.getController();
		cont3.setLangTag(langTag);
		cont3.setPropBund(propBund);
		cont3.setLanguage(l);
		cont3.setTestClient(testClient);
		cont3.setPrimaryStage(primaryStage);
		cont3.setMainView(mainView);
		this.cont = cont3;
		
		
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
		
		//assertEquals(getTextLabel("#nodeLabel"), "Results");
		
		
		//Checking changing content
		clickOn("#contentsArea");
		write("Add");
		clickOn("#commentArea");
		write("hello");
		clickOn("#post");
		clickOn("#commentArea");
		write("comment");
		clickOn("#post");
		clickOn("#commentArea");
		write("type");
		clickOn("#post");
		/////////// test comments
		//assertEquals(getTextLabel("#comment0"), "user: hello");
		//assertEquals(getTextLabel("#comment1"), "user: comment");
		//assertEquals(getTextLabel("#comment2"), "user: type");
		clickOn("#comment1");
		clickOn("#delete");
		clickOn("#comment0");
		clickOn("#delete");
		/////////////////////////////////////////////
		clickOn("#tree");
		clickOn("Mission");
		clickOn("Goal");
		clickOn("Learning Objective");
		clickOn("Assessment Process");
		clickOn("Results");
		
		//assertEquals("Add", getTextTextArea("#contentsArea"));
		//assertEquals(getTextLabel("#comment0"), "user: type");
		//Check add branch
		TreeView thisTree = (TreeView) lookup("#tree").query();
		assertEquals(thisTree.getRoot().getChildren().size(), 1);
		clickOn("Goal");
		clickOn("#addBtn");
		
	
		
		
		clickOn("#tree");
		clickOn("Mission");
		doubleClickOn("Mission");
		
		//////// test compare 
		clickOn("#saveBtn");
		clickOn("#homepageButton");
		clickOn("#menu");
		clickOn("2019");
		clickOn("#submit");
		clickOn("#compareBtn");
		clickOn("#compareMenu");
		clickOn("2019");
		clickOn("#planSubBtn");
		clickOn("#tree1");
		clickOn("#tree2");
		clickOn("Mission");
		clickOn("Goal");
		clickOn("Learning Objective");
		clickOn("Assessment Process");
		clickOn("Results");
		
		assertEquals("2019", lookup("#planA").queryText().getText());
		assertEquals("2019", lookup("#planB").queryText().getText());
		
		clickOn("#homePage");
		
		clickOn("#menu");
		clickOn("2019");
		clickOn("#submit");
		clickOn("#compareBtn");
		clickOn("#compareMenu");
		clickOn("2017");
		clickOn("#planSubBtn");
		clickOn("#tree1");
		clickOn("#tree2");
		clickOn("Mission");
		clickOn("Goal");
		clickOn("Learning Objective");
		clickOn("Assessment Process");
		clickOn("Results");
		
		assertEquals("2019", lookup("#planA").queryText().getText());
		assertEquals("2017", lookup("#planB").queryText().getText());
		
		clickOn("#logout");
		
	//////////////////////

		//assertEquals("Check save", getTextTextArea("#contentsArea"));
		
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


}
