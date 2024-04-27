public class Transicoes {
    String from;
    String to;
    String read;

    public Transicoes(String from, String to, String read) {
        this.from = from;
        this.to = to;
        this.read = read;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
