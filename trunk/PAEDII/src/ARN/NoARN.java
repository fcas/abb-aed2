package ARN;
import ABB.No;

public class NoARN extends No {
	
	public enum Cor{
		VERMELHO, PRETO
	}
	private int numero;
	public Cor cor;
	private NoARN esquerda;
	private NoARN direita;
	private NoARN pai;
	
	public NoARN(int i) {
		this.numero = i;
		cor = Cor.VERMELHO;
		criaFilhos();
	}
	
	public NoARN() {
		direita = null;
		esquerda = null;
		cor = Cor.PRETO;
		setNumero(-1);
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public NoARN getEsquerda() {
		return esquerda;
	}
	
	public void setEsquerda(NoARN esquerda) {
		this.esquerda = esquerda;
	}
	
	public NoARN getDireita() {
		return direita;
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
	
	@Override
	public NoARN Pai() {
		return pai;
	}
	
	public void setPai(NoARN pai) {
		this.pai = pai;
	}
	
	public NoARN Avo () {
		return  Pai().Pai();
	}
	
	public NoARN Tio () {
		
		// o tio estah na esquerda
		if (Avo().getDireita().equals(Pai())){
			return Avo().getEsquerda();
		}
		
		// o tio estah na direita
		else return Avo().getDireita();

	}
	
	public boolean eVermelho(){
		return this.getCor().equals(Cor.VERMELHO);
	}
	
	public boolean ehExterno(){
		return (this.getNumero() == -1);
	}
	
	public void criaFilhos(){
		this.setEsquerda(new NoARN());
		this.getEsquerda().setPai(this);
		this.setDireita(new NoARN());
		this.getDireita().setPai(this);
	}	
}
