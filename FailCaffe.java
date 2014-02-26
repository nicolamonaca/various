/*  AUTHOR: Nicola Lamonaca
 *
 *  DATE: Feb. 26, 2014
 *
 *  DESCRIPTION: 'FailCaffè' is a game designed by Francesco "Ciccio" Granato.
 *  The goal of 'FailCaffè' is to decide who, among n people, will brew coffee.
 *  Each player has 3 dices: a tetrahedron (4 faces), an octahedron (8 faces) and a dodecahedron (12 faces) and 3 shots.
 *  During each shot, the 3 dices are rolled and the player can choose which of the 3 dices to keep, eventually none.
 *  When a dice is chosen, its value is added to the player's points and the dice cannot be kept again in the next roll.
 *  The final player's score is given by the sum of all the kept dices' values.
 *  In the final roll, all the remaining values are added to the player's points.
 *  Dices names are {ID12, ID8, ID4, NONE}. If names are written in lowercase, a exception will be thrown.
 *  
 *  SCREENSHOT: https://imageshack.com/i/nmbllfp
 *
 *  DOWNLOAD: https://db.tt/NQrNWliF
 */ 


import java.util.EnumSet;
import java.util.Random;
import java.util.Scanner;

public class FailCaffe
{	
	private int randInt(Moka part)
	{
		Random rand = new Random();
		int randomNumb = -1;
			
		switch(part)
		{
			case ID12: randomNumb = rand.nextInt(12) + 1;
					   break;
							 
			case ID8: randomNumb = rand.nextInt(8) + 1;
					  break;
							
			case ID4: randomNumb = rand.nextInt(4) + 1;
				
			default: break;
		}
		
		//DEBUG INFO
		//System.out.println("RANDOMNUMB: " + randomNumb);
		//System.out.println("ID: " + id);
		
		return randomNumb;
	}
	
	// Finds the smallest integer value within an integer array
	private static int findSmallest(int[] array)
	{
		int smallest = array[0];
		
		for(int i = 1; i < array.length; i++)
		{
			if(array[i] < smallest)
			{
				smallest = array[i];
			}
		}
		
		return smallest;
	}
	
	// Sums all the the elemnts in an integer array
	private int countPoints(int[] array)
	{
		int points = 0;
		
		for(int i = 0; i < array.length; i++)
		{
			points += array[i];
		}
		
		return points;
	}

	public static void main(String args[])
	{
		final int trials = 3;
		FailCaffe fc = new FailCaffe();
		Dice dice = new Dice();
		int value = -1;
		
		// Reads the number of players
		System.out.print("Please enter the number of players: ");
		Scanner scanner = new Scanner(System.in);
		int numPlayers = scanner.nextInt();
		
		System.out.println("");
		
		int[] points = new int[numPlayers];
		
		Player players[] = new Player[numPlayers];
		
		for(int i = 0; i < players.length; i++)
		{
			// Creates a new player
			players[i] = new Player();
			
			// Asks for his name
			System.out.print("Please enter player #" + (i+1) + "'s name: ");
			players[i].name = scanner.next();
		}
		
		System.out.println("");
		
		// For each player..
		for(int i = 0; i < players.length; i++)
		{
			System.out.println("\nIt's " + players[i].name + "'s turn:");
			
			final EnumSet<Moka> dicesYetToRoll = Moka.VALID_DICES.clone();
			
			// For 'trials' times..
			for(int j = 0; j < trials; j++)
			{
				System.out.println("Roll #" + (j + 1));
				
				// Roll each dice.
				for(Moka m : dicesYetToRoll)
				{
					value = fc.randInt(m);
					dice.values[m.ordinal()] = value;
					System.out.println("- " + m + ": " + value); // Print the dice name and value
				}
				
				try
				{
					Moka moka;
						
					if(dicesYetToRoll.size() >= 1)
					{	
						//DEBUG INFO
						//System.out.println("j = " + j);
						
						if(j == (trials - 1))
						{
							//DEBUG INFO
							//System.out.println("COUNTPOINTS(DICE.VALUES): " + fc.countPoints(dice.values));
							
							players[i].partialPoints += fc.countPoints(dice.values);
							System.out.println("Partial result: " + players[i].partialPoints + " points.\n");
							break;
						}
						
						System.out.print("Which dice do you want to keep? {ID12, ID8, ID4, NONE}: ");
						String next = scanner.next();
						moka = Moka.valueOf(next);
							
						// Check if a dice is to be kept
						if(!next.equalsIgnoreCase("NONE"))
						{
							dicesYetToRoll.remove(moka);
						}
							
						players[i].partialPoints += dice.values[moka.ordinal()];
						dice.values[moka.ordinal()] = 0;
						
						//DEBUG INFO
						//System.out.println("PLAYERS[i].KEPTVALUES[0] " + players[i].keptValues[0]);
						//System.out.println("DICESYETTOROLL.SIZE " + dicesYetToRoll.size());
					}
			 		
					//DEBUG INFO
					//System.out.println("DICESYETTOROLL: " + dicesYetToRoll);
					
					System.out.println("Partial result: " + players[i].partialPoints + " points.\n");
					
					//DEBUG INFO
					//System.out.println("DICE.VALUES: " + dice.values[moka.ordinal()]);
				}
				catch(IllegalArgumentException e)
				{
					System.err.println("Invalid Dice. Aborting.");
					System.exit(-1);
				}
			
				//DEBUG INFO
				//System.out.println("PLAYERS[i].NAME: " + players[i].name);
			}
			
			points[i] = players[i].partialPoints;
		}
		
		for(int i = 0; i < players.length; i++)
		{
			System.out.println(players[i].name + " totalized " + players[i].partialPoints + " points.");
		}
		
		int smallest = findSmallest(points);
		
		for(int i = 0; i < players.length; i++)
		{
			if(players[i].partialPoints == smallest)
			{
				System.out.println("\n" + players[i].name + " lost. Coffee, pleeease!");
				break;
			}
		}
	}
}


enum Moka
{
	ID12, ID8, ID4, NONE;
	
	public static final EnumSet<Moka> VALID_DICES = EnumSet.range(ID12, ID4);
	
	public static final EnumSet<Moka> DICES = EnumSet.allOf(Moka.class);
}


class Dice
{
	Moka moka;
	int[] values = new int[Moka.values().length];
}


class Player
{
	int partialPoints;
	String name;
}
