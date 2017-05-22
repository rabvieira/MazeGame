package maze.Logic;
import java.io.Serializable;
import java.util.Random;

/**
 * Classe que representa o elemento Dragao no jogo;
 * Possui coordenadas da sua posição atual no labirinto;
 * Um objeto desta classe: -move-se aleatorioamente pelo labirinto por posições validas. 
 * 						   -interage com objetos das classes Heroi, Aguia e Espada.
 */

@SuppressWarnings("serial")
public class Dragao extends ElementoLabirinto implements Serializable{
	private boolean vida, acordado, movimenta, soneca;

	public Dragao(){
		vida = true;
		acordado = true;
		movimenta = false;
		soneca = false;
	}

	//Celulas não validas para nascimento: adjacente ao Heroi e dentro de circuito fechado
	public boolean celula_valida(Labirinto l, int x, int y){
		if(l.getCelula(x+1,y)=='H' || l.getCelula(x-1,y)=='H' ||
		   l.getCelula(x,y+1)=='H' || l.getCelula(x,y-1)=='H' ||
		  (l.getCelula(x+1, y) == 'x' && l.getCelula(x-1, y) == 'x' && 
		   l.getCelula(x, y+1) == 'x' && l.getCelula(x, y-1) == 'x'))
			return false;
		else
			return true;
	}
	
	//Dragoes nascem no intervalo [YNASCi, YNASCf] de largura
	public void geraPosicao(Labirinto l){
		int YNASCi = 3, YNASCf = l.getTab().length - 2;
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

	public boolean getVida(){
		return vida;
	}
	
	public void setVida(boolean estado){
		vida = estado;
	}
	
	public void setAcordado(boolean estado){
		acordado = estado;
	}

	public boolean getAcordado(){
		return acordado;
	}

	public boolean getMovimenta(){
		return movimenta;
	}
	
	public void setMovimenta(boolean estado){
		movimenta = estado;
	}
	
	public boolean getSoneca(){
		return soneca;
	}
	
	public void setSoneca(boolean estado){
		soneca = estado;
	}
}
