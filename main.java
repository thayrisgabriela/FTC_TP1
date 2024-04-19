class main{
    public static void main(String[] args){
        Gerador_XML gerador = new Gerador_XML();
        AFD afd = gerador.ler_xml("8Estados.jff");
        gerador.escrever_xml(afd);
    }
}