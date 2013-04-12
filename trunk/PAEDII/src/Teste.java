public class Teste {
	
	public static void main(String[] args) {
		ABB abb = new ABB();
		abb.inserir(4);
		abb.inserir(5);
		abb.print();
		System.out.println(abb.remove(5));
		abb.print();
	}
}
