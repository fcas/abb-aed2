import ARN.ARN;
import exceptions.NoJaExisteException;
import exceptions.PredecessorNotFoundException;
import exceptions.SuccessorNotFoundException;

public class Teste {
	
	public static void main(String[] args) throws SuccessorNotFoundException, PredecessorNotFoundException, NoJaExisteException {
		ARN arn = new ARN();
		arn.inserir(5);
		arn.inserir(4);
		arn.inserir(8);
		arn.inserir(7);
		arn.print();
	}
}
