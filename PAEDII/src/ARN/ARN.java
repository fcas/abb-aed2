package ARN;

import exceptions.ChaveInvalidaException;
import exceptions.NoInvalidoException;
import exceptions.NoJaExisteException;
import ABB.ABB;
import ABB.No;
import ARN.NoARN.Cor;

public class ARN extends ABB {

	private NoARN raiz;
	
	@Override
	public NoARN getRaiz() {
		return raiz;
	}
	
	public boolean isTerminal(NoARN no) {
		return (no.getNumero() == -1);
	}
	
	/**Encontra o menor no da subarvore**/
	public NoARN min (NoARN no) {
		if (no != null){
			if (no.getEsquerda() != null){
				return min(no.getEsquerda());
			}else{
				return no;
			}
		}else{
			return null;
		}
	}
	
	/**Encontra o maior no da subarvore**/
	public NoARN max(NoARN no) {
		if (no != null){
			if (no.getDireita() != null){
				return max(no.getDireita());
			}else{
				return no;
			}
		}else{
			return null;
		}
	}

	/**Busca um no**/
	public NoARN busca(int numero){
		return busca(numero, raiz);
	}
	public NoARN busca(int numero, NoARN no) {
		/* caso 1 = no passado e nulo
		 * caso 2 = no passado e o buscado
		 * caso 3 = no passado e maior que o buscado
		 * caso 4 = no passado e menor que o buscado*/
		NoARN aux = no;
		
		if (aux!= null){		
			if (numero == no.getNumero()) { //caso 2
				aux = no;
			} else if (numero < no.getNumero()) { //caso 3
				aux = busca(numero, no.getEsquerda());
			} else { //caso 4
				aux = busca(numero, no.getDireita());
			}
		}
		else{ //caso 1
			return null;
		}
		return aux;
	}
	
	/**Rotacao direita**/
	private void rotacaoDireita(NoARN y) {
		NoARN x = y.getEsquerda();
		y.setEsquerda(x.getDireita());
		x.getDireita().setPai(y);
		x.setPai(y.Pai());
		if (y.Pai() == null){
			raiz = x;
		} else {
			if (y.Pai().getDireita().equals(y)){
				y.Pai().setDireita(x);
			} else {
				y.Pai().setEsquerda(x);
			}
		}
		x.setDireita(y);
		y.setPai(x);
			
	}

	/**Rotacao esquerda**/
	private void rotacaoEsquerda(NoARN x) {
		NoARN y =  x.getDireita();
		x.setDireita(y.getEsquerda());
		y.getEsquerda().setPai(x);
		y.setPai(x.Pai());
		if (x.Pai() == null){
			raiz = y;
		} else {
			if (x.Pai().getEsquerda().equals(x)){
				x.Pai().setEsquerda(y);
			} else {
				x.Pai().setDireita(y);
			}
		}
		y.setEsquerda(x);
		x.setPai(y);
	}
	
