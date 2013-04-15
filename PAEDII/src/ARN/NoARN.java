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
	

}
