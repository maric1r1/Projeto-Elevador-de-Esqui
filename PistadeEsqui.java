package PCC_projects.TrabalhoPCC;

public class PistadeEsqui{

    static Tempo tempo = new Tempo();
    static Turistas turistas = new Turistas(tempo);
    static Chairlift chairlift = new Chairlift(turistas,tempo);

    public static void main(String[] args) {
        turistas.setDaemon(true);
        tempo.start();
        chairlift.start();
        turistas.start();

        try {
            tempo.join();
            tempo.join();
            chairlift.join();
            turistas.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TempoMedio();
        TaxaAproveitamento();

    }

    private static void TempoMedio(){
        for (int i = 0;i < 20;i++){
            System.out.print("---");
        }
        System.out.println();
        System.out.println("Tempo médio de espera para fila LS: " +chairlift.getMaxMinutosLS() + " : "+chairlift.getMaxSegundosLS());
        System.out.println("Tempo médio de espera para fila RS: " +chairlift.getMaxMinutosRS() + " : "+chairlift.getMaxSegundosRS());
        System.out.println("Tempo médio de espera para fila RT: " +chairlift.getMaxMinutosRT() + " : "+chairlift.getMaxSegundosRT());
        System.out.println("Tempo médio de espera para fila LT: " +chairlift.getMaxMinutosLT() + " : "+chairlift.getMaxSegundosLT());
        for (int i = 0;i < 20;i++){
            System.out.print("---");
        }
        System.out.println();
    }

    private static void TaxaAproveitamento(){
        for (int i = 0;i < 20;i++){
            System.out.print("---");
        }
        System.out.println();
        System.out.println("Taxa de Aproveitamento = ( " + chairlift.getSoma()+" / (" +chairlift.getVezeselevador()+ " * 4) )");
        float a = chairlift.getSoma();
        float b = chairlift.getVezeselevador()*4;
        float total = a/b;
        System.out.printf("Taxa de Aproveitamento = %.4f", total);
        System.out.println();
        System.out.println("Taxa de Aproveitamento = " + (int)(total*100)+"%");
        System.out.println();
        for (int i = 0;i < 20;i++){
            System.out.print("---");
        }
        System.out.println();
    }
}