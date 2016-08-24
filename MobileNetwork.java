//NAME : ZAFAR, BLAL
//ID # 108462601
//--------------


import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Scanner;


public class MobileNetwork implements Serializable {

	static Integer count = new Integer(0);
	
	//-----------
	//MAIN METHOD
	//-----------
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		//main data container
		Hashtable network;
		String[] tracker; //to keep track of all the users inserted into the table
		char menu;
		
		//FIND THE NETWORK OBJECT
		java.io.File file = new java.io.File("network.obj");
		if(file.exists()) //if the file exist read the tree from it else create a new one
		{
			FileInputStream file2 = new FileInputStream("network.obj");
			ObjectInputStream inStream = new ObjectInputStream(file2);
			network = (Hashtable) inStream.readObject();
			
		}
		else
		{//if the file was not found
			network = new Hashtable();
		}
		
		//FIND THE TRACKER and COUNT VARIABLE 
		file = new java.io.File("tracker.obj");
		if(file.exists())
		{
			FileInputStream file3 = new FileInputStream("tracker.obj");
			ObjectInputStream inStream2 = new ObjectInputStream(file3);
			tracker = (String[]) inStream2.readObject();
			count = (Integer) inStream2.readObject();
		}
		else
		{
			tracker = new String[1];
			count = 0;
		}
		
		//Print the initial menu and get the user's choice
		printMenu();
		menu = getInput();
		
