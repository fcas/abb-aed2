package BTREE;

public class NOB extends BTree {
	 public int mNumChaves = 0;
     public int[] mChaves = new int[2 * D - 1];
     public Object[] mObjects = new Object[2 * D - 1];
     public NOB[] mNosFilhos = new NOB[2 * D];
     public boolean mEhNoFolha;

}
