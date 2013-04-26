package BTREE;

import java.util.ArrayList;

public class B {
	private static final int D = 4; // Ordem da árvore
	private Pagina mPaginaRaiz;
	private static final int LEFT_CHILD_PAGE = 0;
	private static final int RIGHT_CHILD_PAGE = 1;

	public B() {
		mPaginaRaiz = new Pagina();
		mPaginaRaiz.mEhPaginaFolha = true;
	}

	class Pagina {

		public int mNumChaves = 0; // número de chaves
		public int[] mChaves = new int[2 * D - 1]; // vetor com as chaves
		public Object[] mObjects = new Object[2 * D - 1];
		public Pagina[] mPaginasFilhas = new Pagina[2 * D]; // filhos de uma
		// página
		public boolean mEhPaginaFolha; // verifica se uma página é folha

		int buscaBinaria(int chave) {
			int indiceEsquerda = 0;
			int indiceDireita = mNumChaves - 1;

			while (indiceEsquerda <= indiceDireita) {
				final int indiceMeio = indiceEsquerda
						+ ((indiceDireita - indiceEsquerda) / 2);
				if (mChaves[indiceMeio] < chave) {
					indiceEsquerda = indiceMeio + 1;
				} else if (mChaves[indiceMeio] > chave) {
					indiceDireita = indiceMeio - 1;
				} else {
					return indiceMeio;
				}
			}

			return -1;
		}

		boolean contem(int chave) {
			return buscaBinaria(chave) != -1;
		}

		// Remove um elemento da página e também o seu filho esquerdo (0) ou
		// direito (+1)
		void remove(int indice, int filhoEsquerdaOuDireita) {
			if (indice >= 0) {
				int i;
				for (i = indice; i < mNumChaves - 1; i++) {
					mChaves[i] = mChaves[i + 1];
					mObjects[i] = mObjects[i + 1];
					if (!mEhPaginaFolha) {
						if (i >= indice + filhoEsquerdaOuDireita) {
							mPaginasFilhas[i] = mPaginasFilhas[i + 1];
						}
					}
				}
				mChaves[i] = 0;
				mObjects[i] = null;
				if (!mEhPaginaFolha) {
					if (i >= indice + filhoEsquerdaOuDireita) {
						mPaginasFilhas[i] = mPaginasFilhas[i + 1];
					}
					mPaginasFilhas[i + 1] = null;
				}
				mNumChaves--;
			}
		}

		void deslocarUmAdireita() {
			if (!mEhPaginaFolha) {
				mPaginasFilhas[mNumChaves + 1] = mPaginasFilhas[mNumChaves];
			}
			for (int i = mNumChaves - 1; i >= 0; i--) {
				mChaves[i + 1] = mChaves[i];
				mObjects[i + 1] = mObjects[i];
				if (!mEhPaginaFolha) {
					mPaginasFilhas[i + 1] = mPaginasFilhas[i];
				}
			}
		}

		int indicePageSubarvoreRaiz(int chave) {
			for (int i = 0; i < mNumChaves; i++) {
				if (chave < mChaves[i]) {
					return i;
				}
			}
			return mNumChaves;
		}
	}

	public void inserir(int chave, Object object) {
		Pagina paginaRaiz = mPaginaRaiz;
		if (!atualizar(mPaginaRaiz, chave, object)) { // verifica se a chave já
			// existe na página
			if (paginaRaiz.mNumChaves == (2 * D - 1)) {
				Pagina novaPaginaRaiz = new Pagina();
				mPaginaRaiz = novaPaginaRaiz;
				novaPaginaRaiz.mEhPaginaFolha = false;
				mPaginaRaiz.mPaginasFilhas[0] = paginaRaiz;

				/**
				 * Split a página raiz, movendo a sua chave mediana para a nova
				 * página raiz.
				 **/

				splitPaginaFilha(novaPaginaRaiz, 0, paginaRaiz);

				/**
				 * Insere a chave numa árvore B, com a nova página criada como
				 * raiz da árvore.
				 **/

				inserirEmPaginaNaoCheia(novaPaginaRaiz, chave, object);

			} else {

				/** Insere a chave numa árvore B tendo como raiz a páginaRaiz. **/

				inserirEmPaginaNaoCheia(paginaRaiz, chave, object);

			}
		}
	}

