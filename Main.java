import java.io.*;
import java.util.Random;

class Main {
    public static void main(String[] args) {
  		int [] grid = new int[233];
  		writeRandomGridToFile("input.txt");
  		//readGridFromFile("input.txt");
  		
  		HexGrid hexGrid = new HexGrid();
  		hexGrid.printGrid(grid);
	
    }
    
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
		    	}
		    }
	    } catch(IOException e){
	    	e.printStackTrace();
	    } finally {
	    	pw.close();
		}
  	}
  	
  	public static void readGridFromFile(String fileName){
		int [] grid = new int [233];
		try {
			Scanner sc = new Scanner(new File(filename));
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				if (!nextLine.isEmpty()) {
					//tokens[0] is the index. Subtract 1 so it is 0 based.
				    grid[tokens[0]-1] = tokens[1];  
				    System.out.println(tall[i]);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
}