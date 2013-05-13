import java.util.List;
import java.util.ArrayList;

/**
 * Cannot find any words on a board. Useful for
 * debugging a game before a good implementation
 * is written.
 * @author Owen Astrachan
 * @author Jesse Hu
 *
 */
public class BadWordOnBoardFinder implements IWordOnBoardFinder {

    private ArrayList<BoardCell> myEmptyList = new ArrayList<BoardCell>();
    
    public List<BoardCell> cellsForWord(BoggleBoard board, String word) {

    	List<BoardCell> list = new ArrayList<BoardCell>();
    	for(int r=0; r < board.size(); r++){
    		for(int c=0; c < board.size(); c++){
    			int index = 0;
    			if (cellsForWordHelper(board,r,c,list,word,index)){ 
    				return list;
    			}
    		}
    	}
    	return myEmptyList;
    }
    
    public boolean cellsForWordHelper(BoggleBoard board, int r, int c, List<BoardCell> list, String word, int index){
    	if(index>=word.length()){ //Word found
    		return true;
    	}
    	if(r>=board.size()||c>=board.size()){ //Out of bounds
    		return false;
    	}
		BoardCell cell = new BoardCell(r, c);
    	if(list.contains(cell)){ //Character visited
    		return false;
    	}
    	if(board.getFace(r,c).charAt(0)==word.charAt(index)){
    		list.add(cell); //Remember cell
    		if(board.getFace(r,c).charAt(0)=='Q'){
    			r = r+1; //Case for 'Qu'
    		}
    		if(cellsForWordHelper(board, r+1, c, list, word, index+1)||cellsForWordHelper(board, r+1, c+1, list, word, index+1)||
    				cellsForWordHelper(board, r, c+1, list, word, index+1)||cellsForWordHelper(board, r-1, c, list, word, index+1)||
    				cellsForWordHelper(board, r-1, c-1, list, word, index+1)||cellsForWordHelper(board, r, c-1, list, word, index+1)||
    				cellsForWordHelper(board, r+1, c-1, list, word, index+1)){
    			return true;
    		}
    		list.remove(list.size()-1);
    	}
    	return false;
    }

}
