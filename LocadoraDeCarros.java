import java.util.*;
import java.io.*;

/**
* Classe que representa uma locadora de carros.
* A locadora tem um determinado número de lojas
* onde cada loja tem uma categoria diferente.
* Seus principais atributos são taxas que variam
* de acordo com a categoria, dia da semana e fidelidade
* do cliente. Sua função é retorna o carro mais
* barato e o nome da loja que este está de acordo
* com a quantidade de passageiros e as datas 
* informadas pelo usuário. Para isso, os dados
* de entrada são escritos em um arquivo de texto,
* depois lidos e interpretados. Assim, é possível
* calcular a taxa para cada uma das lojas e obter
* a menor delas.
*
* @author Gustavo Sousa Garcia.
* @version 1.0.
*/

public class LocadoraDeCarros{

	private String loja;
	private Categoria categoria;
	private double taxa_regular_meio_de_semana;
	private double taxa_regular_fim_de_semana;
	private double taxa_fidelidade_meio_de_semana;
	private double taxa_fidelidade_fim_de_semana;

/** O construtor faz com que as instâncias
* da classe sejam inicializadas com os valores
* passados por parâmetro. Nenhum objeto desta
* classe pode ter seus atributos modificados 
* após serem criados.
* @param categoria: é do tipo Categoria,
* classe que está no mesmo pacote que 
* LocadoraDeCarros.
*/

	public LocadoraDeCarros(String loja, Categoria categoria, double taxa_regular_meio_de_semana, double taxa_regular_fim_de_semana, double taxa_fidelidade_meio_de_semana, double taxa_fidelidade_fim_de_semana){

		this.loja = loja;
		this.categoria = categoria;
		this.taxa_regular_meio_de_semana = taxa_regular_meio_de_semana;
		this.taxa_regular_fim_de_semana = taxa_regular_fim_de_semana;
		this.taxa_fidelidade_meio_de_semana = taxa_fidelidade_meio_de_semana;
		this.taxa_fidelidade_fim_de_semana = taxa_fidelidade_fim_de_semana;
	}
	
	/** Retorna o nome da loja */
	
	public String getLoja(){
	
		return loja;
	}
	
	/** Retorna a categoria de carros disponível na loja */
	
	public Categoria getCategoria(){
	
		return categoria;
	}

	/** Retorna o valor da taxa de segunda a sexta para um cliente regular */
	
	public double getTaxaRegularMeioDeSemana(){
	
		return taxa_regular_meio_de_semana;
	}
	
	/** Retorna o valor da taxa aos sábados e domingos para um cliente regular */

	public double getTaxaRegularFimDeSemana(){
	
		return taxa_regular_fim_de_semana;
	}
	
	/** Retorna o valor da taxa de segunda a sexta para um cliente fidelidade */
	
	public double getTaxaFidelidadeMeioDeSemana(){
	
		return taxa_fidelidade_meio_de_semana;
	}
	
	/** Retorna o valor da taxa aos sábados e domingos para um cliente fidelidade */
	
	public double getTaxaFidelidadeFimDeSemana(){
	
		return taxa_fidelidade_fim_de_semana;
	}
	
	// Método privado que retorna o dia de uma data, 
	// ele é privado porque só foi criado para ser usado 
	// dentro da classe, com o intuito de deixar o código mais fatorado.
	
	private int getDia(int data){
										// data no formato ddmmaaaa
		int dia = data/1000000;        // Considerando que a data seja um número inteiro de 8 algarismos, 
									  //a divisão por 10^6 retornará os 2 primeiros algarismos.

		return dia;
		
	}
	
	// Método privado que retorna o mes de uma data
	
	private int getMes(int data){

		int mes = (data % 1000000)/10000;  	 // O resto da divisao da data por 10^6  retorna um número de 5 ou 6 algarismos 
											//sua divisão por 10^4 retorna o terceiro e quarto algarismo.

		return mes;

	}
	
	// Método privado que retorna o ano de uma data

