package com.lsc.data;

public final class GlobalData {
	// 表示棋盘的二维数组，0表示黑子(玩家)，1表示白子(计算机)，2表示没有棋子。
	public static int[][] M_BOARD = new int[15][15];
	public static int[][] T_BOARD = new int[15][15];// 用来搜索下棋。电脑每一步棋的时候要用来，用来保存下几步的走法。

	// P_TABLE[x][y][k]为true表示[x][y]位置是玩家第k个"5"(获胜组合)中的一个位置。
	public static boolean[][][] P_TABLE = new boolean[15][15][572];
	private static boolean[][][] PTABLE = new boolean[15][15][572];
	// C_TABLE[x][y][k]为true表示[x][y]位置是计算机第k个"5"(获胜组合)中的一个位置。
	public static boolean[][][] C_TABLE = new boolean[15][15][572];
	public static boolean[][][] CTABLE = new boolean[15][15][572];
	// win[0][k]表示玩家在第k个“5”的位置往其方向有win[0][k]个连续的黑棋
	// win[1][k]表示计算机在第k个“5”的位置往右有win[1][k]连续的白棋
	// 取值为9则表示第k个"5"的位置被封死了，没有成5的可能
	// 初始为0
	private int cx, cy;// 电脑下棋的位置
	private int px, py;// 人下棋的位置

	public static int[][] WIN = new int[2][572];
	public static int[][] T_WIN = new int[2][572];
	public static boolean start = true;

	static {// 初始化所有数据
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

		for (int x = 0; x < 15; x++) // x坐标相同的交叉点
		{
			for (int y = 0; y < 11; y++) {
				for (int k = 0; k < 5; k++) {
					// 对玩家来说，第count个能成5的位置为[x][y+k]
					P_TABLE[x][y + k][count] = true;
					C_TABLE[x][y + k][count] = true;
					PTABLE[x][y + k][count] = true;
					CTABLE[x][y + k][count] = true;
				}
				count++;
			}
		}
		for (int y = 0; y < 15; y++) // y坐标相同的交叉点
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

		for (int x = 0; x < 11; x++) // 主对角线╲
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
		for (int x = 4; x < 15; x++) // 次对角线╱
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
	 * 玩家在x,y点上下棋后，各个数据的变化
	 * 
	 * @param x
	 * @param y
	 */
	public void pboardChange(int x, int y) {
		int i = 0, j = 0;
		for (int k = 0; k < 572; k++) // 修改玩家下子后棋盘状态的变化
		{
			// 如果[x][y]位置是玩家第k个"5"中的位置，且第k个"5"没有被封死

			if (P_TABLE[x][y][k] && WIN[0][k] != 9) {
				WIN[0][k]++;
				// System.out.println("玩家连子数：" + WIN[0][k]);
				i++;
			}
			// 如果[x][y]位置是计算机第k个"5"中的位置，则玩家下子后第k个"5"被封死
			if (C_TABLE[x][y][k]) // ○●○○○
			{
				C_TABLE[x][y][k] = false;
				WIN[1][k] = 9;
				j++;
			}
		}
		System.out.println(i + "  " + j);

	}

	/**
	 * 电脑下棋的临时操作。
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
	 * 电脑在x,y点上下棋后，各个数据的变化
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
	 * 在(x,y)点下棋后的得分，计算机得分为正，玩家得分为负
	 * 
	 * @param type
	 *            计算机下为1，玩家下为0;
	 * @param x
	 * @param y
	 * @return
	 */
	public int getScore(int type, int x, int y) {
		int score = 0;
		for (int k = 0; k < 572; k++) {
			if (type == 1) {// 计算机下，得分为正
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
			} else {// 玩家下棋 type==0时
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
	 * 在每一步计算前把原先的棋盘复制到临时棋盘中去
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
	 * 评估函数，遍历所有的能下的点。白子返回得分最大的点，黑子返回最小的点。
	 * 
	 * @param type
	 * @return
	 */
	public int evaluateScore(int type, int[][] panel_board) {
		int score = 0, cscore = -10000, pscore = 10000;
		int temp = 0;
		// copyBoard();// 把数据复制到临时表，先对临时表进行操作
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

				/* 这一部分应该可以是cMakeMove() */
				M_BOARD[cx][cy] = 1;
				panel_board[cx][cy] = 1;
				cboardChange(cx, cy);// 其它数据的变化
			} else {
				// score = -10000;
				
				for (int i = 0; i < 15; i++) {// 遍历所有白子能下的点，找出得分最大的，返回
					for (int j = 0; j < 15; j++) {
						if (panel_board[i][j] == 2) {
							temp = getScore(type, i, j);
							if (temp > cscore) {
								cscore = temp;
								// score = temp;
								this.cx = i;// 电脑在此处下子
								this.cy = j;
							}

						}
					}
				}
				System.out.println(cx + "   " + cy + "  " + cscore+"M_BOARD[][]="+M_BOARD[cx][cy]);
				System.out.println("向下搜索玩家下棋的一步");
				for (int i = 0; i < 15; i++) {// 遍历所有黑子能下的点，找出得分最小的，返回
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
				if (-pscore > cscore) {// 搜索到第二步的时候，如果玩家下棋的得分的绝对值总能大于电脑下棋的值。则电脑在下玩家的位置
					// 即防守
					System.out.println("下玩家想下的位置");

					/* cMakeMove() */
					M_BOARD[px][py] = 1;
					cboardChange(px, py);
					panel_board[px][py] = 1;
				} else {
					System.out.println("下自己的位置");
					M_BOARD[cx][cy] = 1;
					cboardChange(cx, cy);
					panel_board[cx][cy] = 1;
				}
			}
		}
		// if (type == 0) {
		// score = 10000;
		// for (int i = 0; i < 15; i++) {// 遍历所有黑子能下的点，找出得分最小的，返回
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
		// T_BOARD[temp_px][temp_py] = 0;// 玩家在此下棋
		// pboardChange(temp_px, temp_py);
		// }
		return score;
	}

	// int AlphaBeta(int depth, int alpha, int beta) {
	// int val;
	// if (depth == 0) {
	// return getScore();
	// 　}
	// 　GenerateLegalMoves();
	// while (MovesLeft()) {
	// 　　MakeNextMove();
	// 　　val = -AlphaBeta(depth - 1, -beta, -alpha);
	// 　　UnmakeMove();
	// 　　if (val >= beta) {
	// 　　　return beta;
	// 　　}
	// 　　if (val > alpha) {
	// 　　　alpha = val;
	// 　　}
	// 　}
	// 　return alpha;
	// }

	/**
	 * 在当前的棋盘找一个空的位置下棋
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
