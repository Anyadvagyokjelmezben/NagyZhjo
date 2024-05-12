import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Vasarlo implements Kozos{
    public final String nev;
    protected String igazolvanySzam;
    private int penz;

    public Vasarlo(String nev, String igazolvanySzam) {
        this.nev = nev;
        this.igazolvanySzam = igazolvanySzam;
    }

    @Override public void penztKolt(int mennyit){
        if (mennyit > this.penz){
            throw new PenzException("Nincs eleg penz!");
        }else {
            this.penz -= mennyit;
        }
    }

    public void vagyontKiszamit(String filename){
        try{
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String sor = scanner.nextLine();
                String[] reszek = sor.split(";");
                String tipus = reszek[1];
               int osszeg = Integer.parseInt(reszek[2]);
                    if(tipus.equals("BEVETEL")){
                        this.penz += osszeg;
                  } else if (tipus.equals("KIADAS")){
                         this.penz -= osszeg;
                    }
            }
            scanner.close();
        } catch (FileNotFoundException e){
            System.out.println("A fájl nem található: " + filename);
        }
    }

   @Override public int getPenz() {
        return penz;
    }
}
