package maze.gui;

import java.io.Serializable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import maze.Logic.EstadoJogo;

/**
 * Classe responsável pela atualização dos gráficos do jogo e captação dos comandos inseridos pelo utilizador.
 */

@SuppressWarnings("serial")
public class GraphicsDraw  extends JPanel implements KeyListener,MouseListener,Serializable {

	int k = 0,w = 0,numDragoes=0,labTam=0,numDragoesSonolentos = 0,numDragoesTemp=0,labTamTemp=0,numDragoesSonolentosTemp = 0;

	private EstadoJogo controle  = new EstadoJogo(false);
	private int aguiaAcao        = KeyEvent.VK_ENTER;
	private int direitaAcao      = KeyEvent.VK_RIGHT;
	private int esquerdaAcao     = KeyEvent.VK_LEFT;
	private int baixoAcao        = KeyEvent.VK_DOWN;
	private int cimaAcao         = KeyEvent.VK_UP;
	private boolean config       = false;

	private int configControlesAux = 1;

	//Construtor da classe o qual é adicionado um keyListener e um mouseListener.
	public GraphicsDraw() {
		this.addKeyListener(this);
		this.addMouseListener(this);
	}

	//Metodos get e set padrões para as variaveis presentes na classe.
	public void setConfig(boolean b){
		config = b;
	}

	public void setConfigControlesAux(int n){
		configControlesAux = n;
	}

	public int getConfigControlesAux(){
		return configControlesAux;
	}

	public int getNumDragoes(){
		return numDragoes;
	}

	public void setNumDragoes(int n){
		numDragoes = n;
	}

	public int getNumDragoesSonolentos() {
		return numDragoesSonolentos;
	}

	public void setNumDragoesSonolentos(int n){
		numDragoesSonolentos = n;
	}

	public int getLabTam(){
		return labTam;
	}

	public void setLabTam(int n){
		labTam = n;
	}

	public EstadoJogo getControle(){
		return controle;	
	}

	public void setControle(EstadoJogo c){
		this.controle = c;	
	}

	public int getLabTamTemp() {

		return labTamTemp;
	}

	public int getNumDragoesTemp() {

		return numDragoesTemp;
	}

	public int getNumDragoesSonolentosTemp() {

		return numDragoesSonolentosTemp;
	}

	public void setLabTamTemp(int labTam) {
		labTamTemp = labTam;

	}

	public void setNumDragoesTemp(int numDragoes) {
		numDragoesTemp = numDragoes;
	}

	public void setNumDragoesSonolentosTemp(int numDragoesSonolentos) {
		numDragoesSonolentosTemp = numDragoesSonolentos;	
	}

	//Metodo paint resposavel pela atualização dos graficos do jogo.
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		int x = 0;
		int y = 0;

		//Carregamento das imagens relativa aos elementos presentes no labirinto.
		ImageIcon HeroiSprite = new ImageIcon("heroi.png");
		ImageIcon HeroiEspada = new ImageIcon("hero.png");
		ImageIcon EspadaSprite = new ImageIcon("espada.png");
		ImageIcon DragaoSprite = new ImageIcon("dragao.png");
		ImageIcon DragaoEspada = new ImageIcon("dragao2.png");
		ImageIcon DragaoSonolento = new ImageIcon("DragaoSono.png");
		ImageIcon AguiaSprite = new ImageIcon("aguia.png");

