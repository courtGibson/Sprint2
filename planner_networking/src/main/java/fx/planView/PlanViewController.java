package fx.planView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import fx.checkSave.CheckSaveController;
import fx.homePageView.HomePageViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
	ArrayList<Label> ls = new ArrayList<Label>();
	ArrayList<String> com;
	String selectedCom;

	Boolean builtTree = false;

	@FXML
	private Button logoutButton;

	@FXML
	private Label nodeLabel;

	@FXML
	private Button homepageButton;

	@FXML
	private TreeView tree;

	@FXML
	TextArea contentsArea;

	@FXML
	TextArea commentArea;

	@FXML
	Button post;

	@FXML
	Button delete;

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

	public void deleteComment()
	{
		int index = com.indexOf(selectedCom);
		if (index >= 0)
		{
			com.remove(index);
		}
		setComments();

	}

	public void postComment()
	{
		String comment = commentArea.getText();

		if (comment != null)
		{
			String newComment = (user.getText()) + ": " + comment;
			currentNode.getComments().add(newComment);

			setComments();
		}

	}

	private void setComments()
	{
		ls.clear();
		commentArea.clear();
		com = currentNode.getComments();
		
		System.out.println("currentNode: "+currentNode);
		
		if (com.size() > 0)
		{
			
			
			for (int i = 0; i < com.size(); i++)
			{
				// String currString = com;
				Label curr = new Label(com.get(i));
				curr.setId("comment"+i);
				curr.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
				{
					public void handle(MouseEvent m)
					{
						curr.setStyle("-fx-background-color: yellow");
						selectedCom = curr.getText();
					};
				});

				ls.add(curr);

				VBox v = new VBox();

				for (Label l : ls)
				{
					v.getChildren().add(l);
				}
				scroll.setContent(v);
				scroll.setDisable(false);

			}
		} 
		else
		{
			
			VBox v = new VBox();
			scroll.setContent(v);
		}

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
	@FXML
	ScrollPane scroll;

	private Object PlanNode;

	// lets never touch this again... it works
	public void logout() throws IOException
	{
		System.out.println("logout");

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		// this.mainView = loader.load();
		// assertThat(mainView!=null);
		BorderPane newMain = loader.load();

		LoginViewController cont = loader.getController();
		cont.setMainView(newMain);
		cont.setTestClient(testClient);
		cont.setPrimaryStage(primaryStage);

		primaryStage.setUserData(cont);
		primaryStage.getScene().setRoot(newMain);

	}

	public void homepage() throws IOException
	{
		System.out.println("homepage");

		if (saveBtn.isDisabled())
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
			// this.mainView = loader.load();
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
	public void buildTree() throws RemoteException
	{

		if (!builtTree)
		{

			removeBtn.setDisable(true);
			addBtn.setDisable(true);
			saveBtn.setDisable(true);

			commentArea.setDisable(true);
			scroll.setDisable(true);

			contentsArea.setText("");

			TreeItem<PlanNode> theRoot = makeTree();

			tree.setRoot(theRoot);

			tree.getSelectionModel().selectedItemProperty()
					.addListener((observable, oldValue, newValue) -> handleTreeClick((TreeItem<PlanNode>) newValue));

			builtTree = true;

		}
	}

	private void handleTreeClick(TreeItem<PlanNode> newValue)
	{
		try
		{

			removeBtn.setDisable(false);

			addBtn.setDisable(false);

			this.currentNode = newValue.getValue();

			nodeLabel.setText(currentNode.getName());

			setContents(currentNode.getData());
			System.out.println("\n\ncurrentNode data: "+currentNode.getData());
			
			commentArea.setDisable(false);
			setComments();

		} catch (Exception E)
		{

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

	public void save() throws IllegalArgumentException, RemoteException
	{
		this.testClient.setCurrNode(currentNode);
		this.testClient.pushPlan(testClient.getCurrPlanFile());
		removeBtn.setDisable(true);
		addBtn.setDisable(true);
		saveBtn.setDisable(true);

	}

	public void setContents(String stringContent)
	{

		contentsArea.setText(stringContent);

	}

	public void changeContent()
	{

		saveBtn.setDisable(false);
		String contentValue = contentsArea.getText();
		System.out.println("\n\ntext is: "+contentValue);
		currentNode.setData(contentValue);
		System.out.println("\n\ntext in node: "+currentNode.getData());

	}

}