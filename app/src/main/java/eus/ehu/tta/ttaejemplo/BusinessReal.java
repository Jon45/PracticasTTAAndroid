package eus.ehu.tta.ttaejemplo;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BusinessReal implements BusinessInterface {

    RestClient rest;

    public BusinessReal(String baseURI)
    {
        rest = new RestClient(baseURI);
        rest.setHttpBasicAuth("12345678A","tta");
    }
    @Override
    public boolean login(String userName, String Password) {
        return true;
    }

    public TestTTA getNewTest(int id) {
        TestTTA test = new TestTTA();

        try {
            JSONObject json = rest.getJson(String.format("getTest?id=%d",id));
            test.setIdEjercicio(id);
            test.setPregunta(json.getString("wording"));
            JSONArray jsonArray = json.getJSONArray("choices");
            List<Opcion> opciones = new ArrayList<>();

            for (int i=0; i < jsonArray.length(); i++)
            {
                JSONObject opcionJSON = jsonArray.getJSONObject(i);
                if (opcionJSON.getBoolean("correct") == true)
                {
                    test.setCorrecta(i);
                }
                Opcion opcion = new Opcion();
                opcion.setTexto(opcionJSON.getString("answer"));
                opcion.setAyuda(opcionJSON.getString("advise"));
                if (!opcionJSON.isNull("resourceType")) {
                    opcion.setMimeTypeAyuda(opcionJSON.getJSONObject("resourceType").getString("mime"));
                }

                opciones.add(opcion);
            }
            test.setOpciones(opciones);
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
