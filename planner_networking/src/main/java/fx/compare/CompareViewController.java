package fx.compare;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

import fx.checkSave.CheckSaveController;
import fx.homePageView.HomePageViewController;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loginView.LoginViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Language;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.PlanNode;

public class CompareViewController 
{

	public CompareViewController() 
	{
		
	}
	
	BorderPane mainView;
	public PlanFile plan;
	public Client testClient;
	String langTag;
	String propBund;
	Language l;
	Stage primaryStage;
	PlanFile originalPlan;
	PlanFile comparePlan;
	

	String dept;
	String user;
	

	String planAYear;
	String planBYear;
	
	@FXML
	public Text planA;
	
	@FXML
	public Text planB;
	
    @FXML
    private Button homePage;
	
	@FXML
	private Button logout;
	
	@FXML
	public Text comparePlans;
	
	public void setPlans(PlanFile pA, PlanFile pB)
	{
		originalPlan = pA;
		comparePlan = pB;
		System.out.println("planA: "+pA);

	}
	
	public void setTestClient(Client testClient)
	{
		this.testClient = testClient;
		
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
	
	public void setPrimaryStage(Stage primaryStage)
	{

		this.primaryStage = primaryStage;
	}

	public void setNewText()
	{
	
		homePage.setText(l.getNewWord("homePage.text"));
		logout.setText(l.getNewWord("logout.text"));
		comparePlans.setText(l.getNewWord("comparePlan.text"));

	
	}
	
	@FXML
	public void logout() throws IOException
	{
		System.out.println("logout");

		FXMLLoader loader = new FXMLLoader();
		Locale locale = Locale.forLanguageTag(langTag);
		
		ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		// this.mainView = loader.load();
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

	}

	@FXML
	public void homepage() throws IOException
	{
		System.out.println("homepage");


			FXMLLoader loader = new FXMLLoader();
			Locale locale = Locale.forLanguageTag(langTag);
			
			ResourceBundle labels = ResourceBundle.getBundle(propBund, locale);

			loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
			loader.setResources(labels);

			this.mainView = loader.load();

			HomePageViewController cont = loader.getController();
			cont.setLangTag(langTag);
			cont.setPropBund(propBund);
			cont.setLanguage(l);

			cont.setTestClient(testClient);
			cont.setPrimaryStage(primaryStage);
			this.testClient = cont.getTestClient();

			cont.setDept(dept);
			cont.setUser(user);

			primaryStage.getScene().setRoot(mainView);

	}
	
	PlanNode currentNode;
	Boolean builtTree = false;
	
	@FXML
	public TreeView<PlanNode> tree1;
	
	@FXML
	public TreeView<PlanNode> tree2;
	
	@FXML
	public Label nodeLabel;
	
	@FXML
	TextArea contentsArea;
	
	public void buildTree() throws RemoteException
	{
		//System.out.println("\n\nHello from build tree");
		if (!builtTree)
		{


			TreeItem<PlanNode> theRoot = makeTree(originalPlan);

			tree1.setRoot(theRoot);

			tree1.getSelectionModel().selectedItemProperty()
					.addListener((observable, oldValue, newValue) -> handleTreeClick((TreeItem<PlanNode>) newValue));
			
			TreeItem<PlanNode> theRoot2 = makeTree(comparePlan);

			tree2.setRoot(theRoot2);

			tree2.getSelectionModel().selectedItemProperty()
					.addListener((observable, oldValue, newValue) -> handleTreeClick((TreeItem<PlanNode>) newValue));

			builtTree = true;
			
			compareTrees(theRoot, theRoot2);

		}
	}
	
	private void compareTrees(TreeItem<PlanNode> root1, TreeItem<PlanNode> root2)
	{
		int lenCh1 = root1.getChildren().size();
		int lenCh2 = root2.getChildren().size();
		
		if (lenCh1 < lenCh2)
		{
			System.out.println("lenCh1 < lenCh2");
			for (int i = 0; i<lenCh1; i++)
			{
				 String d1 = root1.getChildren().get(i).getValue().getData();
				 String d2 = root2.getChildren().get(i).getValue().getData();
				 
				//Node n1 = root1.getChildren().get(i).g;
				 
				 if(d1 != d2)
				 {
					 //System.out.println("root1: "+root1.getChildren().get(i));
					 //System.out.println("root1 graphic: "+root1.getChildren().get(i).getGraphic());
					 root1.getChildren().get(i).getGraphic().setStyle("-fx-background-color: yellow");
					 root2.getChildren().get(i).getGraphic().setStyle("-fx-background-color: yellow");
					 System.out.println("yellow color set");
				 }
				 
				 compareTrees(root1.getChildren().get(i), root2.getChildren().get(i));
				 
			}
			
			for (int i = lenCh1; i < lenCh2; i++)
			{
				root2.getChildren().get(i).getGraphic().setStyle("-fx-background-color: orange");
				System.out.println("orange color set");
			}
		}
		else if(lenCh1 > lenCh2)
		{
			System.out.println("lenCh1 > lenCh2");
			for (int i = 0; i<lenCh2; i++)
			{
				 String d1 = root1.getChildren().get(i).getValue().getData();
				 String d2 = root2.getChildren().get(i).getValue().getData();
				 
				 if(d1 != d2)
				 {
					 System.out.println("root1: "+root1.getChildren().get(i));
					 System.out.println("root1 graphic: "+root1.getChildren().get(i).getGraphic());
					 root1.getChildren().get(i).getGraphic().setStyle("-fx-background-color: yellow");
					 root2.getChildren().get(i).getGraphic().setStyle("-fx-background-color: yellow");
					 System.out.println("yelow color set");
				 }
				 
				 compareTrees(root1.getChildren().get(i), root2.getChildren().get(i));
				 
			}
			
			for (int i = lenCh2; i < lenCh1; i++)
			{
				root1.getChildren().get(i).getGraphic().setStyle("-fx-background-color: orange");
				 System.out.println("orange color set");
			}
		}
		else
		{
			
			System.out.println("lenCh1 == lenCh2");
			for (int i = 0; i<lenCh1; i++)
			{
				 String d1 = root1.getChildren().get(i).getValue().getData();
				 String d2 = root2.getChildren().get(i).getValue().getData();
				 
				 if(d1 != d2)
				 {
					 root1.getChildren().get(i).getGraphic().setStyle("color: orange");
					 root2.getChildren().get(i).getGraphic().setStyle("-color: yellow");
					 System.out.println("yellow color set");
				 }
				 
				 compareTrees(root1.getChildren().get(i), root2.getChildren().get(i));
				 
			}
		}
		

		
	}
	
	private void handleTreeClick(TreeItem<PlanNode> newValue)
	{
		try
		{



			this.currentNode = newValue.getValue();

			nodeLabel.setText(currentNode.getName());

			//setContents(currentNode.getData());
			System.out.println("\n\ncurrentNode data: "+currentNode.getData());
			

		} catch (Exception E)
		{

		}

	}
	
	public void setContents(String stringContent)
	{

		contentsArea.setText(stringContent);

	}

	public TreeItem<PlanNode> makeTree(PlanFile p) throws RemoteException
	{

		TreeItem<PlanNode> rootItem = getProducts(p.getPlan().getRoot());

		return rootItem;

	}

	// This method creates an ArrayList of TreeItems (Products)
	public TreeItem<PlanNode> getProducts(PlanNode root) throws RemoteException
	{

		// This will be the final ArrayList passed back to FXTreeView.java for build
		// (should only hold Mission for centre)
		// ArrayList<TreeItem<PlanNode>> FinalNodeList = new
		// ArrayList<TreeItem<PlanNode>>();

		TreeItem<PlanNode> currentTreeItem = new TreeItem<PlanNode>(root);

		// FinalNodeList.add(currentTreeItem);

		getKids(root, currentTreeItem);

		return currentTreeItem;
	}

	private void getKids(PlanNode parentNode, TreeItem<PlanNode> parentTreeItem)
	{

		if (parentNode.getChildren().isEmpty())
		{
			return;
		}

		for (int i = 0; i < parentNode.getChildren().size(); i++)
		{
			TreeItem<PlanNode> newChild = new TreeItem<PlanNode>(parentNode.getChildren().get(i));
			
			
			
			System.out.println("new graphic: "+newChild.getGraphic());
			parentTreeItem.getChildren().add(newChild);
			getKids(parentNode.getChildren().get(i), newChild);

		}

	}
	
	/**
	 * @param dept the dept to set
	 */
	public void setDept(String deptName)
	{
		dept = deptName;
	}
	
	/**
	 * @param user the user to set
	 */
	public void setUser(String userName)
	{
		user = userName;
	}


}
