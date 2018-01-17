package eus.ehu.tta.ttaejemplo;

import android.net.Uri;

import java.io.InputStream;

/**
 * Created by jon on 12/22/17.
 */

public interface BusinessInterface {
    public abstract boolean login (String dni, String password);
    public abstract TestTTA getNewTest(int id);
    public abstract String getNewExercise(int id);
    public abstract boolean uploadFile(String path, InputStream is, String filename);
    public abstract boolean sendTest(int userId,int respuesta);
}
