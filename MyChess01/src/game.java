
public class game {
	
	/*
	 * 1: jiang
	 * 2: shi
	 * 3: xiang
	 * 4: ma
	 * 5: che
	 * 6: pao
	 * 7: bing
	 * */
//	piece blackRemain[] = new piece[16];
//	piece redRemain[] = new piece[16];
	
	private int MAX_ITER = 4;
	
	private int AIpx = 0;
	private int AIpy = 0;
	private int AItx = 0;
	private int AIty = 0;
	
	private int pieceValue[] = {
		1000, 50, 50, 55, 65, 60, 45	
	};
	private int pieceMoveOption[] = {
			4, 4, 4, 8, 34, 34, 4
	};
	private int pieceMovex[][] = {
			{0, 0, 1, -1},
			{1, 1, -1, -1},
			{2, 2, -2, -2},
			{1, 1, 2, 2, -1, -1, -2, -2},
			{1, 2, 3, 4, 5, 6, 7, 8, -1, -2, -3, -4, -5, -6, -7, -8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 2, 3, 4, 5, 6, 7, 8, -1, -2, -3, -4, -5, -6, -7, -8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, -1}
	};
	private int pieceMovey[][] = {
			{1, -1, 0, 0},
			{1, -1, 1, -1},
			{2, -2, 2, -2},
			{2, -2, 1, -1, 2, -2, 1, -1},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, -3, -4, -5, -6, -7, -8, -9},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, -3, -4, -5, -6, -7, -8, -9},
			{1, -1, 0, 0}
	};
	
	public game() {
		
	}
	
	public static final int defBoard[][] = {
			{ 5,  4,  3,  2,  1,  2,  3,  4,  5},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  6,  0,  0,  0,  0,  0,  6,  0},
			{ 7,  0,  7,  0,  7,  0,  7,  0,  7},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-7,  0, -7,  0, -7,  0, -7,  0, -7},
			{ 0, -6,  0,  0,  0,  0,  0, -6,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-5, -4, -3, -2, -1, -2, -3, -4, -5}
	};
	public int gameBoard[][] = {
			{ 5,  4,  3,  2,  1,  2,  3,  4,  5},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  6,  0,  0,  0,  0,  0,  6,  0},
			{ 7,  0,  7,  0,  7,  0,  7,  0,  7},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-7,  0, -7,  0, -7,  0, -7,  0, -7},
			{ 0, -6,  0,  0,  0,  0,  0, -6,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-5, -4, -3, -2, -1, -2, -3, -4, -5}
	};
	
	public boolean selectPiece(int px, int py) {
		if(gameBoard[py][px] < 0) {
			return true;
		}
		else return false;
	}
	
	public int getPieceSelected() {
		return AIpy * 9 + AIpx;
	}
	public int getPlacePlaced() {
		return AIty * 9 + AItx;
	}
	
	public void AIMakeMove() {
		int v = maxValue(-100000, 100000, MAX_ITER);
//		System.out.println(v);
		
//		System.out.println(AItx + " " + AIty);
//		System.out.println(AIpx + " " + AIpy);
		
		gameBoard[AIty][AItx] = gameBoard[AIpy][AIpx];
		gameBoard[AIpy][AIpx] = 0;
		System.out.println("current: " + evaluation(gameBoard));
//		for(int i = 0; i < 10; i++) {
//			for(int j = 0; j < 9; j++) {
//				System.out.print(gameBoard[i][j] + "\t");
//			}
//			System.out.println();
//		}
	}
	
	private int isGameOver() {
		boolean bk = false;
		boolean rk = false;
		for(int i = 0; i < 3; i++) {
			for(int j = 3; j < 6; j++) {
				if(gameBoard[i][j] == 1) {
					bk = true;
					break;
				}
			}
			if(bk) break;
		}
		for(int i = 7; i < 10; i++) {
			for(int j = 3; j < 6; j++) {
				if(gameBoard[i][j] == -1) {
					rk = true;
					break;
				}
			}
			if(rk) break;
		}
		if(rk == false) return -1;
		else if(bk == false) return 1;
		else return 0;
	}
	
	private int maxValue(int alpha, int beta, int maxIteration) {
		if(maxIteration == 0 || terminal(gameBoard)) return evaluation(gameBoard);
		int v = -100000;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if(gameBoard[i][j] > 0) {
					for(int e = 0; e < pieceMoveOption[gameBoard[i][j] - 1]; e++) {
						int tx = j + pieceMovex[gameBoard[i][j] - 1][e];
						int ty = i + pieceMovey[gameBoard[i][j] - 1][e];
						
						if(tx < 0 || tx > 8 || ty < 0 || ty > 9) continue;
						int temp = gameBoard[ty][tx];
						if(gameBoard[ty][tx] <= 0) {
							if(checkMoveLegitimacy(gameBoard[i][j], j, i, tx, ty)) {
								gameBoard[ty][tx] = gameBoard[i][j];
								gameBoard[i][j] = 0;
								
								if(isGameOver() != 0) {
									v = evaluation(gameBoard);
									gameBoard[i][j] = gameBoard[ty][tx];
									gameBoard[ty][tx] = temp;
									return v;
								}
								
								int tv = minValue(alpha, beta, maxIteration - 1);
								
								gameBoard[i][j] = gameBoard[ty][tx];
								gameBoard[ty][tx] = temp;
								
								//System.out.println("tv: " + tv);
								if(tv > v) {
									v = tv;
									if(maxIteration == MAX_ITER) {
										AIpx = j;
										AIpy = i;
										AItx = tx;
										AIty = ty;
									}
								}
								
								if(tv >= beta) return v;
								if(tv > alpha) alpha = tv;
							}
						}
					}
				}
			}
		}
		return v;
	}
	
	private int minValue(int alpha, int beta, int maxIteration) {
		if(maxIteration == 0 || terminal(gameBoard)) return evaluation(gameBoard);
		int v = 100000;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if(gameBoard[i][j] < 0) {
					for(int e = 0; e < pieceMoveOption[-gameBoard[i][j] - 1]; e++) {
						
						int tx = j + pieceMovex[-gameBoard[i][j] - 1][e];
						int ty = i + pieceMovey[-gameBoard[i][j] - 1][e];
						
						if(tx < 0 || tx > 8 || ty < 0 || ty > 9) continue;
						int temp = gameBoard[ty][tx];
						if(gameBoard[ty][tx] >= 0) {
							if(checkMoveLegitimacy(-gameBoard[i][j], j, i, tx, ty)) {
								gameBoard[ty][tx] = gameBoard[i][j];
								gameBoard[i][j] = 0;
								int tv = maxValue(alpha, beta, maxIteration - 1);
								
								gameBoard[i][j] = gameBoard[ty][tx];
								gameBoard[ty][tx] = temp;
							
								if(tv < v) v = tv;
								if(tv <= alpha) return v;
								if(tv < beta) beta = tv;
							}
						}
						
					}
				}
			}
		}
		return v;
	}
	
	public int playerMove(int piece, int plx, int ply) {
		int px = (piece) % 9;
		int py = (piece) / 9;
		if(gameBoard[ply][plx] >= 0) {
			if(checkMoveLegitimacy(-gameBoard[py][px], px, py, plx, ply)) {
				gameBoard[ply][plx] = gameBoard[py][px];
				gameBoard[py][px] = 0;
				return 1;
			}
			else return 0;
		}
		else if(gameBoard[ply][plx] < 0) {
			return 2;
		}
		else return 0;
	}
	
	private boolean terminal(int board[][]) {
		int bk = 0;
		int rk = 0;
		for(int i = 0 ; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if(board[i][j] == 1) bk++;
				else if(board[i][j] == -1) rk++;
				
			}
		}
		if(bk == 1 && rk == 1) return false;
		else return true;
	}
	
	private boolean checkIfGeneralAlign() {
		
		for(int i = 0; i < 3; i++) {
			for(int j = 3; j < 6; j++) {
				if(gameBoard[i][j] == 1) {
					for(int k = 1; k < 10; k++) {
						if(i + k > 9) {
							return false;
						}
						else if(gameBoard[i + k][j] == -1) {
							return true;
						}
						else if(gameBoard[i + k][j] != 0) {
							return false;
						}
						else {
							continue;
						}
					}
					return false;
				}
			}
		}
		
		return false;
	}
	
	private boolean checkIfPieceUnderAtt(int p, int px, int py) {
		int res = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if((gameBoard[i][j] < 0 && p < 0) || (gameBoard[i][j] > 0 && p > 0)) {
					if(checkMoveLegitimacy(Math.abs(gameBoard[i][j]), px, py, j, i)) {
						res--;
					}
				}
				if((gameBoard[i][j] < 0 && p > 0) || (gameBoard[i][j] > 0 && p < 0)) {
					if(checkMoveLegitimacy(Math.abs(gameBoard[i][j]), px, py, j, i)) {
						res++;
					}
				}
			}
		}
		if(res > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private int evaluation(int board[][]) {
		
		int res = 0;
		int redPower = 0;
		int blackPower = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if(board[i][j] != 0) {
					
					int currentPiece = Math.abs(board[i][j]);
					int tempRes = 0;
					if(currentPiece != 1) {
						if(!checkIfPieceUnderAtt(board[i][j], j, i)) {
							tempRes += pieceValue[currentPiece - 1];
						}
					}
					else {
						tempRes += pieceValue[currentPiece - 1];
					}
//					for(int e = 0; e < pieceMoveOption[currentPiece - 1]; e++) {
//						int tx = j + pieceMovex[currentPiece - 1][e];
//						int ty = i + pieceMovey[currentPiece - 1][e];
//						if(tx < 0 || tx > 8 || ty < 0 || ty > 9) continue;
//						if(!checkMoveLegitimacy(currentPiece, j, i, tx, ty)) continue;
//						if(board[ty][tx] == 0) 
//							tempRes += 1;
//					}
										
					if(board[i][j] > 0) blackPower += tempRes;
					if(board[i][j] < 0) redPower += tempRes;
				}
			}
		}
		res = blackPower - redPower;
		return res;
	}
	
	private boolean checkMoveLegitimacy(int p, int px, int py, int tx, int ty) {
		int temp = gameBoard[ty][tx];
		gameBoard[ty][tx] = gameBoard[py][px];
		gameBoard[py][px] = 0;
		if(checkIfGeneralAlign()) {
			gameBoard[py][px] = gameBoard[ty][tx];
			gameBoard[ty][tx] = temp;
			return false;
		}
		gameBoard[py][px] = gameBoard[ty][tx];
		gameBoard[ty][tx] = temp;
		switch(p) {
		case 1:
			if(Math.abs(tx - px) + Math.abs(ty - py) == 1 && tx >= 3 && tx <= 5 && ((ty >= 7 && ty <= 9) || (ty >= 0 && ty <= 2))) {
				return true;
			}
			else return false;
		case 2:
			if(Math.abs(tx - px) == 1 && Math.abs(ty - py) == 1 && tx >= 3 && tx <= 5 && ((ty >= 7 && ty <= 9) || (ty >= 0 && ty <= 2))) {
				return true;
			}
			else return false;
		case 3:
			if(py <= 4) {
				if(ty <= 4 && Math.abs(tx - px) == 2 && Math.abs(ty - py) == 2 && gameBoard[(ty - py) / 2 + py][(tx - px) / 2 + px] == 0) {
					return true;
				}
				else return false;
			}
			else {
				if(ty >= 5 && Math.abs(tx - px) == 2 && Math.abs(ty - py) == 2 && gameBoard[(ty - py) / 2 + py][(tx - px) / 2 + px] == 0) {
					return true;
				}
				else return false;
			}
		case 4:
			if(Math.abs(tx - px) == 2 && Math.abs(ty - py) == 1) {
				if(gameBoard[py][(tx - px) / 2 + px] == 0) return true;
				else return false;
			}
			if(Math.abs(tx - px) == 1 && Math.abs(ty - py) == 2) {
				if(gameBoard[(ty - py) / 2 + py][px] == 0) return true;
				else return false;
			}
		case 5:
			if(tx - px == 0) {
				int st = Math.min(ty, py);
				int en = Math.max(ty, py);
				for(int i = st + 1; i < en; i++) {
					if(gameBoard[i][px] != 0) return false;
				}
				return true;
			}
			else if(ty - py == 0) {
				int st = Math.min(tx, px);
				int en = Math.max(tx, px);
				for(int i = st + 1; i < en; i++) {
					if(gameBoard[py][i] != 0) return false;
				}
				return true;
			}
		case 6:
			if(gameBoard[ty][tx] == 0) {
				if(tx - px == 0) {
					int st = Math.min(ty, py);
					int en = Math.max(ty, py);
					for(int i = st + 1; i < en; i++) {
						if(gameBoard[i][px] != 0) return false;
					}
					return true;
				}
				else if(ty - py == 0) {
					int st = Math.min(tx, px);
					int en = Math.max(tx, px);
					for(int i = st + 1; i < en; i++) {
						if(gameBoard[py][i] != 0) return false;
					}
					return true;
				}
			}
			else {
				if(tx - px == 0) {
					int st = Math.min(ty, py);
					int en = Math.max(ty, py);
					int cnt = 0;
					for(int i = st + 1; i < en; i++) {
						if(gameBoard[i][px] != 0) cnt++;
					}
					if(cnt == 1)
						return true;
					else return false;
				}
				else if(ty - py == 0) {
					int st = Math.min(tx, px);
					int en = Math.max(tx, px);
					int cnt = 0;
					for(int i = st + 1; i < en; i++) {
						if(gameBoard[py][i] != 0) cnt++;
					}
					if(cnt == 1)
						return true;
					else return false;
				}
			}
		case 7:
			if(gameBoard[py][px] < 0) {
				if(py >= 5) {
					if(tx == px && ty - py == -1) return true;
					else return false;
				}
				else {
					if((Math.abs(tx - px) == 1 && ty - py == 0) || (tx == px && ty - py == -1)) return true;
					else return false;
				}
			}
			else {
				if(py <= 4) {
					if(tx == px && ty - py == 1) return true;
					else return false;
				}
				else {
					if((Math.abs(tx - px) == 1 && ty - py == 0) || (tx == px && ty - py == 1)) return true;
					else return false;
				}
			}
		default:
			break;
		}
			
		return false;
	}
	
}
