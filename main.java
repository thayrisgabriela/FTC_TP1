import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class main{
    public static void main(String[] args) throws IOException{
        Gerador_XML gerador = new Gerador_XML();
        System.out.println("----------------------------------------\nEscreva o nome do arquivo contendo o AFD\n----------------------------------------");
        System.out.println("Deixe em branco e o algoritmo executará com o arquivo 8Estados.jff");
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String path_afd = sc.readLine();
        
        path_afd.trim();
        if(path_afd.equals("")){
            path_afd = "8Estados.jff";
        }
        
        

        //Execução do algoritmo nLogn
        AFD afd = gerador.ler_xml(path_afd);
        long startTime = System.nanoTime();
        nLOGn_Minimizer minizador = new nLOGn_Minimizer();
        long endTime = System.nanoTime();
        afd = minizador.minimizacaoNLogN(afd);
        String path_saida = "nLogn_minization.jff";
        gerador.escrever_xml(afd, path_saida);

        long elapsedTime = endTime - startTime;
        System.out.println("Tempo do algoritmo nLogn em nanosegundos: " + elapsedTime);

        //Execução do algoritmo N2
        AFD afd2 = gerador.ler_xml(path_afd);
        startTime = System.nanoTime();
        n2_Minimizer minimizer2 = new n2_Minimizer();
        endTime = System.nanoTime();
        afd2 = minimizer2.n2Minimization(afd2);
        String path_saida2 = "n2_minization.jff";
        gerador.escrever_xml(afd2, path_saida2);

        elapsedTime = endTime - startTime;
        System.out.println("Tempo do algoritmo nLogn em nanosegundos: " + elapsedTime);
    }
}
