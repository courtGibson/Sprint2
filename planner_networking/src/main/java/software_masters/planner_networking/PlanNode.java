package software_masters.planner_networking;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * @author Courtney and Jack
 * @author lee and wesley
 */

public class PlanNode implements Serializable
{
	private static final long serialVersionUID = 5908372020728915437L;
	private PlanNode parent;
	private String name;
	private String data;
	private ArrayList<String> comments = new ArrayList<String>();
	private ArrayList<PlanNode> children = new ArrayList<PlanNode>();

	// constructor is data is not known
	/**
	 * Takes a PlanNode parent, String name, String data, and list of children Sets
	 * values in node
	 * 
	 * @param parent parent of node
	 * @param name   name of node
	 * @param data   data for node
	 * @param child  list of children
	 */
	public PlanNode(PlanNode parent, String name, String data, ArrayList<PlanNode> child) throws RemoteException
	{
		this.name = name;
		this.parent = parent;
		this.data = data;

	}

	// empty constructor for XML
	public PlanNode() throws RemoteException
	{
		this(null, "blank", "empty", null);
	}

	// Getter and setters
	/**
	 * returns a String name of node
	 * 
	 * @return String name of node
	 */
	public String getName()
	{
		return name;
	}

	public ArrayList<String> getComments()
	{
		return comments;
	}
	
	/**
	 * Sets name of node
	 * 
	 * @param name name to set as name of node
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns node's data
	 * 
	 * @return String data of node
	 */
	public String getData()
	{
		return data;
	}

	/**
	 * Takes a String data and sets node's data
	 * 
	 * @param data data to set as data of node
	 */
	public void setData(String data)
	{
		this.data = data;
	}

	/**
	 * returns the parent node
	 * 
	 * @return PlanNode parent of node
	 */
	public PlanNode getParent()
	{
		return parent;
	}

	/**
	 * Takes a PlanNode parent and sets the nodes parent
	 * 
	 * @param parent parent to set as parent of node
	 */
	public void setParent(PlanNode parent)
	{
		this.parent = parent;
	}

	/**
	 * Returns a list of children nodes
	 * 
	 * @return ArrayList list of children
	 */
	public ArrayList<PlanNode> getChildren()
	{
		return children;
	}

	//

	// add a PlanNode child to another node
	/**
	 * Takes a node child and adds child to child list
	 * 
	 * @param child child to be added to this node
	 */
	public void addChild(PlanNode child)
	{
		this.children.add(child);
	}

	// remove child node from a node's children list
	/**
	 * @param child child to be removed from this node
	 */
	public void removeChild(PlanNode child)
	{
		this.children.remove(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlanNode other = (PlanNode) obj;
		if (children == null)
		{
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (data == null)
		{
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null)
		{
			if (other.parent != null)
				return false;
		}
		return true;
	}
	
	public String toString()
	{
		return this.name;
		
	}

	

}
