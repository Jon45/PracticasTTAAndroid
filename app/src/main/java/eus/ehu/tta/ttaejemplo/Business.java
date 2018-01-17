package eus.ehu.tta.ttaejemplo;

import android.net.Uri;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Business implements BusinessInterface {

    private TestTTA [] tests;
    private static int numTest = 0;
    public Business ()
    {
        /*tests = new TestTTA[] {
                new TestTTA(
                        1,
                        2,
                        "¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?",
                        new ArrayList<String>(Arrays.asList("Versión de la aplicación","Listado de componentes de la aplicación","Opciones del menú de ajustes","Nivel mínimo de la API de Android requerida","Nombre del paquete java de la aplicación")),
                        "The manifest describes the <b>components of the application</b>: the activities, services, broadcast receivers, and content providers that ...",
                        "text/html"),
                new TestTTA(
                        2,
                        2,
                        "¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?",
                        new ArrayList<String>(Arrays.asList("Versión de la aplicación","Listado de componentes de la aplicación","Opciones del menú de ajustes","Nivel mínimo de la API de Android requerida","Nombre del paquete java de la aplicación")),
                        "https://developer.android.com/guide/topics/manifest/manifest-intro.html?hl=es-419",
                        "text/html"),
                new TestTTA(
                        3,
                        2,
                        "¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?",
                        new ArrayList<String>(Arrays.asList("Versión de la aplicación","Listado de componentes de la aplicación","Opciones del menú de ajustes","Nivel mínimo de la API de Android requerida","Nombre del paquete java de la aplicación")),
                        "http://techslides.com/demos/sample-videos/small.mp4",
                        "video/mp4"),
                new TestTTA(
                        4,
                        2,
                        "¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?",
                        new ArrayList<String>(Arrays.asList("Versión de la aplicación","Listado de componentes de la aplicación","Opciones del menú de ajustes","Nivel mínimo de la API de Android requerida","Nombre del paquete java de la aplicación")),
                        "http://techslides.com/demos/sample-videos/small.mp4",
                        "audio/mp4")
        };*/
    }
    @Override
    public boolean login(String userName, String Password) {
        return true;
    }

    @Override
    public TestTTA getNewTest(int id) {
        TestTTA test = tests[numTest % tests.length];
        numTest++;
        return test;
    }

    @Override
    public String getNewExercise(int id) {
        return "Explica cómo aplicarías el patrón de diseño MVP en el desarrollo de una app para Android";
    }

    @Override
    public boolean uploadFile(String path, InputStream is, String filename) {
        return true;
    }

    @Override
    public boolean sendTest(int idEjercicio,int respuesta) {
        return respuesta == 2;
    }
}
