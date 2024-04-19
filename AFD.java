import java.util.*;

import javafx.util.Pair;

public class AFD {

    private ArrayList<Integer> estados = new ArrayList<Integer>();
    private ArrayList<Character> alfabeto = new ArrayList<Character>();
    private Map<Pair<Integer, Character>, Integer> transicoes = new HashMap<>();
    private Integer estado_inicial;
    private ArrayList<Integer> estados_finais = new ArrayList<Integer>();

    //Métodos Construtores

    AFD(){
        estados = new ArrayList<Integer>();
        alfabeto = new ArrayList<Character>();
        transicoes = new HashMap<>();
        estados_finais = new ArrayList<Integer>();
    }

    AFD(ArrayList<Integer> estados, ArrayList<Character> alfabeto,Map<Pair<Integer, Character>, Integer> transicoes,Integer estado_inicial,ArrayList<Integer> estados_finais){
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.transicoes = transicoes;
        this.estado_inicial = estado_inicial;
        this.estados_finais = estados_finais;
    }
    
    // Recebe o estado e o simbolo lido e retorna o próxximo estado

    public Integer obter_proximo_estado(Integer estadoAtual, Character simbolo) {
        return transicoes.getOrDefault(new Pair<>(estadoAtual, simbolo), null);
    }


    // GETTERS E SETTERS
    public ArrayList<Integer> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Integer> estados) {
        this.estados = estados;
    }

    public ArrayList<Character> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(ArrayList<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public Map<Pair<Integer, Character>, Integer> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<Pair<Integer, Character>, Integer> transicoes) {
        this.transicoes = transicoes;
    }

    public Integer getEstado_inicial() {
        return estado_inicial;
    }

    public void setEstado_inicial(Integer estado_inicial) {
        this.estado_inicial = estado_inicial;
    }

    public ArrayList<Integer> getEstados_finais() {
        return estados_finais;
    }

    public void setEstados_finais(ArrayList<Integer> estados_finais) {
        this.estados_finais = estados_finais;
    }
  

}
