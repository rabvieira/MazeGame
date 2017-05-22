package maze.gui;
import java.io.*;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import maze.Logic.EstadoJogo;

/**
 * Classe responsavel pela criação e inicialização do frame principal do jogo.
 * 
 */

@SuppressWarnings("serial")
public class GraphicInterface implements Serializable {
	private static final int TAMANHO_MAX_LABIRINTO = 17;
	private static final int TAMANHO_MINIMO_LABIRINTO = 9;
	private static final int NUM_MAX_DRAGOES = 5;
	private static final int NUM_MIN_DRAGOES = 1;
	private JFrame frame;
	private GraphicsDraw g = new GraphicsDraw();
	private boolean primeiraVez = true;
	private int labTam,numDragoes,numDragoesSonolentos;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicInterface window = new GraphicInterface();
					window.frame.setLocation(0, 0);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GraphicInterface() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 730);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panelButtons = new JPanel();
		frame.getContentPane().add(panelButtons, BorderLayout.EAST);
		g.setBackground(Color.WHITE);
		frame.getContentPane().add(g,BorderLayout.CENTER);

		//Codigo auto gerado pela aplicação window builder para o layout do panel que contem os botões (Form Layout)
		panelButtons.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("83px"),},
				new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
		}));

		//Criação dos botões. As configurações dos respectivos botões foram criadas em um método a parte.
		JButton btnInicio = new JButton("Novo Jogo");
		btnInicio.setFont(new Font("Calibri",20,11));
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnInicioConfiguracoes();
			}
		});	

		JButton btnConfig = new JButton("Configurar");
		btnConfig.setFont(new Font("Calibri",20,11));
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnConfigConfiguracoes();
			}
		});

		JButton btnExit = new JButton("        Sair       ");
		btnExit.setFont(new Font("Calibri",20,11));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnExitConfiguracoes();
			}
		});

		JButton btnCarregar = new JButton("Carregar");
		btnCarregar.setFont(new Font("Calibri",20,11));
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				btnCarregarConfiguracoes();
			}
		});

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Calibri",20,11));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSalvarConfiguracoes();
			}
		});

		//Adição dos botões nos panelButtons.
		panelButtons.add(btnInicio, "2, 2, left, top");
		panelButtons.add(btnConfig, "2, 4");
		panelButtons.add(btnSalvar, "2, 6");
		panelButtons.add(btnCarregar, "2, 8");
		panelButtons.add(btnExit, "2, 10, left, fill");
	}

	//Metodo criado para a leitura de numeros inteiros com o controle e tratamento dos erros associado a esse processo.
	private int lerInt(String message, boolean errorControl,int min,int max) {
		int num = 0;
		String s = "";
		while(errorControl){
			s = (String)JOptionPane.showInputDialog(message);
			errorControl = false;
			try{
				num = Integer.parseInt(s);
				if (num < min || num > max){
					JOptionPane.showMessageDialog(frame, "Introduza número entre " + min + " e " + max);
					errorControl = true;
				}
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(frame, "O numero digitado apresenta um formato invalido!");
				errorControl = true;
			}	
		}
		return num;
	}

	//Metodos responsaveis pelas configurações dos botões, os nomes dos metodos remetem a qual botão se refere a configuração implementada.
	private void btnInicioConfiguracoes() {
		boolean errorControl = true;	
		int n = JOptionPane.YES_OPTION;

		if(!primeiraVez)
			n = JOptionPane.showConfirmDialog(null,"Gostaria de reiniciar o jogo ?","Reinicio",JOptionPane.YES_NO_OPTION);

		switch (n) {
		case JOptionPane.YES_OPTION:		

			labTam = g.getLabTamTemp();
			numDragoes = g.getNumDragoesTemp();
			numDragoesSonolentos = g.getNumDragoesSonolentosTemp();

			if(primeiraVez){
				labTam = lerInt("Qual o tamanho do labirinto?",errorControl,TAMANHO_MINIMO_LABIRINTO,TAMANHO_MAX_LABIRINTO);
				if((labTam % 2) == 0)
					labTam = labTam + 1;
				errorControl = true;			
				numDragoes = lerInt("Quantos dragoes deseja no labirinto ?",errorControl,NUM_MIN_DRAGOES,NUM_MAX_DRAGOES);
				errorControl = true;
				numDragoesSonolentos = lerInt("Quantos dragoes deseja que sejam sonolentos? ",errorControl,0,numDragoes);

				g.setLabTamTemp(labTam);
				g.setNumDragoesTemp(numDragoes);
				g.setNumDragoesSonolentosTemp(numDragoesSonolentos);
				g.setK(labTam);
				g.setW(labTam);

				primeiraVez = false;
			}

			g.setLabTam(labTam);
			g.setNumDragoes(numDragoes);
			g.setNumDragoesSonolentos(numDragoesSonolentos);
			g.getControle().comecaJogo("n",numDragoes,labTam);
			g.getControle().setMovimenta(numDragoes);
			g.getControle().setSoneca(numDragoesSonolentos);
			g.repaint();
			g.setK(labTam);
			g.setW(labTam);

			g.repaint();
			g.requerirFoco();
			break;
		case JOptionPane.NO_OPTION:
			g.requerirFoco();
			break;
		default:
			;
		}
	}

	private void btnExitConfiguracoes() {
		JOptionPane.showMessageDialog(frame, "O jogo sera fechado");
		frame.setVisible(false);
		System.exit(0);
	}

	private void btnConfigConfiguracoes (){
		boolean errorControl = true;	
		int n;
		Object[] options = {"Configurações de labirinto","Configurações de comandos","Cancelar"};
		n = JOptionPane.showOptionDialog(frame,"O que deseja fazer?","Configurações", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,
				null,options,options[2]);

		switch (n) {
		case JOptionPane.YES_OPTION:		
			labTam = lerInt("Qual o tamanho do labirinto?",errorControl,TAMANHO_MINIMO_LABIRINTO,TAMANHO_MAX_LABIRINTO);
			if((labTam % 2) == 0)
				labTam = labTam + 1;
			errorControl = true;			
			numDragoes = lerInt("Quantos dragoes deseja no labirinto ?",errorControl,NUM_MIN_DRAGOES,NUM_MAX_DRAGOES);
			errorControl = true;
			numDragoesSonolentos = lerInt("Quantos dragoes deseja que sejam sonolentos? ",errorControl,0,numDragoes);

			g.setLabTamTemp(labTam);
			g.setNumDragoesTemp(numDragoes);
			g.setNumDragoesSonolentosTemp(numDragoesSonolentos);
			g.requerirFoco();
			break;	
		case JOptionPane.NO_OPTION:	
			g.setConfig(true);
			JOptionPane.showMessageDialog(null, "Digite o novo comando para o acionamento da aguia:");
			g.requerirFoco();	
			break;
		case JOptionPane.CANCEL_OPTION:
			g.requerirFoco();
			break;
		default:
			;
		}
	}

	private void btnSalvarConfiguracoes() {
		int opcao = 0;
		opcao = JOptionPane.showConfirmDialog(null,"Realmente deseja salvar o jogo ?","Salvar",JOptionPane.YES_NO_OPTION);

		switch (opcao) {
		case JOptionPane.YES_OPTION:
			if(primeiraVez == true)
				return;
			ObjectOutputStream os = null;
			try {
				os = new ObjectOutputStream(new FileOutputStream("ArquivoSalvo.dat"));
				os.writeObject(g.getControle());
			}
			catch (IOException e) { 

			}
			finally { if (os!= null)
				try {
					os.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}		
			g.requerirFoco();
			break;
		case JOptionPane.NO_OPTION:
			g.requerirFoco();
			break;
		default:
			;
		}
	}

	private void btnCarregarConfiguracoes() {
		int opcao = 0;
		opcao = JOptionPane.showConfirmDialog(null,"Realmente deseja carregar o ultimo jogo salvo?","Carregar",JOptionPane.YES_NO_OPTION);

		switch (opcao) {
		case JOptionPane.YES_OPTION:
			ObjectInputStream is = null;
			try {
				is = new ObjectInputStream(new FileInputStream("ArquivoSalvo.dat"));
				g.setControle((EstadoJogo)is.readObject());

				labTam = g.getControle().getL().getTab().length;
				numDragoes = g.getControle().getNumDragoes();
				numDragoesSonolentos = g.getControle().getNumDragoesSonolentos();
				g.setK(labTam);
				g.setW(labTam);
				g.setLabTam(labTam);
				g.setNumDragoes(numDragoes);
				g.setNumDragoesSonolentos(numDragoesSonolentos);
				g.setLabTamTemp(labTam);
				g.setNumDragoesTemp(numDragoes);
				g.setNumDragoesSonolentosTemp(numDragoesSonolentos);
				primeiraVez = false;
				g.repaint();
				JOptionPane.showMessageDialog(frame, "As configurações foram atualizadas!");

			}
			catch (IOException | ClassNotFoundException e) {
				JOptionPane.showMessageDialog(frame, "Falha ao abrir o arquivo!!! Verifique se ele existe. ");		
			}

			finally { if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}			
			g.requerirFoco();
			break;
		case JOptionPane.NO_OPTION:
			g.requerirFoco();
			break;
		default:
			;
		}
	}
}