		while(true)
		{
			switch(menu)
			{
			case 'p':
				printRangeForUser(network, tracker);
				break;
			case 'a' :
				printNetworkRange(network,tracker);
				break;
			case 'i' : 
				Profile newProfile = createProfile();
				if(exists(network, newProfile))
				{
					System.out.println();
					System.out.println("Profile Already exists ...");
					System.out.println();
				}
				else
				{
					network.put(newProfile.getName().hashCode(), newProfile);
					//if the tracker array is not full add the name inside it, else increase it size
					if(count == tracker.length) //expand the size and then put it inside the array
					{
						tracker = expandArray(tracker); //increment the size
						tracker[count] = newProfile.getName();
						count++;
						System.out.println("Profile inserted ...");
						System.out.println();
					}
					else
					{
						tracker[count] = newProfile.getName();
						count++;
						System.out.println("Profile inserted ...");
						System.out.println();
					}
				}
				break;
				
			case 'd' :
				removeProifle(network, tracker);
				System.out.println();
				break;
			case 'u' :
				updateProfile(network);
				System.out.println();
				break;
			case 's' :
				searchNetworkRange(network);
				System.out.println();
				break;
			case 'q' : //save the file as an object
				System.out.println("Ending .....");
				FileOutputStream file3 = new FileOutputStream("network.obj"); //holds the profiles in hashtable
				FileOutputStream file4 = new FileOutputStream("tracker.obj"); //holds the names in an array
				ObjectOutputStream outStream1 = new ObjectOutputStream(file3);
				ObjectOutputStream outStream2 = new ObjectOutputStream(file4);
				outStream1.writeObject(network); //save the hastable
				outStream2.writeObject(tracker); //save the array
				outStream2.writeObject(count); //save the counter
				outStream1.close();
				outStream2.close();
				System.exit(0);
				break;
			}
			
			printMenu();
			menu = getInput();
		}
	}
	//----------- End of main method --------------------------
	
	//PRECONDITION : 
	//POSTCONDITION : a profile is created with valid values
	public static Profile createProfile()
	{
		//temporary data holder
		String temp; 
		char[] tempNameSplit;
		int tempX, tempY;
		
		//Variables that will create the final Profile object
		String tempName = "";
		Point tempLocation;
		int tempRange;
		
		Scanner keyboard = new Scanner(System.in);
		
		//Get the first name
		System.out.print("Enter the First Name : ");
		temp = keyboard.next();
		keyboard.nextLine();
		tempNameSplit = temp.toCharArray();
		tempNameSplit[0] = Character.toUpperCase(tempNameSplit[0]);
		for(int i = 1; i < tempNameSplit.length; i++)
			tempNameSplit[i] = Character.toLowerCase(tempNameSplit[i]);
		temp = new String(tempNameSplit);
		tempName+=temp;
		tempName+=" ";
		
		//Get the last name
		System.out.print("Enter the Last Name : ");
		temp = keyboard.next();
		keyboard.nextLine();
		tempNameSplit = temp.toCharArray();
		//convert the first letter to upperCase
		tempNameSplit[0] = Character.toUpperCase(tempNameSplit[0]);
		//convert all remaining letter to lowerCase
		for(int i = 1; i < tempNameSplit.length; i++)
			tempNameSplit[i] = Character.toLowerCase(tempNameSplit[i]);
		temp = new String(tempNameSplit);
		tempName+=temp;
		
		//Get the point location
		System.out.print("Enter a coordinate pair [x y] : ");
		tempX = keyboard.nextInt();
		tempY = keyboard.nextInt();
		
		tempLocation = new Point(tempX, tempY);
		
		//Enter the preferred network range
		System.out.print("Enter the preferred network range : ");
		tempRange = keyboard.nextInt();
		
		//create the new profile
		Profile newProfile = new Profile(tempName, tempLocation, tempRange);
		
		//handle the exception, without crashing the program
		if(isValidProfile(newProfile))
			return  newProfile;
		else
		{
			System.out.println("Profile not valid, network Range initialized to 1 ...");
			newProfile.setRange(1);
			return newProfile;
		}
		
	}
	//PRECONDITON : any profile object is given as input
	//POSTCONDITION : returns true or false based on the validity of the data
	public static boolean isValidProfile(Profile in)
	{
		if(!(in.getRange() >= 1))
			return false;
		else
			return true;
	}
	//PRECONDITION : takes Hashtable as an input
	//POSTCONDITION : if the profile is found, the profile is returned, otherwise null is returned
	public static Profile findProfile(Hashtable data)
	{
		String temp; 
		char[] tempNameSplit;
		String tempName = "";
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Enter the First Name : ");
		temp = keyboard.next();
		keyboard.nextLine();
		tempNameSplit = temp.toCharArray();
		tempNameSplit[0] = Character.toUpperCase(tempNameSplit[0]);
		for(int i = 1; i < tempNameSplit.length; i++)
			tempNameSplit[i] = Character.toLowerCase(tempNameSplit[i]);
		temp = new String(tempNameSplit);
		tempName+=temp;
		tempName+=" ";
		
		//Get the last name
		System.out.print("Enter the Last Name : ");
		temp = keyboard.next();
		keyboard.nextLine();
		tempNameSplit = temp.toCharArray();
		//convert the first letter to upperCase
		tempNameSplit[0] = Character.toUpperCase(tempNameSplit[0]);
		//convert all remaining letter to lowerCase
		for(int i = 1; i < tempNameSplit.length; i++)
			tempNameSplit[i] = Character.toLowerCase(tempNameSplit[i]);
		temp = new String(tempNameSplit);
		tempName+=temp;
		
		//find the profile in the given Hashtable
		Profile out = (Profile) data.get(tempName.hashCode());
		if(out == null)
		{
			System.out.println();
			System.out.println(tempName+" was not found");
		}
		return out;
		
	}
	//PRECONDITION : a hashTable is given as an input
	public static void removeProifle(Hashtable data, String[] names)
	{
		//Find the profile
		Profile target = findProfile(data);
		int i = 0;
		int targetIndex = - 1;
		
		if(target != null)
		{
			System.out.println();
			System.out.println(target.getName()+" removed from the profile ...");
			data.remove(target.getName().hashCode());
			
			//remove from the names array also
			while((i < count) && !(names[i].equals(target.getName())))
			{
				i++;
			}
			
			//move all elements over
			for(int j = i ; j < count -1 ; j++)
			{
				names[j] = names[j+1];
			}
			
			count--;
			
		}
	}
	//PRECONDITION : a hashTable is given as an input
	public static void updateProfile(Hashtable data)
	{
		//find the profile and store it in a temp variable
		Profile temp = findProfile(data);
		
		if(temp != null)
		{
			System.out.println();
			System.out.println("Updating information for "+temp.getName());
		
			//updating information
			Scanner keyboard = new Scanner(System.in);
			int newX, newY;
			Point newLocation;
			System.out.print("Enter a new coordinate pair [x y] : ");
			newX = keyboard.nextInt();
			newY = keyboard.nextInt();
		
			newLocation = new Point(newX, newY);
			temp.setLocation(newLocation);
		
			System.out.print("Enter the new preferred network range : ");
			int newRange;
			newRange = keyboard.nextInt();
			temp.setRange(newRange);
		}
	}
	//PRECONDITION : a hashTable is given as an input
	public static void searchNetworkRange(Hashtable data)
	{
		Profile userOne = null;
		Profile userTwo = null;
		
		//Find the two users
		System.out.println("Enter the USER # 1 : ");
		userOne = findProfile(data);
		if(userOne != null)
		{
			System.out.println();
			System.out.println("Enter the USER # 2 : ");
			userTwo = findProfile(data);
		}
		
		if((userOne != null) && (userTwo != null))
		{
			System.out.println();
			int distance = (int) userOne.getLocation().distance(userTwo.getLocation());
		
			if((distance <= userOne.getRange()) && (distance <= userTwo.getRange()))
				System.out.println(userOne.getName()+" and "+userTwo.getName()+" are in each other's social network .");
			else
				System.out.println(userOne.getName()+" and "+userTwo.getName()+" are not in each other's social network .");
		}
				
	}
	//prints the menu with available options
	public static void printMenu()
	{
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("A)\tPrint network range for all users");
		System.out.println("I)\tInsert a new user profile into the table.");
		System.out.println("D)\tDelete a user profile from the table.");
		System.out.println("U)\tUpdate the information for a given profile in the table.");
		System.out.println("S)\tSearch to see if two users are within each other's mobile social network.");
		System.out.println("P)\tPrint network range for a name (EXTRA CREDIT)");
		System.out.println("Q)\tQuit Program.");
		System.out.println("---------------------------------------------------------------------------------");
		
	}
	//POSTCONDITION : a character is returned indicating users choice
	public static char getInput()
	{
		Scanner keyboard = new Scanner(System.in);
		String temp;
		
		System.out.print("Enter your choice : ");
		temp = keyboard.next();
		
		return Character.toLowerCase(temp.charAt(0));
				
	}
	//find a profile in a hashTable, given a profile and a hashTable
	//PRECONDITION : a profile and and the hastable is given as input
	//POSITIONDITION : true or false is return based on if the profile is in the table or not
	public static boolean exists(Hashtable table, Profile x)
	{
		//Find the profile in the table
		Profile temp = (Profile) table.get(x.getName().hashCode());
		if(temp == null)
			return false;
		else
			return true;
		
	}
	//PRECONDITON : an array is given as an input
	//POSTCONDITION : an array with increased capacity of one is returned
	public static String[] expandArray(String[] in)
	{
		String[] out= new String[in.length+1];
		System.arraycopy(in, 0, out, 0, in.length);
		
		return out;
	}
	//check if profile a is in range with profile b
	//PRECONDITION : two profiles are given as input 
	//POSTIONCONDITION : true is returned if the two profiles are in range else false is returned
	public static boolean inRange(Profile a, Profile b)
	{
		int distance = (int) a.getLocation().distance(b.getLocation());
	
		if((distance <= a.getRange()) && (distance <= b.getRange()))
			return true;
		else
			return false;
	}
	//PRECONDITION : a hashtable with the data and an array with the all the names stored in the hashtable is given as an input
	public static void printNetworkRange(Hashtable table, String[] names)
	{
		Profile a;
		Profile b;
		
		System.out.println("Name\t\t Network Range");
		System.out.println("----\t\t -------------");
		for(int i = 0; i < count; i++)
		{
			System.out.print(names[i]+"\t ---> ");
			//pull out the first
			a = (Profile) table.get(names[i].hashCode());
			for(int j = 0; j < count; j++)
			{
				//pull the 2nd user from the table
				b = (Profile) table.get(names[j].hashCode());
				if(inRange(a, b) && a != b)
					System.out.print(names[j]+", ");
			}
			System.out.println();
		}
	}
	//print the network range for a user
	public static void printRangeForUser(Hashtable table, String[] names)
	{
		Profile temp = findProfile(table);
		Profile target;
		if(temp != null)
		{
			System.out.println();
			System.out.print(temp.getName()+" ---> ");
			for(int i = 0; i < count; i++)
			{
				target = (Profile) table.get(names[i].hashCode());
				if(inRange(temp, target) && temp != target)
					System.out.print(names[i]+", ");
			}
			System.out.println();
		}
		
		
	}
	
}
