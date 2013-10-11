package com.lsc.test;

public class MaxMinSearchTest {

	public static int[][] a = new int[10][10];
	public static int[][] b = new int[10][10];
	public int x = 0, y = 0;
	private int temp = 0;
	private int type = 1;
	public static int flag = 0;

	public void initData() {
		a[1][2] = 100;
		a[2][3] = -12;
		a[2][4] = -11;
		a[5][8] = 49;
		a[6][7] = -67;
		a[3][1] = 27;
		a[5][1] = 76;
		a[7][0] = -11;
		a[9][1] = -22;
		a[9][3] = 22;
		a[2][0] = -33;
		a[8][3] = 19;
		a[5][0] = 50;
		a[4][9] = -9;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				b[i][j] = a[i][j];
			}

		}
	}

	public void print() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(a[i][j] + "  ");
			}
			System.out.println();
			for (int k = 0; k < 10; k++) {
				System.err.print(b[i][k] + "  ");
			}
			System.out.println();
		}
	}

	public int getMax() {
		int temp = a[0][0];
		int i = 0, j = 0;
		for (i = 0; i < 10; i++) {
			for (j = 0; j < 10; j++) {
				if (a[i][j] > temp) {
					temp = a[i][j];
					this.x = i;
					this.y = j;
				}
			}
		}

		return temp;
	}

	public int getMin() {
		int temp = a[0][0];
		int i = 0, j = 0;
		for (i = 0; i < 10; i++) {
			for (j = 0; j < 10; j++) {
				if (a[i][j] < temp) {
					temp = a[i][j];
					this.x = i;
					this.y = j;
				}
			}
		}

		return temp;
	}

	public int getSocre(int x, int y) {
		return a[x][y];
	}

	public void moveData() {
		this.temp = a[x][y];
		System.out.println(a[x][y] + "   " + x + "  " + y);
		a[x][y] = flag++;
	}

	public void unMove() {
		// System.out.println("==========");
		// System.out.println("temp-->" + temp);
		a[x][y] = this.temp;
		// System.out.println("============");
	}

	public int maxSearch(int dept) {
		int maxValue = 0;
		int temp = 0;
		if (dept == 0) {
			maxValue = this.getMax();// 搜索到最后一个最大的值的时候直接用评估函数获得
		} else {
			moveData();// 相当于最大的已走，在递归完成前生成所有走法。所以应该用一个temp变量来保存原来的数据，在unMove的时候恢复到当前一步
			maxValue = this.getMax();
			temp = minSearch(dept - 1);// 递归调用下一步最小的值
			unMove();
			if (temp > maxValue) {
				maxValue = temp;
			}
			System.out.println("max:" + maxValue);
		}

		return maxValue;
	}

	public int minSearch(int dept) {
		int minValue = 0;
		int temp = 0;
		if (dept == 0) {
			minValue = this.getMin();// 搜索到最后一个最大的值的时候直接用评估函数获得
		} else {
			moveData();// 相当于最大的已走
			minValue = this.getMin();
			temp = maxSearch(dept - 1);// 递归调用下一步最大的值
			System.out.print("back");
			unMove();
			if (temp < minValue) {
				minValue = temp;
			}
			System.out.println("min:" + minValue);
		}

		return minValue;
	}

	public int max_min(int dept) {
		if (type == 1) {
			return maxSearch(dept - 1);
		} else {
			return minSearch(dept - 1);
		}
	}

	public static void main(String[] args) {
		MaxMinSearchTest mm = new MaxMinSearchTest();
		mm.initData();
		// System.out.println(mm.getMax());
		// System.out.println(mm.getMin());
		// mm.minSearch(10);
		mm.max_min(10);
		mm.print();
	}
}
