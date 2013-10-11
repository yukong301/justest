package com.lsc.data;

public final class GlobalData {
	// ��ʾ���̵Ķ�ά���飬0��ʾ����(���)��1��ʾ����(�����)��2��ʾû�����ӡ�
	public static int[][] M_BOARD = new int[15][15];
	public static int[][] T_BOARD = new int[15][15];// �����������塣����ÿһ�����ʱ��Ҫ���������������¼������߷���

	// P_TABLE[x][y][k]Ϊtrue��ʾ[x][y]λ������ҵ�k��"5"(��ʤ���)�е�һ��λ�á�
	public static boolean[][][] P_TABLE = new boolean[15][15][572];
	private static boolean[][][] PTABLE = new boolean[15][15][572];
	// C_TABLE[x][y][k]Ϊtrue��ʾ[x][y]λ���Ǽ������k��"5"(��ʤ���)�е�һ��λ�á�
	public static boolean[][][] C_TABLE = new boolean[15][15][572];
	public static boolean[][][] CTABLE = new boolean[15][15][572];
	// win[0][k]��ʾ����ڵ�k����5����λ�����䷽����win[0][k]�������ĺ���
	// win[1][k]��ʾ������ڵ�k����5����λ��������win[1][k]�����İ���
	// ȡֵΪ9���ʾ��k��"5"��λ�ñ������ˣ�û�г�5�Ŀ���
	// ��ʼΪ0
	private int cx, cy;// ���������λ��
	private int px, py;// �������λ��

	public static int[][] WIN = new int[2][572];
	public static int[][] T_WIN = new int[2][572];
	public static boolean start = true;

	static {// ��ʼ����������
		initData();
	}

