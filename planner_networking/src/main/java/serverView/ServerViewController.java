package serverView;

import java.io.FileNotFoundException;
import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;

import javafx.stage.WindowEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loginView.LoginViewController;
import software_masters.planner_networking.Client;
//import software_masters.planner_networking.EventHandler;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;
import software_masters.planner_networking.Language;
import software_masters.planner_networking.LangEnglish;
import software_masters.planner_networking.LangFrench;
import software_masters.planner_networking.LangSpanish;


//import software_masters.planner_networking.WindowEvent;

public class ServerViewController
{
	
	int count;
	public ServerViewController() 
	{
		//System.out.println("hello from ServerViewController");
		count = 0;
	}

	//main view needs to be local, change everywhere
	Stage primaryStage;
	BorderPane mainView;
	Client testClient;
	Language l = new LangEnglish();
	String langTag = "en-US";
	String propBund = "prop/en";
	
	public Client getTestClient()
	{
	
		return testClient;
	}
	
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

	public void setTestClient(Client testClient)
	{
	
		this.testClient = testClient;
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

	
	
	
	
	@FXML
	public RadioButton DefaultServerButton;
	
	@FXML
	public RadioButton OtherServerButton;
	
	@FXML 
	public TextField OtherServerTextField;
	
	@FXML
	public Button ServerSubmitButton;
	
	private static Server testServer;
	
	static Server actualServer;
	static Registry registry;
	
	@FXML
	public Label error;
	
	@FXML
	public ComboBox<String> selection;
	
	
	@FXML
	public Text servSelect;
	@FXML
	public Label selectLang;
	@FXML
	public Text localHost;
	@FXML
	public Text other;
	
	
	
	public void setNewText()
	{
		
		servSelect.setText(l.getNewWord("serverSelection.text"));
		localHost.setText(l.getNewWord("localHost.text"));
		other.setText(l.getNewWord("other.text"));
		selectLang.setText(l.getNewWord("selectLanguage.text"));
		ServerSubmitButton.setText(l.getNewWord("submit.text"));

		
	}
	
	
	
	private void connect(String hostName) throws Exception
	{
		
		if (hostName.equals("127.0.0.1"))
		{
			registry = LocateRegistry.createRegistry(1078);
	
			ServerImplementation server = ServerImplementation.load();
			
			actualServer = server;
			Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
			registry.rebind("PlannerServer", stub);
		}
		else
		{
			registry = LocateRegistry.getRegistry(hostName, 1078);
			ServerImplementation server = ServerImplementation.load();
			
			actualServer = server;
			Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
			registry.rebind("PlannerServer", stub);
		}
		
		this.testServer = (Server) registry.lookup("PlannerServer");
		this.testClient = new Client(testServer);

		getConnected(testClient);
	}
	
	
	public void connectToServer() throws Exception 
	{
		if(DefaultServerButton.isSelected()) 
		{
			try
			{
				connect("127.0.0.1");
				
			} catch (Exception e)
			{
				
				e.printStackTrace();
			}
			
		}
		else {
			try
			{
				String hostName = OtherServerTextField.getText();
				
				connect(hostName);	
				
				
			} catch(IllegalArgumentException e)
			{
				error.setOpacity(1);
				e.printStackTrace();
			}
		}
	
		

		
		
	}
	
	public void setLanguage()
	{
		String lang = selection.getValue();
		
		if(lang == "Spanish")
		{
			l = new LangSpanish();
			langTag = "sp-SP";
			String propBund = "prop/sp";
			setNewText();
		}
		else if(lang == "French")
		{
			l = new LangFrench();
			langTag = "fr-FR";
			String propBund = "prop/fr";
			setNewText();
		}
		else if(lang == "Default: English")
		{
			l = new LangEnglish();
			langTag = "en-US";
			String propBund = "prop/en";
			setNewText();
		}
		
		
	}
	

	
	public void makeMenu() throws RemoteException
	{	
		//
		
		//ObservableList<String> thisArray = new ObservableList<String>();

		// Use Java Collections to create the List.
		if (count==0)
		{
	        ArrayList<String> list = new ArrayList<String>();
	        
	        list.add("Default: English");
	        list.add("Spanish");
	        list.add("French");
	        
	 
	        // Now add observability by wrapping it with ObservableList.
	        ObservableList<String> thisArray = FXCollections.observableList(list);
			
	        //System.out.println("we are here");
	       // for (int i = 0; i<list.size(); i++)
	       // {
	        	//System.out.println(list.get(i));
	        // }
	        
	        if(!selection.getItems().contains(list.get(0)))
	        {
	        	//thisArray.add(list.get(0));
	        }
	        else if(!selection.getItems().contains(list.get(1)))
	        {
	        	thisArray.add(list.get(1));
	        }
	        else if(!selection.getItems().contains(list.get(2)))
	        {
	        	thisArray.add(list.get(2));
	        }
	        	
	        	
	        	//System.out.println("thisArray: "+thisArray.get(0));
	
	
	           // System.out.println("selection: "+selection.getPromptText());
			
				 selection.setItems(thisArray);
				 count ++;
		}
      
		 
		 
  
	
	}


	private void getConnected(Client testClient) throws Exception
	{

		this.testClient = testClient;
		
		
		FXMLLoader loader = new FXMLLoader();
		Locale locale = Locale.forLanguageTag(langTag);
		
		ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		loader.setResources(labels);

		BorderPane newMain = loader.load();
		
		LoginViewController cont = loader.getController();
		cont.setLangTag(langTag);
		cont.setPropBund(propBund);
		cont.setLanguage(l);
		cont.setMainView(newMain);
		cont.setTestClient(testClient);
		cont.setPrimaryStage(primaryStage);
		
		primaryStage.setUserData(cont);
		primaryStage.getScene().setRoot(newMain);
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		{
	          public void handle(WindowEvent we) 
	          {
	        	  	System.out.println("unbinding server");
	        	  	try
					{
						testServer.save();
					} catch (RemoteException e1)
					{
						
						e1.printStackTrace();
					}
		      		try
					{
						registry.unbind("PlannerServer");
					} catch (RemoteException | NotBoundException e)
					{
						
						e.printStackTrace();
					}
		      		// Unexport; this will also remove us from the RMI runtime
		      		try
					{
						UnicastRemoteObject.unexportObject(registry, true);
					} catch (NoSuchObjectException e)
					{
						
						e.printStackTrace();
					}
		      		System.out.println("Closing RMI Server");
		      		
		      		primaryStage.close();
		      		System.exit(0);
	      	
	          }
	      });  
		
		
		
	}
	

}
