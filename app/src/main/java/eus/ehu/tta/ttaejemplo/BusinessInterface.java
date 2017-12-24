package eus.ehu.tta.ttaejemplo;

import java.util.List;

/**
 * Created by jon on 12/22/17.
 */

public interface BusinessInterface {
    public class TestTTA
    {
        private int correcta;
        private String pregunta;
        private List<String> opciones;

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
    }
    public abstract boolean login (String userName, String Password);
    public abstract TestTTA getNewTest();
    public abstract String getNewExercise();
    public boolean uploadFile();
    public boolean takePhoto();
    public boolean recordAudio();
    public boolean recordVideo();
}
