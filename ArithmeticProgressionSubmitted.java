import java.util.Scanner;

public class Solution
{
	private static int missingNumberFlag = -10000;
	
	// Minimum array.length is 3
	private int findMissingNumber(int[] array)
	{	
		// * firstStep is the distance between the first two values in the array, minus 'increment',
		// that's to say: firstStep = ]array[i], array[i+1][, for each i in [0, array.length-2]
		// * lastStep is the distance between the last two values in the array, minus 'increment';
		// * minimumStep is the minimum between firstStep and lastStep.
		//
		// We need both steps, because it is not said that the step value in the series is given by
		// the distance between the first two elements in the array, especially when the missing element
		// would be in position array[1] or in aray[array.length - 2]; in fact, if we have the progression
		// 1 _ 5 7 9 11, where the missing element is obviously 3, the algorithm would mistakenly think
		// the step in the progression is 4, instead of 1.
		// We find both steps values and take the minimum because if, by absurd, the bigger step would
		// be the true step in the progression, we wouldn't have found a smaller step, like we did instead,
		// so the smaller must be the true step.
		
		int missingNumber = missingNumberFlag;
		int increment = array[0] < array[1]? 1 : -1;
		
		int firstStep = Math.abs(array[1] - array[0] - increment); 
		int lastStep = Math.abs(array[array.length - 1] - array[array.length - 2] - increment);
		int minimumStep = Math.min(firstStep, lastStep);

		boolean ascending = array[0] < array[1] ? true : false;
		
		

		//If it's an ascending order
		if(ascending)
		{
			for(int i = 0; i < array.length - 1; i++)
			{
				if(array[i + 1] - array[i] != minimumStep + 1)
				{
					if(array[i] + minimumStep + 1 == array[i + 1] - minimumStep - 1)
					{
						missingNumber = array[i] + minimumStep + 1;
					}	
				}
			}	
		}
		else
		{
			for(int i = 0; i < array.length - 1; i++)
			{
				if(array[i] - array[i + 1] != minimumStep + 1)
				{
					if(array[i] - minimumStep - 1 == array[i + 1] + minimumStep + 1)
					{
						missingNumber = array[i] - minimumStep - 1;
					}
				}
			}
		}
		
		return missingNumber;
	}
	
    public static void main(String args[]) throws Exception
    {
    	Solution progression = new Solution();
    	
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        
        while(N < 3 || N > 2500)
        {
            System.err.print("*** Please insert a value for N between 3 and 2500. ***");
            N = scanner.nextInt();
        }
        
        int[] numbers = new int[N];
        int tempNumber;
        
        for(int i = 1; i <= N; i++)
        {
            tempNumber = scanner.nextInt();
            
         // Only values in [-10^6, +10^6] are accepted
            while(tempNumber < -Math.pow(10, 6) || tempNumber > Math.pow(10, 6))
            {
                tempNumber = scanner.nextInt();
            }
            
            numbers[i - 1] = tempNumber;
        }
        
        System.out.println(progression.findMissingNumber(numbers));
    }
}
