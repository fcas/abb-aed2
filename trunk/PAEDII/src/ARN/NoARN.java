package ARN;
import ABB.No;

public class NoARN extends No {
	
	public enum Cor{
		VERMELHO, PRETO
	}
	public Cor cor;
	private NoARN esquerda;
	private NoARN direita;
	
	public NoARN(int i) {
		super(i);
		cor = Cor.VERMELHO;
	}
	
	public NoARN getEsquerda() {
		return esquerda;
	}
	
	public NoARN getDireita() {
		return direita;
	}
	
	public void setEsquerda(NoARN esquerda) {
		this.esquerda = esquerda;
	}
	
	public void setDireita(NoARN direita) {
		this.direita = direita;
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
