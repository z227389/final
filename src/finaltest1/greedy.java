package finaltest1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class greedy {

		static City[] cities = new City[29];
		static int[][] distance = new int[29][29];
		public static void main(String[] args) throws IOException
		{
			// TODO Auto-generated method stub
			
			readData();
			start();
		}
		
		static void readData() throws IOException
		{
			String[] s2;
			String s3;
			FileReader fr2 = new FileReader("city.txt");
			BufferedReader sr2 = new BufferedReader(fr2);
			for (int i = 0; i < cities.length; i++)
			{
				s3 = sr2.readLine();
				s2 = s3.split("\\s+");
				cities[i] = new City();
				cities[i].setID(Integer.parseInt(s2[0]));
				cities[i].setX((int) (Double.parseDouble(s2[1])));
				cities[i].setY((int) (Double.parseDouble(s2[2])));

			}
		}
		
		static void start()
		{
			setDistance();
			/*for(int i=0; i<29;i++)
			{
				for(int j = 0; j<29; j++)
				{
					System.out.print(distance[i][j] + " ");
				}
				System.out.println(" ");
			}*/
			solve(9, distance);
		}
		
		static void setDistance()
		{
			for(int i=0; i<29;i++)
			{
				for(int j = 0; j<29; j++)
				{				
					distance[i][j] = computeDistance(cities[i], cities[j]);
					if(distance[i][j] == 0)
						distance[i][j] = 9999;
				}
			}
		}
		
		private static int computeDistance(City a, City b)
		{
			double x1,x2,y1,y2;
			double distance = 0;
			x1 = a.getX();
			x2 = b.getX();
			y1 = a.getY();
			y2 = b.getY();
			
			distance = Math.sqrt(Math.pow((x1 - x2),2) + Math.pow((y1 - y2), 2));
			return (int)distance;
		}
		
		
		static void solve(int point, int[][] data)
		{
			int sum = 0;
			int nextPoint = 0;
			int minPath = 0;
			int initialPoint = point; 
			System.out.println("Depart from node"+(initialPoint+1) + "\n");
			ArrayList<Integer> passed = new ArrayList<Integer>();
			passed.add(initialPoint);
			int[][] points = data;
			
			try
			{
				minPath = points[initialPoint][0];
				for(int i=0;i < points[initialPoint].length;i++)			
				{
					if(points[initialPoint][i] < minPath)
					{
						minPath = points[initialPoint][i];
						nextPoint = i;	
					}
					else
					{
						continue;
					}
				}
				System.out.println("下一節點:"+(nextPoint+1)+" 路徑長:" + minPath + " 路徑總和:"+ minPath);
				passed.add(nextPoint);
			} 
			catch (ArrayIndexOutOfBoundsException e)
			{	}
			
			
			try
			{
				while(passed.size() != 29)
				{
					int min = points[nextPoint][0];
					int localMin = min ,pointer = 0;
					for(int i = 0; i < points[nextPoint].length;i++)
					{
						if(points[nextPoint][i] <= min && passed.contains(i))
						{
							if(i+1 != points[nextPoint].length)
								min = points[nextPoint][i+1];
							else
								continue;
							continue;
						}
						else if(points[nextPoint][i] <= min )
						{
							min = points[nextPoint][i];
							pointer = i;
							localMin = min;
						}
						else
						{
							continue;
						}
					}
					minPath += localMin;
					nextPoint = pointer;	
					System.out.println("下一節點:"+(nextPoint+1)+" 路徑長:" + localMin + " 路徑總和:"+ minPath);
					passed.add(nextPoint);
					if(passed.size() == 7)
					{
						System.out.println("下一節點:"+(initialPoint + 1)+ " 路徑長:" + (points[nextPoint][initialPoint]) + " 路徑總和:" + (minPath + points[nextPoint][initialPoint]));
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException e)
			{	
				}
			}

		}
		
		
	

