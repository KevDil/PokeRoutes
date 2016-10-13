package model;

public enum CastleForm {

	KEEP		(PatternMatrix.Bergfried),

	AXE			(PatternMatrix.Axt),

	WORM		(PatternMatrix.Wurm),

	STAIRWAY 	(PatternMatrix.Treppe),

	PEACOCK		(PatternMatrix.Pfau),

	BARRACK		(PatternMatrix.Baracken),

	GOLDFISH	(PatternMatrix.Goldfisch),

	CACTUS 		(PatternMatrix.Kaktus),

	MONASTERY	(PatternMatrix.Kloster);
	
	private int[][] matrix;
	
	private CastleForm(int[][] matrix) {
		this.matrix=matrix;
	}
	
	public int[][] getMatrix(){
		return this.matrix;
	}
}
