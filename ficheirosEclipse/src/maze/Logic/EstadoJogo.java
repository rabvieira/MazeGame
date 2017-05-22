package maze.Logic;
import java.io.Serializable;
import java.util.Random;

/**
 * Classe responsavel pela logica e controle do jogo.
 * Responsavel por gerar, atualizar e verificar condições de fim para o jogo.
 */

@SuppressWarnings("serial")
public class EstadoJogo implements Serializable{
	private boolean fim;
	private Dragao d[] = new Dragao[10];
	private Heroi h;
	private Espada e;
	private Labirinto l;
	private Aguia a;
	private int numDragoes = 0;
	private int numDragoesSonolentos = 0;

	public EstadoJogo(boolean estadoAtual){
		fim = estadoAtual;	
	}

	public int getNumDragoes(){
		return numDragoes;
	}

	public int getNumDragoesSonolentos(){
		return numDragoesSonolentos;
	}

	public Labirinto getL(){
		return l;
	}

	public boolean getEstadoHeroi(){
		return h.getVida();
	}

	public boolean getFim(){
		return fim;
	}

	public void setFim(boolean estado){
		fim = estado;
	}

	public char[][] adicionaHeroi(Labirinto l,Heroi h){
		char[][] labnew = l.getTab(); 
		labnew[h.getx()][h.gety()] = 'H';
		return labnew;
	}

	public char[][] adicionaDragao(Labirinto l,Dragao d){
		char[][] labnew = l.getTab();
		labnew[d.getx()][d.gety()] = 'D';
		return labnew;
	}

	public char[][] adicionaEspada(Labirinto l, Espada e){
		char[][] labnew = l.getTab(); 
		labnew[e.getx()][e.gety()] = 'E';
		return labnew;
	}

	//Analisa se Heroi e Dragao estão adjacentes
	public boolean verificaDragao(Dragao d,Heroi h){
		if((h.getx() + 1 == d.getx() && d.gety() == h.gety()) ||
				(h.getx() - 1 == d.getx() && d.gety() == h.gety()) ||
				(h.getx() == d.getx() && d.gety() == h.gety() + 1) ||
				(h.getx() == d.getx() && d.gety() == h.gety() - 1))
			return true;
		else
			return false;
	}

	public char[][] movimentaDragao(Labirinto l,Dragao d){
		int k,w;
		boolean moveu = false, mantem = false;
		Random r = new Random();
		k = d.getx();
		w = d.gety();
		while(!moveu && !mantem){
			k = d.getx();
			w = d.gety();

			switch (r.nextInt(5)){
			case 0:
				w = w + 1;
				break;
			case 1:
				k = k + 1;
				break;
			case 2:
				k = k - 1;
				break;
			case 3:
				w = w - 1;
				break;
			case 4:	
				mantem = true;
			}
			if(l.getCelula(k,w) == 'x' || l.getCelula(k,w) == 'S' || l.getCelula(k,w) == 'D' || l.getCelula(k,w) == 'z' 
					|| l.getCelula(k, w) == 'H' || l.getCelula(k, w) == 'A' || l.getCelula(k, w) == 'a' || l.getCelula(k, w) == 'F'){
				moveu = false;
				k = d.getx();
				w = d.gety();
			}
			else
				moveu = true;
		}
		if(mantem){
			if(l.getCelula(d.getx(), d.gety()) == 'a' && a.getVida())
				l.setCelula('D', k, w);
			return l.getTab();
		}
		if(l.getCelula(d.getx(), d.gety()) == 'F' && l.getCelula(k, w) == ' '){
			l.setCelula('E', d.getx(), d.gety());
			l.setCelula('D', k, w);
		}
		if(l.getCelula(d.getx(), d.gety()) == 'D' && l.getCelula(k, w) == 'E'){
			l.setCelula(' ', d.getx(), d.gety());
			l.setCelula('F', k, w);
		}
		if((l.getCelula(d.getx(), d.gety()) == 'D' || l.getCelula(d.getx(), d.gety()) == 'a') && l.getCelula(k, w) == ' '){
			l.setCelula(' ', d.getx(), d.gety());
			l.setCelula('D', k, w);
		}	
		d.setx(k);
		d.sety(w);
		return l.getTab();
	}

	/**
	 * Metodo responsável pela inicialização do labirinto para o começo do jogo baseadas nas configurações especificadas pelo utilizador.
	 * @param s Indica se o utilizador escolheu o labirinto padrão ou não.
	 * @param n Indica o numero de dragões selecionado pelo utilizador.
	 * @param nlab Indica o tamanho do labirinto selecionado pelo utizador.
	 */
	