		//Laço for responsavel pelo desenho do labirinto e seus respectivos elementos.
		//Traduz a informação alfa-numérica presente na variavel controle em informação gráfica.
		for(int i = 0; i < k; i++){
			for(int j = 0; j < w ; j++){
				if(controle.getL().getCelula(i, j) == 'x'){
					g.setColor(Color.GRAY);
					g.fillRect(x, y, 40, 40);
					g.setColor(Color.BLACK);
					g.drawRect(x, y, 40, 40);
				}
				if(controle.getL().getCelula(i, j) == 'H'){
					Image img = HeroiSprite.getImage();
					g.drawImage(img, x, y, null);
				}

				if(controle.getL().getCelula(i, j) == 'A'){
					Image img = HeroiEspada.getImage();
					g.drawImage(img, x, y, null);		
				}

				if(controle.getL().getCelula(i, j) == 'a'){
					Image img = AguiaSprite.getImage();
					g.drawImage(img, x, y, null);		
				}

				if(controle.getL().getCelula(i, j) == 'E'){
					Image img = EspadaSprite.getImage();
					g.drawImage(img, x, y, null);
				}

				if(controle.getL().getCelula(i, j) == 'D'){
					Image img = DragaoSprite.getImage();
					g.drawImage(img, x, y, null);
				}	

				if(controle.getL().getCelula(i, j) == 'F'){
					Image img = DragaoEspada.getImage();
					g.drawImage(img, x, y, null);
				}	

				if(controle.getL().getCelula(i, j) == 'z'){
					Image img = DragaoSonolento.getImage();
					g.drawImage(img, x, y, null);
				}	

				x = x + 40;
			}
			y = y + 40;
			x = 0;
		}
	}

	//Metodos set para as variaveis k e w.
	public void setK(int k){
		this.k = k;
	}

	public void setW(int w){
		this.w = w;
	}

	//Método keyPressed, interpreta a entrada digitada pelo utilizados e aciona a variavel controle de jogo com sua respectiva informação.
	//Responsável pela movimentação do personagem e acionamento da águia.
	//Responsavel pela mudança dos controladores do jogo.
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//flag para ativar a alteração de comandos.
		if(config == true){
			comandosConfiguracoes(e, key);
		}
		else{
			if (key == aguiaAcao){
				//responsável pela atualização da variavel controle.getFim() para false no inicio do jogo.
				if(controle.getFim() == true)
					controle.getL().setTab(controle.atualizaJogo("z",numDragoes));

				controle.getL().setTab(controle.atualizaJogo("voo",numDragoes));
			}

			if (key == direitaAcao){
				controle.getL().setTab(controle.atualizaJogo("d",numDragoes));
			}
			if (key == esquerdaAcao){
				controle.getL().setTab(controle.atualizaJogo("a",numDragoes));
			}
			if (key == baixoAcao){
				controle.getL().setTab(controle.atualizaJogo("s",numDragoes));
			}
			if (key ==cimaAcao){
				controle.getL().setTab(controle.atualizaJogo("w",numDragoes));
			}
			repaint();

			if(controle.getFim() == true){
				int n = JOptionPane.showConfirmDialog(null,"Fim de jogo !! Gostaria de reiniciar o jogo ?","Reinicio",JOptionPane.YES_NO_OPTION);

				switch (n) {
				case JOptionPane.YES_OPTION:
					setLabTam(labTamTemp);
					setNumDragoes(numDragoesTemp);
					getControle().comecaJogo("n",numDragoesTemp,labTamTemp);
					getControle().setMovimenta(numDragoesTemp);
					getControle().setSoneca(numDragoesSonolentosTemp);
					setK(labTamTemp);
					setW(labTamTemp);
					repaint();
					break;
				case JOptionPane.NO_OPTION:
					JOptionPane.showMessageDialog(getComponentPopupMenu(), "fim de jogo... saindo");
					System.exit(0);
					break;
				default:
					;
				}
			}
		}
	}

	private void comandosConfiguracoes(KeyEvent e, int key) {

		switch(configControlesAux){
		case 1:
			//numero elevado necessario para nao se confundir com outras teclas do teclado !
			aguiaAcao        = 1000;
			direitaAcao      = 1000;
			esquerdaAcao     = 1000;
			baixoAcao        = 1000;
			cimaAcao         = 1000;

			aguiaAcao = e.getKeyCode(); 
			JOptionPane.showMessageDialog(null, "Comando da aguia atualizado para"      + " " + KeyEvent.getKeyText(key));
			configControlesAux++;
			JOptionPane.showMessageDialog(null, "Digite o novo comando para o mover o heroi para cima:");
			requerirFoco();
			break;

		case 2:
			cimaAcao = e.getKeyCode();
			if(cimaAcao == aguiaAcao){
				cimaAcao = 1000;
				JOptionPane.showMessageDialog(null, "A tecla " + KeyEvent.getKeyText(key) + " ja esta sendo utilizada!!!");
				JOptionPane.showMessageDialog(null, "Digite o novo comando para o mover o heroi para cima:");
				requerirFoco();
			}
			else{
				JOptionPane.showMessageDialog(null, "Comando para cima atualizado para"     + " " + KeyEvent.getKeyText(key));
				configControlesAux++;
				JOptionPane.showMessageDialog(null, "Digite o novo comando para o mover o heroi para esquerda:");
				requerirFoco();
			}
			break;

		case 3:
			esquerdaAcao = e.getKeyCode();

			if(esquerdaAcao == aguiaAcao || esquerdaAcao == cimaAcao){
				esquerdaAcao = 1000;
				JOptionPane.showMessageDialog(null, "A tecla " + KeyEvent.getKeyText(key) + " ja esta sendo utilizada!!!");
				JOptionPane.showMessageDialog(null, "Digite o novo comando para o mover o heroi para esquerda:");
				requerirFoco();
			}
			else{
				JOptionPane.showMessageDialog(null, "Comando para esquerda atualizado para" + " " + KeyEvent.getKeyText(key));
				configControlesAux++;
				JOptionPane.showMessageDialog(null, "Digite o novo comando para o mover o heroi para baixo:");
				requerirFoco();
			}
			break;

		case 4:
			baixoAcao = e.getKeyCode();

			if(baixoAcao == esquerdaAcao || baixoAcao == aguiaAcao || baixoAcao == cimaAcao){
				baixoAcao = 1000;
				JOptionPane.showMessageDialog(null, "A tecla " + KeyEvent.getKeyText(key) + " ja esta sendo utilizada!!!");
				JOptionPane.showMessageDialog(null, "Digite o novo comando para o mover o heroi para baixo:");
				requerirFoco();
			}
			else{
				JOptionPane.showMessageDialog(null, "Comando para baixo atualizado para"    + " " + KeyEvent.getKeyText(key));
				configControlesAux++;
				JOptionPane.showMessageDialog(null, "Digite o novo comando para o mover o heroi para direita:");
				requerirFoco();
			}
			break;

		case 5:
			direitaAcao = e.getKeyCode();
			if(direitaAcao == baixoAcao || direitaAcao == esquerdaAcao || direitaAcao == aguiaAcao || direitaAcao == cimaAcao){
				direitaAcao = 1000;
				JOptionPane.showMessageDialog(null, "A tecla " + KeyEvent.getKeyText(key) + " ja esta sendo utilizada!!!");
				JOptionPane.showMessageDialog(null, "Digite o novo comando para o mover o heroi para direita:");
				requerirFoco();
			}
			else{
				JOptionPane.showMessageDialog(null, "Comando para direita atualizado para"  + " " + KeyEvent.getKeyText(key));
				config = false;
				configControlesAux = 1;
			}
			break;
		default:
			;
		}
	}

	//Metodo mousePressed responsavel pelo foco na janela para que seja aceito os comandos digitados pelo utilizador.
	@Override
	public void mousePressed(MouseEvent e) {
		this.requestFocusInWindow(true);
	}

	//Metodo para requerir o foco para esta Classe
	public void requerirFoco(){
		this.requestFocusInWindow(true);
	}

	//Metodos que precisam constar pela implemetação das interfaces KeyListener e MouseListener.
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
