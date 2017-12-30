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
        test.setAyuda("The manifest describes the <b>components of the application</b>: the activities, services, broadcast receivers, and content providers that ...");
        return test;
    }

    @Override
    public String getNewExercise() {
        return "Explica cómo aplicarías el patrón de diseño MVP en el desarrollo de una app para Android";
    }

    @Override
    public boolean uploadFile() {
        return true;
    }

    @Override
    public boolean takePhoto() {
        return true;
    }

    @Override
    public boolean recordAudio() {
        return true;
    }

    @Override
    public boolean recordVideo() {
        return true;
    }

    @Override
    public boolean sendTest(int idEjercicio,int respuesta) {
        return respuesta == 2;
    }
}
