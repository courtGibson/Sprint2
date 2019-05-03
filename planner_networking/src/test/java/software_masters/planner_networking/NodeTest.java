package software_masters.planner_networking;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.Test;

public class NodeTest
{

	@Test
	public void test() throws RemoteException
	{
		// make node +
		// add child +
		// remove child
		// get name, data +
		// set name, data +
		// get child when no child

		// make tree node and test data methods
		PlanNode tree = new PlanNode(null, "TreeNode", null, null);
		assertEquals("TreeNode", tree.getName());
		tree.setName("Tree");
		assertEquals("Tree", tree.getName());

		tree.setData("Desciption of mission goals.");
		assertEquals("Desciption of mission goals.", tree.getData());
		tree.setData("Description");
		assertEquals("Description", tree.getData());
		assertEquals(null, tree.getParent());
		assertEquals(true, tree.getChildren().isEmpty());
		
		
		PlanNode compare = new PlanNode(null, "TreeNode", null, null);

		compare.setName("compare");
	


		compare.setData("Not Description");

		
		ArrayList<String> different = tree.compare(compare);
		
		for(int i = 0 ; i < different.size(); i++)
		{
			System.out.println(different.get(i));
			
			
		}
		// make child nodes for tree, test addChild and getParent
		PlanNode n1 = new PlanNode(tree, "Vision", null, null);
		
		n1.addNote("could be better");
		
		assertEquals(n1.getComments().get(0), "could be better");
		
		tree.addChild(n1);
		assertEquals(tree, n1.getParent());
		assertEquals("Vision", n1.getName());
		assertEquals(true, tree.getChildren().contains(n1));

		PlanNode n2 = new PlanNode(tree, "node", null, null);
		tree.addChild(n2);
		assertEquals(true, tree.getChildren().contains(n2));
		assertEquals(tree, n2.getParent());

		// add child to n2
		PlanNode n3 = new PlanNode(n2, "node3", null, null);
		n2.addChild(n3);
		assertEquals(true, n2.getChildren().contains(n3));
		assertEquals(n2, n3.getParent());

		// add child to n3
		PlanNode n4 = new PlanNode(n3, "node3", null, null);
		n3.addChild(n4);

		// get grandparent
		assertEquals(n2, n4.getParent().getParent());

		// remove child from tree with no other children
		tree.removeChild(n1);
		assertEquals(false, tree.getChildren().contains(n1));

		// remove child that has children
		tree.removeChild(n2);
		assertEquals(false, tree.getChildren().contains(n2));

	}

}