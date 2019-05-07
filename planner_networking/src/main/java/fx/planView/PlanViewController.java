package fx.planView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.scene.layout.VBox;
import fx.checkSave.CheckSaveController;
import fx.comments.commentController;
import fx.contentCompare.contCompareController;
import fx.homePageView.HomePageViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loginView.LoginViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.FXTreeView;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.PlanNode;

public class PlanViewController
{
	Stage primaryStage;
	BorderPane mainView;
	Client testClient;
	PlanNode currentNode;
	
	Boolean builtTree = false;
	
	@FXML 
	private Button logoutButton;
	
	@FXML 
	private Button comment;
	
	@FXML
	private Label nodeLabel;
	
	@FXML
	private TextField budgetField;
	
	@FXML
	private Button budgetButton;
	
	@FXML 
	private Button homepageButton;
	
	@FXML
	private TreeView tree;

	@FXML
	private TextArea contents;
	
	@FXML
	Label user;
	
	@FXML
	Label dept;

	/**
	 * @return the user
	 */
	public Label getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String userName)
	{
		user.setText(userName);
	}

	/**
	 * @return the dept
	 */
	public Label getDept()
	{
		return dept;
	}

	/**
	 * @param dept the dept to set
	 */
	public void setDept(String deptName)
	{
		dept.setText(deptName);
	}

	public Client getTestClient()
	{
	
		return testClient;
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
	Button saveBtn;
	@FXML
	Button removeBtn;
	@FXML
	Button addBtn;
	private Object PlanNode;
	
	// lets never touch this again... it works
	public void logout() throws IOException {
		System.out.println("logout");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		//this.mainView = loader.load();
		//assertThat(mainView!=null);
		BorderPane newMain = loader.load();
		
		LoginViewController cont = loader.getController();
		cont.setMainView(newMain);
		cont.setTestClient(testClient);
		cont.setPrimaryStage(primaryStage);
		
		primaryStage.setUserData(cont);
		primaryStage.getScene().setRoot(newMain);
		
	}
	
	public void homepage() throws IOException {
		System.out.println("homepage");
		
		if(saveBtn.isDisabled())
		{
		
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
			this.mainView = loader.load();
		
			HomePageViewController cont = loader.getController();
			cont.setTestClient(testClient);
			cont.setPrimaryStage(primaryStage);
			this.testClient = cont.getTestClient();
			
			
			cont.setDept(dept.getText());
			cont.setUser(user.getText());
		
		
			primaryStage.getScene().setRoot(mainView);
		}
		
		else
		{
			
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fx/checkSave/checkSave.fxml"));
			//this.mainView = loader.load();
			BorderPane newMain = loader.load();
		
			CheckSaveController cont = loader.getController();
			cont.setTestClient(testClient);
			cont.setPrimaryStage(primaryStage);
			cont.setDept(dept.getText());
			cont.setUser(user.getText());
		
			primaryStage.setWidth(500);
			primaryStage.getScene().setRoot(newMain);
		}
			
			
			
			
			
		
		
		
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void buildTree() throws RemoteException {
		
		
		if(!builtTree) {
			
			removeBtn.setDisable(true);
			addBtn.setDisable(true);
			saveBtn.setDisable(true);

		
			contents.setText("");

			TreeItem<PlanNode> theRoot = makeTree();

			tree.setRoot(theRoot);

			
			tree.getSelectionModel().selectedItemProperty()
	        .addListener((observable, oldValue, newValue) -> handleTreeClick((TreeItem<PlanNode>) newValue));

			builtTree = true;

		}
	}
	
	private void handleTreeClick(TreeItem<PlanNode> newValue)
	{
		try {

		removeBtn.setDisable(false);

		addBtn.setDisable(false);

		this.currentNode = newValue.getValue();
	
		
		nodeLabel.setText(currentNode.getName());
		
		setContents(currentNode.getData());
		
		setBudget();
		
		}
		catch (Exception E){
			
		}
		
		
		
	}
	

	public TreeItem<PlanNode> makeTree() throws RemoteException
	{

		TreeItem<PlanNode> rootItem = getProducts(testClient.getCurrPlanFile().getPlan().getRoot());

		
		

		return rootItem;

	}

	// This method creates an ArrayList of TreeItems (Products)
	public TreeItem<PlanNode> getProducts(PlanNode root) throws RemoteException
	{

		// This will be the final ArrayList passed back to FXTreeView.java for build
		// (should only hold Mission for centre)
		//ArrayList<TreeItem<PlanNode>> FinalNodeList = new ArrayList<TreeItem<PlanNode>>();

		TreeItem<PlanNode> currentTreeItem = new TreeItem<PlanNode>(root);

		//FinalNodeList.add(currentTreeItem);

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

			parentTreeItem.getChildren().add(newChild);
			getKids(parentNode.getChildren().get(i), newChild);

		}

	}
	
	
	public void removeNode() throws RemoteException
	{
		
		
		this.testClient.setCurrNode(currentNode);
		
		this.testClient.removeBranch();
		
		
		builtTree = false;
		buildTree();
		
		saveBtn.setDisable(false);
		removeBtn.setDisable(true);
		addBtn.setDisable(true);

			
	}
	
	public void addNode() throws RemoteException
	{
		this.testClient.setCurrNode(currentNode);
		this.testClient.addBranch();
		
		
		builtTree = false;
		buildTree();
		saveBtn.setDisable(false);
		removeBtn.setDisable(true);
		addBtn.setDisable(true);
		
		
		
	}
	
	
	
	public void commentChange() throws IOException
	{

		this.testClient.setCurrNode(currentNode);
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/fx/comments/commentView.fxml"));
		BorderPane view = loader.load();
	
		commentController cont = loader.getController();
		cont.setCurrent(this);
		cont.setPrimaryStage(stage);
		Scene s = new Scene(view);
		stage.setScene(s);
		
		stage.showAndWait();
	
		
	}
	
	public void save() throws IllegalArgumentException, RemoteException 
	{
		this.testClient.setCurrNode(currentNode);
		this.testClient.pushPlan(testClient.getCurrPlanFile());
		System.out.println(testClient.getCurrPlanFile().getYear());
		removeBtn.setDisable(true);
		addBtn.setDisable(true);
		saveBtn.setDisable(true);
		this.testClient.getServer().save();
		
	}

	public void setContents(String stringContent) {
		
		contents.setText(stringContent);
	}
	
	public void setBudget()
	{
		Double check = this.currentNode.getBudget();
		
		if(check == null)
		{
			System.out.println("node has no budget");
			this.currentNode.setBudget(0.0);
			System.out.println("node has no budget and its fixed");
		}
		String budget = this.currentNode.getBudget().toString();
		System.out.println(budget);
		this.budgetField.setText(budget);
	}
	
	
	public void changeContent() {
		
		saveBtn.setDisable(false);
		String contentValue = contents.getText();
		currentNode.setData(contentValue);
		
	}
	
	
	public void changeBudget()
	{
		String oldBudget = this.currentNode.getBudget().toString();
		try
		{
			String newBudget = budgetField.getText();
			
			Double newBudgetDouble = Double.parseDouble(newBudget);
			
			checkBudget(newBudgetDouble, this.currentNode.getBudget());
			
			
			
			
			
		}
		catch (NumberFormatException e)
		{
			this.budgetField.setText(oldBudget);
			
			
		}
		
		
	}
	
	
	public void checkBudget(Double newBudget, Double oldBudget)
	{
		
		ArrayList<PlanNode> children = this.currentNode.getParent().getChildren();
		Double parentBudget = this.currentNode.getParent().getBudget();
		Double ammount = 0.0;
		for(int i = 0 ; i < children.size() ; i++)
		{
			 ammount = ammount + children.get(i).getBudget();
			
			
		}
		ammount = ammount - oldBudget;
		ammount = ammount + newBudget;
		System.out.println(ammount);
		if(ammount < parentBudget)
		{
			
			this.currentNode.setBudget(newBudget);
			this.budgetField.setText(newBudget.toString());
			
		}
		else
		{
			this.budgetField.setText(oldBudget.toString());
	
			
		}
			
			
			
		
		
	}
	
	
	

}