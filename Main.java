import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Main {
    public static void main(String[] args) {
		int[] weights = readGridFromFile("input.txt");
		Node[] graph = makeGraph(weights);
				
		printGrid(graph);
		AStarSearch(graph[226], graph[204]); //end should be 8

		printPath(graph[204]);
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
			for (int j = 0; j < 8; j++) { //print outer row that has 8 hexagons
				if (i >= 233) {
					return;
				}
				if (grid[i] == -1) {
					System.out.print("***   "); //print nothing for the -1's
				} else {
					System.out.format("%03d   ", grid[i]); //print the weight
				}
				i++;
			}
			System.out.print("\n   ");
			for (int k = 0; k < 7; k++) { //print inner row that has 7 hexagons between 2 outer rows
				if (i >= 233) {
					return;
				}
				if (grid[i] == -1) {
					System.out.print("***   "); //print nothing for the -1's
				} else {
					System.out.format("%03d   ", grid[i]); //print the weight
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
		if (i < 1 || i > 233) {	//dont make top or bottom connections
			return false;
		}
		if ((i - 1) % 15 == 0) { //dont make top left and bottom left connections
			return false;
		}
		if ((i - 8) % 15 == 0) { //dont make top right and bottom right connections
			return false;
		}
		return true;
	}

	public static Node[] makeGraph(int[] weights) {
		Node[] graph = new Node[234];
		for (int i = 0; i <= 233; i++) { //make all 233 nodes with no edges
			int w = weights[i];
			Node node = new Node(i, w);
			graph[i] = node;
			//System.out.println("created node " + node);
		}
		for (int i = 1; i <= 233; i++) {
			//for every node check if each of the 6 directions is legal and make 
			//an edge to the node at that direction if it is.
			if (graph[i].weight == -1) { //if this node has weight -1 dont make any edges
				continue;
			}
			//check every direction and if it is a legal index and doesnt have a weight of -1
			if (isLegalIndex(i - 15) && graph[i - 15].weight != -1) {        // Up neighbor
				Edge edge = new Edge(graph[i - 15]);
				graph[i].neighbours.add(edge); // add edge from node at index i to node at index i-15
			}
			if (isLegalIndex(i - 7) && graph[i - 7].weight != -1) {        // UpRight neighbor
				Edge edge = new Edge(graph[i - 7]);
				graph[i].neighbours.add(edge); // add edge from node at index i to node at index i-7
			}
			if (isLegalIndex(i + 8) && graph[i + 8].weight != -1) {        // DownRight neighbor
				Edge edge = new Edge(graph[i + 8]);
				graph[i].neighbours.add(edge); // add edge from node at index i to node at index i+8
			}
			if (isLegalIndex(i + 15) && graph[i + 15].weight != -1) {        // Down neighbor
				Edge edge = new Edge(graph[i + 15]);
				graph[i].neighbours.add(edge); //add edge from node at index i to node at index i+15
			}
			if (isLegalIndex(i + 7) && graph[i + 7].weight != -1) {        // DownLeft neighbor
				Edge edge = new Edge(graph[i + 7]);
				graph[i].neighbours.add(edge); // add edge from node at index i to node at index i+7
			}
			if (isLegalIndex(i - 8) && graph[i - 8].weight != -1) {        // UpLeft neighbor
				Edge edge = new Edge(graph[i - 8]);
				graph[i].neighbours.add(edge); // add edge from node at index i to node at index i-8
			}
		}
		return graph;
	}

	public static void AStarSearch(Node start, Node end) {
		boolean[] visited = new boolean[234];
		boolean endReached = false;

		PriorityQueue<Node> queue = new PriorityQueue<Node>(233, new Comparator<Node>() {
			@Override //override compare method
			public int compare(Node n1, Node n2) {
				if (n1.fValue > n2.fValue) {
					return 1;
				} else if (n1.fValue < n2.fValue) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		queue.add(start);
		System.out.println("\nstarting cost: " + start.weight);
		while ((!queue.isEmpty())) {
			Node curr = queue.poll();
			System.out.println("removed: " + curr + " from the queue");
			visited[curr.index] = true;
			if (curr.index == end.index) {
				endReached = true;
				break;
			}
			curr.gValue = curr.weight;
			for (Edge e : curr.neighbours) {
				Node neighbour = e.target;
				int weight = e.weight;
				int tempGValue = curr.gValue + weight; //used to determine if we change camefrom
				int tempFValue = neighbour.hValue + tempGValue; //used to give priority in the priority queue
				//if neighbour node has been visited and the newer fValue is higher, skip it
				if ((visited[neighbour.index]) /*&& (tempFValue >= neighbour.fValue)*/) {
					continue;
				}
				if ((!queue.contains(neighbour)) || (tempFValue < neighbour.fValue)) {
					neighbour.cameFrom = curr;
					neighbour.gValue = tempGValue;
					neighbour.fValue = tempFValue;

					if (!queue.contains(neighbour)) {
						System.out.println("added node: " + neighbour + " to the queue");
						queue.add(neighbour);
					}

				}

			}
		}
		System.out.println("Finished A star");
	}

	public static void printPath(Node endNode) {
		int totalCost = 0;
		int pathLength = 0;
		
		List<Node> path = new ArrayList<Node>();
		for (Node node = endNode; node.cameFrom != null; node = node.cameFrom) {
			path.add(node);
			System.out.println("adding to path:" + endNode);
		}
		Collections.reverse(path);
		for (Node n : path) {
			totalCost += n.weight;
			pathLength++;
			System.out.println(n.index);
		}
		System.out.println("MINIMAL-COST PATH COSTS: " + totalCost + "\npath length: " + pathLength);
	}

}

class Node {

	public int index;
	public int weight;  //the cost to come to here from the previous node
	public int hValue = 0; //euclidean distance to the end node (in edges). 
	public int gValue;
	public int fValue; //combined heuristic. weight + hValue.
	public ArrayList<Edge> neighbours = new ArrayList<>();
	public Node cameFrom; //the node that points to this node

	int[] distanceToEnd = {99999,
		14, 12, 10, 8, 6, 4, 2, 0,
		13, 11, 9, 7, 5, 3, 1,
		14, 12, 10, 8, 6, 4, 2, 1,
		13, 11, 9, 7, 5, 3, 2,
		14, 12, 10, 8, 6, 4, 3, 2,
		13, 11, 9, 7, 5, 4, 3,
		14, 12, 10, 8, 6, 5, 4, 3,
		13, 11, 9, 7, 6, 5, 4,
		14, 12, 10, 8, 7, 6, 5, 4,
		13, 11, 9, 8, 7, 6, 5,
		14, 12, 10, 9, 8, 7, 6, 5,
		13, 11, 10, 9, 8, 7, 6,
		14, 12, 11, 10, 9, 8, 7, 6,
		13, 12, 11, 10, 9, 8, 7,
		14, 13, 12, 11, 10, 9, 8, 7,
		14, 13, 12, 11, 10, 9, 8,
		15, 14, 13, 12, 11, 10, 9, 8,
		15, 14, 13, 12, 11, 10, 9,
		16, 15, 14, 13, 12, 11, 10, 9,
		16, 15, 14, 13, 12, 11, 10,
		17, 16, 15, 14, 13, 12, 11, 10,
		17, 16, 15, 14, 13, 11, 10,
		18, 17, 16, 15, 14, 13, 12, 11,
		18, 17, 16, 15, 14, 13, 12,
		19, 18, 17, 16, 15, 14, 13, 12,
		19, 18, 17, 16, 15, 14, 13,
		20, 19, 18, 17, 16, 15, 14, 13,
		20, 19, 18, 17, 16, 15, 14,
		21, 20, 19, 18, 17, 16, 15, 14,
		21, 20, 19, 18, 17, 16, 15,
		22, 21, 20, 19, 18, 17, 16, 15};

	public Node(int i, int w) {
		index = i;
		weight = w;
		hValue = distanceToEnd[i];
		fValue = 999; // infinity
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Node)) {
			return false;
		}
		Node n = (Node) o;
		return this.index == n.index && this.weight == n.weight && this.hValue == n.hValue;
	}
	@Override
	public String toString() {
		String from;
		if (cameFrom == null){
			from = "nothing";
		}else{
			from = Integer.toString(cameFrom.index);
		}
		return "index: " + index + " weight: " + weight + " came form " + from + " hValue: " + hValue;
	}

}

class Edge {

	public int weight; //the cost to go to the next node
	public Node target; //the node this edge points to

	public Edge(Node targetNode) {
		target = targetNode;
		weight = targetNode.weight;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Edge)) {
			return false;
		}
		Edge e = (Edge) o;
		return this.target.equals(e.target) && this.target.cameFrom.equals(e.target.cameFrom);
	}
	@Override
	public String toString() {
		return "from: " + target.cameFrom.index + " to: " + target.index;
	}
}
