package PCC_projects.TrabalhoPCC;

public class No {
    protected int item;
    protected No proximo;
    protected Tempo tempo;

    No() {
        tempo = new Tempo();
        tempo.start();
    }

    public int getMaxSeg() {
        return tempo.segundos;
    }

    public int getMaxMin() {
        return tempo.minutos;
    }
}
