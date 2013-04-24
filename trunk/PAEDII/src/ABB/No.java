package ABB;


public class No {
	
	private int numero;
	private No esquerda;
	private No direita;
	private No pai;
	
	public No(int i){
		numero = i;
		esquerda = null;
		direita = null;
		pai = null;
	}
	
	public No(){
		direita = null;
		esquerda = null;
		setNumero(-1);
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public No getEsquerda() {
		return esquerda;
	}
	
	public void setEsquerda(No esquerda) {
		this.esquerda = esquerda;
	}
	
	public No getDireita() {
		return direita;
	}
	
	public void setDireita(No direita) {
		this.direita = direita;
	}

	public No Pai() {
		return pai;
	}
	
	public void setPai(No pai) {
		this.pai = pai;
	}
	
	public boolean ehExterno(){
		return (this.getNumero() == -1);
	}
	
	public void criaFilhos(){
		this.setEsquerda(new No());
		this.getEsquerda().setPai(this);
		this.setDireita(new No());
		this.getDireita().setPai(this);
	}
}
