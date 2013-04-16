package ABB;
import exceptions.NoInvalidoException;
import exceptions.NoJaExisteException;
import exceptions.PredecessorNotFoundException;
import exceptions.SuccessorNotFoundException;

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

	/**Insere um no na arvore
	 * @throws NoJaExisteException **/
	public No inserir (int numero) throws NoJaExisteException{
		if (this.raiz == null){ //se a raiz eh nula. Insere na raiz
			raiz = new No(numero);
			raiz.setPai(raiz);
			return raiz;
		}
		else{ //se a raiz nao eh nula, procura o no na arvore
			try{
				return inserir (raiz, numero);
			}catch (NoJaExisteException e){
				throw new NoJaExisteException();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return raiz;
	}
	private No inserir(No no, int numero) throws NoJaExisteException, NoInvalidoException {
		//Se o valor a ser inserido for menor que o no atual
		if (numero < no.getNumero()){ 
			
			//Se ha subarvore esquerda, continua a busca
			if (no.getEsquerda() != null){
				return inserir(no.getEsquerda(), numero);
			}
			//Se nao houver subarvore esquerda, insere
			else { 
				no.setEsquerda(new No(numero));
				no.getEsquerda().setPai(no);
				return no;
			}
		}
		//Se o valor a ser inserido for maior que o no atual
		else if(numero > no.getNumero()){
			
			//Se ha subarvore direita, continua a busca
			if (no.getDireita() != null){
				return inserir(no.getDireita(), numero);
			}
			//Se nao houver subarvore direita, insere
			else {
				no.setDireita(new No (numero));
				no.getDireita().setPai(no);
				return no;
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
				}else{ //no tem um ou nenhum filho
					aux = no; //guarda apontador do no modificado
					
					if (aux.getEsquerda() != null){ //se so tem filho na esquerda
						No pai = aux.Pai();
						pai.setEsquerda(aux.getEsquerda()); //pai aponta pro neto
						pai.getEsquerda().setPai(pai); //antigo neto (agora filho) aponta pro pai
					} else if (aux.getDireita()!=null) { //se so tem filho na direita
						No pai = aux.Pai();
						pai.setDireita(aux.getDireita()); //pai aponta pro neto ou para o no vazio a direita
						pai.getDireita().setPai(pai); //antigo neto (agora filho) aponta pro pai
					} else { //se nao tem filhos
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
	public void print(){
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
	public void printPos(){
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
	public void printPre(){
		visitarPreOrdem(raiz);
	}
	private void visitarPreOrdem(No node) {
		if (node != null){
			System.out.println(node.getNumero());
			visitarPreOrdem(node.getEsquerda());
			visitarPreOrdem(node.getDireita());
		}
	}
	
	/**
	 * 
	 * @param chave
	 * @return O maior no que seja menor que o no buscado
	 * @throws PredecessorNotFoundException
	 */
	public No predecessor(int chave) throws PredecessorNotFoundException{
		No n = busca(chave);
		if (n.getEsquerda() != null){ // se tem filho esquerdo retorna o max da subarvore esquerda.
			return max(n.getEsquerda()); 
		}else{ //se n�o tem filho esquerdo, retorna o primeiro antecessor cuja subarvore direita contenha n
			No pai = n.Pai();
			No aux = n;
			while (pai.getDireita() != aux){
				aux = pai;
				pai = pai.Pai();
				if (pai.getNumero() == aux.getNumero()){
					throw new PredecessorNotFoundException();
				}
			}
			return pai;
		}
	}
	
	/**
	 * 
	 * @param chave
	 * @return O menor no que seja maior que o no buscado
	 * @throws PredecessorNotFoundException
	 */
	public No sucessor(int chave) throws SuccessorNotFoundException{
		No n = busca(chave);
		if (n.getDireita() != null){ // se tem filho direito retorna o min da subarvore direita.
			return min(n.getDireita()); 
		}else{ //se n�o tem filho direito, retorna o primeiro antecessor cuja subarvore esquerda contenha n
			No pai = n.Pai();
			No aux = n;
			while (pai.getEsquerda() != aux){
				aux = pai;
				pai = pai.Pai();
				if (pai.getNumero() == aux.getNumero()){
					throw new SuccessorNotFoundException();
				}
			}
			return pai;
		}
	}
	
	/**
	 * 
	 * @return verdadeiro se a arvore for binaria de busca ou falso se nao for
	 */
	public boolean arvoreBinariaBuscaValida(){
		return verificaNo(raiz);
	}
	
	private boolean verificaNo(No no){
		/*
		 * caso 1 no externo
		 * caso 2 folha
		 * caso 3 dois filhos
		 * caso 4 s� filho esquerdo
		 * caso 5 so filho direito
		 */
		if (no != null){ //caso 1
			if (no.getEsquerda() == null && no.getDireita() == null){ //caso 2
				return true;
			}else {
				if (no.getEsquerda() != null && no.getDireita() != null){ //caso 3
					if (no.getEsquerda().getNumero() < no.getNumero() && no.getDireita().getNumero() > no.getNumero()){ //os dois filhos obedecem a regra
						return (verificaNo(no.getEsquerda()) &&  verificaNo(no.getDireita()));
					} else { // tem os dois filhos mas um dos dois ou ambos, desobedecem a regra
						return false;
					}
				} else if (no.getEsquerda() != null){ //caso 4
					if (no.getEsquerda().getNumero() < no.getNumero()){ //se o esquerdo obedece a regra
						return verificaNo(no.getEsquerda());
					}else{ //se o esquerdo desobedece
						return false;
					}
				} else { // caso 5
					if (no.getDireita().getNumero() > no.getNumero()){ //se o direito obedece a regra
						return verificaNo(no.getDireita());
					}else {//se o direito desobedece
						return false;
					}
				}
			}
		}else{
			return true;
		}
	}
	

}