package ARN;
import ABB.No;

public class NoARN extends No {
	
	public enum Cor{
		VERMELHO, PRETO
	}
	private Cor cor;
	
	public NoARN(int i) {
		super(i);
		cor = cor.VERMELHO;
	}
	
	public Cor getCor() {
		return cor;
	}
	
	public void alteraCor(Cor cor){
		this.cor = cor;
	}
	
	public No avo(No no) {
		return  no.Pai().Pai();
	}
	
	public No tioEsquerda (No no) {
		return no.Pai().Pai().getEsquerda();
	}
	
	public No tioDireita (No no){ 
		return no.Pai().Pai().getDireita();
	}
	
}