	void splitPaginaFilha(Pagina parentPage, int i, Pagina page) {
		Pagina newPage = new Pagina();
		newPage.mEhPaginaFolha = page.mEhPaginaFolha;
		newPage.mNumChaves = D - 1;
		for (int j = 0; j < D - 1; j++) { // copia os últimos T-1 elementos de
			// node
			// em newNode.
			newPage.mChaves[j] = page.mChaves[j + D];
			newPage.mObjects[j] = page.mObjects[j + D];
		}
		if (!newPage.mEhPaginaFolha) {
			for (int j = 0; j < D; j++) { // copia os últimos T pointeirs de
				// node
				// em newNode.
				newPage.mPaginasFilhas[j] = page.mPaginasFilhas[j + D];
			}
			for (int j = D; j <= page.mNumChaves; j++) {
				page.mPaginasFilhas[j] = null;
			}
		}
		for (int j = D; j < page.mNumChaves; j++) {
			page.mChaves[j] = 0;
			page.mObjects[j] = null;
		}
		page.mNumChaves = D - 1;

		for (int j = parentPage.mNumChaves; j >= i + 1; j--) {
			parentPage.mPaginasFilhas[j + 1] = parentPage.mPaginasFilhas[j];
		}
		parentPage.mPaginasFilhas[i + 1] = newPage;
		for (int j = parentPage.mNumChaves - 1; j >= i; j--) {
			parentPage.mChaves[j + 1] = parentPage.mChaves[j];
			parentPage.mObjects[j + 1] = parentPage.mObjects[j];
		}
		parentPage.mChaves[i] = page.mChaves[D - 1];
		parentPage.mObjects[i] = page.mObjects[D - 1];
		page.mChaves[D - 1] = 0;
		page.mObjects[D - 1] = null;
		parentPage.mNumChaves++;
	}

	void inserirEmPaginaNaoCheia(Pagina page, int chave, Object object) {
		int i = page.mNumChaves - 1;
		if (page.mEhPaginaFolha) {

			while (i >= 0 && chave < page.mChaves[i]) {
				page.mChaves[i + 1] = page.mChaves[i];
				page.mObjects[i + 1] = page.mObjects[i];
				i--;
			}
			i++;
			page.mChaves[i] = chave;
			page.mObjects[i] = object;
			page.mNumChaves++;
		} else {
			// Move back from the last key of node until we find the child
			// pointer to the node
			// that is the root node of the subtree where the new element should
			// be placed.
			while (i >= 0 && chave < page.mChaves[i]) {
				i--;
			}
			i++;
			if (page.mPaginasFilhas[i].mNumChaves == (2 * D - 1)) {
				splitPaginaFilha(page, i, page.mPaginasFilhas[i]);
				if (chave > page.mChaves[i]) {
					i++;
				}
			}
			inserirEmPaginaNaoCheia(page.mPaginasFilhas[i], chave, object);
		}
	}

	public void delete(int chave) {
		delete(mPaginaRaiz, chave);
	}

