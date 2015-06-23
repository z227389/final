package finaltest1;
	import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

	public class hill {

		private int MAX_GEN;// ���йB�⦸��
		private int cityNum; // �����ƶq,�s�X����
		private int[][] distance; // �Z���x�}
		private int bestT;// �̨ΥX�{�N��
		private int[] bestGh;// �̦n�����|�s�X
		private int bestEvaluation;

		private Random random;

		public hill() {

		}

		/**
		 * constructor of GA
		 * 
		 * @param n
		 *            �����ƶq
		 * @param g
		 *            �B��N��
		 * 
		 **/
		//�u��]�t��k�v�O�@�ӸѨM�̨Τư��D�u��
		public hill(int n, int g) {
			cityNum = n; //�����ƶq
			MAX_GEN = g; //�B���|�N�� 
		}

		// ���sĶ���@�����O�A�i�D����Q������N�X�����������Y��ĵ�i�O���R�q
		//@SuppressWarnings("resource")
		/**
		 * ��l��HillClimbing�t��k��
		 * @param filename ����ɮצW�A���ɦs�x�Ҧ������`�I�y�и��
		 * @throws IOException
		 */
		private void init(String filename) throws IOException {
			// Ū���ƾ�
			int[] x;
			int[] y;
			String strbuff;
			BufferedReader data = 
					new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			distance = new int[cityNum][cityNum];
			x = new int[cityNum];
			y = new int[cityNum];
			for (int i = 0; i < cityNum; i++) {
				// Ū���@���ơA��Ʈ榡1 6734 1453
				strbuff = data.readLine();
				// �r������
				String[] strcol = strbuff.split(" ");
				x[i] = Integer.valueOf(strcol[1]);// x�y��
				y[i] = Integer.valueOf(strcol[2]);// y�y��
			}
			// �p��Z���x�}
			// �w�������D�A�Z���p���k�]���@�ˡA
			// ���B�Ϊ��Oatt48�@���רҡA����48�ӫ����A�Z���p���k���ڤ�Z���A���u�Ȭ�10628
			//http://www.dcs.gla.ac.uk/~pat/af2009/tspDemo/att48.tsp
			for (int i = 0; i < cityNum - 1; i++) {
				distance[i][i] = 0; // �﨤�u��0
				for (int j = i + 1; j < cityNum; j++) {
					//double rij = Math.sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
									//* (y[i] - y[j])) / 10.0);
					
					double rij=Math.sqrt((Math.pow((x[i] - x[j]),2)+Math.pow((y[i] - y[j]), 2)/10.0));
					// �|�ˤ��J�A����
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
		//GA �O�N�Ѽƽs�X�i��t�ƹB��A�Ӥ��O�ϥΰѼƥ������j�M�C
		//�V����N���F�褣�@�w�N��Ѫ���ڪ��A
		//�ƹ�W�V����q�`�ݭn�g�L�ѽX�ӱo��Ѫ���ڪ��A�A�g�L�ѽX�᪺�~�O
		// ��l�ƽs�XGhh
		void initGroup() {
			int i, j;
			bestGh[0] = random.nextInt(65535) % cityNum;
			for (i = 1; i < cityNum;)// �s�X����
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
			// �V����A�_�l����,����1,����2...����n �樫���������ᶶ��
			for (int i = 1; i < cityNum; i++) {
				len += distance[chr[i - 1]][chr[i]];
			}
			// ����n,�_�l����
			len += distance[chr[cityNum - 1]][chr[0]];
			return len;
		}

		// ���s�t��k
		public void pashan(int[] Gh, int T) {
			int i, temp, tt = 0;
			int ran1, ran2;
			int e;// �����s��(��t:�Q�Ψ�լV����զX�o��s���V����)
			int[] tempGh = new int[cityNum];
			bestEvaluation = evaluate(Gh);

			// ���s�N��T
			for (tt = 0; tt < T; tt++) {
				for (i = 0; i < cityNum; i++) {
					tempGh[i] = Gh[i];
				}
				ran1 = random.nextInt(65535) % cityNum;
				ran2 = random.nextInt(65535) % cityNum;
				while (ran1 == ran2) {
					ran2 = random.nextInt(65535) % cityNum;
				}

				// ��洫�k��I�F��ާ@   ���s�t��k�u�M��F�񪺫��������
				temp = tempGh[ran1];
				tempGh[ran1] = tempGh[ran2];
				tempGh[ran2] = temp;

				e = evaluate(tempGh);// �����s��

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
			initGroup();// ��l�ƽs�X
			pashan(bestGh, MAX_GEN);

			System.out.println("�̨Ϊ��ץX�{���N�ơG"+bestT);
			//System.out.println(bestT);
			System.out.println("�̨Ϊ���:"+bestEvaluation);
			//System.out.println(bestEvaluation);
			System.out.println("�̨θ��|�G");
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
			hill hillClimbing = new hill(30, 100); //��0~9������ �|�N���Ƴ]�w�b50��
			hillClimbing.init("C:/data.txt");
			hillClimbing.solve();
		}
	}
//���ġG�q�`�|�]�w��
  // 1.�|�N���ƨ�F�@�w����
  // 2.�Ҧ��V���鳣�D�`�ۦ�


