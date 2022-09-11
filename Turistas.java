package PCC_projects.TrabalhoPCC;

public class Turistas extends Thread implements Runnable {

    Tempo tempo;

    Turistas(Tempo tempo) {
        this.tempo = tempo; // Para contação inicial, o objetivo do programa é executar por dois minutos
    }

    int LS = 0; //para auxiliar as fila de uma pessoa no lado esquerdo
    int LT = 0; //para auxiliar as fila de uma tripla no lado esquerdo
    int RT = 0; //para auxiliar as fila de uma tripla no lado direito
    int RS = 0; //para auxiliar as fila de uma pessoa no lado direito

    Fila filaLS = new Fila();//fila de uma pessoa no lado esquerdo
    Fila filaLT = new Fila();//fila de uma tripla no lado esquerdo
    Fila filaRT = new Fila();//fila de uma tripla no lado direito
    Fila filaRS = new Fila();//fila de uma pessoa no lado direito

    public void setLT(int LT) {
        this.LT = LT;
    }

    public void setLS(int LS) {
        this.LS = LS;
    }

    public void setRT(int RT) {
        this.RT = RT;
    }

    public void setRS(int RS) {
        this.RS = RS;
    }

    public int getLS() {
        return this.LS;
    }

    public int getLT() {
        return this.LT;
    }

    public int getRS() {
        return this.RS;
    }

    public int getRT() {
        return this.RT;
    }

    /*synchronized*/ void fila() {  /*Função de incremento na fila,incrementa as pessoas da fila ele obedece cada regras citadas abaixo*/
        if ((this.LS < 2 * this.LT) && (this.LS < 2 * this.RT) && (this.LS < this.RS) && (this.LS < 120)) {
            this.LS += 1;
            filaLS.subir(this.LS);
            System.out.println("Escolheu a fila LS + (total =" + (this.LS) + ") ");
        } else if ((this.RS < 2 * this.LT) && (this.RS < 2 * this.RT) && (this.RS <= this.LS) && (this.RS < 120)) {
            this.RS += 1;
            filaRS.subir(this.RS);
            System.out.println("Escolheu a fila RS+ (total =" + (this.RS) + ")");
        } else if ((this.LT <= this.RT) && (this.LT < 120)) {
            this.LT += 1;
            filaLT.subir(this.LT);
            System.out.println("Escolheu a fila LT+ (total =" + (this.LT) + ")");
        } else if (this.RT < 120) {
            this.RT += 1;
            filaRT.subir(this.RT);
            System.out.println("Escolheu a fila RT+ (total =" + (this.RT) + ")");
        }
    }


    public void run() {
        try {
            while (true) {
                if (!tempo.interrupcao()) {
                    fila();
                    sleep(1000);// Espera de um segundo
                } else {
                    return;// Encerrar loop sem problema ou interrupção forçada
                }
            }
        } catch (InterruptedException ignored) {
        }
    }
}
