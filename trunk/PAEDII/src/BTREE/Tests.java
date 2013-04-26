package BTREE;

public class Tests {
    public static void test1(B bTree) throws Exception {
            System.out.println("Inserindo chaves 1,2,3,4,5,6,7,8. Nessa ordem...");
    		bTree.inserir(1, "1");
            bTree.inserir(2, "2");
            bTree.inserir(3, "3");
            bTree.inserir(4, "4");
            bTree.inserir(5, "5");
            bTree.inserir(6, "6");
            bTree.inserir(7, "7");
            bTree.inserir(8, "8");              
            System.out.println(bTree.toString());
            System.out.println("Buscando a chave 3...");
            System.out.println(bTree.procurarChave(3));
            System.out.println("Buscando a chave 8...");
            System.out.println(bTree.procurarChave2(8));
            System.out.println("Removendo a chave 8...");
            bTree.remover(8);
            System.out.println(bTree.toString());
            System.out.println("Removendo a chave 5...");
            bTree.remover(5);
            System.out.println(bTree.toString());
            System.out.println("Inserindo a chave 5...");
            bTree.inserir(5, "5");
            System.out.println("Imprimindo...");
            System.out.println(bTree.toString());
            System.out.println("Removendo a chave 5...");
            bTree.remover(5);
    }
    
    public static void test2(B bTree) throws Exception {
            int numeros[] = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
                            73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179 };
            
            System.out.println("Inserindo a chaves: 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179. Nessa ordem ");
            
            for (int i = 0; i < numeros.length; i++) {
                    bTree.inserir(numeros[i], String.valueOf(numeros[i]));
            }
            for (int i = 0; i < numeros.length; i++) {
                    String value = String.valueOf(numeros[i]);
                    Object searchResult = (Object) bTree.procurarChave(numeros[i]);                   
                    if (!value.equals(searchResult)) {
                            System.out.println("Oops: Chave" + numeros[i] + " objeto retornado " + searchResult);
                    }
            }
            
            System.out.println("Removendo as chaves 17, 42, 131, 5...verificando se a árvore B continua válida...");
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
            
            for (int i = 0; i < numeros.length; i++) {
                    bTree.inserir(numeros[i], String.valueOf(numeros[i]));
            }               
            for (int i = 0; i < numeros.length; i++) {
                    String value = String.valueOf(numeros[i]);
                    Object buscaResultado = (Object) bTree.procurarChave(numeros[i]);                   
                    if (!value.equals(buscaResultado)) {
                            System.out.println("Oops: Chave " + numeros[i] + " objeto retornado " + buscaResultado);
                    }
            }
            
            for (int i = numeros.length - 1; i >= 0; i--) {                    
                    bTree.remover(numeros[i]);                  
                    bTree.arvoreBValida();                       
            }
    }
    
    public static void test3(B bTree) throws Exception {
            for (int i = 0; i < 1000; i++) {
                    bTree.inserir(i, String.valueOf(i));
            }
            
            System.out.println("Buscando a chave 777...");
            System.out.println("Chave encontrada: " + bTree.procurarChave(777));
            System.out.println("Removendo a chave 777...");
            bTree.remover(777);
            bTree.arvoreBValida();
            System.out.println(bTree.toString());
            System.out.println("Removendo a chave 511...");
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
                    System.out.println("Teste 1 ok!");
            } catch (Exception e1) {
                    System.out.println("Teste 1 falhou!");
            }
            System.out.println();
            
            System.out.println("Teste 2:");
            bTree = new B();
            try {
                    test2(bTree);
                    System.out.println("Teste 2 ok!");
            } catch (Exception e) {
                    System.out.println("Teste 2 falhou!");
            }
            System.out.println();
            
            System.out.println("Teste 3:");
            try {
                    test3(bTree);
                    System.out.println("Teste 3 ok!");
            } catch (Exception e) {
                    System.out.println("Teste 3 falhou!");
            }
            System.out.println();
    }
}