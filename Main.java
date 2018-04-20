import java.io.*;
import java.util.Random;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
  		int[] weights = readGridFromFile("input.txt");
        makeGraph(weights);
	
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
		int[] grid = new int[234];
		int cost; //used to convert the token to an integer
		int index; //index for the grid
		
		try {
			Scanner sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				if (!nextLine.isEmpty()) {
					String delims = "[ ]+";
					String[] tokens = nextLine.split(delims);
					index = Integer.valueOf(tokens[0]);
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
  
  public static makeGraph(int[] weights){
  	//for each node:
    //check all 6 neighbours and if their index is in the range 1 to 233, create the edge.
  		for(int i = 1; i <= 233; i++) {
        	for(int j = 1 ;j <= 6;j++)
        	if ( i <= 1 || i >= 233){
            //dont make connction
            }
    		if((i - 1) % 15 == 0){
            	// dont make top left and bottom left connections
            } // End if
            	
            if((i - 8) % 15 == 0) {
            	// dont make top right and bottom right connections
            } // End if
        } // End for
  } // End makeGraph()
} // End class Main
