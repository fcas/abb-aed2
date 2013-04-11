

public class No {
	
	private int numero;
	private No esquerda;
	private No direita;
	
	//acesso a leitura e escrita; 
	// this refere-se a especificação de uma variável;
	
	public No(int i){
		numero = 0;
		esquerda = null;
		direita = null;
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
	
	
}
