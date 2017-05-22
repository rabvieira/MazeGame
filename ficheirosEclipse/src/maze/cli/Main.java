package maze.cli;
import java.io.Serializable;
import java.util.Scanner;
import maze.Logic.EstadoJogo;

/**
 * Classe que possui o método main (responsável por indicar o início do programa). Controla o fluxo do 
 * código com acesso as outras classes estruturando-as para o funcionamento correto deste. 
 * comandos Heroi: "w": up / "s": down / "d": right / "a": left
 */

@SuppressWarnings("serial")
public class Main implements Serializable{
	private static final int maxDragao = 5;
	private static Scanner sc;

	private static int readInt(Scanner s, String prompt, int min, int max) {
		System.out.println(prompt);
		while(true) {
			try {
				int num = s.nextInt();
				if (num < min || num > max)
					System.out.println("Introduza número entre " + min + " e " + max);
				else
					return num;
			} catch (Exception e) {
				s.nextLine();
				System.out.println("Introduza de novo: ");
			}
		}	
	}	

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		String s = null;
		int nDragoes = 1;
		int confDragao = 0;
		int tamLab = 0;

		ImprimeTela e = new ImprimeTela();
		EstadoJogo controle = new EstadoJogo(false);

		System.out.print("Configuração Padrão? [y/n]: ");
		s = sc.next();

		if(s.equals("y")){
			controle.comecaJogo(s,nDragoes,tamLab);
			controle.setMovimenta(nDragoes);
		}
		else{
			tamLab = readInt(sc, "Digite o tamanho do labirinto ", 11, 51);	
			//tamLab deve ser sempre ímpar devido ao algoritmo de geração deste, tomar isto como premissa
			if((tamLab % 2) == 0)
				tamLab = tamLab + 1;

			nDragoes = readInt(sc, "Quantos dragoes deseja no labirinto ? ", 1, maxDragao);
			controle.comecaJogo(s,nDragoes,tamLab);

			confDragao = readInt(sc, "Quantos dragoes em movimento ? ", 0, nDragoes);
			controle.setMovimenta(confDragao);

			confDragao = readInt(sc, "Quantos deles sonolentos ? ", 0, nDragoes);
			controle.setSoneca(confDragao);	
		}

		e.ImprimeLabirinto(controle.getL());
		while(!controle.getFim()){
			s = sc.next();
			controle.getL().setTab(controle.atualizaJogo(s,nDragoes));
			e.ImprimeLabirinto(controle.getL());
		}

		if(controle.getEstadoHeroi())
			System.out.println("Parabens !!! voce conseguiu sair do labirinto.");
		else
			System.out.println("Que pena, o heroi foi morto pelo dragão.");
	}
}
