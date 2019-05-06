package software_masters.planner_networking;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loginView.LoginViewController;
import serverView.ServerViewController;

public class Main extends Application
{

	Stage primaryStage;
	BorderPane mainView;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		this.primaryStage = primaryStage;
		
		FXMLLoader loader = new FXMLLoader();
		Locale locale = Locale.forLanguageTag("en-US");
		
		ResourceBundle labels = ResourceBundle.getBundle("prop/en", locale);
		
		/*System.out.println("labels: "+labels);
		for (Enumeration<String> e = labels.getKeys(); e.hasMoreElements();)
		{
			 System.out.println("Keys: "+e.nextElement());
		}*/
		      

		loader.setLocation(Main.class.getResource("/serverView/serverView.fxml"));
		loader.setResources(labels);
		mainView = loader.load();//getClass().getResource("/serverView/serverView.fxml"),labels);
		
		
		ServerViewController cont = loader.getController();
		cont.setMainView(mainView);
		cont.setPrimaryStage(primaryStage);
		
		Scene s = new Scene(mainView);
		primaryStage.setScene(s);
		primaryStage.setUserData(cont);
		primaryStage.show();
		
		    
		
		
	}
	
	public static void main(String[] args) 
	{
		launch(args);

	}

}
