package ohtu.verkkokauppa;

public interface KauppaRajapinta {
    void aloitaAsiointi();
    boolean tilimaksu(String nimi, String tiliNumero);
}
