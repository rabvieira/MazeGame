package maze.Logic;
import java.io.Serializable;

/**
 * Classe que representa o elemento Aguia no jogo;
 * Possui coordenadas da sua posição atual e posição origem da chamada no labirinto;
 * Um objeto desta classe: -move-se em direção à espada no labirinto.
 * 						   -só é chamada uma vez por um objeto Heroi.
 * 						   -interage com objetos das classes Heroi, Dragao e Espada.
 */

@SuppressWarnings("serial")
public class Aguia extends ElementoLabirinto implements Serializable{
	private boolean vida = true;
	private boolean voo = false;
	private boolean espada = false;
	private char vaiVoo;
	private int memoriaX, memoriaY;

	public Aguia(int x ,int y){
		memoriaX = x;
		memoriaY = y;
	}

	public boolean getVida(){
		return vida;
	}

	public void setVida(boolean estado){
		vida = estado;
	}

	public int getMemoriaX(){
		return memoriaX;
	}

	public void setMemoriaX(int x){
		memoriaX = x;
	}

	public int getMemoriaY(){
		return memoriaY;
	}

	public void setMemoriaY(int y){
		memoriaY = y;
	}

	public char getVaiVoo(){
		return vaiVoo;
	}
	public void setVaiVoo(char c){
		vaiVoo = c;
	}

	public void setEspada(boolean estado){
		espada = estado;
	}
	public boolean getEspada(){
		return espada;
	}

	public boolean getVoo(){
		return voo;
	}
	public void setVoo(boolean estado){
		voo = estado;
	}

	@Override
	public void geraPosicao(Labirinto l) {

	}
}
