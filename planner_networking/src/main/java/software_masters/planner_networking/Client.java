package software_masters.planner_networking;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author lee kendall and wesley murray
 */

public class Client
{

	/**
	 * This class represents the client which users interact with. It includes
	 * methods for retrieving and editing business plans, keeping track of the
	 * user's cookie after login.
	 * 
	 */
	private String cookie;
	private PlanFile currPlanFile;
	private PlanNode currNode;
	private Server server;
	private ArrayList<PlanFile> deptPlans;
	private Plan currPlan;

	/**
	 * Sets the client's server.
	 * 
	 * @param server
	 */
	public Client(Server server)
	{
		this.server = server;
	}

	/**
	 * Logs in, returns cookie
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws IllegalArgumentException
	 */
	public void login(String username, String password) throws IllegalArgumentException, RemoteException
	{
		this.currPlanFile = null;
		this.currNode = null;
		this.cookie = server.logIn(username, password);
	}

	/**
	 * Returns planFile object from the user's department given a year. Throws
	 * exception if that planFile doesn't exist.
	 * 
	 * @param year
	 * @return
	 * @throws IllegalArgumentException
	 */
	public void getPlan(String year) throws IllegalArgumentException, RemoteException
	{
		this.currPlanFile = server.getPlan(year, this.cookie);
		this.currNode = this.currPlanFile.getPlan().getRoot();
	}

	/**
	 * 
	 * Returns a blank plan outline given a name. Throws exception if the plan
	 * outline doesn't exist.
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */
	public void getPlanOutline(String name) throws IllegalArgumentException, RemoteException
	{
		this.currPlanFile = server.getPlanOutline(name, this.cookie);
		this.currNode = this.currPlanFile.getPlan().getRoot();
	}

	/**
	 * Saves planFile to the user's department if that planFile is marked as
	 * editable. If not editable, an exception is thrown. An exception is also
	 * thrown if a newly created planFile is not assigned a year.
	 * 
	 * @param plan
	 * @throws IllegalArgumentException
	 */
	public void pushPlan(PlanFile plan) throws IllegalArgumentException, RemoteException
	{
		
		server.savePlan(plan, this.cookie);
	}

	/**
	 * Adds new user to loginMap, generates new cookie for user and adds to
	 * cookieMap. Throws exception if user isn't an admin or the department doesn't
	 * exist.
	 * 
	 * @param username
	 * @param password
	 * @param departmentName
	 * @param isAdmin
	 * @throws IllegalArgumentException
	 */
	public void addUser(String username, String password, String departmentName, boolean isAdmin)
			throws IllegalArgumentException, RemoteException
	{
		server.addUser(username, password, departmentName, isAdmin, this.cookie);
	}

	/**
	 * Sets whether or not a planFile is editable
	 * 
	 * @param departmentName
	 * @param year
	 * @param canEdit
	 * @throws IllegalArgumentException
	 */
	public void flagPlan(String departmentName, String year, boolean canEdit)
			throws IllegalArgumentException, RemoteException
	{
		server.flagPlan(departmentName, year, canEdit, this.cookie);

	}

	/**
	 * Adds a new department
	 * 
	 * @param departmentName
	 * @throws IllegalArgumentException
	 */
	public void addDepartment(String departmentName) throws IllegalArgumentException, RemoteException
	{
		server.addDepartment(departmentName, this.cookie);

	}

	/**
	 * Adds a new branch to the business plan tree if allowed
	 * 
	 * @throws IllegalArgumentException
	 * @throws RemoteException
	 */
	public void addBranch() throws IllegalArgumentException, RemoteException
	{
		this.currPlanFile.getPlan().addNode(this.currNode.getParent());
	}

	/**
	 * Removes a branch from the business plan tree if at least one duplicate exists
	 * 
	 * @throws IllegalArgumentException
	 */
	public void removeBranch() throws IllegalArgumentException
	{
		PlanNode temp = this.currNode.getParent();
		this.currPlanFile.getPlan().removeNode(this.currNode);
		this.currNode = temp.getChildren().get(0);
	}

	/**
	 * Sets the data held in the currently accessed node
	 * 
	 * @param data
	 */
	public void editData(String data)
	{
		this.currNode.setData(data);
	}

	/**
	 * @return the data associated with a node
	 */
	public String getData()
	{
		return this.currNode.getData();
	}

	/**
	 * @param year
	 */
	public void setYear(String year)
	{
		this.currPlanFile.setYear(year);
	}

	/**
	 * @return the cookie
	 */
	public String getCookie()
	{
		return cookie;
	}

	/**
	 * @param cookie the cookie to set
	 */
	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	/**
	 * @return the currPlanFile
	 */
	public PlanFile getCurrPlanFile()
	{
		return currPlanFile;
	}

	/**
	 * @param currPlanFile the currPlanFile to set
	 */
	public void setCurrPlanFile(PlanFile currPlanFile)
	{
		this.currPlanFile = currPlanFile;
	}

	/**
	 * @return the currNode
	 */
	public PlanNode getCurrNode()
	{
		return currNode;
	}

	/**
	 * @param currNode the currNode to set
	 */
	public void setCurrNode(PlanNode currNode)
	{
		this.currNode = currNode;
	}

	/**
	 * @return the server
	 */
	public Server getServer()
	{
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(Server server)
	{
		this.server = server;
	}
	
	public void setPlan(Plan p)
	{
		this.currPlan = p;
	}
	
	public Plan getPlan()
	{
		return this.currPlan;
	}

	public ArrayList<PlanFile> getPlans() throws RemoteException
	{
		
		ArrayList<PlanFile> plans = new ArrayList<PlanFile>();
		
		
		this.server.getCookieMap().get(this.cookie).getDepartment().getPlanFileMap()
						.forEachValue(Long.MAX_VALUE, (PlanFile x) -> plans.add(x));
		
		//System.out.println(plans);
		this.deptPlans = plans;
		
		/*for(PlanFile p:plans)
		{
			System.out.println("\n\nPrinting plans in get plans");
			System.out.println(p.getYear());
		}*/
		
		return plans;
		
	}
}
