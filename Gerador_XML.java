import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.util.Pair;




class Gerador_XML{


    public AFD ler_xml(String path_file){
        try {
            File file = new File(path_file);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

    
            // Obtendo os estados
            NodeList nodos_de_estados = document.getElementsByTagName("state");
            ArrayList<Integer> estados = new ArrayList<>();
            Integer estadoInicial = null;
            ArrayList<Integer> estadosFinais = new ArrayList<>();

            for (int i = 0; i < nodos_de_estados.getLength(); i++) {
                Element stateElement = (Element) nodos_de_estados.item(i);
                int id = Integer.parseInt(stateElement.getAttribute("id"));
                estados.add(id);
                if (stateElement.getElementsByTagName("initial").getLength() > 0) {
                    estadoInicial = id;
                }
                if (stateElement.getElementsByTagName("final").getLength() > 0) {
                    estadosFinais.add(id);
                }
            }
            
            // Obtendo as transições
            NodeList transitionNodes = document.getElementsByTagName("transition");
            Map<Pair<Integer, Character>, Integer> transicoes = new HashMap<>();
            ArrayList<Character> alfabeto = new ArrayList<>();
            for (int i = 0; i < transitionNodes.getLength(); i++) {
                Element transitionElement = (Element) transitionNodes.item(i);
                
                transitionElement.getElementsByTagName("from").item(0).getChildNodes().item(0).getNodeValue();
                int from = Integer.parseInt(transitionElement.getElementsByTagName("from").item(0).getChildNodes().item(0).getNodeValue());
                int to = Integer.parseInt(transitionElement.getElementsByTagName("to").item(0).getChildNodes().item(0).getNodeValue());
                char read = transitionElement.getElementsByTagName("read").item(0).getChildNodes().item(0).getNodeValue().charAt(0);
                transicoes.put(new Pair<>(from, read), to);
                if (!alfabeto.contains(read)) {
                    alfabeto.add(read);
                }
            }
            
            //System.out.println(estados);
            //System.out.println(estadoInicial);
            //System.out.println(estadosFinais);
            //System.out.println(alfabeto);
            //System.out.println(transicoes);
            return new AFD(estados, alfabeto, transicoes, estadoInicial, estadosFinais);
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

    }
    
    
    public void escrever_xml(AFD afd){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Criando o documento XML
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("structure");
            doc.appendChild(rootElement);

            // Adicionando o tipo de autômato
            Element typeElement = doc.createElement("type");
            typeElement.appendChild(doc.createTextNode("fa"));
            rootElement.appendChild(typeElement);

            // Adicionando a parte de autômato
            Element automatonElement = doc.createElement("automaton");
            rootElement.appendChild(automatonElement);

            // Adicionando os estados
            for (Integer estado : afd.getEstados()) {
                Element stateElement = doc.createElement("state");
                stateElement.setAttribute("id", estado.toString());
                stateElement.setAttribute("name", "q" + estado);
                if (estado.equals(afd.getEstado_inicial())) {
                    Element initialElement = doc.createElement("initial");
                    stateElement.appendChild(initialElement);
                }
                if (afd.getEstados_finais().contains(estado)) {
                    Element finalElement = doc.createElement("final");
                    stateElement.appendChild(finalElement);
                }
                automatonElement.appendChild(stateElement);
            }

            // Adicionando as transições
            for (Map.Entry<Pair<Integer, Character>, Integer> entry : afd.getTransicoes().entrySet()) {
                Pair<Integer, Character> origemSimbolo = entry.getKey();
                Integer destino = entry.getValue();

                Element transitionElement = doc.createElement("transition");

                Element fromElement = doc.createElement("from");
                fromElement.appendChild(doc.createTextNode(origemSimbolo.getKey().toString()));
                transitionElement.appendChild(fromElement);

                Element toElement = doc.createElement("to");
                toElement.appendChild(doc.createTextNode(destino.toString()));
                transitionElement.appendChild(toElement);

                Element readElement = doc.createElement("read");
                readElement.appendChild(doc.createTextNode(origemSimbolo.getValue().toString()));
                transitionElement.appendChild(readElement);

                automatonElement.appendChild(transitionElement);
            }
            // Transformando o documento em XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            String conteudo = writer.getBuffer().toString();
            String caminhoArquivo = "AFD_Minimizado.jff";
            try (BufferedWriter wwriter = new BufferedWriter(new FileWriter(caminhoArquivo))) {
                wwriter.write(conteudo);
                System.out.println("Conteúdo foi escrito com sucesso no arquivo '" + caminhoArquivo + "'.");
            } catch (IOException e) {
                System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }


}