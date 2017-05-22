package maze.test;
import static org.junit.Assert.*;
import org.junit.Test;
import maze.Logic.EstadoJogo;
import maze.Logic.Labirinto;

/**
 * Classe de testes unitários que almejam simular diversos eventos no jogo.
 *
 */

public class TestesLabirinto {
	Labirinto   l = new Labirinto();

	@SuppressWarnings("deprecation")
	@Test
	public void testMoveCelulaLivre() {
		l.setCelula('H', 1, 2);
		l.setCelula('E', 8, 1);
		l.setCelula('D', 3, 1);
		EstadoJogo controle = new EstadoJogo(false);
		controle.comecaJogo("y",1,10);
		controle.getL().setTab(controle.atualizaJogo("d",1));
		assertEquals(l.getTab(), controle.getL().getTab());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testMoveParede() {
		l.setCelula('H', 1, 1);
		l.setCelula('E', 8, 1);
		l.setCelula('D', 3, 1);
		EstadoJogo controle = new EstadoJogo(false);
		controle.comecaJogo("y",1,10);
		controle.getL().setTab(controle.atualizaJogo("a",1));
		assertEquals(l.getTab(), controle.getL().getTab());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testApanharEspada() {
		String str = "dddssssaaasss";
		l.setCelula('D', 3, 1);
		l.setCelula('A', 8, 1);
		EstadoJogo controle = new EstadoJogo(false);
		controle.comecaJogo("y",1,10);
		for(int i=0 ; i<str.length() ; i++)
			controle.getL().setTab(controle.atualizaJogo(str.substring(i, i+1),1));
		assertEquals(l.getTab(), controle.getL().getTab());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testMorteHeroi() {
		String str = "s";
		l.setCelula('H', 2, 1);
		l.setCelula('D', 3, 1);
		l.setCelula('E', 8, 1);
		EstadoJogo controle = new EstadoJogo(false);
		controle.comecaJogo("y",1,10);
		for(int i=0 ; i<str.length() ; i++)
			controle.getL().setTab(controle.atualizaJogo(str.substring(i, i+1),1));
		assertEquals(l.getTab(), controle.getL().getTab());
		assertEquals(true, controle.getFim());
		assertEquals(false, controle.getEstadoHeroi());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testMatarDragao() {
		String str = "dddssssaaassswwww";
		l.setCelula('A', 4, 1);
		l.setCelula(' ', 3, 1);
		EstadoJogo controle = new EstadoJogo(false);
		controle.comecaJogo("y",1,10);
		for(int i=0 ; i<str.length() ; i++)
			controle.getL().setTab(controle.atualizaJogo(str.substring(i, i+1),1));
		assertEquals(l.getTab(), controle.getL().getTab());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testVitoria() {
		String str = "dddssssaaassswwwwsdddddwwwwddssssd";
		EstadoJogo controle = new EstadoJogo(false);
		controle.comecaJogo("y",1,10);
		for(int i=0 ; i<str.length() ; i++)
			controle.getL().setTab(controle.atualizaJogo(str.substring(i, i+1),1));
		assertEquals(l.getTab(), controle.getL().getTab());
		assertEquals(true, controle.getFim());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSaidaAntecipada() {
		String str = "dddddddssssd";
		l.setCelula('H', 5, 8);
		l.setCelula('D', 3, 1);
		l.setCelula('E', 8, 1);
		EstadoJogo controle = new EstadoJogo(false);
		controle.comecaJogo("y",1,10);
		for(int i=0 ; i<str.length() ; i++)
			controle.getL().setTab(controle.atualizaJogo(str.substring(i, i+1),1));
		assertEquals(l.getTab(), controle.getL().getTab());
		assertEquals(false, controle.getFim());
	}
}