	public void comecaJogo(String s,int n,int nlab){
		d[0] = new Dragao();

		for(int i = 0; i < n; i++)
			d[i] = new Dragao();

		h = new Heroi();	
		e = new Espada();

		constroiLabirinto(s,nlab);

		if(s.equals("y")){
			h.geraPosicaoDefault(1,1);
			a = new Aguia(h.getx(), h.gety());
			a.geraPosicaoDefault(h.getx(), h.gety());
			l.setTab(adicionaHeroi (l,h));

			for(int i = 0; i < n; i++){
				d[i].geraPosicaoDefault(3,1);
				l.setTab(adicionaDragao(l,d[i]));
			}
			
			e.geraPosicaoDefault(8,1);
			l.setTab(adicionaEspada(l,e));
		}

		else{
			h.geraPosicao(l);
			a = new Aguia(h.getx(), h.gety());
			a.geraPosicaoDefault(h.getx(), h.gety());
			l.setTab(adicionaHeroi (l,h));

			for(int i = 0; i < n; i++){
				d[i].geraPosicao(l);
				l.setTab(adicionaDragao(l,d[i]));
			}

			e.geraPosicao(l);
			l.setTab(adicionaEspada(l,e));
		}
	}


	/**
	 * Metodo principal da classe, responsavel pela atualização em tempo real do estado do jogo.
	 * Realiza todas as análises necessárias para a atualização do jogo depois de ter recebido uma entrada do utilizador para movimentar o heroi.
	 * @param s Comando recebido pelo utilizador para movimentar o heroi ("a" = esquerda, "s" = baixo, "d" = direita, "w" = cima).
	 * @param n Variavel para informar o programa o numero de dragoes que o jogo atual contem.
	 * @return Retorna um matriz de caracteres com a atualização do jogo depois das analises feitas pelo metodo(movimento do heroi, do dragao,
	 * se a aguia foi acionada, etc...).
	 */
	public char[][] atualizaJogo(String s,int n){
		int k = 0,w = 0;
		Random r = new Random();
		k = h.getx();
		w = h.gety();

		switch(s){
		case "d":
			l.setCelula(' ',k,w);
			w = w + 1;			
			break;
		case "s":
			l.setCelula(' ',k,w);
			k = k + 1;
			break;
		case "w":
			l.setCelula(' ',k,w);
			k = k - 1;
			break;
		case "a":
			l.setCelula(' ',k,w);
			w = w - 1;
			break;
		case "voo":
			if(!e.getEspada()){
				a.setVoo(true);
				a.setMemoriaX(h.getx());
				a.setMemoriaY(h.gety());
			}
			return l.getTab();

		}

		//quando nao é possivel de o heroi se movimentar.
		if(l.getCelula(k,w) == 'x' || l.getCelula(k,w) == 'z' || l.getCelula(k, w) == 'D'){
			if(!e.getEspada())
				l.setCelula('H',h.getx(),h.gety());
			else
				l.setCelula('A',h.getx(),h.gety());
			setFim(false);
			return l.getTab();
		}

		movimentacaoHeroi(k, w);

		movimentacaoDragao(n, r);

		if(a.getVoo() && a.getVida())
			movimentacaoAguia(n);

		//verifica condiçoes finais de jogo
		for(int i = 0; i < n; i++){
			if(l.getCelula(k,w) == 'S'){
				if(d[i].getVida()){
					System.out.println("Só abre quando os dragoes sao mortos !!!");
					if(!e.getEspada())
						l.setCelula('H',k,w-1);
					else
						l.setCelula('A',k,w-1);
					l.setCelula('S',k,w);
					setFim(false);
					return l.getTab();
				}
				else
					setFim(true);
			}
			else
				setFim(false);
		}

		//combate(verifica se o dragao esta adjacente ao heroi)
		for(int i = 0; i < n; i++){
			if(verificaDragao(d[i],h) && d[i].getAcordado() && !e.getEspada() && d[i].getVida()){
				h.setVida(false);
				setFim(true);
				return l.getTab();
			}
			if(verificaDragao(d[i],h) && e.getEspada() && d[i].getVida() && l.getCelula(d[i].getx(),d[i].gety())!='A'){
				l.setCelula(' ',d[i].getx(),d[i].gety());
				System.out.println("O heroi matou o dragão!!!");
				d[i].setVida(false);
			}
		}
		return l.getTab();
	}

