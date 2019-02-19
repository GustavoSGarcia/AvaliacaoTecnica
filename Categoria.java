public class Categoria {

	private String nome;
	private int limite_passageiros;
	
	Categoria(String nome, int limite_passageiros){
	
		this.nome = nome;
		this.limite_passageiros = limite_passageiros;
	}
	
	public String getNome(){
		
		return nome;
	}
	
	public int getLimitePassageiros(){
		
		return limite_passageiros;
	}
}