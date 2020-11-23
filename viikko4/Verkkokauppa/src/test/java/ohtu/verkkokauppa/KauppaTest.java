package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {
    
    private Pankki pankki;
    private Viitegeneraattori viite;
    private Varasto varasto;
    
    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        // määritellään että viitegeneraattori palauttaa viitten 42        
        when(viite.uusi()).thenReturn(42);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);   
    }
    
    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreillaKunOstetaanKaksiTuotetta() {
        // määritellään että viitegeneraattori palauttaa viitten 42        
        when(viite.uusi()).thenReturn(42);        
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on kahvi jonka hinta on 2 ja saldo 20
        when(varasto.saldo(2)).thenReturn(20); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kahvi", 2));        
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // lisätään koriin tuote 2 eli kahvia
        k.tilimaksu("arttu", "119");
        verify(pankki).tilisiirto("arttu", 42, "119", "33333-44455", 7);   
    }
    
    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreillaKunLisataanKoriinKaksiTuotettaJoistaToinenOnLoppu() {
        // määritellään että viitegeneraattori palauttaa viitten 42        
        when(viite.uusi()).thenReturn(42);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 0
        when(varasto.saldo(1)).thenReturn(0); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on kahvi jonka hinta on 2 ja saldo 20
        when(varasto.saldo(2)).thenReturn(20); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kahvi", 2));
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // lisätään koriin tuote 2 eli kahvia
        k.tilimaksu("arttu", "119");
        verify(pankki).tilisiirto("arttu", 42, "119", "33333-44455", 2);   
    }
    
    @Test
    public void aloitaAsiointiNollaaEdellisenOstoksenTiedot() {
        when(viite.uusi()).thenReturn(42).thenReturn(43);
        when(varasto.saldo(1)).thenReturn(10).thenReturn(9); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(20).thenReturn(19); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kahvi", 2));        
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // lisätään koriin tuote 2 eli kahvia
        k.tilimaksu("arttu", "119");
        verify(pankki).tilisiirto("arttu", 42, "119", "33333-44455", 7);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto("pekka", 43, "12345", "33333-44455", 5);
    }
    
    @Test
    public void kauppaPyytaaUudenViitenumeronJokaMaksutapahtumalle() {
        when(viite.uusi()).thenReturn(42).thenReturn(43);
        when(varasto.saldo(1)).thenReturn(10).thenReturn(9); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(20).thenReturn(19); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kahvi", 2));        
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // lisätään koriin tuote 2 eli kahvia
        k.tilimaksu("arttu", "119");
        verify(viite, times(1)).uusi();
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");
        verify(viite, times(2)).uusi();
    }
    
    @Test
    public void poistaKoristaMetodipoistaaTuotteenKorista() {
        // määritellään että viitegeneraattori palauttaa viitten 42        
        when(viite.uusi()).thenReturn(42);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on kahvi jonka hinta on 2 ja saldo 20
        when(varasto.saldo(2)).thenReturn(20); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kahvi", 2));
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // lisätään koriin tuote 2 eli kahvia
        k.poistaKorista(1); //poistetaan maito korista
        k.tilimaksu("arttu", "119");
        verify(pankki).tilisiirto("arttu", 42, "119", "33333-44455", 2);   
    }    
}