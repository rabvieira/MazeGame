package maze.Logic;
import java.io.Serializable;
import java.util.Random;

/**
 * Classe que representa o elemento Heroi no jogo;
 * Possui coordenadas da sua posição atual no labirinto;
 * Um objeto desta classe: -move-se no labirinto mediante comando do utilizador. 
 * 						   -interage com objetos das classes Dragao, Aguia e Espada.
 */

@SuppressWarnings("serial")
public class Heroi extends ElementoLabirinto implements Serializable{
	private boolean vida;

	public Heroi(){
		vida = true;
	}

	//Heroi nasce no intervalo [0, YNASCf] de largura em uma celula vazia que seja fora de circuito fechado
	public void geraPosicao(Labirinto l){
		Random r = new Random();
		int lin, col, YNASCf = 2;
		boolean paragem=true;
		do{
			lin = r.nextInt(l.getTab().length - 1) + 1;
			col = r.nextInt(YNASCf) + 1;
			if(l.getCelula(lin, col) == ' ' && (l.getCelula(lin+1, col) != 'x' || l.getCelula(lin-1, col) != 'x' ||
					                            l.getCelula(lin, col+1) != 'x' || l.getCelula(lin, col-1) != 'x')){
				x = lin;
				y = col;
				paragem=false;
			}
		}while(paragem);
	}

	public void setVida(boolean estado){
		vida = estado;
	}

	public boolean getVida(){
		return vida;
	}

}
