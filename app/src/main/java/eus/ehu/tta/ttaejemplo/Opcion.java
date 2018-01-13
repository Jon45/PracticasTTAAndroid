package eus.ehu.tta.ttaejemplo;

import android.os.Parcel;
import android.os.Parcelable;

public class Opcion implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.texto);
        dest.writeString(this.ayuda);
        dest.writeString(this.mimeTypeAyuda);
    }

    public Opcion() {
    }

    protected Opcion(Parcel in) {
        this.texto = in.readString();
        this.ayuda = in.readString();
        this.mimeTypeAyuda = in.readString();
    }

    public static final Parcelable.Creator<Opcion> CREATOR = new Parcelable.Creator<Opcion>() {
        @Override
        public Opcion createFromParcel(Parcel source) {
            return new Opcion(source);
        }

        @Override
        public Opcion[] newArray(int size) {
            return new Opcion[size];
        }
    };
}