	/**Insere em metodo ABB e rebalanceia a arvore**/
	public NoARN inserir(int numero) throws NoJaExisteException, ChaveInvalidaException {
		// w eh variavel auxiliar para a insercao em ABB
		NoARN w =  inserirEmABB(numero);
		
		// z eh o noh inserido na ARN
		NoARN z = w; 
		consertaInsere(z);
		return w;
	}
	private NoARN inserirEmABB(int numero) throws NoJaExisteException, ChaveInvalidaException {
		if (numero < 0){
			throw new ChaveInvalidaException();
		}
		if (this.raiz == null){ //se a raiz eh nula. Insere na raiz
			raiz = new NoARN(numero);
			raiz.setPai(null);
			return raiz;
		}
		else{ //se a raiz nao eh nula, procura o no na arvore
			try{
				return inserirEmABB (raiz, numero);
			}catch (NoJaExisteException e){
				throw new NoJaExisteException();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return raiz;
	}
	private NoARN inserirEmABB(NoARN no, int numero) throws NoJaExisteException, ChaveInvalidaException{
		//Se o valor a ser inserido for menor que o no atual
		if (numero < no.getNumero()){
			
			//Se ha subarvore esquerda, continua a busca
			if (no.getEsquerda() != null){
				return inserirEmABB(no.getEsquerda(), numero);
			}
			//Se nao houver subarvore esquerda, insere
			else { 
				no.setEsquerda(new NoARN(numero));
				no.getEsquerda().setPai(no);
				return no.getEsquerda();
			}
		}
		//Se o valor a ser inserido for maior que o no atual
		else if(numero > no.getNumero()){
			
			//Se ha subarvore direita, continua a busca
			if (no.getDireita() != null){
				return inserirEmABB(no.getDireita(), numero);
			}
			//Se nao houver subarvore direita, insere
			else {
				no.setDireita(new NoARN (numero));
				no.getDireita().setPai(no);
				return no.getDireita();
			}
		}
		//Se o valor a ser inserido for igual ao no atual
		else{
			throw new NoJaExisteException();
		}
	}
	private void consertaInsere(NoARN z) {
		
		//enquanto z nao for a raiz e o pai de z for vermelho
		while (!(z.equals(this.getRaiz())) && (z.Pai().getCor()
				.equals(Cor.VERMELHO))) {
			
			//se o pai de z eh filho esquerdo do avo de z
			if (z.Pai().equals(z.Avo().getEsquerda())) {
				//checando o lado
				NoARN y = z.Tio();
				if(y.eVermelho()){
					//caso 1
					z.Pai().alteraCor(Cor.PRETO);
					y.alteraCor(Cor.PRETO);
					z.Avo().alteraCor(Cor.VERMELHO);
					z = z.Avo();
				} else {
					 /*casos 2 e 3 */ 
					if (z.Pai().getDireita().equals(z)){
						//caso 2
						z = z.Pai();
						rotacaoEsquerda(z);
					}
					//caso 3
					z.Pai().alteraCor(Cor.PRETO);
					z.Avo().alteraCor(Cor.VERMELHO);
					rotacaoDireita(z.Avo());
				}
				
				
			} else {
				//checando o lado
				NoARN y = z.Tio();
				if (y.eVermelho()){ 	
					//caso 4
					z.Pai().alteraCor(Cor.PRETO);
					y.alteraCor(Cor.PRETO);
					z.Avo().alteraCor(Cor.VERMELHO);
					z = z.Avo();
				} else {
					/* casos 5 e 6 */
					if (z.Pai().getEsquerda().equals(z)){
						//caso 5
						z = z.Pai();
						rotacaoDireita(z);
					}
				//caso 6
				z.Pai().alteraCor(Cor.PRETO);
				z.Avo().alteraCor(Cor.VERMELHO);
				rotacaoEsquerda(z.Avo());
				}
			}

		}
		
		getRaiz().alteraCor(Cor.PRETO);

	}

	/**Remove em metodo ABB e rebalanceia a arvore**/
	public NoARN remover(int numero){
		NoARN x = removeEmABB(numero);
		if (x.getNumero() != -1 && !(x.eVermelho())){
			consertaRemove(x);
		}
		return x;
	}
	private NoARN removeEmABB(int numero) {
		try {
			return removeEmABB(numero, raiz);
		} catch (NoInvalidoException e) {
			e.printStackTrace();
		}
		return null;
	}
	private NoARN removeEmABB(int numero, NoARN no) throws NoInvalidoException {
		NoARN aux = no;
		NoARN ok = null;
		if (aux != null) {
			//Se o no a ser removido for menor que o no atual
			if (numero < no.getNumero()){
				ok = removeEmABB(numero, no.getEsquerda());
			}
			//Se o no a ser removido for maior que o no atual
			else if (numero > no.getNumero()){
				ok = removeEmABB(numero, no.getDireita());
			}
			//Se o no a ser removido for o atual
			else{
				if(no.getEsquerda().getNumero() != -1 && no.getDireita().getNumero() != -1) { //tem os dois filhos
					aux = min(no.getDireita());
					no.setNumero(aux.getNumero());
					ok = removeEmABB(aux.getNumero(), no.getDireita());
				}else{ //no tem um ou nenhum filho
					aux = no; //guarda apontador do no modificado
					
					if (aux.getEsquerda().getNumero() != -1){ //se so tem filho na esquerda
						NoARN pai = aux.Pai();
						pai.setEsquerda(aux.getEsquerda()); //pai aponta pro neto
						pai.getEsquerda().setPai(pai); //antigo neto (agora filho) aponta pro pai
						ok = pai.getEsquerda();
					} else { //se so tem um ou nenhum filho
						NoARN pai = aux.Pai();
						pai.setDireita(aux.getDireita()); //pai aponta pro neto ou para o no vazio a direita
						pai.getDireita().setPai(pai); //antigo neto (agora filho) aponta pro pai
						ok = pai.getDireita();
					}
				}
			}
		}else{
			ok = null;
		}
		return ok;
	}
	private void consertaRemove(NoARN x) {
		while(!(x.equals(this.getRaiz())) && !(x.eVermelho())){
			if (x.equals(x.Pai().getEsquerda())){
				//checando o lado
				NoARN w = x.Pai().getDireita();
				if (w.eVermelho()){ //irmao vermelho
					//caso 1
					w.alteraCor(Cor.PRETO);
					x.Pai().alteraCor(Cor.VERMELHO);
					rotacaoEsquerda(x.Pai());
					w = x.Pai().getDireita();
				}
				if (!(w.getEsquerda().eVermelho() || w.getDireita().eVermelho())) {
					//caso 2
					w.alteraCor(Cor.VERMELHO);
					x = x.Pai();
				} else {
					if (!(w.getDireita().eVermelho())){
						//caso 3
						w.getEsquerda().alteraCor(Cor.PRETO);
						w.alteraCor(Cor.VERMELHO);
						rotacaoDireita(w);
						w = x.Pai().getDireita();
					}
					//caso 4
					w.alteraCor(x.Pai().getCor());
					x.Pai().alteraCor(Cor.PRETO);
					w.getDireita().alteraCor(Cor.PRETO);
					rotacaoEsquerda(x.Pai());
					x = raiz;
				}
			}else {
				//checando o lado
				NoARN w = x.Pai().getEsquerda();
				if (w.eVermelho()){ //irmao vermelho
					//caso 5
					w.alteraCor(Cor.PRETO);
					x.Pai().alteraCor(Cor.VERMELHO);
					rotacaoDireita(x.Pai());
					w = x.Pai().getEsquerda();
				}
				if (!(w.getDireita().eVermelho() || w.getEsquerda().eVermelho())) {
					//caso 6
					w.alteraCor(Cor.VERMELHO);
					x = x.Pai();
				} else {
					if (!(w.getEsquerda().eVermelho())){
						//caso 7
						w.getDireita().alteraCor(Cor.PRETO);
						w.alteraCor(Cor.VERMELHO);
						rotacaoEsquerda(w);
						w = x.Pai().getEsquerda();
					}
					//caso 8
					w.alteraCor(x.Pai().getCor());
					x.Pai().alteraCor(Cor.PRETO);
					w.getEsquerda().alteraCor(Cor.PRETO);
					rotacaoDireita(x.Pai());
					x = raiz;
				}
			}
		}
		x.alteraCor(Cor.PRETO);
	}

	/**Imprime os nos da Arvore percorrendo em ordem**/
	public void print(){
		visitarEmOrdem(getRaiz());
	}
	private void visitarEmOrdem(NoARN node) {
		if (node != null){
			this.visitarEmOrdem(node.getEsquerda());
			System.out.println(node.getNumero());
			System.out.println(node.getCor().name() + "\n");
			visitarEmOrdem(node.getDireita());
		}
	}
	
	/**Imprime os nos da Arvore percorrendo em pos-ordem**/
	public void printPos(){
		visitarPosOrdem(getRaiz());
	}
	private void visitarPosOrdem(NoARN node) {
		if (node != null){
			visitarPosOrdem(node.getEsquerda());
			visitarPosOrdem(node.getDireita());
			System.out.println(node.getNumero());
			System.out.println(node.getCor().name() + "\n");
		}
	}
	
	/**Imprime os nos da Arvore percorrendo em pre-ordem**/
	public void printPre(){
		visitarPreOrdem(getRaiz());
	}
	private void visitarPreOrdem(NoARN node) {
		if (node != null){
			System.out.println(node.getNumero());
			System.out.println(node.getCor().name() + "\n");
			visitarPreOrdem(node.getEsquerda());
			visitarPreOrdem(node.getDireita());
		}
	}
	
	public boolean arvoreRubroNegraValida(){
			return verificaNo(raiz);
	}
	
	public boolean verificaNo(NoARN no){
		/*
		 * caso 1 no externo
		 * caso especial: ser a raiz
		 * caso 2 folha
		 * caso 3 dois filhos
		 * caso 4 so filho esquerdo
		 * caso 5 so filho direito
		
		 	1.	Coloracao – Todo no possui uma e apenas uma de duas cores: vermelho ou preto.
			2.	Raiz – A cor do no raiz e PRETA.
			3.	No Externo – Todo no externo possui cor PRETA.
			4.	No Interno – Todo no interno associado a cor VERMELHA possui dois filhos de cor PRETA.
			5.	Profundidade – Para cada no, todos os caminhos do no a um no externo possuem o mesmo numero de nos associados a cor PRETA.
			6.  Arvore Binaria de Busca - Todo filho esquerdo tem que ser menor que o pai e todo filho direito tem que ser maior.
		 */
		if (no.getNumero() != -1){
			
			
		}else {//caso 1
			if (no.getCor().equals(Cor.PRETO)){
				return true;
			} else
				return false;
		}
	}
}
