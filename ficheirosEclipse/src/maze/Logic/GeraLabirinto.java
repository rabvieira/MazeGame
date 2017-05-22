package maze.Logic;
import java.io.Serializable;
import java.util.Random;
import java.util.Stack;

/**
 * Classe que implementa a geração do labirinto. Estes são de dois modos:
 * Default: Labirinto padrão definido na primeira iteração;
 * Aleatório: Utiliza algoritmo backtracker para gerar labirintos aleatorios.
 */

@SuppressWarnings("serial")
public class GeraLabirinto implements Serializable {
	private int Si,Sj;

	public void setSi(int i){Si = i;}
	public int  getSi(){return Si;}
	public void setSj(int j){Sj = j;}
	public int  getSj(){return Sj;}

	public char[][] geraLabirintoDefault(){
		char[][] labnew =  
			   {{'x','x','x','x','x','x','x','x','x','x'},
				{'x',' ',' ',' ',' ',' ',' ',' ',' ','x'},
				{'x',' ','x','x',' ','x',' ','x',' ','x'},
				{'x',' ','x','x',' ','x',' ','x',' ','x'},
				{'x',' ','x','x',' ','x',' ','x',' ','x'},
				{'x',' ',' ',' ',' ',' ',' ','x',' ','S'},
				{'x',' ','x','x',' ','x',' ','x',' ','x'},
				{'x',' ','x','x',' ','x',' ','x',' ','x'},
				{'x',' ','x','x',' ',' ',' ',' ',' ','x'},
				{'x','x','x','x','x','x','x','x','x','x'}};

		return labnew;
	}

	public char[][] geraMatrizX(int n){
		int i,j;
		char labX[][] = new char[n][n];
		for(i=0 ; i<n ; i++){
			for(j=0 ; j<n ; j++){
				if(i%2==0 || j%2==0)
					labX[i][j] = 'x';
				else
					labX[i][j] = ' ';
			}
		}
		return labX;
	}	

	public char[][] inputSaida(char[][] matriz, int x, int y){
		char lab[][] = matriz;
		setSi(x);
		setSj(y);
		lab[x][y] = 'S';
		return lab;
	}

	public int escolheCaminho(int Sx, int Sy, int tam, int[][] visitados){
		Random r = new Random();
		boolean v = false, up=false, down=false, left=false, right=false;
		int n=0;
		while(!v){
			n = r.nextInt(4);
			switch(n){
			case 0:
				if(Sx + 2 < tam && visitados[Sx + 2][Sy]==0)
					v = true;
				down = true;
				break;
			case 1:
				if(Sx - 2 > 0 && visitados[Sx - 2][Sy]==0)
					v = true;
				up = true;
				break;
			case 2:
				if(((Sy + 2) < tam) && visitados[Sx][Sy+2]==0)
					v = true;
				right = true;
				break;
			case 3:
				if(Sy - 2 > 0 && visitados[Sx][Sy-2]==0)
					v = true;
				left = true;
				break;
			}
			if(up && down && left && right)
				return -1;
		}
		return n;
	}	

	public char[][] geraLabirinto(int n){
		int Sx, Sy, direcao;
		int visitados[][] = new int[n][n];
		char labnew[][] = geraMatrizX(n);
		Stack<Integer> cel_x=new Stack<Integer>();
		Stack<Integer> cel_y=new Stack<Integer>();
		Random r = new Random();
		do
			Sx = r.nextInt(n-1) + 1;
		while((Sx%2)==0);
		Sy = n - 1;
		labnew = inputSaida(labnew,Sx,Sy);
		Sy--;
		while(true){
			visitados[Sx][Sy] = 1;
			direcao = escolheCaminho(Sx,Sy,n,visitados);
			if(direcao!=-1)
				switch(direcao){
				case 0: 
					cel_x.push(Sx);
					cel_y.push(Sy);
					labnew[Sx+1][Sy] = ' ';
					Sx+=2;
					break;
				case 1: 
					cel_x.push(Sx);
					cel_y.push(Sy);
					labnew[Sx-1][Sy] = ' ';
					Sx-=2; 
					break;
				case 2: 
					cel_x.push(Sx);
					cel_y.push(Sy);
					labnew[Sx][Sy+1] = ' ';
					Sy+=2; 
					break;
				case 3:
					cel_x.push(Sx);
					cel_y.push(Sy);
					labnew[Sx][Sy-1] = ' ';
					Sy-=2; 
					break;
				}
			else
				if(!cel_x.empty() && !cel_y.empty()){
					Sx = cel_x.pop();
					Sy = cel_y.pop();
				}
				else break;
		}
		return labnew;
	}
}
