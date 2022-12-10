import java.lang.Math;

public class pairs
{
	final static int INF = 10000;		// define for the "infinity" when vertices do not have a path connnecting them
	public static void main(String[] args)
	{
		//sanity_check();	// call sanity check function; commented out to allow program to run as intended
	
		int max = 10;		// for the sake of formatting when i was printing the resulting tables, edge weights can only be max of 10
		int min = 1;		// minimum edge weight is -5
		int range = max - min; // range used in random number generation for the edge weights
		int temp = 0;
		int flag = 0;
		

		for(int a = 1; a <= 180; a++)	// loop to create multiple randomly generated graphs of different sizes for testing
		{
			int size = a * 10;	// the size of the graphs increments by +10 for each loop iteration

			int graph[][] = new int[size][size]; // declare 2D array for the table of the shortest paths between vertices

			for(int i = 0; i < size; i++)	// loop to fill the postions in the 2D graph
			{
				for(int j = 0; j < size; j++)
				{
					if(i == j)
						graph[i][j] = 0;  // when i == j, fill spot with 0 to represent a vertex's path directly to itself
					else
					{
						while(flag == 0)
						{
							temp =  (int)(Math.random() * range) +1;
							if(temp != 0)
								break;
						}
						graph[i][j] = temp;
					}
				}	
						
						

				// This section is to randomly decide the density of the graph In order to have a wider range of graphs to test.
				int sparse = (int)(Math.random() * (size/2) + 1);	// randomly decides how many edges will become INF (no connection)
				for(int k = 0; k < sparse; k++)
				{
					int x = (int)(Math.random() * size-1) + 1;	// Randomly generate ints x and y to change the value at graph[x][y]
					int y = (int)(Math.random() * size-1) + 1;	// to INF to represent no direct connection between two vertices

					// This will only change the value at graph[x][y] if it is NOT 0. 0 represents a vertex's path directly to itself, and
					// must be left as 0.

					if(x == y)					// If the x and y happen to be the same, leave the graph[x][y] as 0
					{
						continue;
					}
					else if(graph[x][y] == 0)			// Backup check, if graph[x][y] is 0, leave it alone
					{
						continue;
					}
					else						// otherwise, change the randomly decided graph[x][y] to INF
					{
						graph[x][y] = INF;
					}
				}

			}

			// This for loop is used to display the randomly generated 2D arrays (graphs)
			// It is commented out in order to make the printed finish results easier to read
			/* 
			for(int z = 0; z < size; z++)
			{
				for(int q = 0; q < size; q++)
				{
					if(graph[z][q] == INF)
					System.out.print("INF ");
				else if(graph[z][q] > 9)
				if(q == size)
					System.out.print(graph[z][q]);
					else
					System.out.print(graph[z][q] + "  ");
				else
					if(q == size)
						System.out.print(graph[z][q]);
					else
						System.out.print(graph[z][q] + "   ");
				}
				System.out.println();
			}
			*/


			double fw_case = 0;	// holds the average time it took to find shortest paths with the floyd warshall algorithm
			double dj_case = 0;	// holds the average time it took to find shortest paths using dijkstra's algorithm as a subroutine

			int b;			// Declared before loop, used to track the number of times the algorithms run. It is used later to
						// calculate the average runtime.

			for(b = 0; b < 5; b++)	// loop to use both algorithms on the current random graph 5 times each
			{
				fw floyd = new fw();			// create new floyd warshall class instance
				double fstart = System.nanoTime();
				int[][] one = floyd.shortestPath(graph, size); // call floyd warshall algorithm on current random graph
				double end = System.nanoTime();
				double ftime = (end - fstart) / 1000000;
				fw_case += ftime;			// add to total floyd warshall runtime
				//System.out.println("---floyd---");
				//floyd.showRes(one, size);

									//NOTE: commands to print the results are commented out to make the output look neat
									//	for easy reading of the results and data collection

				dijkstra dj = new dijkstra();		// create new dijkstra's as subroutine instance
				double dstart = System.nanoTime();
				int[][] two = dj.dj(graph, size);	// call dijkstras as subroutine on current random graph
				double dend = System.nanoTime();
				double dtime = (dend - dstart) / 1000000;
				dj_case += dtime;			// add to total dijkstras runtime
				//System.out.println("---dijkstra---");
				//dj.display(two, size);
			}
			fw_case = (fw_case / b);		// calculate average floyd warshall runtime for current random graph
			dj_case = (dj_case / b);		// calculate average dijkstras as subroutine runtime for current graph

								// Only print the results of the first random graph, and every other graph after that can be evenly
								// multiplied by 100. I do this in order to have a feasibly amount of data to see and use.
			if(size == 10 || size % 100 == 0)	
			{
				System.out.println("-----Average Time to Find All Shortest Paths Using Dijkstra's as a Subroutine for graph with " + size + " vertices-----");
				System.out.println(dj_case + " milliseconds");
				System.out.println("-----Average Time to Find All Shortest Paths Using Floyd Warshall for graph with " + size + " vertices-----");
				System.out.println(fw_case + " milliseconds");
				System.out.println();
			}
		}	
	

	}

	/* sanity_check function is used for the sanity check part of the project,
	 * Given a pretyped graph, checks if the two algorithms produce the same
	 * correct resulting 2D array of all pairs shortest paths.
	 */
	public static void sanity_check()
	{
		int[][] check1 = { {0,3,8,INF,-4}, {INF,0,INF,1,7}, {INF,4,0,INF,INF}, {2,INF,-5,0,INF}, {INF,INF,INF,6,0} };
		int vc = check1.length;

		System.out.println("---Sanity Check---\nTest Graph:");
		for(int z = 0; z < vc; z++)
			{
				for(int q = 0; q < vc; q++)
				{
					if(check1[z][q] == INF)
					System.out.print("INF ");
				else if(check1[z][q] > 9)
				if(q == vc)
					System.out.print(check1[z][q]);
					else
					System.out.print(check1[z][q] + "  ");
				else
					if(q == vc)
						System.out.print(check1[z][q]);
					else
						System.out.print(check1[z][q] + "   ");
				}
				System.out.println();
			}
		System.out.println();
		
			
		
		System.out.println("---Results with using Floyd Warshall---");
		fw sc1 = new fw();
		int[][]c1a = sc1.shortestPath(check1, check1.length);
		sc1.showRes(c1a, check1.length);
		System.out.println();

		System.out.println("---Results with using Dijkstra's as subroutine---");
		dijkstra sc2 = new dijkstra();
		int[][]c1b = sc2.dj(check1, check1.length);
		sc2.display(c1b, check1.length);
		System.out.println();
	}
}