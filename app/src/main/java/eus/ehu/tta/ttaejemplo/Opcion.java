package eus.ehu.tta.ttaejemplo;

public class Opcion {
    private String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    public String getMimeTypeAyuda() {
        return mimeTypeAyuda;
    }

    public void setMimeTypeAyuda(String mimeTypeAyuda) {
        this.mimeTypeAyuda = mimeTypeAyuda;
    }

    private String ayuda;
    private String mimeTypeAyuda;
}