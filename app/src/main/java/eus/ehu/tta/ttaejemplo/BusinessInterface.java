package eus.ehu.tta.ttaejemplo;

/**
 * Created by jon on 12/22/17.
 */

public interface BusinessInterface {
    public abstract boolean login (String userName, String Password);
    public abstract TestTTA getNewTest();
    public abstract String getNewExercise();
    public abstract boolean uploadFile();
    public abstract boolean sendTest(int idEjercicio,int respuesta);
}
