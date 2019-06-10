package br.edu.ifpb.pweb2.SimpleEvents.util;

public enum Status {
	REPROVADO(1), APROVADO(0), NEUTRO(2);
	
	private final int valor;
	Status(int valorOpcao){
        valor = valorOpcao;
    }
    public int getValor(){
        return valor;
    }
}
