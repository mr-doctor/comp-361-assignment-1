public class Tromino {

	// deficient parts of the board are represented by -1, unfilled portions by 0, filled by 1
	private int[][] board;

	public Tromino(int size, int x, int y) {

		board = new int[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = 0;
			}
		}

		board[y][x] = -1;
	}

	public void tile(int size, int xStart, int yStart) {
		// only one tromino will fit on this board
		if (size == 2) {
			// check every tile
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					// if the tile is not deficient
					if (board[xStart + i][yStart + j] >= 0) {
						// fill in the board
						board[xStart + i][yStart + j] = 1;
					}
				}
			}
		} else {

			int xDeficient = xStart;
			int yDeficient = yStart;

			for (int x = xStart; x < xStart + size; x++) {
				for (int y = yStart; y < yStart + size; y++) {
					if (board[x][y] < 0) {
						xDeficient = x;
						yDeficient = y;
					}
				}
			}

			// check if deficient tile is in top left quad
			if (xDeficient < xStart + size / 2 && yDeficient < yStart + size / 2) {

				tile(size / 2, xStart, yStart);

				board[xStart + size / 2][yStart + size / 2] = 1;
				board[xStart + size / 2][yStart + size / 2 - 1] = 1;
				board[xStart + size / 2 - 1][yStart + size / 2] = 1;

				// recurse into bottom left
				tile(size / 2, xStart, yStart + size / 2);
				// recurse into bottom right
				tile(size / 2, xStart + size / 2, yStart);
			}
		}
		print();
		System.out.println();

	}

	public void print() {

		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board[i].length; j++)
				System.out.print(board[i][j] + "\t");
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Tromino t = new Tromino(4, 1, 1);
		t.tile(4, 0, 0);

		t.print();
	}
}
