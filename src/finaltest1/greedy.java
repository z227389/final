package finaltest1;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class greedy {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	 	  String[] s2;
	 	  String s3;  
	 	  City[] cities = new City[29];
	      double city[][] = new double[29][29];
	      ArrayList<Integer> node = new ArrayList<Integer>(); //用來儲存到底要走哪些城市	      
	        
	      FileReader fr2=new FileReader("bayg29.txt");
	      BufferedReader sr2=new BufferedReader(fr2);
	        
	      	//將從txt檔讀到的資料存進cities矩陣中
	       	for ( int i = 0; i < cities.length; i++){ 
	            s3 = sr2.readLine();
	            s2 = s3.split("\\s+");
	            cities[i] = new City();
	            cities[i].setX((int) (Double.parseDouble(s2[1])));
	            cities[i].setY((int) (Double.parseDouble(s2[2])));
	            //System.out.println(cities[i].getY());
  			}
	       	
	       	//從cities矩陣中用get將作標取出，並利用distance函式，將各城市的x y值 互相平方相加開根號 得到距離，再存進city矩陣中
	       	for(int i =0;i < cities.length ; i++ ){
	       		for(int j =0;j < cities.length ; j++){
	       			if(i==j){
	       				city[i][j] = 9999;
	       			}else{
	       			city[i][j] = distance(cities[i].getX(),cities[i].getY(),cities[j].getX(),cities[j].getY());    			
	       			}
	       		}
	       	}
	       	
	      //檢測矩陣是否存入各城市間的距離
	       	/*for(int i =0;i<29;i++){
	       		for(int j=0;j<29;j++){
	       			System.out.printf("%.3f   ",city[i][j]);
	       		}
	       		System.out.println();
	       	}*/
	       	
	       	int count = 0; //作為計數器使用，因為有29座城市 因此要作29次計算
	       	double path = 0; //path是目前走訪城市的總路徑
	       	int current_city = 9; //初始起始點、之後會作為每次的起點
	       	int start = 0; //到達點
	       	int steps=0;//第幾次
	       	double temp = 0;//用來作為比較的目前路徑，若其他路徑比較好則會替換掉
	       	
	       	while( count < city.length) //計數器在此使用 作29次計算
			{
				if( count == city.length-1) //作到第29次 因為count是由0開始，所以只會到28次，因此用city.length-1
				{
					path = path + city[0][current_city]; //最後走到的城市會在current_city內，所以要算從第一個城市到最後程式的距離，在加進總路徑中，才算完成TSP
					
					System.out.printf("The city%d to city10 path is: %.3f\n", current_city+1, city[0][current_city]);
					System.out.printf("Final step:%d\n",steps+1);
				}
				else
				{
					start = current_city;
					node.add(start);
					temp = 9999; //先將目前temp設為相當遙遠的路徑，比它小的路徑就會替換掉它
				
					for(int target = 0; target < 29; target++)
					{
						
						for(int already_city = 0; already_city <node.size(); already_city++)
						{
							if(target == node.get(already_city) ) break;
							else if(city[start][target] < temp && already_city == (node.size()-1) )
							{
									temp = city[start][target];	
									current_city = target; //將目標傳回給start 這樣下次就會從這個城市出發
							}
						
						}
						
					}
					path = path + temp;
					System.out.printf("The city%d to city%d path is: %.3f\n", start+1,current_city+1,temp);
					steps++;
				    System.out.printf("Step:%d\n",steps);
				}
				count++;
			}
	    
	       	
	        System.out.printf("The final sum path is %.3f",path);
	}
	
	//distance函式 用來計算城市間的距離
	static double distance(int x1,int y1,int x2,int y2) 
	{	double dis = 0;
		double x,y;
		x = Math.pow(x1 - x2, 2);
		y = Math.pow(y1 - y2, 2);
		dis =Math.sqrt(x+y);
		return dis;
	}
	
}
	   
 
