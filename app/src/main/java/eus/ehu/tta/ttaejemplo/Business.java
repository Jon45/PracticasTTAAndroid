package eus.ehu.tta.ttaejemplo;

import java.util.ArrayList;
import java.util.Arrays;

public class Business implements BusinessInterface {
    @Override
    public boolean login(String userName, String Password) {
        return true;
    }

    @Override
    public TestTTA getNewTest() {
        TestTTA test = new TestTTA();
        test.setPregunta("¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?");
        test.setOpciones(new ArrayList<String>(Arrays.asList("Versión de la aplicación","Listado de componentes de la aplicación","Opciones del menú de ajustes","Nivel mínimo de la API de Android requerida","Nombre del paquete java de la aplicación")));
        test.setCorrecta(2);
        return test;
    }

    @Override
    public String getNewExercise() {
        return null;
    }

    @Override
    public boolean uploadFile() {
        return false;
    }

    @Override
    public boolean takePhoto() {
        return false;
    }

    @Override
    public boolean recordAudio() {
        return false;
    }

    @Override
    public boolean recordVideo() {
        return false;
    }

    @Override
    public boolean sendTest(int idEjercicio,int respuesta) {
        return true;
    }
}