	private int getAno(int data){

		int ano = (data % 1000000) % 10000;  // esta operação retorna os quatro últimos algarismos da data.

		
		return ano;

	}
	/** Método que retorna o dia da semana
	* O valor 1 representa domingo, 2 segunda-feira 
	* e asssim sucessivamente.
	
	*/
	public int getDiaSemana(int data){

		int d = getDia(data);
		int m = getMes(data);
		int a = getAno(data);
		
		if(m==1){
			
			m = 13;						// o mes de janeiro deve ser representado por 13
			a = a-1;                   // o ano para o mes de janeiro deve ser representado pelo ano anterior
									
		}
		if(m==2){
			
			m = 14;						// o mes de fevereiro deve ser representado por 14
			a = a-1;				   // o ano para o mes de fevereiro deve ser representado pelo ano anterior
		}

		int k = d + 13*m/5 + 497*a/400 +13/5;           // Expressao matemática envolvendo dia,mes e ano
		
		int DiaSemana = (k % 7)+ 1 ;                    // o resto da divisao por 7 retorna o inteiro que representa um dia da semana

		return DiaSemana;                              // 1 - > domingo, 2-> segunda, ..., 7 -> sabado

	}
	/** Método que calcula a taxa cobrada
	* em função da data e do cliente ser fiel
	* ou não.
	*/
	
	public double calcularTaxa(boolean fiel, int diaSemana){
	
		if(diaSemana == 1 || diaSemana == 7){

			if(fiel){

				return getTaxaFidelidadeFimDeSemana();
			}
			else {

				return getTaxaRegularFimDeSemana();
			}
		}
		else{
			
			if(fiel){

				return getTaxaFidelidadeMeioDeSemana();
			}
			else{

				return getTaxaRegularMeioDeSemana();
			}

		}	
		
	}
	
	/** Este método é estático e recebe um inteiro que representa a quantidade
	* de passageiros e uma String que representa uma ou mais datas
	* separadas por virgula. Ele escreve os dados passados por 
	* parâmetro em um arquivo e depois o fecha.
	*/
	public static void salvarDados(int quantidade_passageiros, String datas){

		try{

			FileWriter arq = new FileWriter("Entrada.txt");
							
			arq.write(quantidade_passageiros + "," + datas);   

			arq.close();
		}catch(Exception e){}
	}
	/** O método lê os dados de um arquivo, seleciona 
	* a primeira linha, divide a linha em substrings de 
	* acordo com o separador ",". A primeira substring é
	* interpretada como  a quantidade de passageiros e as
	* demais como datas. Com estes dados mais o parâmetro
	* booleano é retornado o valor da taxa total por passageiro.
	* @param fiel: é true se o o cliente for fiel e false se
	* for regular.
	*/
	
	public double lerDadosEcalcularTaxaTotal(boolean fiel){
		
		double taxa = 0;
		
		try{
			
			BufferedReader arq = new BufferedReader (new FileReader ("Entrada.txt"));
			
			String linha = arq.readLine();                  // obtem uma linha do arquivo
			
			String campos [] = linha.split(",");            // divide a linha em substrings de acordo com o separador passado por parâmetro.
			
			int tamanho = campos.length;                    // obtem quantas substrings a linha possui.
				
			int quantidade_passageiros = Integer.parseInt(campos[0]);   	// transforma a quantidade de passageiro em int.
				
			for(int i = 1; i < tamanho; i++){

				int data = Integer.parseInt(campos[i]);  		//transforma a data em int.
					
				int diaSemana = getDiaSemana(data);           // Recebe um inteiro que representa o dia da semana 
					
				taxa = taxa + (calcularTaxa(fiel,diaSemana))/quantidade_passageiros;  // calcula a taxa total por passageiro para as datas informadas
					
			}
			arq.close();                  // fecha o arquivo

		}catch(Exception e){}
		
		return taxa;
		
	}
	
	// Método publico e estático que retorna a posição da menor taxa em um ArrayList de taxas

