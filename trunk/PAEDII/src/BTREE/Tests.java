package BTREE;

public class Tests {
    public static void test1(B bTree) throws Exception {
            bTree.inserir(1, "1");
            bTree.inserir(2, "2");
            bTree.inserir(3, "3");
            bTree.inserir(4, "4");
            bTree.inserir(5, "5");
            bTree.inserir(6, "6");
            bTree.inserir(7, "7");
            bTree.inserir(8, "8");              
            System.out.println(bTree.toString());
            System.out.println(bTree.procurarChave(3));
            System.out.println(bTree.procurarChave2(8));
            bTree.remover(8);
            System.out.println(bTree.toString());
            bTree.remover(5);
            System.out.println(bTree.toString());
            bTree.inserir(5, "5");
            System.out.println(bTree.toString());
            bTree.remover(5);
    }
    
    public static void test2(B bTree) throws Exception {
            int primeNumbers[] = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
                            73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179 };
            
            for (int i = 0; i < primeNumbers.length; i++) {
                    bTree.inserir(primeNumbers[i], String.valueOf(primeNumbers[i]));
            }
            for (int i = 0; i < primeNumbers.length; i++) {
                    String value = String.valueOf(primeNumbers[i]);
                    Object searchResult = (Object) bTree.procurarChave(primeNumbers[i]);                   
                    if (!value.equals(searchResult)) {
                            System.out.println("Oops: Key " + primeNumbers[i] + " retrieved object " + searchResult);
                    }
            }
            
            bTree.remover(17);
            bTree.arvoreBValida();
            bTree.remover(42);
            bTree.arvoreBValida();
            bTree.remover(131);
            bTree.arvoreBValida();
            bTree.remover(5);
            bTree.arvoreBValida();
            
            for (int i = 77; i >= 0; i--) {                 
                    bTree.remover(i);                        
                    bTree.arvoreBValida();                       
            }
            
            for (int i = 0; i < primeNumbers.length; i++) {
                    bTree.inserir(primeNumbers[i], String.valueOf(primeNumbers[i]));
            }               
            for (int i = 0; i < primeNumbers.length; i++) {
                    String value = String.valueOf(primeNumbers[i]);
                    Object searchResult = (Object) bTree.procurarChave(primeNumbers[i]);                   
                    if (!value.equals(searchResult)) {
                            System.out.println("Oops: Key " + primeNumbers[i] + " retrieved object " + searchResult);
                    }
            }
            
            for (int i = primeNumbers.length - 1; i >= 0; i--) {                    
                    bTree.remover(primeNumbers[i]);                  
                    bTree.arvoreBValida();                       
            }
    }
    
    public static void test3(B bTree) throws Exception {
            for (int i = 0; i < 1000; i++) {
                    bTree.inserir(i, String.valueOf(i));
            }
            
            System.out.println(bTree.procurarChave(777));
            bTree.remover(777);
            bTree.arvoreBValida();
            System.out.println(bTree.toString());
            bTree.remover(511);
            bTree.arvoreBValida();
            System.out.println(bTree.toString());
            
            for (int i = 1000; i >= 0; i--) {
                    bTree.remover(i);                        
                    bTree.arvoreBValida();                       
            }
    }
    
    public static void main(String[] args) {
            B bTree = new B();
            
            System.out.println("Test 1:");
            try {
                    test1(bTree);
                    System.out.println("Test 1 ok!");
            } catch (Exception e1) {
                    System.out.println("Test 1 failed!");
            }
            System.out.println();
            
            System.out.println("Test 2:");
            bTree = new B();
            try {
                    test2(bTree);
                    System.out.println("Test 2 ok!");
            } catch (Exception e) {
                    System.out.println("Test 2 failed!");
            }
            System.out.println();
            
            System.out.println("Test 3:");
            try {
                    test3(bTree);
                    System.out.println("Test 3 ok!");
            } catch (Exception e) {
                    System.out.println("Test 3 failed!");
            }
            System.out.println();
    }
}