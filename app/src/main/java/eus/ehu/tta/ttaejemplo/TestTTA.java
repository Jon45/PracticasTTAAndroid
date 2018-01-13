package eus.ehu.tta.ttaejemplo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TestTTA implements Parcelable {
    private int idEjercicio;
    private int correcta;
    private String pregunta;
    private List<Opcion> opciones;

    public TestTTA ()
    {

    }
    
    public TestTTA(int idEjercicio, int correcta, String pregunta, List<Opcion> opciones) {
        this.idEjercicio = idEjercicio;
        this.correcta = correcta;
        this.pregunta = pregunta;
        this.opciones = opciones;
    }

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

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idEjercicio);
        dest.writeInt(this.correcta);
        dest.writeString(this.pregunta);
        dest.writeList(this.opciones);
    }

    protected TestTTA(Parcel in) {
        this.idEjercicio = in.readInt();
        this.correcta = in.readInt();
        this.pregunta = in.readString();
        this.opciones = new ArrayList<Opcion>();
        in.readList(this.opciones, Opcion.class.getClassLoader());
    }

    public static final Creator<TestTTA> CREATOR = new Creator<TestTTA>() {
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
