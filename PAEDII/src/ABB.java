public class ABB {

	private Node node;
	private ABB esquerda;
	private ABB direita;

	public ABB() {
		this.node = null;
		this.esquerda = null;
		this.direita = null;
	}

	public boolean isTerminal() {
		return (esquerda == null && direita == null);
	}

	public boolean isFolha() {
		return (esquerda.isTerminal() && direita.isTerminal());
	}

	public Node getNode() {
		return node;
	}

	public ABB getDireita() {
		return direita;
	}

	public ABB getEsquerda() {
		return esquerda;
	}

	public void inserir(Node node) {
		if (this.isTerminal()) {
			this.node = node;
			this.esquerda = new ABB();
			this.direita = new ABB();
		} else if (node.getNumero() < this.node.getNumero()) {
			esquerda.inserir(node);
		} else if (node.getNumero() > this.node.getNumero()) {
			direita.inserir(node);
		} else {
			System.out.println("O no ja existe");
		}
	}

	public Node busca(int numero) {
		Node aux = null;
		if (!this.isTerminal()) {
			if (numero == node.getNumero()) {
				aux = node;
			} else if (numero < node.getNumero()) {
				aux = esquerda.busca(numero);
			} else {
				aux = direita.busca(numero);
			}
		}
		return aux;
	}

	public Node remove(int numero) {
		Node aux = null;
		if (!this.isTerminal()) {
			if (node.getNumero() == numero) {
				aux = node;

				if (this.isFolha()) {
					node = null;
					esquerda = null;
					direita = null;
				} else if (esquerda.isTerminal()) {
					this.node = direita.node;
					this.esquerda = direita.esquerda;
					this.direita = direita.direita;
				} else if (direita.isTerminal()) {
					this.node = esquerda.node;
					this.direita = esquerda.direita;
					this.esquerda = esquerda.esquerda;
				} else {
					ABB menor = direita.buscaMenor();
					this.node = menor.node;
					direita.remove(menor.getNode().getNumero());
				}

			} else if (numero > node.getNumero()) {
				aux = direita.remove(numero);
			} else if (numero < node.getNumero()) {
				aux = esquerda.remove(numero);
			}

		}

		return aux;
	}

	private ABB buscaMenor() {
		ABB menor = this;
		while (!menor.esquerda.isTerminal()) {
			menor = menor.esquerda;
		}
		return menor;
	}

	private ABB buscaMaior() {
		ABB maior = this;
		while (!maior.direita.isTerminal()) {
			maior = maior.esquerda;
		}
		return maior;
	}

	public void visitarEmOrdem(Node node) {
		visitarEmOrdem(esquerda.getNode());
		System.out.println(node.getNumero());
		visitarEmOrdem(direita.getNode());
	}

	public void visitarPreOrdem(Node node) {
		System.out.println(node.getNumero());
		visitarPreOrdem(esquerda.getNode());
		visitarPreOrdem(direita.getNode());
	}

	public void visitarPosOrdem(Node node) {
		visitarPosOrdem(esquerda.getNode());
		visitarPosOrdem(direita.getNode());
		System.out.println(node.getNumero());
	}

}