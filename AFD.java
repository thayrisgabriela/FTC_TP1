import java.util.*;

import javafx.util.Pair;

public class AFD {

    private ArrayList<String> estados = new ArrayList<String>();
    private ArrayList<String> alfabeto = new ArrayList<String>();
    private Map<Pair<String, String>, String> transicoes = new HashMap<>();
    private String estado_inicial;
    private ArrayList<String> estados_finais = new ArrayList<String>();

    //Métodos Construtores

    AFD(){
        estados = new ArrayList<String>();
        alfabeto = new ArrayList<String>();
        transicoes = new HashMap<>();
        estados_finais = new ArrayList<String>();
    }

    AFD(ArrayList<String> estados, ArrayList<String> alfabeto,Map<Pair<String, String>, String> transicoes,String estado_inicial,ArrayList<String> estados_finais){
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.transicoes = transicoes;
        this.estado_inicial = estado_inicial;
        this.estados_finais = estados_finais;
    }
    
    // Recebe o estado e o simbolo lido e retorna o próxximo estado

    public String obter_proximo_estado(String estadoAtual, String simbolo) {
        return transicoes.getOrDefault(new Pair<>(estadoAtual, simbolo), null);
    }

    // 
    public void adicionaEstado(String estado){
        estados.add(estado);
    }
    public void adicionaEstadoFinal(String estado){
        estados_finais.add(estado);
    }
    public void adicionaTransicao(String estadoorigem,String simbolo,String estadoDestino){
        transicoes.put(new Pair<>(estadoorigem, simbolo), estadoDestino);
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

    public Map<Pair<String, String>, String> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<Pair<String, String>, String> transicoes) {
        this.transicoes = transicoes;
    }

    public String getEstado_inicial() {
        return estado_inicial;
    }

    public void setEstado_inicial(String estado_inicial) {
        this.estado_inicial = estado_inicial;
    }

    public ArrayList<String> getEstados_finais() {
        return estados_finais;
    }

    public void setEstados_finais(ArrayList<String> estados_finais) {
        this.estados_finais = estados_finais;
    }
    
    

}