	public static void initData() {
		start = true;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 572; j++) {
				WIN[i][j] = 0;
			}

		}
		int count = 0;
		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15; y++) {
				M_BOARD[x][y] = T_BOARD[x][y] = 2;
				for (int k = 0; k < 572; k++) {
					P_TABLE[x][y][k] = false;
					C_TABLE[x][y][k] = false;
					PTABLE[x][y][k] = false;
					CTABLE[x][y][k] = false;
				}
			}
		}

		for (int x = 0; x < 15; x++) // x������ͬ�Ľ����
		{
			for (int y = 0; y < 11; y++) {
				for (int k = 0; k < 5; k++) {
					// �������˵����count���ܳ�5��λ��Ϊ[x][y+k]
					P_TABLE[x][y + k][count] = true;
					C_TABLE[x][y + k][count] = true;
					PTABLE[x][y + k][count] = true;
					CTABLE[x][y + k][count] = true;
				}
				count++;
			}
		}
		for (int y = 0; y < 15; y++) // y������ͬ�Ľ����
		{
			for (int x = 0; x < 11; x++) {
				for (int k = 0; k < 5; k++) {
					P_TABLE[x + k][y][count] = true;
					C_TABLE[x + k][y][count] = true;
					PTABLE[x + k][y][count] = true;
					CTABLE[x + k][y][count] = true;
				}
				count++;
			}
		}

		for (int x = 0; x < 11; x++) // ���Խ��ߨv
		{
			for (int y = 0; y < 11; y++) {
				for (int k = 0; k < 5; k++) {
					P_TABLE[x + k][y + k][count] = true;
					C_TABLE[x + k][y + k][count] = true;
					PTABLE[x + k][y + k][count] = true;
					CTABLE[x + k][y + k][count] = true;
				}
				count++;
			}
		}
		for (int x = 4; x < 15; x++) // �ζԽ��ߨu
		{
			for (int y = 0; y < 11; y++) {
				for (int k = 0; k < 5; k++) {
					P_TABLE[x - k][y + k][count] = true;
					C_TABLE[x - k][y + k][count] = true;
					PTABLE[x - k][y + k][count] = true;
					CTABLE[x - k][y + k][count] = true;
				}
				count++;
			}
		}

	}

	/**
	 * �����x,y��������󣬸������ݵı仯
	 * 
	 * @param x
	 * @param y
	 */
	public void pboardChange(int x, int y) {
		int i = 0, j = 0;
		for (int k = 0; k < 572; k++) // �޸�������Ӻ�����״̬�ı仯
		{
			// ���[x][y]λ������ҵ�k��"5"�е�λ�ã��ҵ�k��"5"û�б�����

			if (P_TABLE[x][y][k] && WIN[0][k] != 9) {
				WIN[0][k]++;
				// System.out.println("�����������" + WIN[0][k]);
				i++;
			}
			// ���[x][y]λ���Ǽ������k��"5"�е�λ�ã���������Ӻ��k��"5"������
			if (C_TABLE[x][y][k]) // ������
			{
				C_TABLE[x][y][k] = false;
				WIN[1][k] = 9;
				j++;
			}
		}
		System.out.println(i + "  " + j);

	}

	/**
	 * �����������ʱ������
	 * 
	 * @param x
	 * @param y
	 */
	public void ctemp_boardChange(int x, int y) {
		for (int k = 0; k < 572; k++) {
			if (CTABLE[x][y][k] && T_WIN[1][k] != 9) {
				T_WIN[1][k]++;
			}
			if (PTABLE[x][y][k]) {
				PTABLE[x][y][k] = false;
				T_WIN[0][k] = 9;
			}
		}
	}

	/**
	 * ������x,y��������󣬸������ݵı仯
	 * 
	 * @param x
	 * @param y
	 */
	public void cboardChange(int x, int y) {

		for (int k = 0; k < 572; k++) {
			if (C_TABLE[x][y][k] && WIN[1][k] != 9) {
				WIN[1][k]++;
			}
			if (P_TABLE[x][y][k]) {
				P_TABLE[x][y][k] = false;
				WIN[0][k] = 9;
			}
		}
	}

	/**
	 * ��(x,y)�������ĵ÷֣�������÷�Ϊ������ҵ÷�Ϊ��
	 * 
	 * @param type
	 *            �������Ϊ1�������Ϊ0;
	 * @param x
	 * @param y
	 * @return
	 */
	public int getScore(int type, int x, int y) {
		int score = 0;
		for (int k = 0; k < 572; k++) {
			if (type == 1) {// ������£��÷�Ϊ��
				if (C_TABLE[x][y][k]) {
					switch (WIN[1][k]) {//
					case 1:
						score += 5;
						break;
					case 2:
						score += 50;
						break;
					case 3:
						score += 500;
						break;
					case 4:
						score += 5000;
						break;
					default:
						break;
					}
				}
			} else {// ������� type==0ʱ
				if (P_TABLE[x][y][k]) {
					switch (WIN[0][k]) {
					case 1:
						score -= 5;
						break;
					case 2:
						score -= 50;
						break;
					case 3:
						score -= 500;
						break;
					case 4:
						score -= 5000;
						break;

					}
				}
			}
		}

		return score;
	}

	/**
	 * ��ÿһ������ǰ��ԭ�ȵ����̸��Ƶ���ʱ������ȥ
	 */
	// public void copyBoard() {
	// for (int i = 0; i < 15; i++) {
	// for (int j = 0; j < 15; j++) {
	// T_BOARD[i][j] = M_BOARD[i][j];
	// for (int k = 0; k < 572; k++) {
	// CTABLE[i][j][k] = C_TABLE[i][j][k];
	// PTABLE[i][j][k] = P_TABLE[i][j][k];
	// }
	// }
	// }
	// for (int i = 0; i < 2; i++) {
	// for (int j = 0; j < 572; j++) {
	// T_WIN[i][j] = WIN[i][j];
	// }
	// }
	// }

	/**
	 * �����������������е����µĵ㡣���ӷ��ص÷����ĵ㣬���ӷ�����С�ĵ㡣
	 * 
	 * @param type
	 * @return
	 */
	public int evaluateScore(int type, int[][] panel_board) {
		int score = 0, cscore = -10000, pscore = 10000;
		int temp = 0;
		// copyBoard();// �����ݸ��Ƶ���ʱ���ȶ���ʱ����в���
		if (type == 1) {
			if (start) {
				if (M_BOARD[7][7] == 2) {
					cx = 7;
					cy = 7;

				} else {
					cx = 8;
					cy = 8;
				}
				System.out.println("start----");
				start = false;

				/* ��һ����Ӧ�ÿ�����cMakeMove() */
				M_BOARD[cx][cy] = 1;
				panel_board[cx][cy] = 1;
				cboardChange(cx, cy);// �������ݵı仯
			} else {
				// score = -10000;
				
				for (int i = 0; i < 15; i++) {// �������а������µĵ㣬�ҳ��÷����ģ�����
					for (int j = 0; j < 15; j++) {
						if (panel_board[i][j] == 2) {
							temp = getScore(type, i, j);
							if (temp > cscore) {
								cscore = temp;
								// score = temp;
								this.cx = i;// �����ڴ˴�����
								this.cy = j;
							}

						}
					}
				}
				System.out.println(cx + "   " + cy + "  " + cscore+"M_BOARD[][]="+M_BOARD[cx][cy]);
				System.out.println("����������������һ��");
				for (int i = 0; i < 15; i++) {// �������к������µĵ㣬�ҳ��÷���С�ģ�����
					for (int j = 0; j < 15; j++) {
						if (T_BOARD[i][j] == 2) {
							temp = getScore(0, i, j);
							if (temp < pscore) {
								pscore = temp;
								// score = temp;
								this.px = i;
								this.py = j;
							}

						}
					}
				}
				System.out.println("pscore:" + pscore + "cscore:" + cscore);
				if (-pscore > cscore) {// �������ڶ�����ʱ������������ĵ÷ֵľ���ֵ���ܴ��ڵ��������ֵ�������������ҵ�λ��
					// ������
					System.out.println("��������µ�λ��");

					/* cMakeMove() */
					M_BOARD[px][py] = 1;
					cboardChange(px, py);
					panel_board[px][py] = 1;
				} else {
					System.out.println("���Լ���λ��");
					M_BOARD[cx][cy] = 1;
					cboardChange(cx, cy);
					panel_board[cx][cy] = 1;
				}
			}
		}
		// if (type == 0) {
		// score = 10000;
		// for (int i = 0; i < 15; i++) {// �������к������µĵ㣬�ҳ��÷���С�ģ�����
		// for (int j = 0; j < 15; j++) {
		// if (M_BOARD[i][j] == 2) {
		// temp = getScore(type, i, j);
		// if (temp < score) {
		// score = temp;
		// this.temp_px = i;
		// this.temp_py = j;
		// }
		//
		// }
		//622909397510333712
		// }
		// }
		// T_BOARD[temp_px][temp_py] = 0;// ����ڴ�����
		// pboardChange(temp_px, temp_py);
		// }
		return score;
	}

	// int AlphaBeta(int depth, int alpha, int beta) {
	// int val;
	// if (depth == 0) {
	// return getScore();
	// ��}
	// ��GenerateLegalMoves();
	// while (MovesLeft()) {
	// ����MakeNextMove();
	// ����val = -AlphaBeta(depth - 1, -beta, -alpha);
	// ����UnmakeMove();
	// ����if (val >= beta) {
	// ������return beta;
	// ����}
	// ����if (val > alpha) {
	// ������alpha = val;
	// ����}
	// ��}
	// ��return alpha;
	// }

	/**
	 * �ڵ�ǰ��������һ���յ�λ������
	 * 
	 * @param x
	 * @param y
	 * @param nowboard
	 * @return
	 */
	public boolean searchBank(int x, int y, int[][] nowboard) {
		// boolean flag = false;
		if (nowboard[x][y] == 2) {
			return true;
		}
		return false;
	}

	public void MakeNextMove() {

	}

	public static void main(String[] args) {
		// for (int i = 0; i < 15; i++) {
		// for (int j = 0; j < 15; j++) {
		// System.out.print("  " + M_BOARD[i][j]);
		// }
		// System.out.println();
		// }
		// GlobalData gd = new GlobalData();
		// gd.pboardChange(2, 3);
		// System.out.println(P_TABLE[0][0][1]);
	}
}
