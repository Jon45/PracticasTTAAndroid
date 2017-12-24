package eus.ehu.tta.ttaejemplo;

import java.util.List;

/**
 * Created by jon on 12/22/17.
 */

public interface BusinessInterface {

    public class TestTTA
    {
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
    }
    public abstract boolean login (String userName, String Password);
    public abstract TestTTA getNewTest();
    public abstract String getNewExercise();
    public abstract boolean uploadFile();
    public abstract boolean takePhoto();
    public abstract boolean recordAudio();
    public abstract boolean recordVideo();
    public abstract boolean sendTest(int idEjercicio,int respuesta);
}
