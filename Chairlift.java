package PCC_projects.TrabalhoPCC;

public class Chairlift extends Thread implements Runnable {

    Turistas turistas;
    Tempo tempo;
    int elevador = 0;
    int soma = 0;
    int vezeselevador = 0;
    int maxMinutosLT = 0;
    int maxSegundosLT = 0;
    int maxMinutosLS = 0;
    int maxSegundosLS = 0;
    int maxMinutosRT = 0;
    int maxSegundosRT = 0;
    int maxMinutosRS = 0;
    int maxSegundosRS = 0;

    public int getMaxMinutosLS() {
        return maxMinutosLS;
    }

    public int getMaxMinutosLT() {
        return maxMinutosLT;
    }

    public int getMaxMinutosRS() {
        return maxMinutosRS;
    }

    public int getMaxMinutosRT() {
        return maxMinutosRT;
    }

    public int getMaxSegundosLS() {
        return maxSegundosLS;
    }

    public int getMaxSegundosLT() {
        return maxSegundosLT;
    }

    public int getMaxSegundosRS() {
        return maxSegundosRS;
    }

    public int getMaxSegundosRT() {
        return maxSegundosRT;
    }

    public int getSoma() {
        return soma;
    }

    public int getVezeselevador() {
        return vezeselevador;
    }

    public Chairlift(Turistas turistas, Tempo tempo) {
        this.turistas = turistas;
        this.tempo = tempo;
    }


    /* incrementa cada subida de 4 ou menos pessoas, visto que o elevador só pode suportar 4 pessoas por vezes*/
    public void volta() {
        if (this.elevador != 0) {
            this.soma += this.elevador;
            this.elevador = 0; // depois de sair o elevador fica vazio
            this.vezeselevador += 1;
        }
    }

    /* regra para o LT, sobrando a vaga acrescentar com uma pessoa do RS*/
    private void regraLT() {
        int LT = turistas.getLT();
        for (int i = 0; i < 3; i++) {
            if (this.maxMinutosLT <= turistas.filaLT.getMaxMin() && this.maxSegundosLT <= turistas.filaLT.getMaxSeg()) { //Maior tempo de espera LT
                this.maxMinutosLT = turistas.filaLT.getMaxMin();
                this.maxSegundosLT = turistas.filaLT.getMaxSeg();
            }
            this.elevador += 1;
            LT -= 1;
            turistas.filaLT.descer();
        }
        turistas.setLT(LT);
        if (turistas.getRS() >= 1) {
            int RS = turistas.getRS();
            if (this.maxMinutosRS <= turistas.filaRS.getMaxMin() && this.maxSegundosRS <= turistas.filaRS.getMaxSeg()) { //Maior tempo de espera RS
                this.maxMinutosRS = turistas.filaRS.getMaxMin();
                this.maxSegundosRS = turistas.filaRS.getMaxSeg();
            }
            this.elevador += 1;
            RS -= 1;
            turistas.filaRS.descer();
            turistas.setRS(RS);
        }
    }
    /* regra para o RT, sobrando a vaga acrescentar com uma pessoa do LS*/
    private void regraRT() {
        int RT = turistas.getRT();
        for (int i = 0; i < 3; i++) {
            if (this.elevador != 4) {
                if (this.maxMinutosRT <= turistas.filaRT.getMaxMin() && this.maxSegundosRT <= turistas.filaRT.getMaxSeg()) { //Maior tempo de espera RT
                    this.maxMinutosRT = turistas.filaRT.getMaxMin();
                    this.maxSegundosRT = turistas.filaRT.getMaxSeg();
                }
                this.elevador += 1;
                RT -= 1;
                turistas.filaRT.descer();
            }
        }
        turistas.setRT(RT);
        if (turistas.getLS() >= 1) {
            int LS = turistas.getLS();
            if (this.maxMinutosLS <= turistas.filaLS.getMaxMin() && this.maxSegundosLS <= turistas.filaLS.getMaxSeg()) { //Maior tempo de espera LS
                this.maxMinutosLS = turistas.filaLS.getMaxMin();
                this.maxSegundosLS = turistas.filaLS.getMaxSeg();
            }
            this.elevador += 1;
            LS -= 1;
            turistas.setLS(LS);
            turistas.filaLS.descer();
        }
    }
    /* regra para o LS, sobrando a vaga acrescentar com a quantidade necessária de pessoa do RS para cobrir*/
    private void regraLS() {
        int LS = turistas.getLS();
        for (int i = 0; i <= LS; i++) {
            if (this.elevador != 4) {
                if (this.maxMinutosLS <= turistas.filaLS.getMaxMin() && this.maxSegundosLS <= turistas.filaLS.getMaxSeg()) {//Maior tempo de espera LS
                    this.maxMinutosLS = turistas.filaLS.getMaxMin();
                    this.maxSegundosLS = turistas.filaLS.getMaxSeg();
                }
                this.elevador += 1;
                LS -= 1;
                turistas.filaLS.descer();
            }
        }
        turistas.setLS(LS);
        if (this.elevador < 4) {
            regraRS(false); //Para executar caso ele não tenha sido executado antes(objetivo de evitar loop)
        }
    }
    /* regra para o RS, sobrando a vaga acrescentar com a quantidade necessária de pessoa do LS para cobrir*/
    private void regraRS(boolean execute) {
        int RS = turistas.getRS();
        for (int i = 0; i <= RS; i++) {
            if (this.elevador != 4) {
                this.elevador += 1;
                RS -= 1;
                if (this.maxMinutosRS <= turistas.filaRS.getMaxMin() && this.maxSegundosRS <= turistas.filaRS.getMaxSeg()) {
                    this.maxMinutosRS = turistas.filaRS.getMaxMin();
                    this.maxSegundosRS = turistas.filaRS.getMaxSeg();
                }
                turistas.filaRS.descer();
            }
        }
        turistas.setRS(RS);
        if (elevador < 4 && execute) {//Se não executou o LS antes execute (objetivo de evitar loop)
            regraLS();
        }
    }

