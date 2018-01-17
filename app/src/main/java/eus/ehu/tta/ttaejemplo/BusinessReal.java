package eus.ehu.tta.ttaejemplo;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class BusinessReal implements BusinessInterface {

    RestClient rest;

    public BusinessReal(String baseURI)
    {
        rest = new RestClient(baseURI);
    }

    public BusinessReal(String baseURI, String dni, String password)
    {
        this(baseURI);
        setAuthentication(dni,password);
    }

    private void setAuthentication(String dni, String password) {
        rest.setHttpBasicAuth(dni,password);
    }

    @Override
    public boolean login(String dni, String password) {
        boolean correctLogin = false;
        rest.setHttpBasicAuth(dni,password);
        try {
            JSONObject json = rest.getJson(String.format("getStatus?dni=%s",dni));
            if (json != null)
            {
                UserData userData = UserData.getInstance();
                userData.setUserID(json.getInt("id"));
                userData.setUserName(json.getString("user"));
                userData.setPassword(password);
                userData.setDni(dni);
                correctLogin = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return correctLogin;
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
    public String getNewExercise(int id) {
        String exercise = null;
        try {
            JSONObject json = rest.getJson(String.format("getExercise?id=%d",id));
            exercise = json.getString("wording");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exercise;
    }

    @Override
    public boolean uploadFile(String path, InputStream is, String filename) {
        boolean result = false;
        try {
            int code = rest.postFile(path,is,filename);
            if (code == HttpURLConnection.HTTP_NO_CONTENT)
            {
                result = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean sendTest(int userId,int respuesta) {
        JSONObject jsonObject = new JSONObject();
        boolean isTestCorrect = false;
        try {
            jsonObject.put("userId",userId);
            jsonObject.put("choiceId",respuesta);
            int statusCode = rest.postJson(jsonObject,"postChoice");
            if (statusCode == HttpURLConnection.HTTP_NO_CONTENT)
            {
                isTestCorrect = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return isTestCorrect;
    }
}
