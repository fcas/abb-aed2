import exceptions.NoInvalidoException;
import exceptions.NoJaExisteException;

public class ABB {
	
	private No raiz;
	
	/**Construtor**/
	public ABB() {
		this.raiz = null;
	}

	/**Indica se um no eh folha**/
	public boolean isTerminal(No no) {
		return (no.getEsquerda() == null && no.getDireita() == null);
	}
	
	/**Busca um no**/
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

	public void inserir(No no, int numero) throws NoJaExisteException, NoInvalidoException {
		if (no != null){
			//Se o valor a ser inserido for menor que o no atual
			if (numero < no.getNumero()){ 
				
				//Se ha subarvore esquerda, continua a busca
				if (no.getEsquerda() != null){
					inserir(no.getEsquerda(), numero);
				}
				//Se nao houver subarvore esquerda, insere
				else { 
					no.setEsquerda(new No(numero));
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
				}
			}
			//Se o valor a ser inserido for igual ao no atual
			else{
				throw new NoJaExisteException();
			}
		}else{
			throw new NoInvalidoException();
		}
	}

	public No Min (No no) {
		if (no != null){
			if (no.getEsquerda() != null){
				return Min(no.getEsquerda());
			}else{
				return no;
			}
		}else{
			return null;
		}
	}

	public No Max(No no) {
		if (no != null){
			if (no.getDireita() != null){
				return Min(no.getDireita());
			}else{
				return no;
			}
		}else{
			return null;
		}
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
				ok = remove(numero, no.getEsquerda());
			}
			//Se o no a ser removido for o atual
			else{
				if(no.getEsquerda() != null && no.getDireita() != null) { //tem os dois filhos
					aux = Min(no.getDireita());
					no.setNumero(aux.getNumero());
					remove(aux.getNumero(), no.getDireita());
				}else{ //no tem um ou nenhum filho
					aux = no;
					if (aux.getEsquerda() != null){
						No aux2 = Pai(aux, raiz);
						
					}
					
				}
			}
		}else{
			ok = false;
		}
	}
	
	public No Pai(No busca, No no){
		if (busca.getNumero() < no.getNumero()){
			if (no.getEsquerda().getNumero() != busca.getNumero()){
				return Pai(busca, no.getEsquerda());
			}else{
				return no;
			}
		} else if (busca.getNumero() > no.getNumero()){
			if (no.getDireita().getNumero() != busca.getNumero()){
				return Pai (busca, no.getDireita());
			}else {
				return no;
			}
		} else{
			return null;
		}
	}
	
	
	private No AchaPai (No busca){
		return Pai(busca, raiz);
	}
	

	public void visitarEmOrdem(No node) {
		visitarEmOrdem(esquerda.getNode());
		System.out.println(node.getNumero());
		visitarEmOrdem(direita.getNode());
	}

	public void visitarPreOrdem(No node) {
		System.out.println(node.getNumero());
		visitarPreOrdem(esquerda.getNode());
		visitarPreOrdem(direita.getNode());
	}

	public void visitarPosOrdem(No node) {
		visitarPosOrdem(esquerda.getNode());
		visitarPosOrdem(direita.getNode());
		System.out.println(node.getNumero());
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
	void print(){
		
	}
	
	//TODO
	boolean arvoreBinariaBuscaValida(){
		return false;
	}

}