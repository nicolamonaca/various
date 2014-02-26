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
		//System.out.println("randomNumb: " + randomNumb);
		//System.out.println("id: " + id);
		
		return randomNumb;
	}
	
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

	public static void main(String args[])
	{
		final int trials = 3;
		
		System.out.print("Please enter the number of players: ");
		Scanner scanner = new Scanner(System.in);
		int numPlayers = scanner.nextInt();
		System.out.println("");
		
		Player players[] = new Player[numPlayers];
		int[] points = new int[numPlayers];
		
		for(int i = 0; i < players.length; i++)
		{
			// Creates a new player
			players[i] = new Player();
			
			// Asks for his name
			System.out.print("Please enter player #" + (i+1) + "'s name: ");
			players[i].name = scanner.next();
		}
		
		System.out.println("");
		
		FailCaffe fc = new FailCaffe();
		
		// For each player..
		for(int i = 0; i < players.length; i++)
		{
			// For three times..
			for(int j = 0; j < trials; j++)
			{
				// Roll each dice.
				for(Moka dice : Moka.values())
				{
					players[i].points += fc.randInt(dice);
				}
			}
			
			points[i] = players[i].points;
			
			System.out.println(players[i].name + " totalized " + players[i].points + " points.");
		}
		
		int largest = findSmallest(points);
		
		for(int i = 0; i < players.length; i++)
		{
			if(players[i].points == largest)
			{
				System.out.println("\n" + players[i].name + " lost. Coffee, pleeease!");
				break;
			}
		}
	}
}

enum Moka
{
	ID12, ID8, ID4
}

class Player
{
	int points;
	String name;
}
