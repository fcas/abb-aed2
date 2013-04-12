import exceptions.NoInvalidoException;
import exceptions.NoJaExisteException;

public class ABB {

	public No raiz;

	/** Construtor **/
	public ABB() {
		this.raiz = null;
	}

	/** Indica se um no eh folha **/
	public boolean isTerminal(No no) {
		return (no.getEsquerda() == null && no.getDireita() == null);
	}

	/** Busca um no **/
	public No busca(int numero, No no) {
		/*
		 * caso 1 = no passado e nulo caso 2 = no passado e o buscado caso 3 =
		 * no passado e maior que o buscado caso 4 = no passado e menor que o
		 * buscado
		 */
		No aux = no;

		if (aux != null) {
			if (numero == no.getNumero()) { // caso 2
				aux = no;
			} else if (numero < no.getNumero()) { // caso 3
				aux = busca(numero, no.getEsquerda());
			} else { // caso 4
				aux = busca(numero, no.getDireita());
			}
		} else { // caso 1
			return null;
		}
		return aux;
	}

	/** Insere um no na arvore **/
	public void inserir(int numero) {
		if (this.raiz == null) {
			raiz = new No(numero);
		} else {
			try {
				inserir(raiz, numero);
			} catch (NoJaExisteException e) {
				System.out.println("n� j� existe");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void inserir(No no, int numero) throws NoJaExisteException,
			NoInvalidoException {
		if (no != null) {
			// Se o valor a ser inserido for menor que o no atual
			if (numero < no.getNumero()) {

				// Se ha subarvore esquerda, continua a busca
				if (no.getEsquerda() != null) {
					inserir(no.getEsquerda(), numero);
				}
				// Se nao houver subarvore esquerda, insere
				else {
					no.setEsquerda(new No(numero));
				}
			}
			// Se o valor a ser inserido for maior que o no atual
			else if (numero > no.getNumero()) {

				// Se ha subarvore direita, continua a busca
				if (no.getDireita() != null) {
					inserir(no.getDireita(), numero);
				}
				// Se nao houver subarvore direita, insere
				else {
					no.setDireita(new No(numero));
				}
			}
			// Se o valor a ser inserido for igual ao no atual
			else {
				throw new NoJaExisteException();
			}
		} else {
			System.out.println("colocando " + numero + " na raiz");
			no = new No(numero);
			System.out.println(raiz.getNumero());
		}
	}

	/** Encontra o menor no da subarvore **/
	public No Min(No no) {
		if (no != null) {
			if (no.getEsquerda() != null) {
				return Min(no.getEsquerda());
			} else {
				return no;
			}
		} else {
			return null;
		}
	}

	/** Encontra o maior no da subarvore **/
	public No Max(No no) {
		if (no != null) {
			if (no.getDireita() != null) {
				return Min(no.getDireita());
			} else {
				return no;
			}
		} else {
			return null;
		}
	}

	/** Insere um no na arvore **/
	public void remove(int numero) {
		try {
			removeNumero(numero, raiz);
		} catch (NoInvalidoException e) {
			System.out.println("no nao existe");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Tenta remover o no indicado da arvore e retorna o sucesso da operacao **/
	public boolean removeNumero(int numero, No no) throws NoInvalidoException {
		No aux = no;
		boolean ok = true;
		if (aux != null) {
			// Se o no a ser removido for menor que o no atual
			if (numero < no.getNumero()) {
				ok = removeNumero(numero, no.getEsquerda());
			}
			// Se o no a ser removido for maior que o no atual
			else if (numero > no.getNumero()) {
				ok = removeNumero(numero, no.getEsquerda());
			}
			// Se o no a ser removido for o atual
			else {
				if (no.getEsquerda() != null && no.getDireita() != null) { // tem
																			// os
																			// dois
																			// filhos
					aux = Min(no.getDireita());
					no.setNumero(aux.getNumero());
					ok = removeNumero(aux.getNumero(), no.getDireita());
				} else { // no tem um ou nenhum filho
					aux = no; // guarda apontador do no modificado
					if (aux.getEsquerda() != null) {
						No pai = AchaPai(aux);
						pai.setEsquerda(aux.getEsquerda()); // pai aponta pro
															// neto
					} else if (aux.getDireita() != null) {
						No pai = AchaPai(aux);
						pai.setDireita(aux.getDireita()); // pai aponta pro neto
					}
				}
			}
		} else {
			ok = false;
		}
		return ok;
	}

	private No Pai(No busca, No no) {
		if (busca.getNumero() < no.getNumero()) {
			if (no.getEsquerda().getNumero() != busca.getNumero()) {
				return Pai(busca, no.getEsquerda());
			} else {
				return no;
			}
		} else if (busca.getNumero() > no.getNumero()) {
			if (no.getDireita().getNumero() != busca.getNumero()) {
				return Pai(busca, no.getDireita());
			} else {
				return no;
			}
		} else {
			return null;
		}
	}

	/** Retorna o pai do no indicado **/
	public No AchaPai(No busca) {
		return Pai(busca, raiz);
	}

	/** Imprime os nos da Arvore percorrendo em ordem **/
	public void visitarEmOrdem(No node) {
		if (node != null) {
			visitarEmOrdem(node.getEsquerda());
			System.out.println(node.getNumero());
			visitarEmOrdem(node.getDireita());
		}
	}

	/** Imprime os nos da Arvore percorrendo em pre-ordem **/
	public void visitarPreOrdem(No node) {
		System.out.println(node.getNumero());
		visitarPreOrdem(node.getEsquerda());
		visitarPreOrdem(node.getDireita());
	}

	/** Imprime os nos da Arvore percorrendo em pos-ordem **/
	public void visitarPosOrdem(No node) {
		visitarPosOrdem(node.getEsquerda());
		visitarPosOrdem(node.getDireita());
		System.out.println(node.getNumero());
	}

	// TODO
	public No predecessor(No n) {
		return null;
	}

	// TODO
	public No sucessor(No n) {
		return null;
	}

	// TODO
	void print() {
		visitarEmOrdem(raiz);
	}

	// TODO
	boolean arvoreBinariaBuscaValida() {
		return false;
	}

}