	private void movimentacaoAguia(int n) {
		int i,j;

		i = a.getx();
		j = a.gety();

		if(a.getx() == a.getMemoriaX() && a.gety() == a.getMemoriaY() && a.getEspada()){
			l.setCelula('E', a.getx(), a.gety());
			e.setx(a.getx());
			e.sety(a.gety());
			a.setEspada(false);
			a.setVoo(false);
			return;
		}

		if(!a.getEspada()){
			if(a.gety() < e.gety()){
				j  = j + 1;
			}

			if(a.gety() > e.gety()){
				j  = j - 1;
			}

			if(a.gety() == e.gety()){
				if(a.getx() < e.getx()){
					i  = i + 1;
				}
				if(a.getx() > e.getx()){
					i  = i - 1;
				}
			}

			if(a.getVaiVoo() == 'F'){
				l.setCelula('E', e.getx(),e.gety());
				System.out.println("A aguia foi morta pelo dragao!!!");
				a.setVida(false);
			}

			if(a.getVaiVoo() == 'x'){
				l.setCelula('x',a.getx(), a.gety());
			}

			if(a.getVaiVoo() == ' '){
				l.setCelula(' ',a.getx(), a.gety());
			}

			if(a.getVaiVoo() == 'D'){
				for(int w = 0; w < n; w++){
					if(d[w].getx() == a.getx() && d[w].gety() == a.gety()){
						l.setCelula('D',a.getx(), a.gety());
					}
				}

			}

			a.setVaiVoo(l.getCelula(i, j));

			if(a.getVaiVoo() == 'E'){
				System.out.println("A aguia pegou a espada!!!");
				a.setEspada(true);
				a.setx(i);
				a.sety(j);
				for(int w = 0; w < n; w++){
					if((a.getx() == d[w].getx() && a.gety() + 1 == d[w].gety())
							||(a.getx() == d[w].getx() && a.gety() - 1 == d[w].gety())
							||(a.getx() + 1 == d[w].getx() && a.gety() == d[w].gety())
							||(a.getx() - 1 == d[w].getx() && a.gety() == d[w].gety())){
						System.out.println("... Mas foi morta pelo Dragao.");
						l.setCelula('E', e.getx(), e.gety());
						a.setVida(false);
						return;
					}
				}
			}
		}
		else{
			if(a.gety() < a.getMemoriaY()){
				j  = j + 1;
			}

			if(a.gety() > a.getMemoriaY()){
				j  = j - 1;
			}

			if(a.gety() == a.getMemoriaY()){
				if(a.getx() < a.getMemoriaX()){
					i  = i + 1;
				}
				if(a.getx() > a.getMemoriaX()){
					i  = i - 1;
				}
			}

			if(a.getVaiVoo() == 'x'){
				l.setCelula('x',a.getx(), a.gety());
			}

			if(a.getVaiVoo() == ' '){
				l.setCelula(' ',a.getx(), a.gety());
			}

			if(a.getVaiVoo() == 'E'){
				l.setCelula(' ',a.getx(), a.gety());
			}

			a.setVaiVoo(l.getCelula(i, j));
		}

		l.setCelula('a', i, j);
		a.setx(i);
		a.sety(j);
	}

	private void movimentacaoDragao(int n, Random r) {
		for(int i = 0; i < n; i++){
			if(d[i].getVida() && d[i].getSoneca() && l.getCelula(d[i].getx(), d[i].gety()) != 'F'){
				if(r.nextInt(10)<2){
					d[i].setAcordado(false);
					l.setCelula('z',d[i].getx(),d[i].gety());
				}
				else{
					d[i].setAcordado(true);
					l.setCelula('D',d[i].getx(),d[i].gety());
				}
			}
			if(d[i].getVida() && d[i].getAcordado() && !d[i].getMovimenta()){
				if(l.getCelula(d[i].getx(), d[i].gety()) == 'a' && a.getVida())
					l.setCelula('D', d[i].getx(), d[i].gety());
			}
			if(d[i].getVida() && d[i].getAcordado() && d[i].getMovimenta()){
				l.setTab(movimentaDragao(l,d[i]));
			}
		}
	}

	private void movimentacaoHeroi(int k, int w) {
		if(l.getCelula(k,w) == ' ' && !e.getEspada()){
			l.setCelula('H',k,w);
			h.setx(k);
			h.sety(w);
		}

		if(l.getCelula(k,w) == ' ' && e.getEspada()){
			l.setCelula('A',k,w);
			h.setx(k);
			h.sety(w);
		}

		if(l.getCelula(k,w) == 'E'){
			System.out.println("O heroi pegou a espada !!!");
			e.setEspada(true);
			l.setCelula('A',k,w);
			h.setx(k);
			h.sety(w);
		}

		if(a.getVoo() == false){
			a.setx(k);
			a.sety(w);
		}
	}

	public void setMovimenta(int k) {
		for(int i = 0; i < k; i++){
			d[i].setMovimenta(true);
		}
	}

	public void setSoneca(int k) {
		numDragoesSonolentos = k;
		for(int i = 0; i < k; i++){
			d[i].setSoneca(true);
		}
	}

	public void constroiLabirinto(String s,int nlab) {
		if(s.equals("y"))
			l = new Labirinto();
		else 
			l = new Labirinto(nlab);
	}
}