	public void delete(Pagina page, int chave) {
		if (page.mEhPaginaFolha) {

			int i;
			if ((i = page.buscaBinaria(chave)) != -1) {

				page.remove(i, LEFT_CHILD_PAGE);
			}
		} else {
			int i;
			if ((i = page.buscaBinaria(chave)) != -1) {
				Pagina paginaFilhaEsquerda = page.mPaginasFilhas[i];
				Pagina paginaFilhaDireita = page.mPaginasFilhas[i + 1];
				if (paginaFilhaEsquerda.mNumChaves >= D) {
					Pagina predecessorPage = paginaFilhaEsquerda;
					Pagina pageRasura = predecessorPage;
					while (!predecessorPage.mEhPaginaFolha) {
						pageRasura = predecessorPage;
						predecessorPage = predecessorPage.mPaginasFilhas[page.mNumChaves - 1];
					}
					page.mChaves[i] = predecessorPage.mChaves[predecessorPage.mNumChaves - 1];
					page.mObjects[i] = predecessorPage.mObjects[predecessorPage.mNumChaves - 1];
					delete(pageRasura, page.mChaves[i]);
				} else if (paginaFilhaDireita.mNumChaves >= D) {
					Pagina sucessorPage = paginaFilhaDireita;
					Pagina pageRasura = sucessorPage;
					while (!sucessorPage.mEhPaginaFolha) {
						pageRasura = sucessorPage;
						sucessorPage = sucessorPage.mPaginasFilhas[0];
					}
					page.mChaves[i] = sucessorPage.mChaves[0];
					page.mObjects[i] = sucessorPage.mObjects[0];
					delete(pageRasura, page.mChaves[i]);
				} else {
					int indiceChaveMediana = mergeNodes(paginaFilhaEsquerda,
							paginaFilhaDireita);
					moverChave(page, i, RIGHT_CHILD_PAGE, paginaFilhaEsquerda,
							indiceChaveMediana);
					delete(paginaFilhaEsquerda, chave);
				}
			} else {
				i = page.indicePageSubarvoreRaiz(chave);
				Pagina paginaFilha = page.mPaginasFilhas[i];
				if (paginaFilha.mNumChaves == D - 1) {
					Pagina irmaoFilhoEsquerda = (i - 1 >= 0) ? page.mPaginasFilhas[i - 1]
							: null;
					Pagina irmaoFilhoDireita = (i + 1 <= page.mNumChaves) ? page.mPaginasFilhas[i + 1]
							: null;
					if (irmaoFilhoEsquerda != null
							&& irmaoFilhoEsquerda.mNumChaves >= D) {
						paginaFilha.deslocarUmAdireita();
						paginaFilha.mChaves[0] = page.mChaves[i - 1];
						paginaFilha.mObjects[0] = page.mObjects[i - 1];
						if (!paginaFilha.mEhPaginaFolha) {
							paginaFilha.mPaginasFilhas[0] = irmaoFilhoEsquerda.mPaginasFilhas[irmaoFilhoEsquerda.mNumChaves];
						}
						paginaFilha.mNumChaves++;

						page.mChaves[i - 1] = irmaoFilhoEsquerda.mChaves[irmaoFilhoEsquerda.mNumChaves - 1];
						page.mObjects[i - 1] = irmaoFilhoEsquerda.mObjects[irmaoFilhoEsquerda.mNumChaves - 1];

						irmaoFilhoEsquerda.remove(
								irmaoFilhoEsquerda.mNumChaves - 1,
								RIGHT_CHILD_PAGE);
					} else if (irmaoFilhoDireita != null
							&& irmaoFilhoDireita.mNumChaves >= D) {
						paginaFilha.mChaves[paginaFilha.mNumChaves] = page.mChaves[i];
						paginaFilha.mObjects[paginaFilha.mNumChaves] = page.mObjects[i];
						if (!paginaFilha.mEhPaginaFolha) {
							paginaFilha.mPaginasFilhas[paginaFilha.mNumChaves + 1] = irmaoFilhoDireita.mPaginasFilhas[0];
						}
						paginaFilha.mNumChaves++;

						page.mChaves[i] = irmaoFilhoDireita.mChaves[0];
						page.mObjects[i] = irmaoFilhoDireita.mObjects[0];

						irmaoFilhoDireita.remove(0, LEFT_CHILD_PAGE);
					} else {
						if (irmaoFilhoEsquerda != null) {
							int medianKeyIndex = mergeNodes(paginaFilha,
									irmaoFilhoEsquerda);
							moverChave(page, i - 1, LEFT_CHILD_PAGE, paginaFilha,
									medianKeyIndex);
						} else if (irmaoFilhoDireita != null) {
							int medianKeyIndex = mergeNodes(paginaFilha,
									irmaoFilhoDireita);
							moverChave(page, i, RIGHT_CHILD_PAGE, paginaFilha,
									medianKeyIndex);
						}
					}
				}
				delete(paginaFilha, chave);
			}
		}
	}

	int mergeNodes(Pagina dstPage, Pagina srcPage) {
		int indiceChaveMediana;
		if (srcPage.mChaves[0] < dstPage.mChaves[dstPage.mNumChaves - 1]) {
			int i;

			if (!dstPage.mEhPaginaFolha) {
				dstPage.mPaginasFilhas[srcPage.mNumChaves + dstPage.mNumChaves
				                       + 1] = dstPage.mPaginasFilhas[dstPage.mNumChaves];
			}
			for (i = dstPage.mNumChaves; i > 0; i--) {
				dstPage.mChaves[srcPage.mNumChaves + i] = dstPage.mChaves[i - 1];
				dstPage.mObjects[srcPage.mNumChaves + i] = dstPage.mObjects[i - 1];
				if (!dstPage.mEhPaginaFolha) {
					dstPage.mPaginasFilhas[srcPage.mNumChaves + i] = dstPage.mPaginasFilhas[i - 1];
				}
			}

			indiceChaveMediana = srcPage.mNumChaves;
			dstPage.mChaves[indiceChaveMediana] = 0;
			dstPage.mObjects[indiceChaveMediana] = null;

			for (i = 0; i < srcPage.mNumChaves; i++) {
				dstPage.mChaves[i] = srcPage.mChaves[i];
				dstPage.mObjects[i] = srcPage.mObjects[i];
				if (!srcPage.mEhPaginaFolha) {
					dstPage.mPaginasFilhas[i] = srcPage.mPaginasFilhas[i];
				}
			}
			if (!srcPage.mEhPaginaFolha) {
				dstPage.mPaginasFilhas[i] = srcPage.mPaginasFilhas[i];
			}
		} else {

			indiceChaveMediana = dstPage.mNumChaves;
			dstPage.mChaves[indiceChaveMediana] = 0;
			dstPage.mObjects[indiceChaveMediana] = null;

			int compensar = indiceChaveMediana + 1;
			int i;
			for (i = 0; i < srcPage.mNumChaves; i++) {
				dstPage.mChaves[compensar + i] = srcPage.mChaves[i];
				dstPage.mObjects[compensar + i] = srcPage.mObjects[i];
				if (!srcPage.mEhPaginaFolha) {
					dstPage.mPaginasFilhas[compensar + i] = srcPage.mPaginasFilhas[i];
				}
			}
			if (!srcPage.mEhPaginaFolha) {
				dstPage.mPaginasFilhas[compensar + i] = srcPage.mPaginasFilhas[i];
			}
		}
		dstPage.mNumChaves += srcPage.mNumChaves;
		return indiceChaveMediana;
	}

