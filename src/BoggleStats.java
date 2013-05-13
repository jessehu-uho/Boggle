import java.io.InputStream;
import java.util.*;

import javax.swing.ProgressMonitorInputStream;

/**
 * Generates empirical analysis data to determine running times for each configuration
 * of the lexicon and word-finder implementation. 
 * 
 * @author Jesse Hu
 *
 */
public class BoggleStats {

    ArrayList<Integer> myBoardScores, myLexiconScores;
   
    private static final int MIN_WORD = 3;
    private static final int NUM_TRIALS = 10000;
    
    public BoggleStats(){
        myBoardScores = new ArrayList<Integer>();
        myLexiconScores = new ArrayList<Integer>();
    }
    
    
    public String wordTester(IAutoPlayer player, ILexicon lex, ArrayList<Integer> log, int count){
        
        BoggleBoardFactory.setRandom(new Random(12345));
        log.clear();
        double start = System.currentTimeMillis();
        int hiScore = 0;
        BoggleBoard hiBoard = null;
        for(int k=0; k < count; k++){
            BoggleBoard board = BoggleBoardFactory.getBoard(5);
            player.clear();
            player.findAllValidWords(board, lex, MIN_WORD);
            log.add(player.getScore()); 
            if(player.getScore()>hiScore){
            	hiScore = player.getScore();
            	hiBoard = board;
            }
        }
               
        double end = System.currentTimeMillis();
        int max = Collections.max(log);
        return String.format("%s %s\t count: %d\tmax: %d\ttime: %f" + hiBoard.toString(), 
               player.getClass().getName(),
               lex.getClass().getName(),count,max,(end-start)/1000.0);
    }
    
    public void doTests(ILexicon lex){
//        IAutoPlayer ap1 = new LexiconFirstAutoPlayer(); 
//        String result = wordTester(ap1,lex,myBoardScores,NUM_TRIALS);
//        System.out.println(result);
        IAutoPlayer ap2 = new BoardFirstAutoPlayer();
        String result2 = wordTester(ap2,lex,myLexiconScores, NUM_TRIALS);
        System.out.println(result2);
//        for(int k=0; k < NUM_TRIALS; k++) {
//            if (!myBoardScores.get(k).equals(myLexiconScores.get(k))){
//                System.out.println(k+"\t"+myBoardScores.get(k)+"\t"+myLexiconScores.get(k));
//            }
//        }
    }
    
    public void runTests(ILexicon lex, ArrayList<String> list){
        lex.load(list);
        doTests(lex);
    }
    
    public static void main(String[] args){
        ILexicon lex = new SimpleLexicon();   
        InputStream is = lex.getClass().getResourceAsStream("/ospd3.txt");   
        ProgressMonitorInputStream pmis = StoppableReader.getMonitorableStream(is, "reading..."); 
        Scanner s = new Scanner(pmis);
        ArrayList<String> list = new ArrayList<String>();
        while (s.hasNext()){
            list.add(s.next().toLowerCase());
        }
        BoggleStats bs = new BoggleStats();
        
        bs.runTests(lex,list);
        lex = new BinarySearchLexicon();
        bs.runTests(lex,list);
        lex = new TrieLexicon();
        bs.runTests(lex,list);
//        TrieLexicon trieLex = new TrieLexicon();
//        System.out.println(trieLex.nodeCount());
//        System.out.println(trieLex.oneWayCount());
//        CompressedTrieLexicon comTrieLex = new CompressedTrieLexicon();
//        System.out.println(comTrieLex.nodeCount());
//        System.out.println(comTrieLex.oneWayCount());
    }
}
