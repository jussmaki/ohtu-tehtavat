package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
public class StatisticsTest {

    Reader readerStub = new Reader() {
 
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();
 
            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));
 
            return players;
        }
    };
 
    Statistics stats;
   
    @Before
    public void setUp(){
        stats = new Statistics(readerStub);
    }  
    @Test
    public void pelaajanHakeminenNimellaToimii() {
        Player pelaaja = stats.search("Kurri");
        assertEquals("Kurri", pelaaja.getName());
    }
    
    @Test
    public void pelaajanHakeminenNimellaPalauttaaNullKunEiLoydy() {
        Player pelaaja = stats.search("Selänne");
        assertEquals(null, pelaaja);
    }
    
    @Test
    public void pelaajienHakeminenTiiminNimellaToimii() {
        List<Player> pelaajat = stats.team("EDM");
        ArrayList<String> joukkoe = new ArrayList<>();
        joukkoe.add("Semenko");
        joukkoe.add("Kurri");
        joukkoe.add("Gretzky");
        for (Player pelaaja : pelaajat) {
            if (!joukkoe.contains(pelaaja.getName())) {
                fail();
            }
        }
    }
    
    @Test
    public void topScorersPalauttaaOikeanMaaran() {
        List<Player> pelaajat = stats.topScorers(3);
        assertEquals(3, pelaajat.size());        
    }
    
    @Test
    public void topScoresPalauttaaOikeatPelaajat() {
        List<Player> pelaajat = stats.topScorers(3);
        assertEquals("Gretzky", pelaajat.get(0).getName()); 
        assertEquals("Lemieux", pelaajat.get(1).getName()); 
        assertEquals("Yzerman", pelaajat.get(2).getName()); 
    }
}
