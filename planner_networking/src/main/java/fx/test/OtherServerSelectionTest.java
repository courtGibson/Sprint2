package fx.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Locale;
import java.util.ResourceBundle;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import serverView.ServerViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.LangEnglish;
import software_masters.planner_networking.Language;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;
import javafx.scene.layout.BorderPane;


public class OtherServerSelectionTest extends ApplicationTest
{
	
	static Server testServer;
	static Client testClient;
	static Server actualServer;
	static Registry registry;
	private Stage primaryStage;
	BorderPane mainView;
	ServerViewController cont;
	String langTag = "en-US";
	String propBund = "prop/en";
	Language l = new LangEnglish();

	
	@Override
	public void start(Stage primaryStage) throws Exception 
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
		
		this.cont = cont;
		
	
	}
	
	
	//other methods
	public void checkRBText(String id, String val)
	{
		assertThat(lookup(id).queryAs(Text.class)).hasText(val);
	
	}
	

	public void type(String id, String val)
	{
		clickOn(id);
		push(javafx.scene.input.KeyCode.SHORTCUT, javafx.scene.input.KeyCode.A);
		write(val);
		
	}

	
	@Test
	public void checkOtherConnect()
	{
		
		clickOn("#OtherServerButton");
		
		checkRBText("#localHost", "Local Host:");
		checkRBText("#other", "Other:");
		
		clickOn("#OtherServerTextField");
		write("127.0.0.1");
		
		clickOn("#ServerSubmitButton");
		
		assertTrue(primaryStage.getUserData().toString().contains("loginView"));
		
		assertThat(cont.getTestClient().getCookie() == null);
		clickOn("#UsernameTextField");
		write("user");
		clickOn("#PasswordTextField");
		write("user");
		clickOn("#LoginSubmitButton");
		assertThat(cont.getTestClient().getCookie() != null);

		
		
	}
	
}
