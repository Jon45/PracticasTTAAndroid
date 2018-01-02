package eus.ehu.tta.ttaejemplo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TestTTA implements Parcelable {
    private int idEjercicio;
    private int correcta;
    private String pregunta;
    private List<String> opciones;
    private String ayuda;

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idEjercicio);
        dest.writeInt(this.correcta);
        dest.writeString(this.pregunta);
        dest.writeStringList(this.opciones);
        dest.writeString(this.ayuda);
    }

    public TestTTA() {
    }

    protected TestTTA(Parcel in) {
        this.idEjercicio = in.readInt();
        this.correcta = in.readInt();
        this.pregunta = in.readString();
        this.opciones = in.createStringArrayList();
        this.ayuda = in.readString();
    }

    public static final Parcelable.Creator<TestTTA> CREATOR = new Parcelable.Creator<TestTTA>() {
        @Override
        public TestTTA createFromParcel(Parcel source) {
            return new TestTTA(source);
        }

        @Override
        public TestTTA[] newArray(int size) {
            return new TestTTA[size];
        }
    };
}
