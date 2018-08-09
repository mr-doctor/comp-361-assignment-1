import java.util.ArrayList;
import java.util.List;

public class Tromino {

	// deficient parts of the board are represented by the X character, unfilled portions by a space,
	// 		filled by a character of the alphabet (one per tromino)
	private char[][] board;
	private char trominoIndex = 'a';

	int step = 0;

	private static final char EMPTY_TILE = ' ';
	private static final char DEFICIENT_TILE = 'X';

	public Tromino(int size, int x, int y) {
		board = new char[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = EMPTY_TILE;
			}
		}

		board[x][y] = DEFICIENT_TILE;
	}

	private void tile(int size, int xStart, int yStart) {

		// only one tromino will fit on this board
		if (size == 2) {
			// check every tile
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					// if the tile is not deficient and unfilled
					if (board[xStart + i][yStart + j] == EMPTY_TILE) {
						// fill in the board
						board[xStart + i][yStart + j] = trominoIndex;
					}
				}
			}
			// move onto the next character to fill in
			trominoIndex++;
		} else {

			int xDeficient = xStart;
			int yDeficient = yStart;

			// find the deficient tile
			for (int x = xStart; x < xStart + size; x++) {
				for (int y = yStart; y < yStart + size; y++) {
					if (board[x][y] != EMPTY_TILE) {
						xDeficient = x;
						yDeficient = y;
					}
				}
			}

			int newSize = size / 2;

			// check if deficient tile is in top left quad
			if (xDeficient < xStart + newSize && yDeficient < yStart + newSize) {

				tile(newSize, xStart, yStart); // tile(n/2, m1)

				board[xStart + newSize][yStart + newSize - 1] = trominoIndex;
				board[xStart + newSize][yStart + newSize] = trominoIndex;
				board[xStart + newSize - 1][yStart + newSize] = trominoIndex;

				trominoIndex++;

				// recurse into bottom left
				tile(newSize, xStart, yStart + newSize); // tile(n/2, m2)
				// recurse into top right
				tile(newSize, xStart + newSize, yStart); // tile(n/2, m3)
				// recurse into bottom right
				tile(newSize, xStart + newSize, yStart + newSize); // tile(n/2, m4)

				// check if deficient tile is in bottom left quad
			} else if (xDeficient < xStart + newSize && yDeficient >= yStart + newSize) {

				tile(newSize, xStart, yStart + newSize);

				board[xStart + newSize][yStart + newSize - 1] = trominoIndex;
				board[xStart + newSize - 1][yStart + newSize - 1] = trominoIndex;
				board[xStart + newSize][yStart + newSize] = trominoIndex;

				trominoIndex++;

				// recurse into top left
				tile(newSize, xStart, yStart); // tile(n/2, m2)
				// recurse into top right
				tile(newSize, xStart + newSize, yStart); // tile(n/2, m3)
				// recurse into bottom right
				tile(newSize, xStart + newSize, yStart + newSize); // tile(n/2, m4)

				// check if deficient tile is in top right quad
			} else if (xDeficient >= xStart + newSize && yDeficient < yStart + newSize) {

				tile(newSize, xStart + newSize, yStart); // tile(n/2, m1)

				board[xStart + newSize][yStart + newSize] = trominoIndex;
				board[xStart + newSize - 1][yStart + newSize] = trominoIndex;
				board[xStart + newSize - 1][yStart + newSize - 1] = trominoIndex;

				trominoIndex++;

				// recurse into top left
				tile(newSize, xStart, yStart); // tile(n/2, m2)
				// recurse into bottom left
				tile(newSize, xStart, yStart + newSize); // tile(n/2, m3)
				// recurse into bottom right
				tile(newSize, xStart + newSize, yStart + newSize); // tile(n/2, m4)

				// bottom right
			} else if (xDeficient >= xStart + newSize && yDeficient >= yStart + newSize) {

				tile(newSize, xStart + newSize, yStart + newSize);

				board[xStart + newSize][yStart + newSize - 1] = trominoIndex;
				board[xStart + newSize - 1][yStart + newSize - 1] = trominoIndex;
				board[xStart + newSize - 1][yStart + newSize] = trominoIndex;

				trominoIndex++;

				// recurse into top right
				tile(newSize, xStart + newSize, yStart); // tile(n/2, m3)
				// recurse into bottom left
				tile(newSize, xStart, yStart + newSize); // tile(n/2, m4)
				// recurse into top left
				tile(newSize, xStart, yStart); // tile(n/2, m2)

				// check if deficient tile is in top right quad
			}
		}
		System.out.println(step++);
		System.out.println(this + "\n");
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (char[] aBoard : board) {
			for (char anABoard : aBoard) {
				stringBuilder.append("[").append(anABoard).append("]");
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	public boolean distinct(List<Integer> a, int val) {
		List<Integer> aSorted = mergeSort(a, true);
		List<Integer> aPrime = new ArrayList<>();

		for (Integer anAscending : aSorted) {
			aPrime.add(val - anAscending);
		}
		aPrime = mergeSort(aPrime, true);

		for (int i = 0; i < aSorted.size(); i++) {
			if (aSorted.get(i).equals(aPrime.get(i))) {
				return true;
			}
		}
		return false;
	}

	public List<Integer> mergeSort(List<Integer> m, boolean ascending) {
		if (m.size() <= 1) {
			return m;
		}
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();
		for (int i = 0; i < m.size(); i++) {
			if (i < m.size() / 2) {
				left.add(m.get(i));
			} else {
				right.add(m.get(i));
			}
		}
		left = mergeSort(left, ascending);
		right = mergeSort(right, ascending);

		return merge(left, right, ascending);
	}

	public List<Integer> merge(List<Integer> left, List<Integer> right, boolean ascending) {
		List<Integer> output = new ArrayList<>();

		while (!left.isEmpty() && !right.isEmpty()) {
			if ((ascending) ? left.get(0) <= right.get(0) : left.get(0) >= right.get(0)) {
				output.add(left.remove(0));
			} else {
				output.add(right.remove(0));
			}
		}

		while (!left.isEmpty()) {
			output.add(left.remove(0));
		}
		while (!right.isEmpty()) {
			output.add(right.remove(0));
		}
		return output;
	}

	public static void main(String[] args) {
		int size = 8;

		Tromino t = new Tromino(size, 1, 1);
		t.tile(size, 0, 0);

	}
}
