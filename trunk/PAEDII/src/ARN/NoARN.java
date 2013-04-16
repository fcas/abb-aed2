package ARN;
import ABB.No;

public class NoARN extends No {
	
	public enum Cor{
		VERMELHO, PRETO
	}
	public Cor cor;
	
	public NoARN(int i) {
		super(i);
		cor = Cor.VERMELHO;
	}
	
	public Cor getCor() {
		return cor;
	}
	
	public void alteraCor(Cor cor){
		this.cor = cor;
	}
	
	public NoARN Avo () {
		return  (NoARN) super.Pai().Pai();
	}
	
	@Override
	public NoARN Pai(){ 
		return (NoARN) super.Pai();
	}
	
	public NoARN Tio () {
		
		// o tio estah na esquerda
		if (super.Pai().Pai().getDireita().equals(super.Pai())){
			return (NoARN) super.Pai().Pai().getEsquerda();
		}
		
		// o tio estah na direita
		else return (NoARN) super.Pai().Pai().getDireita();

	}
	
}