    private int v0 = 0; // Para não deixar o LT ter mais prioridade que o RT
    private int v1 = 0;// Para não deixar o LT ter mais prioridade que o RT

    /*Função de subida no elevador, decrementa as pessoas da fila ele obedece cada regras citadas acima*/
    private/* synchronized*/ void decremento(boolean volta) {// volta-> para evitar loop entre o LS e RS
        if (volta) {
            boolean n = true; // Para não executar caso o anterior tiver sido executado, usado depois do desempenho do if ou else if não atender o objetivo
            if (turistas.getLT() >= 3 && v0 <= v1) {
                regraLT();
                System.out.println("Teste - executou regraLT");
                v0 += 1;
                n = false;
            }
            if (turistas.getRT() >= 3 && v0 > v1 && n) {
                regraRT();
                System.out.println("Teste - executou regraRT");
                v1 += 1;
                n = false;
            }
            if (turistas.getLS() >= 1 && n) {
                regraLS();
                System.out.println("Teste - executou regraLS");
                n = false;
            }
            if (turistas.getRS() >= 1 && n) {
                regraRS(true);
                System.out.println("Teste - executou regraRS");
            }
            System.out.println("Teste - volta");
            System.out.println("Teste - subida: " + this.elevador);
            volta();
            System.out.println("Teste - Verificação");
            verificacao();
        }
    }

    void verificacao() { // verificar no final total dentro das filas
        System.out.println("LT = " + turistas.getLT());
        System.out.println("Maximo de espera atual do LT = " + this.maxMinutosLT + " : " + this.maxSegundosLT);
        System.out.println("RT = " + turistas.getRT());
        System.out.println("Maximo de espera atual do RT = " + this.maxMinutosRT + " : " + this.maxSegundosRT);
        System.out.println("LS = " + turistas.getLS());
        System.out.println("Maximo de espera atual do LS = " + this.maxMinutosLS + " : " + this.maxSegundosLS);
        System.out.println("RS = " + turistas.getRS());
        System.out.println("Maximo de espera atual do RS = " + this.maxMinutosRS + " : " + this.maxSegundosRS);
        System.out.println("soma = " + this.soma);
    }

    public void run() {
        try {
            while (true) {
                if (!tempo.interrupcao()) {
                    decremento(turistas.getLS() >= 1); // para não ocorrer loop entre LS e RS
                    sleep(5000); // Espera de cinco segundos
                } else {
                    return; // Encerrar loop sem problema ou interrupção forçada
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
