package PCC_projects.TrabalhoPCC;

public class Fila extends No {
    protected No primeiro; // turista mais recente
    protected No ultimo;  // turista mais antigo

    public boolean isEmpty() {
        return primeiro == null;
    }

    public void subir(int item) {
        No anterior = ultimo;
        ultimo = new No();
        ultimo.item = item;
        ultimo.proximo = null;
        ultimo.tempo = new Tempo();
        ultimo.tempo.start();
        if (isEmpty()) primeiro = ultimo;
        else anterior.proximo = ultimo;
    }

    public void descer() {
        primeiro = primeiro.proximo;
        if (isEmpty()) ultimo = null;
    }
}
