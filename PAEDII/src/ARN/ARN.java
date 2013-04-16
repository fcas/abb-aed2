package ARN;

import exceptions.NoJaExisteException;
import ABB.ABB;
import ABB.No;
import ARN.NoARN.Cor;

public class ARN extends ABB {

	@Override
	public NoARN getRaiz() {
		return (NoARN) super.getRaiz();
	}
	
	public void insereEmABB(int chave) throws NoJaExisteException {
		super.inserir(chave);
	}

	public No inserir(int numero) throws NoJaExisteException {
		// w eh variavel auxiliar para a insercao em ABB
		NoARN w = (NoARN) super.inserir(numero);
		w.alteraCor(Cor.VERMELHO);
		// z eh o noh inserido na ARN
		NoARN z = w; 
		consertaInsere(z);
		return w;
	}

	private void consertaInsere(NoARN no) {
		
		while (!(no.equals((NoARN)super.getRaiz()) && (no.Pai().getCor()
				.equals(Cor.VERMELHO)))) {
			
			if (no.Pai().equals(no.Pai().Pai().getEsquerda())) {
				
				// caso 1
				if(no.Tio().getCor().equals(Cor.VERMELHO)){ 
					no.Pai().alteraCor(Cor.PRETO);
					no.Tio().alteraCor(Cor.PRETO);
					no.Avo().alteraCor(Cor.VERMELHO);
					no = no.Avo();
				} else /*caso 2 */ { 
					if (no.Pai().getDireita().equals(no)){
						no = no.Pai();
						rotacaoEsquerda(no); 
					}
					
					//caso 3
					
					no.Pai().alteraCor(Cor.PRETO);
					no.Avo().alteraCor(Cor.VERMELHO);
					rotacaoDireita(no.Avo());
				}
				
				
			} else {
				
				if (no.Tio().getCor().equals(Cor.VERMELHO)){ 
					//caso 4
					no.Pai().alteraCor(Cor.PRETO);
					no.Tio().alteraCor(Cor.PRETO);
					no.Avo().alteraCor(Cor.VERMELHO);
					no = no.Avo();
				} else /* caso 5 */ {
					if (no.Pai().getEsquerda().equals(no)){ 
						no = no.Pai();
						rotacaoDireita(no);
					}
				}
				
				no.Pai().alteraCor(Cor.PRETO);
				no.Avo().alteraCor(Cor.VERMELHO);
				rotacaoEsquerda(no.Avo());
				
			}

		}
		
		getRaiz().alteraCor(Cor.PRETO);

	}

	private void rotacaoDireita(NoARN avo) {
		// TODO Auto-generated method stub
		
	}

	private void rotacaoEsquerda(NoARN no) {
		// TODO Auto-generated method stub
		
	}
}
