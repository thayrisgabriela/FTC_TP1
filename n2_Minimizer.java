import java.util.*;

public class n2_Minimizer extends AFD {
    public ArrayList<String> minimizedEstados = new ArrayList<String>();
    public String minimizedEstadoInicial;
    public ArrayList<String> minimizedEstadosFinais = new ArrayList<String>();
    private String from;


public AFD n2Minimization(AFD afd) {
    // Criação de conjuntos para estados finais e não finais
    ArrayList<String> estadosFinais = new ArrayList<>(afd.getEstados_finais());
    ArrayList<String> semEstadosFinais = new ArrayList<>(afd.getEstados());
    semEstadosFinais.removeAll(estadosFinais);
    
    // Inicialização da partição com os conjuntos de estados finais e não finais
    List<ArrayList<String>> partition = new ArrayList<>();
    partition.add(estadosFinais);
    partition.add(semEstadosFinais);
    
    // Fila para processamento dos conjuntos
    Queue<ArrayList<String>> queue = new LinkedList<>();
    queue.add(estadosFinais.size() <= semEstadosFinais.size() ? estadosFinais : semEstadosFinais);
    
    while (!queue.isEmpty()) {
        ArrayList<String> queueElement = queue.poll();
        for (String read : afd.getAlfabeto()) {
            // Busca transições de um determinado estado com um símbolo específico no autômato
            ArrayList<String> set1 = new ArrayList<>();
            for (String estado : afd.getEstados()) {
                String to = afd.obter_proximo_estado(estado, read);
                //String to = afd.findTransicoes(estado, read);
                if (queueElement.contains(to)) {
                    set1.add(estado);
                }
            }

            for (ArrayList<String> set2 : new ArrayList<>(partition)) {
                ArrayList<String> intersection = new ArrayList<>(set1);
                intersection.retainAll(set2);
                
                // Se a interseção não for vazia e a diferença também não
                if (!intersection.isEmpty() && !set2.removeAll(set1)) {
                    partition.add(new ArrayList<>(intersection));
                    partition.add(new ArrayList<>(set2));

                    // Adiciona os conjuntos na fila
                    queue.add(intersection.size() <= set2.size() ? intersection : set2);
                }
            }
        }
    }

    // Geração do AFD minimizado
    AFD minimizedAFD = new AFD();
    minimizedAFD.setAlfabeto(afd.getAlfabeto());

    for (ArrayList<String> part : partition) {
        String model = part.get(0);
        minimizedAFD.adicionaEstado(model);

        for (String estado : part) {
            if (afd.getEstado_inicial().contains(estado)) {
                minimizedAFD.setEstado_inicial(model);
            }
            if (afd.getEstados_finais().contains(estado)) {
                minimizedAFD.adicionaEstadoFinal(model);
            }
        }

        for (String read : afd.getAlfabeto()) {
            String to = afd.obter_proximo_estado(model, read);
            //Integer to = afd.findTransicoes(model, read);
            for (ArrayList<String> part2 : partition) {
                if (part2.contains(to)) {
                    String toModel = part2.get(0);
                    
                    minimizedAFD.adicionaTransicao(model, read, toModel);
                    break;
                }
            }
        }
    }
    return minimizedAFD;
}

}