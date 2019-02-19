import java.util.*;
import java.io.*;

/** Nesta classe são instanciados os obejtos
* da classe LocadoraDeCarros e Categoria.
* Dados informados pelo usuário são interpretados
* e o nome da loja e a categoria de carros mais
* barata é retornada. 
*/

public class Menu{
	
	/**Método responsável por apresentar a empresa para o cliente,
	* orientá-lo, adquirir dados e imprimir qual categoria de carros
	* é mais barata e a loja que ele deve ir.
	*/


	public static void comunicarComCliente(){
	
		Scanner entrada = new Scanner(System.in);
		
		final double tr_mds_compactos = 210;
		final double tf_mds_compactos = 150;
		final double tr_fds_compactos = 200;
		final double tf_fds_compactos = 90;
														// variáveis usadas para instanciar um objeto da classe LocadoraDeCarros
		final double tr_mds_esportivos = 530;        
		final double tf_mds_esportivos = 150;
		final double tr_fds_esportivos = 200;
	    final double tf_fds_esportivos = 90;

		final double tr_mds_suvs = 630;
		final double tf_mds_suvs = 580;
		final double tr_fds_suvs = 600;
		final double tf_fds_suvs = 590;                

		final int limite_passageiros_compactos = 4;			// variáveis usadas para instanciar um objeto da classe categoria
		final int limite_passageiros_esportivos = 2;
		final int limite_passageiros_suvs = 7;
		
		
		final boolean fiel_south = false;      // neste trecho de código é definido se o cliente é fiel (true) ou regular (false)
		final boolean fiel_north = false;
		final boolean fiel_west = false;
		
		Categoria compactos = new Categoria ("Compactos",limite_passageiros_compactos);
		Categoria esportivos = new Categoria ("Esportivos",limite_passageiros_esportivos);
		Categoria suvs = new Categoria ("Suvs",limite_passageiros_suvs);
		
	
		LocadoraDeCarros southCar = new LocadoraDeCarros("SouthCAr",compactos,tr_mds_compactos,tr_fds_compactos,tf_mds_compactos,tf_fds_compactos);
		LocadoraDeCarros northCar = new LocadoraDeCarros("NorhCAr",suvs,tr_mds_suvs,tr_fds_suvs,tf_mds_suvs,tf_fds_suvs);
		LocadoraDeCarros westCar = new LocadoraDeCarros("WestCAr",esportivos,tr_mds_esportivos,tr_fds_esportivos,tf_mds_esportivos,tf_fds_esportivos);
		
		
		int quantidade_passageiros;              
		String datas;
		
		
		String opcao = "1"; 

		do{

			System.out.println("\n------------------MENU PRINCIPAL--------------------");
			System.out.println("  Seja Bem Vindo a nossa empresa!"); 
			System.out.println("  Temos 3 lojas (SouthCar,NorthCar e WestCar) com variadas taxas de aluguel.");
			System.out.println("  Trabalhamos com carros compactos com espaco para 4 passageiros,");
			System.out.println("  esportivos para 2 pessoas e suvs que comportam ateh 7 passageiros.");
			System.out.println("  Por favor, nos informe a quantidade de passageiros e as datas"); 
			System.out.println("  que farah uso de nossos servicos. A categoria de carros ");
			System.out.println("  mais baratos nos encontramos para voce!!!");
					
			System.out.println("\nQual a quantidade de passageiros?\n");
			System.out.printf("Quantidade de Passageiros: ");
			quantidade_passageiros = entrada.nextInt();
					
			if(quantidade_passageiros < 1 || quantidade_passageiros > 7){
						
				System.out.println("\n  Desculpe, nossas lojas nao trabalham com ");
				System.out.println("  esta quantidade de passageiros, informe ");
				System.out.println("  uma quantidade entre 1 e 7 ou digite outro ");
				System.out.println("  valor para sair do aplicativo. ");
						
				System.out.printf("Quantidade de Passageiros: ");
				quantidade_passageiros = entrada.nextInt();
						
			}
					
			if(quantidade_passageiros < 1 || quantidade_passageiros > 7){
						
					break;                                                       // caso a quantidade de passageiros seja inválida o aplicativo é fechado
			}
					
			System.out.println("\n  Por favor, informe as datas que farah uso de nosso carro.");
			System.out.println("  Digite-as separadas por virgula, nao use Enter antes");
			System.out.println("  de digitar todas. Voce pode usar os formatos a seguir:");
			System.out.println("  01/05/2019 e 01052019");
			System.out.println("  Exemplo de Entrada: 01/05/2019,02/05/2019,03/05/2019\n");
			
			System.out.printf("Datas: ");
			datas = entrada.next();
			
			if(!LocadoraDeCarros.dataValida(datas)){
				
				System.out.println("\n  Desculpe, este formato de data não é aceito.");
				System.out.println("  Entre novamente no aplicativo e digite -a como especificado.");
				
					break;
			}
					
			LocadoraDeCarros.salvarDados(quantidade_passageiros,datas);          // Escreve os dados lidos no arquivo Entrada.txt
					
					
			if(quantidade_passageiros <= suvs.getLimitePassageiros()){
				
				double taxa = northCar.lerDadosEcalcularTaxaTotal(fiel_north);		// calcula a taxa para carros suvs e a insere na 1° posição do ArrayList
				
				ArrayList<Double> taxas = new ArrayList<>();
				
				taxas.add(0,taxa);
				
				if(quantidade_passageiros <= compactos.getLimitePassageiros()){
						
					taxa = southCar.lerDadosEcalcularTaxaTotal(fiel_south);				// calcula a taxa para carros compactos e a insere na 2° posição do ArrayList
					taxas.add(1,taxa);
	
					if(quantidade_passageiros <= esportivos.getLimitePassageiros()){
						
						taxa = westCar.lerDadosEcalcularTaxaTotal(fiel_west);                //  calcula a taxa para carros esportivos e a insere na 3° posição do ArrayList
						taxas.add(2,taxa);
					}
				}
				
				if(LocadoraDeCarros.PosicaoMenorTaxa(taxas) == 0){
					
					System.out.printf("\n%s",suvs.getNome());                // se a menor taxa estiver na primeira posição, imprime a categoria suvs e a loja NorthCAr
					System.out.printf(": %s\n",northCar.getLoja());
				}
				if(LocadoraDeCarros.PosicaoMenorTaxa(taxas) == 1){
					
					System.out.printf("\n%s",compactos.getNome());     // se a menor taxa estiver na segunda posição, imprime a categoria compactos e a loja southCAr
					System.out.printf(": %s\n",southCar.getLoja());     
				}
				if(LocadoraDeCarros.PosicaoMenorTaxa(taxas) == 2){
					
					System.out.printf("\n%s",esportivos.getNome());  // se a menor taxa estiver na terceira posição, imprime a categoria esportivos e a loja WestCAr
					System.out.printf(": %s",westCar.getLoja());
				}
				
			}
				
			System.out.println("\n  Deseja verificar o valor para uma data ou ");     // possibilita o cliente de verificar qual o carro mais barato
			System.out.println("  quantidade de passageiros diferentes?");			 // para uma data e/ou quantidade de passageiros diferentes.
			System.out.println("  Se sim digite 1, se nao aperte qualquer tecla.");
					
			opcao = entrada.next();
		
		}while(opcao.equals("1"));                 // condição para encerrar o loop e fechar o programa.
	}

}