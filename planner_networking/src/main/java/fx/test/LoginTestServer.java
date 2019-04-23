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



public class LoginTestServer extends ApplicationTest
{
	
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
	public void testGoodUsernamePassword()
	{
		assertThat(cont.getTestClient().getCookie() == null);
		clickOn("#UsernameTextField");
		write("user");
		clickOn("#PasswordTextField");
		write("user");
		clickOn("#LoginSubmitButton");
		assertThat(cont.getTestClient().getCookie() != null);

	}
	
	@Test
	public void testBadUsernamePassword()
	{
		assertThat(cont.getTestClient().getCookie() == null);
		assertEquals(getText("#error"), "Label");
		clickOn("#UsernameTextField");
		write("user");
		clickOn("#PasswordTextField");
		write("notuser");
		clickOn("#LoginSubmitButton");
		assertEquals(getText("#error"), "Your username or password is incorrect.");
		assertThat(cont.getTestClient().getCookie() == null);

		
		
	}
	
	@SuppressWarnings("unchecked")
	String getText(String label)
	{

		Label thisLabel = (Label) lookup(label).query();
		return thisLabel.textProperty().get();

	}


}
