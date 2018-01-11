package eus.ehu.tta.ttaejemplo;

import android.net.Uri;

/**
 * Created by jon on 12/22/17.
 */

public interface BusinessInterface {
    public abstract boolean login (String userName, String Password);
    public abstract TestTTA getNewTest(int id);
    public abstract String getNewExercise();
    public abstract boolean uploadFile(Uri uri);
    public abstract boolean sendTest(int idEjercicio,int respuesta);
}
