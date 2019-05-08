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
import software_masters.planner_networking.LangFrench;
import software_masters.planner_networking.LangSpanish;
import software_masters.planner_networking.Language;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;
import javafx.scene.layout.BorderPane;


public class DefaultServerSelectionTest extends ApplicationTest
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
		cont.setLangTag(langTag);
		cont.setPropBund(propBund);
		cont.setLanguage(l);

		cont.setMainView(mainView);
		cont.setPrimaryStage(primaryStage);
		
		Scene s = new Scene(mainView);
		primaryStage.setScene(s);
		primaryStage.show();
		
		this.cont = cont;
		
	
	}
	

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
	public void checkDefaultConnect()
	{
		
		clickOn("#DefaultServerButton");
		
		checkRBText("#localHost", "Local Host:");
		checkRBText("#other", "Other:");
	
		clickOn("#ServerSubmitButton");
		
		clickOn("#selection");
		clickOn("Spanish");
		
		assertTrue(primaryStage.getUserData().toString().contains("loginView"));
		
		
		assertThat(cont.getTestClient().getCookie() == null);
		clickOn("#UsernameTextField");
		write("user");
		clickOn("#PasswordTextField");
		write("user");
		clickOn("#LoginSubmitButton");
		assertThat(cont.getTestClient().getCookie() != null);
		
	}
	
	@Test
	public void checkLanguageEng()
	{		
		assertThat(lookup("#servSelect").queryText()).hasText("Server Selection");
		assertThat(lookup("#localHost").queryAs(Text.class)).hasText("Local Host:");
		assertThat(lookup("#selectLang").queryAs(Label.class)).hasText("Select Language:");
		assertThat(lookup("#ServerSubmitButton").queryButton().getText().contentEquals("Submit"));
		assertThat(lookup("#other").queryAs(Text.class)).hasText("Other:");
		
		
	/*	clickOn("#ServerSubmitButton");
		cont.setLangTag("en-US");
		cont.setLanguage(new LangEnglish());
		cont.setPropBund("prop/en");
		assertThat(lookup("#login").queryText()).hasText("Login");
		assertThat(lookup("#user").queryAs(Text.class)).hasText("Username:");
		assertThat(lookup("#pass").queryAs(Label.class)).hasText("Password:");
		assertThat(lookup("#ServerSubmitButton").queryButton().getText().contentEquals("Submit"));*/
	}
	
	@Test
	public void checkLanguageSpa()
	{
		clickOn("#selection");
		clickOn("Spanish");
		
		assertThat(lookup("#servSelect").queryText()).hasText("Selecci\u00F3n de Servidor");
		assertThat(lookup("#localHost").queryAs(Text.class)).hasText("Anfitri\u00F3n Local:");
		assertThat(lookup("#selectLang").queryAs(Label.class)).hasText("Seleccione el Idioma:");
		assertThat(lookup("#ServerSubmitButton").queryButton().getText().contentEquals("Enviar"));
		assertThat(lookup("#other").queryAs(Text.class)).hasText("Otro:");
		
		/*clickOn("#ServerSubmitButton");
		cont.setLangTag("sp-SP");
		cont.setLanguage(new LangSpanish());
		cont.setPropBund("prop/sp");
		assertThat(lookup("#login").queryText()).hasText("Iniciar Sesi\u00F3n");
		assertThat(lookup("#user").queryAs(Text.class)).hasText("Nombre de Usuario:");
		//assertThat(lookup("#pass").queryAs(Label.class)).hasText("Contrase\u00F1a:");
		assertThat(lookup("#ServerSubmitButton").queryButton().getText().contentEquals("Enviar"));*/
		
	
	}
	
	@Test
	public void checkLanguageFre()
	{
		clickOn("#selection");
		clickOn("French");
		
		
		assertThat(lookup("#servSelect").queryText()).hasText("S\u00E9lection du Serveur");
		assertThat(lookup("#localHost").queryAs(Text.class)).hasText("H\u00F4te Local:");
		assertThat(lookup("#selectLang").queryAs(Label.class)).hasText("Choisir la Langue:");
		assertThat(lookup("#ServerSubmitButton").queryButton().getText().contentEquals("Soumettre"));
		assertThat(lookup("#other").queryAs(Text.class)).hasText("Autre:");
		
		/*clickOn("#ServerSubmitButton");
		cont.setLangTag("fr-FR");
		cont.setLanguage(new LangFrench());
		cont.setPropBund("prop/fr");
		assertThat(lookup("#login").queryText()).hasText("S'Identifier");
		assertThat(lookup("#user").queryAs(Text.class)).hasText("Username:");
		assertThat(lookup("#pass").queryAs(Label.class)).hasText("Password:");
		assertThat(lookup("#ServerSubmitButton").queryButton().getText().contentEquals("Soumettre"));*/
	}
	
	

	
}
