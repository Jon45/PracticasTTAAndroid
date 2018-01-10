package eus.ehu.tta.ttaejemplo;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;

public class BusinessReal implements BusinessInterface {

    public BusinessReal()
    {

    }
    @Override
    public boolean login(String userName, String Password) {
        return true;
    }

    @Override
    public TestTTA getNewTest() {
        return null;
    }

    @Override
    public String getNewExercise() {
        return "";
    }

    @Override
    public boolean uploadFile(Uri uri) {
        return true;
    }

    @Override
    public boolean sendTest(int idEjercicio,int respuesta) {
        return true;
    }
}
