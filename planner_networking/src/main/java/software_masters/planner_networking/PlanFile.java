package software_masters.planner_networking;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * @author lee.kendall
 * @author wesley murray
 */
public class PlanFile implements Serializable, budget // extends UnicastRemoteObject
{

	private static final long serialVersionUID = 8679415216780269027L;
	private String year;
	private boolean canEdit;
	private Plan plan;
	private Double budget;
	/**
	 * @param year
	 * @param canEdit
	 * @param plan
	 */
	public PlanFile(String year, boolean canEdit, Plan plan) throws RemoteException
	{
		this.year = year;
		this.canEdit = canEdit;
		this.plan = plan;
	}

	/**
	 * Default constructor for serialization
	 */
	public PlanFile() throws RemoteException
	{
		this.year = null;
		this.canEdit = false;
		this.plan = null;
	}

	/**
	 * @return the year
	 */
	public String getYear()
	{
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year)
	{
		this.year = year;
	}

	/**
	 * @return the canEdit
	 */
	public boolean isCanEdit()
	{
		return canEdit;
	}

	/**
	 * @param canEdit the canEdit to set
	 */
	public void setCanEdit(boolean canEdit)
	{
		this.canEdit = canEdit;
	}

	/**
	 * @return the plan
	 */
	public Plan getPlan()
	{
		return plan;
	}

	/**
	 * @param plan the plan to set
	 */
	public void setPlan(Plan plan)
	{
		this.plan = plan;
		
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
		PlanFile other = (PlanFile) obj;
		if (canEdit != other.canEdit)
			return false;
		if (plan == null)
		{
			if (other.plan != null)
				return false;
		} else if (!plan.equals(other.plan))
			return false;
		if (year == null)
		{
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public Double getBudget()
	{
		return budget;
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void setBudget(Double money)
	{
		this.budget = money;
		this.getPlan().getRoot().setBudget(money);
		
	}

}
