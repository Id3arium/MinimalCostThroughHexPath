import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Main {
	static long start;
	static long end;
	
    public static void main(String[] args) throws IOException {
    	start = System.currentTimeMillis();
    	PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
    	
		int[] weights = readGridFromFile("input.txt");
		Node[] graph = makeGraph(weights);

		AStarSearch(graph[226], graph[8]); //end should be 8

		printPath(graph[8],graph[226], pw);
		pw.close();
	} // End main()

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


	public static boolean isLegalIndex(int i) {
		return (i >= 1 && i <= 233);
	} // End isLegalIndex()
	
	public static boolean isLeftWall(int i){
		return (i - 1) % 15 == 0;
	} // End isLeftWall()
	
	public static boolean isRightWall(int i){
		return (i - 8) % 15 == 0;
	} // End isRightWall()

	public static Node[] makeGraph(int[] weights) {
		Node[] graph = new Node[234];
		for (int i = 0; i <= 233; i++) { //make all 233 nodes with no edges
			int w = weights[i];
			Node node = new Node(i, w);
			graph[i] = node;
		} // End for
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
			if (isLegalIndex(i - 7) && graph[i - 7].weight != -1 && !isRightWall(i)) {        // UpRight neighbor
				Edge edge = new Edge(graph[i - 7]);
				graph[i].neighbours.add(edge); // add edge from node at index i to node at index i-7
			}
			if (isLegalIndex(i + 8) && graph[i + 8].weight != -1 && !isRightWall(i)) {        // DownRight neighbor
				Edge edge = new Edge(graph[i + 8]);
				graph[i].neighbours.add(edge); // add edge from node at index i to node at index i+8
			}
			if (isLegalIndex(i + 15) && graph[i + 15].weight != -1)  {        // Down neighbor
				Edge edge = new Edge(graph[i + 15]);
				graph[i].neighbours.add(edge); //add edge from node at index i to node at index i+15
			}
			if (isLegalIndex(i + 7) && graph[i + 7].weight != -1 && !isLeftWall(i)) {        // DownLeft neighbor
				Edge edge = new Edge(graph[i + 7]);
				graph[i].neighbours.add(edge); // add edge from node at index i to node at index i+7
			}
			if (isLegalIndex(i - 8) && graph[i - 8].weight != -1 && !isLeftWall(i)) {        // UpLeft neighbor
				Edge edge = new Edge(graph[i - 8]);
				graph[i].neighbours.add(edge); // add edge from node at index i to node at index i-8
			}
		} // End for
		return graph;
	} // End makeGraph()

	public static void AStarSearch(Node start, Node end) {
		boolean[] visited = new boolean[234];
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
			} // End compare()
		});
		queue.add(start);
		while ((!queue.isEmpty())) {
			Node curr = queue.poll();
			if (curr.index == end.index) {
				break;
			}
			visited[curr.index] = true; //pop node from the queue and add its neighbours to the queue
			for (Edge e : curr.neighbours) {
				Node neighbour = e.target;
				int tempGValue = curr.gValue + e.weight; //used to determine if we change camefrom
				int tempFValue = neighbour.hValue + tempGValue; //used to give priority in the priority queue
				if ((visited[neighbour.index])) { //if neighbour node has been visited, skip it
					continue;
				}
				if ((!queue.contains(neighbour)) || (tempFValue < neighbour.fValue)) {
					neighbour.cameFrom = curr;
					neighbour.gValue = tempGValue;
					neighbour.fValue = tempFValue;
					
					if (!queue.contains(neighbour)) {
						queue.add(neighbour);
					}
				}
			} // End for
		} // End while
	} // End AStarSearch()

	public static void printPath(Node endNode, PrintWriter pw) {
		int totalCost = 0;
		List<Node> path = new ArrayList<Node>();
		
		for (Node node = endNode; node != null; node = node.cameFrom) {
			path.add(node);
		} // End for
		Collections.reverse(path);
		for (Node n : path) {
			totalCost += n.weight;
			System.out.println(n.index);
			pw.println(n.index);
		} // End for
		System.out.println("MINIMAL-COST PATH COSTS: " + totalCost);
		pw.println("MINIMAL-COST PATH COSTS: " + totalCost);
		end = System.currentTimeMillis();
		System.out.println("Time taken: " + (end - start) + " milliseconds");
	} // End printPath()
} // End Main class

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

	//public double f_scores = 0;
	public Node(int i, int w) {
		index = i;
		weight = w;
		hValue = distanceToEnd[i];
		fValue = 999999; // infinity
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Node)) {
			return false;
		}
		Node n = (Node) o;
		return this.index == n.index && this.weight == n.weight && this.hValue == n.hValue;
	} // End equals()
} // End Node class

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
	} // End equals()
} // End Edge class
