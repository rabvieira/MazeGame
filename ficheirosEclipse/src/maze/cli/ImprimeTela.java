package maze.cli;
import java.io.Serializable;
import maze.Logic.Labirinto;

/**
 * Classe responsável pela representação (no console) do objeto labirinto para com o utilizador.
 */

@SuppressWarnings("serial")
public class ImprimeTela implements Serializable{
	public ImprimeTela(){}

	public void ImprimeLabirinto(Labirinto l){
		for(int i=0 ; i<l.getTab().length ; i++){
			for(int j=0 ; j<l.getTab().length ; j++){
				System.out.print("[" + l.getCelula(i,j) + "] ");
			}
			System.out.print("\n");
		}
	}
}
