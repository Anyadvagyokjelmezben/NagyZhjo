import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Vallalkozas implements Kozos {
    private final List<Integer> befektetesek = new ArrayList<>();

    @Override public int getPenz() {
        int osszeg = 0;

        for (int penz: befektetesek) {
            osszeg += penz;
        }

        return osszeg;
    }



    @Override public void penztKolt(int mennyit) {
        if (getPenz() < mennyit) {
            throw new PenzException("Nincs eleg penz!");
        }

        Iterator<Integer> iterator = befektetesek.iterator();
        while (iterator.hasNext() && mennyit > 0) {
            int elem = iterator.next();
            mennyit -= elem;
            iterator.remove();
        }

        befektetesek.add(-mennyit);
    }
}
