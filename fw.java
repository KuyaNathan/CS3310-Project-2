public class fw 
{
	final static int INF = 10000;	// declare large integer value to represent infinity

	/* shortestPath function implements Floyd Warshall's algorithm to find all pairs shortest paths
	 * of a given graph.
	 * It takes a 2D array graph and its number of vertices as input, and returns a 2D array
	 * of the weight values of all pairs shortest paths.
	 */
	public int[][] shortestPath(int graph[][], int size)
	{
		int d[][] = new int[size][size];	// declare 2D array d[][]
		for(int u = 0; u < size; u++)		// iterate through d, set all values equal to that of inputed graph
		{
			for(int v = 0; v < size; v++)
			{
				d[u][v] = graph[u][v];
			}
		}

		for(int k = 0; k < size; k++)	// iterate through d, updating weight values when necessary
		{
			for(int u = 0; u < size; u++)
			{
				for(int v = 0; v < size; v++)
				{
					if((d[u][k] + d[k][v]) < d[u][v])
					{
						d[u][v] = (d[u][k] + d[k][v]);
					}
				}
			}
		}
		return d;	// return d, which holds the weight values for all pairs shortest paths
	}	

	/* showres function is used just to display the resulting 2D array neatly for comparison and testing */
	public void showRes(int graph[][], int size)
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

	// main function used to run test cases
	public static void main(String[] args)
	{
		int[][] graph =  { {0,3,8,INF,-4}, {INF,0,INF,1,7}, {INF,4,0,INF,INF}, {2,INF,-5,0,INF}, {INF,INF,INF,6,0} };
		
		//{ {0,1,1,1,4}, {10,0,4,INF,INF}, {1,INF,0,3,3}, {2,INF,7,0,INF}, {4,2,3,3,0} };

		fw he = new fw();
		int[][] wow = he.shortestPath(graph, graph.length);
		he.showRes(wow, wow.length);
	}
}
