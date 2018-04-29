import java.io.*;
import java.util.Random;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
		int[] weights = readGridFromFile("input.txt");
		Node[] graph = makeGraph(weights);

		printGrid(graph);
		AStarSearch(graph[226], graph[8]);

		printPath(graph[8]);
	} // End main()

	public static void writeRandomGrid(String fileName) {
		PrintWriter pw = null;
		Random rn = new Random();
		try {
			pw = new PrintWriter(new FileWriter(fileName));
			for (int i = 1; i <= 233; i++) {
				double randNum = rn.nextDouble();
				if (randNum > 0.75) {
					pw.printf("%d   %d\n", i, rn.nextInt(9) + 1);
				} else {
					pw.printf("%d  -1\n", i);
				} // End if
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		} // End try
	} // End writeRandomGrid()

	public static int[] readGridFromFile(String fileName) {
		int[] grid = new int[234];
		int weight; //used to convert the token to an integer
		int index; //index for the grid

		try {
			Scanner sc = new Scanner(new File(fileName));
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				if (!nextLine.isEmpty()) {
					String[] tokens = nextLine.split("[ ]+");
					index = Integer.valueOf(tokens[0]);
					weight = Integer.valueOf(tokens[1]);
					grid[index] = weight;
				} // End if
			} // End while
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // End try
		return grid;
	} // End readGridFromFile()

	public static void printGrid(int[] grid) {
		int i = 1;
		while (true) {
			//if ()
			for (int j = 0; j < 8; j++) {
				if (i >= 233) {
					return;
				}
				if (grid[i] == -1) {
					System.out.print("***   ");
				} else {
					System.out.format("%03d   ", grid[i]);
				}
				i++;
			}
			System.out.print("\n   ");
			for (int k = 0; k < 7; k++) {
				if (i >= 233) {
					return;
				}
				if (grid[i] == -1) {
					System.out.print("***   ");
				} else {
					System.out.format("%03d   ", grid[i]);
				}
				i++;
			}
			System.out.println();
		}
	}

	public static void printGrid(Node[] grid) {
		int i = 1;
		while (true) {
			//if ()
			for (int j = 0; j < 8; j++) {
				if (i >= 233) {
					return;
				}
				if (grid[i].weight == -1) {
					System.out.print("***   ");
				} else {
					System.out.format("%03d   ", grid[i].weight);
				}
				i++;
			}
			System.out.print("\n   ");
			for (int k = 0; k < 7; k++) {
				if (i >= 233) {
					return;
				}
				if (grid[i].weight == -1) {
					System.out.print("***   ");
				} else {
					System.out.format("%03d   ", grid[i].weight);
				}
				i++;
			}
			System.out.println();
		}
	}

	public static boolean isLegalIndex(int i) {
		if (i < 1 || i > 233) {
			return false;
		}
		if ((i - 1) % 15 == 0) {
			return false;
		}
		if ((i - 8) % 15 == 0) { // dont make top right and bottom right connections
			return false;
		}
		return true;
	}

	public static Node[] makeGraph(int[] weights) {
		Node[] graph = new Node[234];
		for (int i = 1; i <= 233; i++) { //make all 233 nodes with no edges
			int w = weights[i];
			Node node = new Node(i, w);
			graph[i] = node;
			System.out.println("created node: " + node);
		}
		//for each node:
		//check all 6 neighbors and if their index is in the range 1 to 233, create the edge.
		for (int i = 1; i <= 233; i++) {
			//for each of the 6 directions check if legal
			if (isLegalIndex(i - 15)) {        // UP neighbor
				Edge edge = new Edge(graph[i-15]);
				graph[i-15].neighbours.add(edge);
			}
			if (isLegalIndex(i - 7)) {        // UP neighbor
				Edge edge = new Edge(graph[i-7]);
				graph[i-15].neighbours.add(edge);
			}
			if (isLegalIndex(i + 8)) {        // UP neighbor
				Edge edge = new Edge(graph[i+8]);
				graph[i-15].neighbours.add(edge);
			}
			if (isLegalIndex(i + 15)) {        // UP neighbor
				Edge edge = new Edge(graph[i+15]);
				graph[i-15].neighbours.add(edge);
			}
			if (isLegalIndex(i + 7)) {        // UP neighbor
				Edge edge = new Edge(graph[i+7]);
				graph[i-15].neighbours.add(edge);
			}
			if (isLegalIndex(i + 8)) {        // UP neighbor
				Edge edge = new Edge(graph[i+8]);
				graph[i-15].neighbours.add(edge);
			}
		}
		//TD: if up is legal

		//make edge from i to direction in nodes. make a new edge in the nodes neighbours array.
		//TODO: if downRight is legal
		//TODO: if down is legal
		//TODO: if downLeft is legal
		//TODO: if upLeft is legal
		return graph;
	}

	public static void AStarSearch(Node start, Node end) {
		boolean[] visited = new boolean[234];
		boolean endReached = false;

		PriorityQueue<Node> queue = new PriorityQueue<Node>(233, new Comparator<Node>() {
			@Override //override compare method
			public int compare(Node n1, Node n2) {
				if (n1.totalWeight > n2.totalWeight) {
					return 1;
				} else if (n1.totalWeight < n2.totalWeight) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		
		queue.add(start);
		System.out.println("starting cost: "+start.weight);
		while ((!queue.isEmpty()) && (!endReached)) {
			Node curr = queue.poll();
			visited[curr.index] = true;
			if (curr.index == end.index){
				endReached = true;
			}
			for (Edge e : curr.neighbours){
				if (e.target.weight == -1){
					continue;
				}
				Node neighbour = e.target;
				
				int tempTotalCost = neighbour.distToEnd + e.weight;
			}
		}
	}

	public static void printPath(Node endNode) {
		int totalCost = 0;
		List<Node> path = new ArrayList<Node>();
		for (Node node = endNode; node != null; node = node.cameFrom) {
			path.add(node);
		}
		Collections.reverse(path);
		for (Node n : path) {
			totalCost += n.weight;
			System.out.println(n.index);
		}
		System.out.println("MINIMAL-COST PATH COSTS: " + totalCost);
	}
	
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
