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
	      ArrayList<Integer> node = new ArrayList<Integer>(); //�Ψ��x�s�쩳�n�����ǫ���	      
	        
	      FileReader fr2=new FileReader("bayg29.txt");
	      BufferedReader sr2=new BufferedReader(fr2);
	        
	      	//�N�qtxt��Ū�쪺��Ʀs�icities�x�}��
	       	for ( int i = 0; i < cities.length; i++){ 
	            s3 = sr2.readLine();
	            s2 = s3.split("\\s+");
	            cities[i] = new City();
	            cities[i].setX((int) (Double.parseDouble(s2[1])));
	            cities[i].setY((int) (Double.parseDouble(s2[2])));
	            //System.out.println(cities[i].getY());
  			}
	       	
	       	//�qcities�x�}����get�N�@�Ш��X�A�çQ��distance�禡�A�N�U������x y�� ���ۥ���ۥ[�}�ڸ� �o��Z���A�A�s�icity�x�}��
	       	for(int i =0;i < cities.length ; i++ ){
	       		for(int j =0;j < cities.length ; j++){
	       			if(i==j){
	       				city[i][j] = 9999;
	       			}else{
	       			city[i][j] = distance(cities[i].getX(),cities[i].getY(),cities[j].getX(),cities[j].getY());    			
	       			}
	       		}
	       	}
	       	
	      //�˴��x�}�O�_�s�J�U���������Z��
	       	/*for(int i =0;i<29;i++){
	       		for(int j=0;j<29;j++){
	       			System.out.printf("%.3f   ",city[i][j]);
	       		}
	       		System.out.println();
	       	}*/
	       	
	       	int count = 0; //�@���p�ƾ��ϥΡA�]����29�y���� �]���n�@29���p��
	       	double path = 0; //path�O�ثe���X�������`���|
	       	int current_city = 9; //��l�_�l�I�B����|�@���C�����_�I
	       	int start = 0; //��F�I
	       	int steps=0;//�ĴX��
	       	double temp = 0;//�Ψӧ@��������ثe���|�A�Y��L���|����n�h�|������
	       	
	       	while( count < city.length) //�p�ƾ��b���ϥ� �@29���p��
			{
				if( count == city.length-1) //�@���29�� �]��count�O��0�}�l�A�ҥH�u�|��28���A�]����city.length-1
				{
					path = path + city[0][current_city]; //�̫ᨫ�쪺�����|�bcurrent_city���A�ҥH�n��q�Ĥ@�ӫ�����̫�{�����Z���A�b�[�i�`���|���A�~�⧹��TSP
					
					System.out.printf("The city%d to city10 path is: %.3f\n", current_city+1, city[0][current_city]);
					System.out.printf("Final step:%d\n",steps+1);
				}
				else
				{
					start = current_city;
					node.add(start);
					temp = 9999; //���N�ثetemp�]���۷��������|�A�񥦤p�����|�N�|��������
				
					for(int target = 0; target < 29; target++)
					{
						
						for(int already_city = 0; already_city <node.size(); already_city++)
						{
							if(target == node.get(already_city) ) break;
							else if(city[start][target] < temp && already_city == (node.size()-1) )
							{
									temp = city[start][target];	
									current_city = target; //�N�ؼжǦ^��start �o�ˤU���N�|�q�o�ӫ����X�o
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
	
	//distance�禡 �Ψӭp�⫰�������Z��
	static double distance(int x1,int y1,int x2,int y2) 
	{	double dis = 0;
		double x,y;
		x = Math.pow(x1 - x2, 2);
		y = Math.pow(y1 - y2, 2);
		dis =Math.sqrt(x+y);
		return dis;
	}
	
}
	   
 
