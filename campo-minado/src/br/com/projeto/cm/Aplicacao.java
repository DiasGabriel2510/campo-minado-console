package br.com.projeto.cm;

import br.com.projeto.cm.modelo.Tabuleiro;
import br.com.projeto.cm.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro (6, 6, 3);
		new TabuleiroConsole(tabuleiro);
		
	}
	
}

