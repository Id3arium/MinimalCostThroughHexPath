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
		int weight; //used to convert the token to an integer
		int index; //index for the grid
		
		try {
			Scanner sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				if (!nextLine.isEmpty()) {
					String delims = "[ ]+";
					String[] tokens = nextLine.split(delims);
					index = Integer.valueOf(tokens[0]);
					weight = Integer.valueOf(tokens[1]);
					
					grid[index] = weight;  
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
	
  public static boolean isLegalIndex(int i){
  		if ( i < 1 || i > 233){
        	return false;
        }
        if((i - 1) % 15 == 0){
        	return false;	
          // dont make top left and bottom left connections
          return false;
        } 
        if((i - 8) % 15 == 0) {
          // dont make top right and bottom right connections
          return false;
        } 
        return true;
  }
	
  ppublic static makeGraph(int[] weights){
      for(int i = 1; i <= 233; i++) {
    	//make all 233 nodes with no edges
      int w = weights[i];
      Node node = new Node(i,w);
      System.out.Println("created node: "+ node);
    }
  	//for each node:
    //check all 6 neighbors and if their index is in the range 1 to 233, create the edge.
  		for(int i = 1; i <= 233; i++) {
      	//for each of the 6 directions check if legal
        	if ( isLegalIndex(i - 15)){//if up is legal
    			//make up edge
    			Edge edge = new Edge();
    		}
    		if (isLegalIndex(i - 15)){//if upRight is legal
    			
    		}
    		if ( isLegalIndex(i - 7)){
    			//if up is legal
    			//make up edge
    			Edge edge = new Edge();
    		}if ( isLegalIndex(i - 15)){
    			//if up is legal
    			//make up edge
    			Edge edge = new Edge();
    		}if ( isLegalIndex(i - 15)){
    			//if up is legal
    			//make up edge
    			Edge edge = new Edge();
    		}if ( isLegalIndex(i - 15)){
    			//if up is legal
    			//make up edge
    			Edge edge = new Edge();
    		}
    	}
          		//TD: if up is legal
              	
              	//make edge from i to direction in nodes. make a new edge in the nodes neighbours array.
              
              
              //TODO: if downRight is legal
              //TODO: if down is legal
              //TODO: if downLeft is legal
	      //TODO: if upLeft is legal
              
        } // End for
        
  } // End makeGraph()
} // End class Main



class Node{

        public final int index;
        public final int weight;
        public Edge[] neighbors;
        
        public Node cameFrom;
        
        /*
        public double g_scores;
        public final double h_scores; 	// local wieght
        public double f_scores = 0;
        */
        public Node(int i, int w){
	    index = i;
	    weight = w;
        }			
				/*
        public Node getUpNode(){
        	//this.index + 15;   
          return  
        }
        */
        public String toString(){
            return "index: " + index + "weight: " + weight;
        }

}

class Edge{
	public final double weight;
	public final Node target;

	public Edge(Node targetNode, double w){
		target = targetNode;
		weight = w;
}
}

*/
