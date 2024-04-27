import java.util.*;

public class AFDMinimizer extends AFD {
    public ArrayList<String> minimizedEstados = new ArrayList<String>();
    public String minimizedEstadoInicial;
    public ArrayList<String> minimizedEstadosFinais = new ArrayList<String>();
    private Integer from;



    public AFD nLognMinimization(AFD afd) {
        /*
         * Cria conjunto para os estados finais e outro para os estados não finais.
         */
        Set<String> estadosFinais = new HashSet<>(afd.getEstadosFinais());
        Set<String> semEstadosFinais = new HashSet<>(afd.getEstados());
        semEstadosFinais.removeAll(estadosFinais);
        List<Set<String>> partition = new ArrayList<>();
        partition.add(estadosFinais);
        partition.add(semEstadosFinais);

        Queue<Set<String>> queue = new LinkedList<>();
        if (estadosFinais.size() <= semEstadosFinais.size()) {
            queue.add(estadosFinais);
        } else {
            queue.add(semEstadosFinais);
        }
        while (!queue.isEmpty()) {
            Set<String> queueElement = queue.poll();
            for (String read : afd.getAlfabeto()) {
                /*
                 * Busca transições de um deterinado estado com um símbo específico no autômato
                 */
                Set<String> set1 = new HashSet<>();
                for (String estados : afd.getEstados()) {
                //String to = afd.findTransicoes(estados, read);
                Integer to = afd.obter_proximo_estado(estados, read);
                    if (queueElement.contains(to)) {
                        set1.add(estados);
                    }
                }

                /*
                 * Busca por conjuntos que compartilham elementos entre uma partição e outra e
                 * que tenham elementos que não estão em em uma partição específica
                 */
                for (Set<String> set2 : new ArrayList<>(partition)) {
                    Set<String> intersection = new HashSet<>(set1);
                    intersection.retainAll(set2);

                    Set<String> diff = new HashSet<>(set2);
                    diff.removeAll(set1);
                    /* Verifica se a interseção e a diferença entre dois conjuntos não são vazias */
                    if (!intersection.isEmpty() && !diff.isEmpty()) {
                        partition.remove(set2);
                        partition.add(intersection);
                        partition.add(diff);

                        if (queue.contains(set2)) {
                            queue.remove(set2);
                            /*
                             * Adiciona o resultado da interseção e da diferença na fila, inserindo o menor
                             * conjunto primeiro
                             */
                            if (intersection.size() <= diff.size()) {
                                queue.add(intersection);
                                queue.add(diff);
                            } else {
                                queue.add(diff);
                                queue.add(intersection);
                            }
                        } else {
                            /*
                             * Adiciona na fila o conjunto menor entre os dois conjuntos, para manter a
                             * ordenação entre eles
                             */
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

        /*
         * Gera AFD Minimizado após todas as trativas, atualizando lista de estados e
         * transições para o novo AFD
         */
        AFD minimizedAFD = new AFD();
        minimizedAFD.setAlfabeto(afd.getAlfabeto());

        for (Set<String> part : partition) {
            String model = part.iterator().next();
            minimizedAFD.addEstados(model);

            for (String estados : part) {
                if (afd.getEstadoInicial().contains(estados)) {
                    minimizedAFD.setEstadoInicial(model);
                }
                if (afd.getEstadosFinais().contains(estados)) {
                    minimizedAFD.addEstadosFinais(model);
                }
            }

            for (String read : afd.getAlfabeto()) {
                Integer to = afd.obter_proximo_estado(model, read);
                //Integer to = afd.findTransicoes(model, read);
                for (Set<String> part2 : partition) {
                    if (part2.contains(to)) {
                        String toModel = part2.iterator().next();
                        Transicoes newTransicoes = new Transicoes(model, toModel, read);
                        minimizedAFD.addTransicoes(from, read, to);
                        break;
                    }
                }
            }
        }

        return minimizedAFD;
    }

public AFD n2Minimization(AFD afd) {
    // Criação de conjuntos para estados finais e não finais
    ArrayList<String> estadosFinais = new ArrayList<>(afd.getEstadosFinais());
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
                Integer to = afd.obter_proximo_estado(estado, read);
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
        minimizedAFD.addEstados(model);

        for (String estado : part) {
            if (afd.getEstadoInicial().contains(estado)) {
                minimizedAFD.setEstadoInicial(model);
            }
            if (afd.getEstadosFinais().contains(estado)) {
                minimizedAFD.addEstadosFinais(model);
            }
        }

        for (String read : afd.getAlfabeto()) {
            Integer to = afd.obter_proximo_estado(model, read);
            //Integer to = afd.findTransicoes(model, read);
            for (ArrayList<String> part2 : partition) {
                if (part2.contains(to)) {
                    String toModel = part2.get(0);
                    Transicoes newTransicao = new Transicoes(model, toModel, read);
                    minimizedAFD.addTransicoes(from, read, to);
                    break;
                }
            }
        }
    }
    return minimizedAFD;
}

}