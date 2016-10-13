package controller;

public class MatrixUtility {
	public static int[][] rotate(int castle[][]) {
		int l = castle.length;
		int rotatedCastle[][] = new int[l][l];

		for (int i = 0; i < l; i++) {
			int k = l;
			for (int j = 0; j < l; j++, k--) {
				rotatedCastle[i][j] = castle[k - 1][i];
			}

		}

		return rotatedCastle;
	}
	
	public static int[][] squareMatrix(int a[][]) {
		int w = a.length,
				h = a[0].length;
		
		if(h == w)
			return a;
		
		if(h > w) {
			int result[][] = new int[h][h];
			int diff = h - w;
			
			int leftFill = (diff / 2) + (diff%2);
			
			for(int i = 0; i < w; i++)
					for(int j = 0; j < h; j++) 
						result[i+leftFill][j] = a[i][j];
			
			return result;
			
		} else  { //w > h
			int result[][] = new int[w][w];
			int diff = w - h;
			
			int topFill = (diff / 2) + (diff%2);
			
			for(int i = 0; i < w; i++)
					for(int j = 0; j < h; j++) 
						result[i][j+topFill] = a[i][j];
			
			return result;
		}
	}

	public static boolean compareMatrix(int a[][], int b[][]) {
		try {
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a.length; j++) {
					if (a[i][j] != b[i][j])
						return false;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	public static boolean compareRotateMatrix(int a[][], int b[][]) {
		if (compareMatrix(a, b))
			return true;
		a = rotate(a);
		if (compareMatrix(a, b))
			return true;
		a = rotate(a);
		if (compareMatrix(a, b))
			return true;
		a = rotate(a);
		if (compareMatrix(a, b))
			return true;
		return false;
	}
}
