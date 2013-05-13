import java.util.Random;
import java.io.InputStream;

/**
 * Play a game of Boggle. To play code must instantiate the
 * BoggleGui gui object with a working IWordOnBoardFinder and
 * a working ILexicon, as well as a working IAutoPlayer.
 * @author Jesse Hu
 */

public class BoggleMain {

    public static void main(String[] args) {
    	BoggleBoardFactory.setRandom(new Random(11125));
        
        ILexicon lexicon = new SimpleLexicon();
        IWordOnBoardFinder finder = new GoodWordOnBoardFinder();
        System.out.println(BoggleBoardFactory.getRandom().toString());
        
        InputStream is = lexicon.getClass().getResourceAsStream("/ospd3.txt");      
        IAutoPlayer compPlayer = new BoardFirstAutoPlayer();
        BoggleGUI bgui = new BoggleGUI(lexicon,finder,is, compPlayer);
    }
}