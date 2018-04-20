import java.io.*;

class PrintHexGrid {
  public static void printGrid(int[] grid) {
    int i = 1;
    while (true){
        for (int j = 0;j < 8;j++){
        	if (i >= 233){
            	return;
        	}
            System.out.format("%03d   ",grid[i]); 
            i++;
        }
        System.out.print("\n   ");          
        for (int k = 0; k < 7; k++){
        	if (i >= 233){
        		return;
        	}
            System.out.format("%03d   ",grid[i]); 
            i++;
        }
        
        System.out.println(); 
    }
  }
}
