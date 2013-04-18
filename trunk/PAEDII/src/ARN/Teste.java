package ARN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ABB.No;
import ARN.NoARN.Cor;

import exceptions.ChaveInvalidaException;
import exceptions.NoJaExisteException;
import exceptions.VerificacaoFalhouException;

public class Teste {

	private ARN arn;
	public BufferedReader in;
	private final String caminhoArquivo = System.getProperty("user.dir")  + System.getProperty("file.separator") + "arn.in.txt";
	public Teste() {
		arn = new ARN();
		try {
			in = new BufferedReader(new FileReader(caminhoArquivo));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void insert(int numero) throws NoJaExisteException, ChaveInvalidaException, VerificacaoFalhouException{
		NoARN no = arn.inserir(numero);
		imprime();
		if (arn.arvoreRubroNegraValida()){
			System.out.println("[ok]");
		}
	}
	
	private void remove(int numero) throws VerificacaoFalhouException{
		NoARN no = arn.remover(numero);
		imprime();
		if (arn.arvoreRubroNegraValida()){
			System.out.println("[ok]");
		}
	}
	
	private void imprime(){
		visitarPreOrdem(arn.getRaiz());
	}
	private void visitarPreOrdem(NoARN node) {
		if (node != null){
			if (node.getNumero() != -1){
				System.out.print(node.getNumero());
				if (node.getCor().equals(Cor.PRETO)){
					System.out.print("p");
				}else {
					System.out.print("v");
				}
				System.out.print(" ");
			}
			visitarPreOrdem(node.getEsquerda());
			visitarPreOrdem(node.getDireita());
		}
	}
	
	private void buscaNo(int numero) {
		NoARN result = arn.busca(numero);
		System.out.println("no " + result.getNumero());
		System.out.println("cor: "+ result.getCor());
		if (result.getEsquerda()!=null)
			System.out.println("filho esquerdo "+ result.getEsquerda().getNumero());
		else
			System.out.println("sem filho esquerdo");
		if (result.getDireita()!=null)
			System.out.println("filho direito "+ result.getDireita().getNumero());
		else
			System.out.println("sem filho direito");
		if (result.Pai() != null)
			System.out.println("pai: " + result.Pai().getNumero());
		else
			System.out.println("sem pai");
	}
	
	public static void main(String[] args) throws IOException, NumberFormatException, NoJaExisteException, ChaveInvalidaException, VerificacaoFalhouException {
		Teste t = new Teste();
		String aux;
		String[] aux2;
		do{
			aux = t.in.readLine();
			aux2 = aux.split(" ");
			if (aux2[0].equals("insert")){
				t.insert(Integer.parseInt(aux2[1]));
			} else if (aux2[0].equals("remove")){
				t.remove(Integer.parseInt(aux2[1]));
			}else if (aux2[0].equals("busca")){
				t.buscaNo(Integer.parseInt(aux2[1]));
			}
		}while (aux != null);
	}
	
	
}
