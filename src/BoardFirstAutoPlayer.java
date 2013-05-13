import java.util.ArrayList;
import java.util.List;

/**
 * Implements AbstractAutoPlayer using a board-first approach.
 * Iterates through all possible neighboring cells, checking for valid words using the ILexicon.
 * @author Jesse Hu
 *
 */
public class BoardFirstAutoPlayer extends AbstractAutoPlayer {

	private IWordOnBoardFinder myFinder;

	public BoardFirstAutoPlayer(){
		myFinder = new GoodWordOnBoardFinder();
	}

	@Override
	public void findAllValidWords(BoggleBoard board, ILexicon lex, int minLength) {
		clear();
		List<BoardCell> list = new ArrayList<BoardCell>();
		for(int r=0; r < board.size(); r++){
			for(int c=0; c < board.size(); c++){
				StringBuilder s = new StringBuilder();
				findAllValidWordsHelper(board,r,c,list,s,lex,minLength);
			}
		}
	}
	public void findAllValidWordsHelper(BoggleBoard board, int r, int c, List<BoardCell> list, StringBuilder s, ILexicon lex, int minLength){
		if(r<0||r>=board.size()||c<0||c>=board.size()){ //Out of bounds
			return;
		}
		BoardCell cell = new BoardCell(r, c);
		if(list.contains(cell)){ //Character visited
			return;
		}
		s.append(board.getFace(r, c));
		if(lex.wordStatus(s)==LexStatus.WORD||lex.wordStatus(s)==LexStatus.PREFIX){
			if(lex.wordStatus(s)==LexStatus.WORD&&s.length()>=minLength){
				add(s.toString());
			}
			list.add(cell); //Remember cell
			findAllValidWordsHelper(board, r+1, c, list, s, lex, minLength);
			findAllValidWordsHelper(board, r+1, c-1, list, s, lex, minLength);
			findAllValidWordsHelper(board, r, c-1, list, s, lex, minLength);
			findAllValidWordsHelper(board, r-1, c-1, list, s, lex, minLength);
			findAllValidWordsHelper(board, r-1, c, list, s, lex, minLength);
			findAllValidWordsHelper(board, r-1, c+1, list, s, lex, minLength);
			findAllValidWordsHelper(board, r, c+1, list, s, lex, minLength);
			findAllValidWordsHelper(board, r+1, c+1, list, s, lex, minLength);
			list.remove(cell); //Remove cell
		}
		if(board.getFace(r,c).length()>1) // "Qu" case handles both characters
			s.deleteCharAt(s.length()-1);
		s.deleteCharAt(s.length()-1);
	}
}
