package eus.ehu.tta.ttaejemplo;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BusinessReal implements BusinessInterface {

    private RestClient rest;

    public BusinessReal(String baseURI) {
        rest = new RestClient(baseURI);
    }

    @Override
    public boolean login(String userName, String Password) {
        return true;
    }

    @Override
    public TestTTA getNewTest(int id) {
        TestTTA test = new TestTTA();

        try {
            JSONObject json = rest.getJson(String.format("getTest?id=%d",id));
            test.setIdEjercicio(id);
            JSONArray jsonArray = json.getJSONArray("opciones");
            String[] opciones = new String[jsonArray.length()];
            for (int i=0; i < jsonArray.length(); i++)
            {
                JSONObject opcion = jsonArray.getJSONObject(i);
                if (opcion.getBoolean("correct") == true)
                {
                    test.setCorrecta(i);
                }
                opciones
            }
            test.setOpciones(opciones);
            test.setMimeTypeAyuda(jsonArray.getJSONObject(0).getJSONObject("resourceType").getString("mime"));
            test.setAyuda(json.getString("advise"));
            test.setPregunta(json.getString("wording"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return test;
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
