package maze.Logic;
import java.io.Serializable;
import java.util.Random;

/**
 * Classe que representa o elemento Espada no jogo;
 * Possui coordenadas da sua posição atual no labirinto;
 * Um objeto desta classe: -possui posição estatica no labirinto.
 * 						   -apenas desaparece quando capturada pelo Heroi ou Aguia.
 * 						   -interage com objetos das classes Heroi, Dragao e Aguia.
 */

@SuppressWarnings("serial")
public class Espada extends ElementoLabirinto implements Serializable {
	private boolean espada = false;

	public boolean getEspada(){
		return espada;
	}

	//Celulas não validas para nascimento: adjacente ao Heroi ou em cima de [Dragoes e Heroi]
	public boolean celula_valida(Labirinto l, int x, int y){
		if(l.getCelula(x, y) == 'H' || l.getCelula(x, y) == 'D' || l.getCelula(x, y-1)=='H')
			return false;
		else
			return true;
	}
	
	//Espada nasce no intervalo [YNASCi, YNASCf] de largura
	public void geraPosicao(Labirinto l) {
		int YNASCi = 3, YNASCf = l.getTab().length - 3;
		Random r = new Random();
		int lin, col;
		boolean paragem=true;
		do{
			lin = r.nextInt(l.getTab().length - 1) + 1;
			col = YNASCi + r.nextInt(YNASCf - YNASCi);
			if((l.getCelula(lin,col) == ' ') && celula_valida(l,lin,col)){
				x = lin;
				y = col;
				paragem=false;
			}
		}while(paragem);
	}

	public void setEspada(boolean b) {
		espada = b;
	}
}
