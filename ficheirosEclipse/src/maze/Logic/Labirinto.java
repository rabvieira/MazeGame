package maze.Logic;
import java.io.Serializable;

/**
 * Classe que representa o Labirinto no jogo;
 * Comporta a representação dos outros elementos do jogo de forma a possibilitar a interação
 * entre estes. Possui apenas uma saída.
 */

@SuppressWarnings("serial")
public class Labirinto implements Serializable {
	private char tab[][];
	private int Si,Sj;
	private GeraLabirinto g = new GeraLabirinto();

	public Labirinto(int n){
		tab = g.geraLabirinto(n);
	}

	public Labirinto(){
		tab = g.geraLabirintoDefault();
	}

	public void setCelula(char c, int x, int y){
		tab[x][y] = c;
	}

	public char getCelula(int x, int y){
		return tab[x][y];
	}

	public void setTab(char[][] labnew){
		tab = labnew;
	}

	public char[][] getTab(){
		return tab;
	}

	public void setSi(int i){
		Si = i;
	}

	public int getSi(){
		return Si;
	}

	public void setSj(int j){
		Sj = j;
	}

	public int getSj(){
		return Sj;
	}
}
