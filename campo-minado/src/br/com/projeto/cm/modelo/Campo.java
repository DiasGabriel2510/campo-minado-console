package br.com.projeto.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.projeto.cm.excecao.ExplosaoException;

public class Campo {

	private final int lin;  //atributo para linha
	private final int col;  //atributo para coluna	
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int lin, int col){
		this.lin = lin;
		this.col = col;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linDiferente = lin != vizinho.lin;
		boolean colDiferente = col != vizinho.col;
		boolean diagonal = linDiferente && colDiferente; //somente se linha e coluna forem diferentes
		
		//calculo das distancias entre linhas e colunas
		int deltaLin = Math.abs(lin - vizinho.lin); //calcula a diferenca entre as linhas
		int deltaCol = Math.abs(col - vizinho.col); //calcula a diferenca entre as colunas
		int deltaGeral = deltaLin + deltaCol; //soma das diferencas

		
		if(deltaGeral == 1 && !diagonal) { //diferenca deve ser igual a 1 caso esteja na mesma linha ou coluna
			vizinhos.add(vizinho);
			return true;
		} else if(deltaGeral == 2 && diagonal) { //diferenca deve ser igual a 2 caso esteja na diagonal
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	
	void alternarMarcacao() {  //alternar marcacao para saber se bombas estao naquela regiao
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	boolean abrir() {
		if(!aberto && !marcado){  //se campo nao estiver aberto e nao marcado entao ira abrir
			aberto = true;
			
			if(minado) {
				throw new ExplosaoException();
			}
			
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			
			return true;
		} else {
		
			return false;
		}
	}
	
	boolean vizinhancaSegura(){
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar(){
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}
	
	public int getLin() {
		return lin;
	}

	public int getCol() {
		return col;
	}

	boolean objetivoAlcancado() {  //abrir espacos seguros ou marcar minas
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {  //reiniciar jogo
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString() {
		if(marcado) {
			return "X";
		} else if(aberto && minado) {
			return "*";
		} else if(aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		} else if(aberto) {
			return " ";
		} else {
			return "?";
		}
	}

}
