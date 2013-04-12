import exceptions.PredecessorNotFoundException;
import exceptions.SuccessorNotFoundException;

public class Teste {
	
	public static void main(String[] args) throws SuccessorNotFoundException, PredecessorNotFoundException {
		ABB abb = new ABB();
		abb.inserir(5);
		abb.inserir(4);
		abb.inserir(8);
		abb.inserir(7);
		abb.print();
		System.out.println("predecessor de 7 = "+ abb.predecessor(7).getNumero());
		System.out.println("sucessor de 7 = "+ abb.sucessor(7).getNumero());
	}
}
