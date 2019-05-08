package fx.test;

import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.ComboBoxMatchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import java.io.IOException;
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

import fx.choosePlan.ChoosePlanController;
import fx.homePageView.HomePageViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loginView.LoginViewController;
import serverView.ServerViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.LangEnglish;
import software_masters.planner_networking.Language;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;



public class HomePageTest extends ApplicationTest
{
	private Server testServer;
	private Client testClient;
	private Server actualServer;
	private Registry registry;
	private Stage stage;
	private HomePageViewController cont;
	String langTag = "en-US";
	String propBund = "prop/en";
	Language l = new LangEnglish();


	@Override
	public void start(Stage stage) throws Exception
	{
		System.out.println("Start");
		this.stage = stage;
		
		registry = LocateRegistry.createRegistry(1077);
		ServerImplementation server = ServerImplementation.load();
		
		actualServer = server;
		Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
		registry.rebind("PlannerServer", stub);
		
		this.testServer = (Server) registry.lookup("PlannerServer");
		
		this.testClient = new Client(testServer);
		String username = "user";
		testClient.login(username, "user");
			
		FXMLLoader loader = new FXMLLoader();
		Locale locale = Locale.forLanguageTag("en-US");
		
		ResourceBundle labels = ResourceBundle.getBundle("prop/en", locale);
		loader.setResources(labels);
		loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
		Scene s = new Scene(loader.load());
		cont = loader.getController();
		cont.setUser(username);
		cont.setLangTag(langTag);
		cont.setPropBund(propBund);
		cont.setLanguage(l);

		String deptName = testClient.getServer().getCookieMap().get(testClient.getCookie()).getDepartment().getDeptName();
		System.out.println("deptName: "+deptName);
		cont.setDept(deptName);
		cont.setTestClient(testClient);
		cont.setUser(username);
		
		cont.setPrimaryStage(stage);
		stage.setWidth(800);
		stage.setScene(s);
		stage.show();
		
		
		
	}
	
	
	@After
	public void tearDown () throws Exception
	{
		registry.unbind("PlannerServer");
		UnicastRemoteObject.unexportObject(registry, true);
		System.out.println("Closing RMI Server");
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMenu()
	{
		//assertEquals(true, ((Node) lookup("#menu")).);
		assertEquals("Selected Plan:", getComboBoxText("#menu"));
		clickOn("#menu");
		
		//type(KeyCode.DOWN);
		clickOn("2019");
		
		clickOn("#submit");
		
		assertEquals("2019", cont.getTestClient().getCurrPlanFile().getYear());
		
	}
	


	
	
	
	private String getComboBoxText(String id)
	{

		String c = lookup(id).queryComboBox().getPromptText();
		return c;

	}
	
	@Test
	public void testLogout()
	{
		assertEquals("Logout", getButtonText("#logoutButton"));
		clickOn("#logoutButton");
		
	}
	
	private String getButtonText(String id)
	{
		Button b = lookup(id).query();
		return b.getText();
	}
	
	
	@Test
	public void testLabels()
	{
		// labels based on what client is logged on
		assertEquals(getTextLabel("#user"), "user");
		assertEquals(getTextLabel("#dept"), "default");
		
		
	}
	
	String getTextLabel(String label)
	{

		Label thisLabel = (Label) lookup(label).query();
		return thisLabel.textProperty().get();

	}
	
	
	
}
