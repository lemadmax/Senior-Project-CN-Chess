
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
	
	private boolean checkMoveLegitimacy(int p, int px, int py, int tx, int ty) {
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
