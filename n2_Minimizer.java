import java.util.*;

public class n2_Minimizer extends AFD {
    public ArrayList<String> minimizedEstados = new ArrayList<String>();
    public String minimizedEstadoInicial;
    public ArrayList<String> minimizedEstadosFinais = new ArrayList<String>();



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

                    ArrayList<String> diff = new ArrayList<>(set2);
                    diff.removeAll(set1);
                    /* Verifica se a interseção e a diferença entre dois conjuntos não são vazias */

                    if (!intersection.isEmpty() && !diff.isEmpty()) {
                        partition.remove(set2);
                        partition.add(intersection);
                        partition.add(diff);
                        if (queue.contains(set2)) {
                            queue.remove(set2);
                            //Adiciona o resultado da interseção e da diferença na fila, inserindo o menor conjunto primeiro
                            if (intersection.size() <= diff.size()) {
                                queue.add(intersection);
                                queue.add(diff);
                            } else {
                                queue.add(diff);
                                queue.add(intersection);
                            }
                        } else {
                            //Adiciona na fila o conjunto menor entre os dois conjuntos, para manter a ordenação entre eles //
                            if (intersection.size() <= diff.size()) {
                                queue.add(intersection);
                            } else {
                                queue.add(diff);
                            }
                        }
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