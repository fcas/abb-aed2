package ABB;
import java.util.Scanner;

import exceptions.ChaveInvalidaException;
import exceptions.NoJaExisteException;
import exceptions.PredecessorNotFoundException;
import exceptions.SuccessorNotFoundException;


public class Menu {

	private ABB abb;
	private int option;
	Scanner in = new Scanner(System.in);
	
	public Menu() {
		abb = new ABB();
	}
	
	public void mainMenu(){
		System.out.println("\n------------------------\n");
		System.out.println("Arvore Binaria de Busca:");
		System.out.println("0- Sair");
		System.out.println("1- Inserir");
		System.out.println("2- Remover");
		System.out.println("3- Imprimir Percurso Em Ordem");
		System.out.println("4- Imprimir Percurso Pre Ordem");
		System.out.println("5- Imprimir Percurso Pos Ordem");
		System.out.println("6- Buscar No");
		System.out.println("7- Procurar Sucessor");
		System.out.println("8- Procurar Predecessor");
		System.out.println("9- Buscar No Maximo");
		System.out.println("10- Buscar No Minimo");
		System.out.println("11- Verificar se Arvore � ABB");
	}
	
	public void init() throws NoJaExisteException, SuccessorNotFoundException, PredecessorNotFoundException{
		
		do{
			mainMenu();
			option = in.nextInt();
			
			switch(option){
				case 1: insercao();
						break;
				case 2: remocao();
						break;
				case 3: inOrdem();
						break;
				case 4: preOrdem();
						break;
				case 5: posOrdem();
						break;
				case 6: buscaNo();
						break;
				case 7: sucessor();
						break;
				case 8: predecessor();
						break;
				case 9: max();
						break;
				case 10: min();
						break;
				case 11: validacao();
						break;
				case 0: in.close();
						System.out.println("Ate logo!");
						return;
				default: 
						break;
					
			}
			
		}while (option != 0);
	}
	
	private void validacao() {
		System.out.println("Arvore Binaria de Busca Valida: " + abb.arvoreBinariaBuscaValida());
	}

	private void min() {
		No result = abb.min(abb.getRaiz());
		System.out.println("O menor no da arvore e: " + result.getNumero());
	}

	private void max() {
		No result = abb.max(abb.getRaiz());
		System.out.println("O maior no da arvore e: " + result.getNumero());
	}

	private void predecessor() throws PredecessorNotFoundException{
		int numero;
		System.out.println("Digite o numero do no que se quer achar o predecessor");
		numero = in.nextInt();
		
		No result = abb.predecessor(numero);
		System.out.println("O predecessor de "+ numero + " � " + result.getNumero());
	}

	private void sucessor() throws SuccessorNotFoundException{
		int numero;
		System.out.println("Digite o numero do no que se quer achar o sucessor");
		numero = in.nextInt();
		
		No result = abb.sucessor(numero);
		System.out.println("O sucessor de "+ numero + " � " + result.getNumero());
	}

	private void buscaNo() {
		int numero;
		System.out.println("Digite o numero do no a ser buscado");
		numero = in.nextInt();
		
		No result = abb.busca(numero);
		System.out.println("no " + result.getNumero());
		if (result.getEsquerda()!=null)
			System.out.println("filho esquerdo "+ result.getEsquerda().getNumero());
		else
			System.out.println("sem filho esquerdo");
		if (result.getDireita()!=null)
			System.out.println("filho direito "+ result.getDireita().getNumero());
		else
			System.out.println("sem filho direito");
	}

	private void posOrdem() {
		abb.printPos();
	}

	private void preOrdem() {
		abb.printPre();
	}

	private void inOrdem() {
		abb.print();
	}

	private void remocao() {
		int numero;
		System.out.println("Digite o numero a ser removido da arvore");
		numero = in.nextInt();
		
		boolean success = (abb.remove(numero) != null);
		if (success)
			System.out.println("remocao bem sucedida");
		else
			System.out.println("remocao falhou");
	}

	private void insercao() throws NoJaExisteException {
		int numero;
		System.out.println("Digite o numero a ser inserido na arvore");
		numero = in.nextInt();
		
		try {
			abb.inserir(numero);
		} catch (ChaveInvalidaException e) {
			System.out.println("Valor inserido invalido.\nPor favor insira um numero nao negativo");
		}
	}

	public static void main(String[] args) throws NoJaExisteException, SuccessorNotFoundException, PredecessorNotFoundException {
		Menu menu = new Menu();
		menu.init();
	}
	
}