package PCC_projects.TrabalhoPCC;

public class Tempo extends Thread implements Runnable {
    int horas;
    int minutos;
    int segundos;

    Tempo() {
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
    }

    public void run() {
        try {
            while (true) {
                if (this.segundos == 59) {
                    this.minutos += 1;
                    this.segundos = 0;
                } else {
                    this.segundos += 1;
                }
                if (interrupcao()) {
                    System.out.println("Terminou a execução");
                    return;// Encerrar loop sem problema ou interrupção forçada
                }
                //impressao();
                sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /*usado para auxiliar na contagem de tempo*/
    private void impressao() {
        System.out.println(this.horas + " : " + this.minutos + " : " + this.segundos);
    }

    /*notificar que é dois minutos para encerrar o run*/
    protected boolean interrupcao() {
        return this.minutos == 2;
    }

}
