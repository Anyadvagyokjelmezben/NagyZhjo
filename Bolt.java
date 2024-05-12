import java.util.*;

public class Bolt {
    private final boolean nagykereskedes;
    private int penz;
    private Map<String, Integer> termekKeszlet;
    private Map<String, Integer> termekAra;

    public Bolt(boolean nagykereskedes) {
        if (nagykereskedes) {
            this.nagykereskedes = true;
        } else {
            this.nagykereskedes = false;
        }
        this.penz = 0;
        this.termekKeszlet = new HashMap<>();
        this.termekAra = new HashMap<>();
    }

    public Integer[] lekerdezes(String termek){
        Integer[] list = new Integer[2];
        if(termekAra.containsKey(termek)){
            list[0] = termekAra.get(termek);
            list[1] = termekKeszlet.get(termek);
        } else {
            list [0] = null;
            list [1] = null;
         }
            return list;
    }

    public void szallitas(String termek, int mennyiseg){
        if(termekKeszlet.containsKey(termek)){
           termekKeszlet.put(termek,termekKeszlet.get(termek) + mennyiseg);
        } else if (!termekKeszlet.containsKey(termek)) {
            termekKeszlet.put(termek, mennyiseg);
            termekAra.put(termek,1000);
        }
    }
    public void arvaltozas(String termek, int minimumar, int ajanlottar) {
        if (termekAra.containsKey(termek)) {
            if (!nagykereskedes) {
                termekAra.put(termek, ajanlottar);
            } else {
                termekAra.put(termek, minimumar);
            }
        }
    }

    public String[] termekListazas(boolean arSzerintiRendezes) {
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(termekAra.entrySet());
        if (arSzerintiRendezes) {
            lista.sort(Map.Entry.comparingByValue());
        } else {
            lista.sort(Map.Entry.comparingByKey());
        }
        String[] termekLista = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            termekLista[i] = lista.get(i).getKey();
        }
        return termekLista;
    }

    public boolean vasarlas(Vasarlo vasarlo, Map<String, Integer> bevasarloLista) {
        int osszesar = 0;
        Map<String, Integer> vasaroltTermekKeszlet = new HashMap<>();
        boolean teljesVasarlas = true;

        for (Map.Entry<String, Integer> bevasaroltTermek : bevasarloLista.entrySet()) {
            String termek = bevasaroltTermek.getKey();
            int mennyiseg = bevasaroltTermek.getValue();

            if (termekKeszlet.containsKey(termek)) {
                int raktaronLevoMennyiseg = termekKeszlet.get(termek);
                int vasaroltMennyiseg = Math.min(raktaronLevoMennyiseg, mennyiseg);
                if (vasaroltMennyiseg < mennyiseg) {
                    teljesVasarlas = false;
                }
                int aktualisAr = vasaroltMennyiseg * termekAra.get(termek);
                if (vasarlo.getPenz() >= osszesar + aktualisAr) {
                    osszesar += aktualisAr;
                    vasaroltTermekKeszlet.put(termek, vasaroltMennyiseg);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        // A vasarlas
        for (Map.Entry<String, Integer> bevasaroltTermek : vasaroltTermekKeszlet.entrySet()) {
            String termek = bevasaroltTermek.getKey();
            int mennyiseg = bevasaroltTermek.getValue();

            termekKeszlet.put(termek, termekKeszlet.get(termek) - mennyiseg);
            this.penz += mennyiseg * termekAra.get(termek);
            vasarlo.penztKolt(mennyiseg * termekAra.get(termek));
        }

        return teljesVasarlas;
    }

}

