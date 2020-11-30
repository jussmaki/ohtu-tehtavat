package laskin;

public class Sovelluslogiikka {
 
    private int tulos;
    private int vanhaTulos;
 
    public void plus(int luku) {
        vanhaTulos = tulos;
        tulos += luku;
    }
     
    public void miinus(int luku) {
        vanhaTulos = tulos;        
        tulos -= luku;
    }
 
    public void nollaa() {
        vanhaTulos = tulos;        
        tulos = 0;
    }
 
    public int tulos() {
        return tulos;
    }
    
    public int vanhaTulos() {
        tulos = vanhaTulos;
        return vanhaTulos;
    }
}