	public static  int PosicaoMenorTaxa(ArrayList<Double> taxas){
		
		int posMenor = -1;
		int cont =0;
		int tamanho = taxas.size();             // quantidade de taxas que o ArrayList possui, oq depende do limite de passageiros de cada loja.
		
		for(int i = 0; i < tamanho; i++){
			
			for(int j= 0; j < tamanho; j++){
				
				if(taxas.get(i) <= taxas.get(j)){          // compara cada elemento do ArrayList com ele mesmo e com todos os outros
					
					cont++;
				}
			}
			if(cont==tamanho){
														// se o numero de comparações verdadeiras forem igual ao tamanho do ArrayList
				posMenor = i;						   // conclui -se que este elemento é o menor e sua posição é armazenada em uma variável.
				
			}
			cont=0;                                 // o contador de comparações verdadeiras é reinicializado para que o próximo elemento seja comparado.
		}
		
		return posMenor;
	
	}
	/** Metodo que trata datas inválidas,
	*Os formatos válidos  são ddmmaaaa e dd/mm/aaaa
	*onde as letras d,m e a são inteiros que representam
	* dia ,mês e ano respsctivamente. Uma data inválida
	* faz com que o método retorne false.
	*/
	
	
	public static boolean dataValida(String datas){
		
		String campos [] = datas.split(",");
		
		for(int i = 0; i < campos.length; i++){
			
			String data = campos[i];
			
			if(data.length() < 8 || data.length() > 10 ){
				
				return false;
			}
			
			if(data.length() == 9){
				
				return false;
			}
			
			// verifica datas no formato ddmmaaa
			
			if(data.length()== 8){
				
				String [] caracteresValidos = {"0","1","2","3","4","5","6","7","8","9"};
				
				String subCampos[] = data.split("");
				
				int cont=0;
				
				boolean ehAlgarismo = false;
				
				for(int j=0; j < subCampos.length; j++){
					
					String caracter = subCampos[j];
					
					for(int k = 0 ; k < 10; k++){
						
						if(caracter.equals(caracteresValidos[k])){
						
							cont++;
						}
					}
				}
				
				if(cont == subCampos.length){
					
					ehAlgarismo = true;
				
					int date = Integer.parseInt(data);
					
					int dia = date/1000000;
					int mes = (date%1000000)/10000;
					int ano = (date % 1000000) % 10000;
				
					if(dia > 31 || dia < 1){
					
						return false;
					}
					if(dia > 30 &&(mes == 2 || mes == 4 || mes == 6 || mes == 9 || mes == 11 )){
					
						return false;
					}
					
					boolean ehBissexto = false;
					
					if(ano%4 ==0 && ano%100 !=0){
						
						ehBissexto = true;
					}
					if(ano%4 == 0 && ano%100 == 0 && ano%400 == 0){
						
						ehBissexto = true;
					}
					
					if(dia > 29 && mes == 2){
						
						return false;
					}
				
					if(dia > 28 && mes == 2) {
						
						if(!ehBissexto){
						
							return false;
						}
					}
				}
				else{
					
					return false;
				}
			}
			
			
			
			// verifica datas no formato dd//mm/aaaa
			
			
			if(data.length()== 10){
				
				String [] caracteresValidos = {"0","1","2","3","4","5","6","7","8","9"};
				
				String subCampos[] = data.split("");
				
				if(!(subCampos[2].equals("/")) || !(subCampos[5].equals("/"))){
					
					return false;
				}
				
				int cont=0;
				
				boolean ehAlgarismo = false;
				
				for(int j=0; j < subCampos.length; j++){
					
					String caracter = subCampos[j];
					
					for(int k = 0 ; k < 10; k++){
						
						if(caracter.equals(caracteresValidos[k])){
						
							cont++;
						}
					}
				}
				
				if(cont == (subCampos.length - 2) ){
					
					ehAlgarismo = true;
					
					String data_sem_barra [] = data.split("/");
				
					int dia = Integer.parseInt(data_sem_barra[0]);
					int mes = Integer.parseInt(data_sem_barra[1]);
					int ano = Integer.parseInt(data_sem_barra[2]);
					
				
					if(dia > 31 || dia < 1){
					
						return false;
					}
					if(dia > 30 &&(mes == 2 || mes == 4 || mes == 6 || mes == 9 || mes == 11 )){
					
						return false;
					}
					
					boolean ehBissexto = false;
					
					if(ano%4 ==0 && ano%100 !=0){
						
						ehBissexto = true;
					}
					if(ano%4 == 0 && ano%100 == 0 && ano%400 == 0){
						
						ehBissexto = true;
					}
				
					if(dia > 29 && mes == 2){
						
						return false;
					}
				
					if(dia > 28 && mes == 2) {
						
						if(!ehBissexto){
						
							return false;
						}
					}
					
					datas = datas.replace("/","");
				}
				else{
					
					return false;
				}
			}
			
		}
		
		return true;
	}

}