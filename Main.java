import java.io.*;
import java.util.Random;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
  		readGridFromFile("input.txt");
	
    } // End main()
    
  	public static void writeRandomGrid(String fileName){
	    PrintWriter pw = null;
	    Random rn = new Random();
	    try {
			pw = new PrintWriter(new FileWriter(fileName));
			for (int i = 1; i <= 233; i++) {
				double randNum = rn.nextDouble();
		    	if(randNum > 0.75){
		    		pw.printf("%d   %d\n",i,rn.nextInt(9)+1);
		    	} else {
		    		pw.printf("%d  -1\n",i);
		    	} // End if
		    }
	    } catch(IOException e){
	    	e.printStackTrace();
	    } finally {
	    	pw.close();
		} // End try
  	} // End writeRandomGrid()
  	
  	public static int[] readGridFromFile(String fileName){
		int[] grid = new int[233];
		int cost; //used to convert the token to an integer
		int index; //index for the grid
		
		try {
			Scanner sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				if (!nextLine.isEmpty()) {
					String delims = "[ ]+";
					String[] tokens = nextLine.split(delims);
					index = Integer.valueOf(tokens[0]) - 1;
					cost = Integer.valueOf(tokens[1]);
					
					grid[index] = cost;  
				} // End if
			} // End while
			
			for(int i = 0; i < grid.length; i++)
				System.out.println(grid[i]);
				    
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // End try
		
		return grid;
	} // End readGridFromFile()
} // End class Main
