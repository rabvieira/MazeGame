package maze.Logic;
import java.io.Serializable;

/**
 * Classe Abstrata que representa um elemento genérico do jogo.
 */

@SuppressWarnings("serial")
public abstract class ElementoLabirinto implements Serializable{
	protected int x;
	protected int y;

	public abstract void geraPosicao(Labirinto l);

	public void geraPosicaoDefault(int x,int y){
		this.x = x;
		this.y = y;
	}

	public int getx(){
		return x;
	}

	public int gety(){
		return y;
	}

	public void setx(int xnew){
		x = xnew;
	}

	public void sety(int ynew){
		y = ynew;
	}
}
