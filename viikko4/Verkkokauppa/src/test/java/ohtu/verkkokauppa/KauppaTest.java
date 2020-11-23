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
        // määritellään että viitegeneraattori palauttaa viitten 42        
        when(viite.uusi()).thenReturn(42);
        varasto = mock(Varasto.class);
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
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
}