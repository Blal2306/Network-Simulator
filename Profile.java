import java.awt.Point;
import java.io.Serializable;
import java.util.Hashtable;

public class Profile implements Serializable{
	
	private String fullName;
	private Point location;
	private int networkRange;
	
	//PRECONDITION : full name, Point object, networkRange is given as input
	//POSTCONDITION : Profile object is constructed
	public Profile(String fullName, Point location, int networkRange)
	{
		this.fullName = fullName;
		
		this.location = location;
		this.networkRange = networkRange;
	}
	
	//No argument constructor
	public Profile()
	{
		fullName = "";
		location = null;
		networkRange = 1;
	}
	//ACCESSOR AND MUTATOR methods
	public void setName(String fullName)
	{
		this.fullName = fullName;
	}
	public String getName()
	{
		return fullName;
	}
	public void setLocation(Point newPoint)
	{
		location = newPoint;
	}
	public Point getLocation()
	{
		return location;
	}
	public void setRange(int r)
	{
		networkRange = r;
	}
	public int getRange()
	{
		return networkRange;
	}
	//----------------
	public void printProfile()
	{
		System.out.println("Name : "+fullName);
		System.out.println("Location : "+(int)location.getX()+", "+(int)location.getY());
		System.out.println("Network Range : "+networkRange);
		System.out.println();
	}
}
