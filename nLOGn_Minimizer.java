import java.util.ArrayList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import java.util.Queue;
import java.util.Set;

public class nLOGn_Minimizer {

    public AFD minimizacaoNLogN(AFD automato) {
        Set<String> estadosFinais = new HashSet<>(automato.getEstados_finais());
        Set<String> estadosNaoFinais = new HashSet<>(automato.getEstados());
        estadosNaoFinais.removeAll(estadosFinais);
        List<Set<String>> particao = new ArrayList<>();
        particao.add(estadosFinais);
        particao.add(estadosNaoFinais);

        Queue<Set<String>> fila = new LinkedList<>();
        if (estadosFinais.size() <= estadosNaoFinais.size()) {
            fila.add(estadosFinais);
        } else {
            fila.add(estadosNaoFinais);
        }

        while (!fila.isEmpty()) {
            Set<String> elementoFila = fila.poll();
            for (String simbolo : automato.getAlfabeto()) {
                
                Set<String> conjunto1 = new HashSet<>();
                for (String estado : automato.getEstados()) {
                    String destino = automato.obter_proximo_estado(estado, simbolo);
                    if (elementoFila.contains(destino)) {
                        conjunto1.add(estado);
                    }
                }

            
                for (Set<String> conjunto2 : new ArrayList<>(particao)) {
                    Set<String> intersecao = new HashSet<>(conjunto1);
                    intersecao.retainAll(conjunto2);

                    Set<String> diferenca = new HashSet<>(conjunto2);
                    diferenca.removeAll(conjunto1);

                    if (!intersecao.isEmpty() && !diferenca.isEmpty()) {
                        particao.remove(conjunto2);
                        particao.add(intersecao);
                        particao.add(diferenca);

                        if (fila.contains(conjunto2)) {
                            fila.remove(conjunto2);

                            if (intersecao.size() <= diferenca.size()) {
                                fila.add(intersecao);
                                fila.add(diferenca);
                            } else {
                                fila.add(diferenca);
                                fila.add(intersecao);
                            }
                        } else {

                            if (intersecao.size() <= diferenca.size()) {
                                fila.add(intersecao);
                            } else {
                                fila.add(diferenca);
                            }
                        }
                    }
                }
            }
        }

        AFD automatoMinimizado = new AFD();
        automatoMinimizado.setAlfabeto(automato.getAlfabeto());

        for (Set<String> parte : particao) {
            String modelo = parte.iterator().next();
            automatoMinimizado.adicionaEstado(modelo);

            for (String estado : parte) {
                if (estado.equals(automato.getEstado_inicial())) {
                    automatoMinimizado.setEstado_inicial(modelo);
                }
                if (automato.getEstados_finais().contains(estado)) {
                    automatoMinimizado.adicionaEstadoFinal(modelo);
                }
            }

            for (String simbolo : automato.getAlfabeto()) {
                String destino = automato.obter_proximo_estado(modelo, simbolo);
                for (Set<String> parte2 : particao) {
                    if (parte2.contains(destino)) {
                        String destinoModelo = parte2.iterator().next();
                        automatoMinimizado.adicionaTransicao(modelo, simbolo, destinoModelo);
                        break;
                    }
                }
            }
        }

        return automatoMinimizado;
    }
}
