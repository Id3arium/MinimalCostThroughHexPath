import java.io.*;
import java.util.Random;

class Main {
    public static void main(String[] args) {
  		int [] grid = new int[233];
  		writeRandomGridToFile("input.txt");
  		//readGridFromFile("input.txt");
  		
  		HexGrid hexGrid = new HexGrid();
  		hexGrid.printGrid(grid);
	
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
  	
  	public static void readGridFromFile(String fileName){
		int [] grid = new int [233];
		int val; //used to convert the token to an integer
		
		try {
			Scanner sc = new Scanner(new File(filename));
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				if (!nextLine.isEmpty()) {
					//delimiters include whitespace, carriage return, and newline
					String delims = "\\s*\\r?\\n\\s*";
					String[] tokens = nextLine.split(delims);
					val = Integer.valueOf(tokens[1]);
					
					//tokens[0[] is the index
					grid[tokens[0]] = val;  
				}
			} // End while
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // End try
	} // End readGridFromFile()
} // End class Main
