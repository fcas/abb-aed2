package ARN;

import exceptions.NoJaExisteException;
import ABB.ABB;
import ABB.No;
import ARN.NoARN.Cor;

public class ARN extends ABB {

	public void insereEmABB(int chave) throws NoJaExisteException {
		super.inserir(chave);
	}

	public No inserir(int numero) throws NoJaExisteException {
		NoARN noARN = (NoARN) super.inserir(numero);
		noARN.alteraCor(Cor.VERMELHO);
		consertaInsere(noARN);
		return noARN;
	}

	private void consertaInsere(NoARN noARN) {
		while (((noARN.Pai().equals(noARN)) && (noARN.getCor()
				.equals(Cor.VERMELHO)))) {
			// caso 1
			if (noARN.Pai().equals(noARN.Pai().Pai().getEsquerda())) {

			}

		}

	}
}
