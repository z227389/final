package finaltest1;
	import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

	public class hill {

		private int MAX_GEN;// 反覆運算次數
		private int cityNum; // 城市數量,編碼長度
		private int[][] distance; // 距離矩陣
		private int bestT;// 最佳出現代數
		private int[] bestGh;// 最好的路徑編碼
		private int bestEvaluation;

		private Random random;

		public hill() {

		}

		/**
		 * constructor of GA
		 * 
		 * @param n
		 *            城市數量
		 * @param g
		 *            運行代數
		 * 
		 **/
		//「基因演算法」是一個解決最佳化問題工具
		public hill(int n, int g) {
			cityNum = n; //城市數量
			MAX_GEN = g; //運行疊代數 
		}

		// 給編譯器一條指令，告訴它對被批註的代碼元素內部的某些警告保持靜默
		//@SuppressWarnings("resource")
		/**
		 * 初始化HillClimbing演算法類
		 * @param filename 資料檔案名，該檔存儲所有城市節點座標資料
		 * @throws IOException
		 */
		private void init(String filename) throws IOException {
			// 讀取數據
			int[] x;
			int[] y;
			String strbuff;
			BufferedReader data = 
					new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			distance = new int[cityNum][cityNum];
			x = new int[cityNum];
			y = new int[cityNum];
			for (int i = 0; i < cityNum; i++) {
				// 讀取一行資料，資料格式1 6734 1453
				strbuff = data.readLine();
				// 字元分割
				String[] strcol = strbuff.split(" ");
				x[i] = Integer.valueOf(strcol[1]);// x座標
				y[i] = Integer.valueOf(strcol[2]);// y座標
			}
			// 計算距離矩陣
			// 針對具體問題，距離計算方法也不一樣，
			// 此處用的是att48作為案例，它有48個城市，距離計算方法為歐氏距離，最優值為10628
			//http://www.dcs.gla.ac.uk/~pat/af2009/tspDemo/att48.tsp
			for (int i = 0; i < cityNum - 1; i++) {
				distance[i][i] = 0; // 對角線為0
				for (int j = i + 1; j < cityNum; j++) {
					//double rij = Math.sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
									//* (y[i] - y[j])) / 10.0);
					
					double rij=Math.sqrt((Math.pow((x[i] - x[j]),2)+Math.pow((y[i] - y[j]), 2)/10.0));
					// 四捨五入，取整
					int tij = (int) Math.round(rij);
					if (tij < rij) {
						distance[i][j] = tij + 1;
						distance[j][i] = distance[i][j];
					} else {
						distance[i][j] = tij;
						distance[j][i] = distance[i][j];
					}
				}
			}
			distance[cityNum - 1][cityNum - 1] = 0;

			bestGh = new int[cityNum];
			bestEvaluation = Integer.MAX_VALUE;
			bestT = 0;

			random = new Random(System.currentTimeMillis());
		}
		//GA 是將參數編碼進行演化運算，而不是使用參數本身做搜尋。
		//染色體代表的東西不一定代表解的實際狀態
		//事實上染色體通常需要經過解碼來得到解的實際狀態，經過解碼後的才是
		// 初始化編碼Ghh
		void initGroup() {
			int i, j;
			bestGh[0] = random.nextInt(65535) % cityNum;
			for (i = 1; i < cityNum;)// 編碼長度
			{
				bestGh[i] = random.nextInt(65535) % cityNum;
				for (j = 0; j < i; j++) {
					if (bestGh[i] == bestGh[j]) {
						break;
					}
				}
				if (j == i) {
					i++;
				}
			}
		}

		public int evaluate(int[] chr) {
			int len = 0;
			// 染色體，起始城市,城市1,城市2...城市n 行走城市的先後順序
			for (int i = 1; i < cityNum; i++) {
				len += distance[chr[i - 1]][chr[i]];
			}
			// 城市n,起始城市
			len += distance[chr[cityNum - 1]][chr[0]];
			return len;
		}

		// 爬山演算法
		public void pashan(int[] Gh, int T) {
			int i, temp, tt = 0;
			int ran1, ran2;
			int e;// 評價新值(交配:利用兩組染色體組合得到新的染色體)
			int[] tempGh = new int[cityNum];
			bestEvaluation = evaluate(Gh);

			// 爬山代數T
			for (tt = 0; tt < T; tt++) {
				for (i = 0; i < cityNum; i++) {
					tempGh[i] = Gh[i];
				}
				ran1 = random.nextInt(65535) % cityNum;
				ran2 = random.nextInt(65535) % cityNum;
				while (ran1 == ran2) {
					ran2 = random.nextInt(65535) % cityNum;
				}

				// 兩交換法實施鄰域操作   爬山演算法只尋找鄰近的城市做比較
				temp = tempGh[ran1];
				tempGh[ran1] = tempGh[ran2];
				tempGh[ran2] = temp;

				e = evaluate(tempGh);// 評價新值

				if (e < bestEvaluation) {
					bestT = tt;
					bestEvaluation = e;
					for (i = 0; i < cityNum; i++) {
						Gh[i] = tempGh[i];
					}
				}
			}

		}

		public void solve() {
			initGroup();// 初始化編碼
			pashan(bestGh, MAX_GEN);

			System.out.println("最佳長度出現的代數："+bestT);
			//System.out.println(bestT);
			System.out.println("最佳長度:"+bestEvaluation);
			//System.out.println(bestEvaluation);
			System.out.println("最佳路徑：");
			for (int i = 0; i < cityNum; i++) {
				System.out.print(bestGh[i] + ",");
				if (i % 10 == 0 && i != 0) {
					System.out.println();
				}
			}
		}

		/**
		 * @param args
		 * @throws IOException
		 */
		public static void main(String[] args) throws IOException {
			System.out.println("Start....");
			hill hillClimbing = new hill(30, 100); //走0~9的城市 疊代次數設定在50次
			hillClimbing.init("C:/data.txt");
			hillClimbing.solve();
		}
	}
//收斂：通常會設定為
  // 1.疊代次數到達一定次數
  // 2.所有染色體都非常相似