	void moverChave(Pagina srcPage, int srcIndiceChave, int indiceFilho,
			Pagina dstNode, int indiceChaveMediana) {
		dstNode.mChaves[indiceChaveMediana] = srcPage.mChaves[srcIndiceChave];
		dstNode.mObjects[indiceChaveMediana] = srcPage.mObjects[srcIndiceChave];
		dstNode.mNumChaves++;

		srcPage.remove(srcIndiceChave, indiceFilho);

		if (srcPage == mPaginaRaiz && srcPage.mNumChaves == 0) {
			mPaginaRaiz = dstNode;
		}
	}

	public Object search(int chave) {
		return search(mPaginaRaiz, chave);
	}

	public Object search(Pagina page, int chave) {
		int i = 0;
		while (i < page.mNumChaves && chave > page.mChaves[i]) {
			i++;
		}
		if (i < page.mNumChaves && chave == page.mChaves[i]) {
			return page.mObjects[i];
		}
		if (page.mEhPaginaFolha) {
			return null;
		} else {
			return search(page.mPaginasFilhas[i], chave);
		}
	}

	public Object search2(int chave) {
		return search2(mPaginaRaiz, chave);
	}

	public Object search2(Pagina page, int chave) {
		while (page != null) {
			int i = 0;
			while (i < page.mNumChaves && chave > page.mChaves[i]) {
				i++;
			}
			if (i < page.mNumChaves && chave == page.mChaves[i]) {
				return page.mObjects[i];
			}
			if (page.mEhPaginaFolha) {
				return null;
			} else {
				page = page.mPaginasFilhas[i];
			}
		}
		return null;
	}

	private boolean atualizar(Pagina page, int chave, Object object) {
		while (page != null) {
			int i = 0;
			while (i < page.mNumChaves && chave > page.mChaves[i]) {
				i++;
			}
			if (i < page.mNumChaves && chave == page.mChaves[i]) {
				page.mObjects[i] = object;
				return true;
			}
			if (page.mEhPaginaFolha) {
				return false;
			} else {
				page = page.mPaginasFilhas[i];
			}
		}
		return false;
	}

	String printBTree(Pagina page) {
		String string = "";
		if (page != null) {
			if (page.mEhPaginaFolha) {
				for (int i = 0; i < page.mNumChaves; i++) {
					string += page.mObjects[i] + ", ";
				}
			} else {
				int i;
				for (i = 0; i < page.mNumChaves; i++) {
					string += printBTree(page.mPaginasFilhas[i]);
					string += page.mObjects[i] + ", ";
				}
				string += printBTree(page.mPaginasFilhas[i]);
			}
		}
		return string;
	}

	public String toString() {
		return printBTree(mPaginaRaiz);
	}

	void validacao() throws Exception {
		ArrayList<Integer> array = getChaves(mPaginaRaiz);
		for (int i = 0; i < array.size() - 1; i++) {
			if (array.get(i) >= array.get(i + 1)) {
				throw new Exception("B-Tree invalida: " + array.get(i)
						+ " maior do que " + array.get(i + 1));
			}
		}
	}

	ArrayList<Integer> getChaves(Pagina page) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		if (page != null) {
			if (page.mEhPaginaFolha) {
				for (int i = 0; i < page.mNumChaves; i++) {
					array.add(page.mChaves[i]);
				}
			} else {
				int i;
				for (i = 0; i < page.mNumChaves; i++) {
					array.addAll(getChaves(page.mPaginasFilhas[i]));
					array.add(page.mChaves[i]);
				}
				array.addAll(getChaves(page.mPaginasFilhas[i]));
			}
		}
		return array;
	}
}