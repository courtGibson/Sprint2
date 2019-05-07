/**
 * 
 */
package software_masters.planner_networking;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lee.kendall
 * @author wesley murray
 */

public class Department implements Serializable,  budget
{
	
	String deptName;
	
	
	Double budget;
	
	
	public void setDeptName(String name)
	{
		this.deptName = name;
	}
	
	public String getDeptName()
	{
		return this.deptName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3346352576860944178L;
	/**
	 * Object which stores the planFiles of a particular department based on year
	 */
	private ConcurrentHashMap<String, PlanFile> planFileMap;

	public Department()  throws RemoteException
	{
		planFileMap = new ConcurrentHashMap<String, PlanFile>();
	}

	/**
	 * Retrieves planFile from department based on year
	 * 
	 * @param year
	 * @return plan corresponding to the passed year
	 * @throws IllegalArgumentException
	 */
	public PlanFile getPlan(String year) throws IllegalArgumentException, RemoteException
	{
		if (planFileMap.containsKey(year))
		{
			return planFileMap.get(year);
		}
		throw new IllegalArgumentException("A plan with this year doesn't exist in this department");
	}

	/**
	 * Adds new planFile to department hash with a corresponding year. Overwrites
	 * old planFile if a planFile with the passed year already exists.
	 * 
	 * @param year of planFile to be added
	 * @param plan
	 */
	public void addPlan(String year, PlanFile plan)  throws RemoteException
	{
		planFileMap.put(year, plan);
	}

	/**
	 * Removes planFile from department hash given a year
	 * @param year of planFile to be removed
	 */
	public void removePlan(String year)  throws RemoteException
	{
		planFileMap.remove(year);
	}

	/**
	 * Checks if the plan exists within this department
	 * @param year
	 * @return
	 */
	public boolean containsPlan(String year)  throws RemoteException
	{
		return this.planFileMap.containsKey(year);
	}

	/**
	 * @return the planFileMap
	 */
	public ConcurrentHashMap<String, PlanFile> getPlanFileMap()  throws RemoteException
	{
		return planFileMap;
	}

	/**
	 * @param planFileMap the planFileMap to set
	 */
	public void setPlanFileMap(ConcurrentHashMap<String, PlanFile> planFileMap)  throws RemoteException
	{
		this.planFileMap = planFileMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((planFileMap == null) ? 0 : planFileMap.hashCode());
		return result;
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
		Department other = (Department) obj;
		if (planFileMap == null)
		{
			if (other.planFileMap != null)
				return false;
		} else if (!Department.<String, PlanFile>hashesEqual(planFileMap, other.planFileMap))
			return false;
		return true;
	}

	private static <K, V> boolean hashesEqual(ConcurrentHashMap<K, V> map1, ConcurrentHashMap<K, V> map2)
	{
		for (Enumeration<K> keyList = map1.keys(); keyList.hasMoreElements();)
		{
			K key = keyList.nextElement();
			if (!map1.containsKey(key))
				return false;
			if (!map2.containsKey(key))
				return false;
			if (!map1.get(key).equals(map2.get(key)))
				return false;
		}
		return true;
	}

	@Override
	public Double getBudget() 
	{
		
		return this.budget;
		
	}

	@Override
	public void setBudget(Double budget)
	{
		
		
		this.budget = budget;
		
	}

}
