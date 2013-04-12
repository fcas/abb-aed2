import exceptions.NoInvalidoException;
import exceptions.NoJaExisteException;

public class ABB {
	
	private No raiz;
	
	/**Construtor**/
	public ABB() {
		this.raiz = null;
	}

	public No getRaiz() {
		return raiz;
	}
	
	/**Indica se um no eh folha**/
	public boolean isTerminal(No no) {
		return (no.getEsquerda() == null && no.getDireita() == null);
	}
	
	/**Busca um no**/
	public No busca(int numero){
		return busca(numero, raiz);
	}
	public No busca(int numero, No no) {
		/* caso 1 = no passado e nulo
		 * caso 2 = no passado e o buscado
		 * caso 3 = no passado e maior que o buscado
		 * caso 4 = no passado e menor que o buscado*/
		No aux = no;
		
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

	/**Insere um no na arvore**/
	public void inserir (int numero){
		if (this.raiz == null){ //se a raiz eh nula. Insere na raiz
			raiz = new No(numero);
			raiz.setPai(raiz);
		}
		else{ //se a raiz nao eh nula, procura o no na arvore
			try{
				inserir (raiz, numero);
			}catch (NoJaExisteException e){
				System.out.println("no ja existe");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	private void inserir(No no, int numero) throws NoJaExisteException, NoInvalidoException {
		//Se o valor a ser inserido for menor que o no atual
		if (numero < no.getNumero()){ 
			
			//Se ha subarvore esquerda, continua a busca
			if (no.getEsquerda() != null){
				inserir(no.getEsquerda(), numero);
			}
			//Se nao houver subarvore esquerda, insere
			else { 
				no.setEsquerda(new No(numero));
				no.getEsquerda().setPai(no);
			}
		}
		//Se o valor a ser inserido for maior que o no atual
		else if(numero > no.getNumero()){
			
			//Se ha subarvore direita, continua a busca
			if (no.getDireita() != null){
				inserir(no.getDireita(), numero);
			}
			//Se nao houver subarvore direita, insere
			else {
				no.setDireita(new No (numero));
				no.getDireita().setPai(no);
			}
		}
		//Se o valor a ser inserido for igual ao no atual
		else{
			throw new NoJaExisteException();
		}
	}
	
	/**Encontra o menor no da subarvore**/
	public No min (No no) {
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
	public No max(No no) {
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
	
	/**Tenta remover o no indicado da arvore e retorna o sucesso da operacao**/
	public boolean remove(int numero){
		try {
			return remove(numero, raiz);
		} catch (NoInvalidoException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean remove(int numero, No no) throws NoInvalidoException{
		No aux = no;
		boolean ok = true;
		if (aux != null) {
			//Se o no a ser removido for menor que o no atual
			if (numero < no.getNumero()){
				ok = remove(numero, no.getEsquerda());
			}
			//Se o no a ser removido for maior que o no atual
			else if (numero > no.getNumero()){
				ok = remove(numero, no.getDireita());
			}
			//Se o no a ser removido for o atual
			else{
				if(no.getEsquerda() != null && no.getDireita() != null) { //tem os dois filhos
					aux = min(no.getDireita());
					no.setNumero(aux.getNumero());
					ok = remove(aux.getNumero(), no.getDireita());
					System.out.println("ok ");
				}else{ //no tem um ou nenhum filho
					aux = no; //guarda apontador do no modificado
					
					if (aux.getEsquerda() != null){ //se s� tem filho na esquerda
						No pai = aux.Pai();
						pai.setEsquerda(aux.getEsquerda()); //pai aponta pro neto
						pai.getEsquerda().setPai(pai);
					} else if (aux.getDireita()!=null) { //se s� tem filho na direita ou n�o tem filho
						System.out.println("entrei");
						No pai = aux.Pai();
						System.out.println("pai = " + pai.getNumero());
						System.out.println("pai = " + pai.getDireita().getNumero());
						pai.setDireita(aux.getDireita()); //pai aponta pro neto ou para o n� vazio � direita
						System.out.println("paiD = " + pai.getDireita().getNumero());
						pai.getDireita().setPai(pai);
					} else {
						No pai = aux.Pai();
						pai.setDireita(aux.getDireita());
					}
				}
			}
		}else{
			ok = false;
		}
		return ok;
	}
	
	/**Imprime os nos da Arvore percorrendo em ordem**/
	void print(){
		visitarEmOrdem(raiz);
	}
	private void visitarEmOrdem(No node) {
		if (node != null){
			visitarEmOrdem(node.getEsquerda());
			System.out.println(node.getNumero());
			visitarEmOrdem(node.getDireita());
		}
	}
	
	/**Imprime os nos da Arvore percorrendo em pos-ordem**/
	void printPos(){
		visitarPosOrdem(raiz);
	}
	private void visitarPosOrdem(No node) {
		if (node != null){
			visitarPosOrdem(node.getEsquerda());
			visitarPosOrdem(node.getDireita());
			System.out.println(node.getNumero());
		}
	}
	
	/**Imprime os nos da Arvore percorrendo em pre-ordem**/
	void printPre(){
		visitarPreOrdem(raiz);
	}
	private void visitarPreOrdem(No node) {
		if (node != null){
			System.out.println(node.getNumero());
			visitarPreOrdem(node.getEsquerda());
			visitarPreOrdem(node.getDireita());
		}
	}
	
	//TODO
	public No predecessor(No n){
		return null;
	}
	
	//TODO
	public No sucessor(No n){
		return null;
	}
	
	//TODO
	boolean arvoreBinariaBuscaValida(){
		return false;
	}

}