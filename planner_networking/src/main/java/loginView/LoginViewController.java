package loginView;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

import fx.homePageView.HomePageViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import software_masters.planner_networking.Account;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Department;
import software_masters.planner_networking.Language;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Server;




public class LoginViewController
{
	Stage primaryStage;
	BorderPane mainView;
	Client testClient;
	String langTag;
	String propBund;
	Language l;

	Server server;
	
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
	
	public void setServer(Server server)
	{
		this.server = server;
	}
	
	
	public Stage getPrimaryStage()
	{
	
		return primaryStage;
	}


	public void setPrimaryStage(Stage primaryStage)
	{
	
		this.primaryStage = primaryStage;
	}


	public BorderPane getMainView()
	{
	
		return mainView;
	}


	public void setMainView(BorderPane mainView)
	{
	
		this.mainView = mainView;
	}



	
	public Client getTestClient()
	{
	
		return testClient;
	}


	public void setTestClient(Client testClient)
	{
	
		this.testClient = testClient;
	}


	
	@FXML
	private TextField UsernameTextField;
	
	@FXML 
	private TextField PasswordTextField;
	
	@FXML 
	private Button LoginSubmitButton;
	
	@FXML
	private Label error;
	@FXML
	private Text login;
	@FXML
	private Text user;
	@FXML
	private Text pass;
	
	
	public void setNewText()
	{
		
		login.setText(l.getNewWord("login.text"));
		user.setText(l.getNewWord("username.text"));
		pass.setText(l.getNewWord("password.text"));
		LoginSubmitButton.setText(l.getNewWord("submit.text"));

		
	}
	
	
	@FXML
	public void onButtonSubmit() throws Exception
	{
		
		
		String username = UsernameTextField.textProperty().get();
		String password = PasswordTextField.textProperty().get();
		
		System.out.println(username);
		System.out.println(password);
		// sends to login function
		
		
	
		try{
			
			testClient.login(username, password);
	        
	        
	    }catch(IllegalArgumentException e)
		{
	    	
			
			LoginSubmitButton.setOnAction(event -> {
					try
					{
						onButtonSubmit();
					} catch (Exception e1)
					{
						e1.printStackTrace();
					}
			
			});
			
			
	    }
		
		
		if (testClient.getCookie()!=null)
		{
			connectIfValid(username);
		}
		else
		{
			error.setOpacity(1);
			error.setTranslateX(150);
			error.setText(l.getNewWord("loginError.text"));
			//onButtonSubmit();
		}
		
		
		
		
		
		
		
		
		
	}
	
	private void connectIfValid(String username) throws Exception
	{
		System.out.print(testClient.getCookie());
		System.out.print("logged in");
		
		getConnected(testClient, username);
	}

	// switch scenes
	private void getConnected(Client testClient, String username) throws Exception
	{

		this.testClient = testClient;
		
		FXMLLoader loader = new FXMLLoader();
		Locale locale = Locale.forLanguageTag(langTag);
		
		ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

		loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
		loader.setResources(labels);
		BorderPane newMain = loader.load();
		
		HomePageViewController cont = loader.getController();
		cont.setLangTag(langTag);
		cont.setPropBund(propBund);
		cont.setLanguage(l);
		cont.setUser(username);

		String deptName = testClient.getServer().getCookieMap().get(testClient.getCookie()).getDepartment().getDeptName();
		System.out.println("deptName: "+deptName);
		cont.setDept(deptName);
		cont.setTestClient(testClient);
		
		cont.setUser(username);
		
		
		cont.setPrimaryStage(primaryStage);
		
		primaryStage.setUserData(cont);
		
		
		
		
	
	
		
		
		primaryStage.setWidth(800);
		primaryStage.getScene().setRoot(newMain);
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
