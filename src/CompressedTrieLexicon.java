import java.util.ArrayList;

/**
 * Implements ILexicon using a trie with method to "compress" the branches when possible.
 * Uses and implements wordStatus to assist with compression.
 * 
 * @author Jesse Hu
 */
public class CompressedTrieLexicon extends TrieLexicon {
	
	public CompressedTrieLexicon() {
		myRoot = new Node('x', null);
		mySize = 0;
	}
	 
	@Override
	public void load(ArrayList<String> list) {
		super.load(list);
		compress();

	}

	public void compress(){
		compressHelper(myRoot);

	}
	public void compressHelper(Node root){
		if(root==null){
			return;
		}
		if(root.children.size()>0){
			for(Node child:root.children.values()){
				compressHelper(child);
			}
		}
		if(root.children.isEmpty()){
			if(root.parent.children.size()==1&&!root.parent.isWord){
				root.parent.info = root.parent.info + root.info;
				root.parent.children.clear();
				root.parent.isWord = true;
			}
		}
	}

	@Override
	public LexStatus wordStatus(StringBuilder s){
		Node t = myRoot;
		for (int k = 0; k < s.length(); k++) {
			if(t.info.length()>1){
				if(s.indexOf(t.info)==s.length()-t.info.length()&&s.length()>=t.info.length()){
					return LexStatus.WORD;
				}
				if(t.info.startsWith(s.substring(k-1,s.length()))){
					return LexStatus.PREFIX;
				}
				return LexStatus.NOT_WORD;
			}
			char ch = s.charAt(k);
			t = t.children.get(ch);
			if (t == null)
				return LexStatus.NOT_WORD; // no path below? done
		}
		return t.isWord ? LexStatus.WORD : LexStatus.PREFIX; 
	}
	@Override
	public LexStatus wordStatus(String s) {
		return wordStatus(new StringBuilder(s));
	}
	
	
}