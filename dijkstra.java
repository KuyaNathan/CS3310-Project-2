public class dijkstra
{
	final static int INF = 10000;	// large int value to represent infinity

	/* dijkstraPaths function takes a 2D array graph, the number of vertices, and the source vertex
	 * as input. It then performs Dijkstra's algorithm to find shortest paths from source vertext
	 * to all other vertices, saves it in an array called dist[], and returns dist
	 */
	public int[] dijkstraPaths(int graph[][], int size, int source)
	{
		int dist[] = new int[size];		// declare array for edge weight values
		boolean prev[] = new boolean[size];	// declare array for visited vertices

		for(int a = 0; a < size; a++)		// Initially fill the dist[] array with infinity,
		{					// and the prev[] array with false
			prev[a] = false;
			dist[a] = INF;
		}

		dist[source] = 0;
		for(int a = 0; a < size; a++)
		{
			int v = Relax(dist, prev, size);	// relax to find smallest edge weight
			prev[v] = true;

			for(int b = 0; b < size; b++)	// if an edge has smaller weight and has not been visited, update arrays
			{
				if(!prev[b] && graph[v][b] != 0 && (dist[v] + graph[v][b] < dist[b]))
					dist[b] = dist[v] + graph[v][b];
			}
		}
		return dist;			// return dist[] array of shortest path from source vertex to all others
	}

	/* Relax function is used to determine which edge weight path is smaller*/
	public int Relax(int d[], boolean p[], int size)
	{
		int min = INF;
		int ver = -1;
		for(int a = 0; a < size; a++)
		{
			if(!p[a] && d[a] < min)
			{
				min = d[a];
				ver = a;
			}
		}
		return ver;
	}

	/* dj function is uses dijkstraPaths function in order to use Dijkstra's algorithm as a subroutine to find all
	 * pairs shortest paths. Since dijkstra's returns the shortest paths from one vertex to all others, it is used
	 * multiple times (once per vertex) to fill a 2D array with the values of all pairs shortest paths.
	 * It takes a 2D array graph and the number of vertices as input, and outputs a 2D array of the all pairs shortest paths
	 */
	public int[][] dj(int graph[][], int size)
	{
		int res[][] = new int[size][size];	// declare 2D array to hold value of all pairs shortest paths
		for(int i = 0; i < size; i++)		// loop to iterate through each row (vertex) one by one
		{
			int temp[] = dijkstraPaths(graph, size, i); // use Dijkstra's on the current vertex
			for(int j = 0; j < size; j++)	// loop to iterate through array for current vertex, 
			{
				res[i][j] = temp[j];	// update with shortest paths
			}
		}
		return res;	// return all pairs shortest paths
	}

	// display function is used just to print the resulting 2D arrays neatly for comparison and testing
	public void display(int graph[][], int size)
	{
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(graph[i][j] == INF)
					System.out.print("INF ");
				else if(graph[i][j] > 9 || graph[i][j] < 0)
					if(j == size)
					System.out.print(graph[i][j]);
					else
					System.out.print(graph[i][j] + "  ");
				else
					if(j == size)
						System.out.print(graph[i][j]);
					else
						System.out.print(graph[i][j] + "   ");
			}
			System.out.println();
		}
	}

	// main function used to run test cases and make sure implementation works properly
	public static void main(String[] args)
	{
		int[][] graph = { {0,3,8,INF,-4}, {INF,0,INF,1,7}, {INF,4,0,INF,INF}, {2,INF,-5,0,INF}, {INF,INF,INF,6,0} };

		//{ {0,1,1,1,4}, {10,0,4,INF,INF}, {1,INF,0,3,3}, {2,INF,7,0,INF}, {4,2,3,3,0} };
		
		dijkstra he = new dijkstra();
		int[][] a = he.dj(graph, graph.length);
		he.display(a, a.length);

	}	
}

