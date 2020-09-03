package uniftec.joao.com.projetoempreendedor;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HttpRequest extends AsyncTask<String, Void, String> {
    public static  String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... params){
        String stringUrl = params[0];
        String result = null;
        OutputStream out = null;
        String auth,basicAuth,asas;

        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);
            //Create a connection
            HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();

            auth = "tccandrejoao" + ":" + "AndreJoao*";

            basicAuth = "Basic " + new String(Base64.getEncoder().encode(auth.getBytes()));

            connection.setRequestProperty ("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestMethod("POST");

            //Connect to our url
            connection.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("usuario", "joao.buogo");
            jsonParam.put("senha", "121331");
            jsonParam.put("email", "joao.buogo@metadados.com.br");
            jsonParam.put("nome", "Joao");
            jsonParam.put("cnpj", " ");
            jsonParam.put("cpf", "12982901821");
            jsonParam.put("administrador", 1);

            Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(connection.getResponseCode()));
            Log.i("MSG" , connection.getResponseMessage());

            asas = os.toString();

            connection.disconnect();

//            InputStreamReader streamReader = new
//                    InputStreamReader(connection.getInputStream());
//            //Create a new buffered reader and String Builder
//            BufferedReader reader = new BufferedReader(streamReader);
//            StringBuilder stringBuilder = new StringBuilder();
//            //Check if the line we are reading is not null
//            while((inputLine = reader.readLine()) != null){
//                stringBuilder.append(inputLine);
//            }
            //Close our InputStream and Buffered reader
           // reader.close();
            //streamReader.close();
            //Set our result equal to our stringBuilder
            //result = stringBuilder.toString();
        }
        catch(IOException | JSONException e){
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    public HttpRequest(String metodo){
        REQUEST_METHOD = metodo;
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}
