import java.util.*;

import javafx.util.Pair;

public class AFD {

    private ArrayList<String> estados = new ArrayList<String>();
    private ArrayList<String> alfabeto = new ArrayList<String>();
    private Map<Pair<Integer, String>, Integer> transicoes = new HashMap<>();
    private String estadoInicial;
    private ArrayList<String> estadosFinais = new ArrayList<String>();

    //Métodos Construtores

    AFD(){
        estados = new ArrayList<String>();
        alfabeto = new ArrayList<String>();
        transicoes = new HashMap<>();
        estadosFinais = new ArrayList<String>();
       // estadoInicial = new ArrayList<String>();
    }

    AFD(ArrayList<String> estados, ArrayList<String> alfabeto,Map<Pair<Integer, String>, Integer> transicoes,String estadoInicial,ArrayList<String> estadosFinais){
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.transicoes = transicoes;
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
    }
    

    // Recebe o estado e o simbolo lido e retorna o próxximo estado

    public Integer obter_proximo_estado(Integer estadoAtual, String simbolo) {
        return transicoes.getOrDefault(new Pair<>(estadoAtual, simbolo), null);
    }


    // GETTERS E SETTERS
    public ArrayList<String> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<String> estados) {
        this.estados = estados;
    }

    public ArrayList<String> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(ArrayList<String> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public Map<Pair<Integer, String>, Integer> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<Pair<Integer, String>, Integer> transicoes) {
        this.transicoes = transicoes;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public ArrayList<String> getEstadosFinais() {
        return estadosFinais;
    }

    public void setEstadosFinais(ArrayList<String> estadosFinais) {
        this.estadosFinais = estadosFinais;
    }


    public void addEstados(String estados) {
        this.estados.add(estados);
    }

    public void addEstadosFinais(String estadosFinais) {
        this.estadosFinais.add(estadosFinais);
    }

    public void addTransicoes(Integer from, String read, Integer to) {
        this.transicoes.put(new Pair<>(from, read), to);
    }

    public void removeEstados(String estados) {
        this.estados.remove(estados);
    }

    public void removeTransicoes(Integer from, String read) {
        this.transicoes.remove(new Pair<>(from, read));
    }
}

