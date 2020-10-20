package uniftec.joao.com.projetoempreendedor;

import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityBase extends AppCompatActivity {

    public final String cServidor = "https://apitcc20200818071412.azurewebsites.net/api";
    public String tipoRequisicao = "";

    void desabilitaInteracao()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    void habilitaInteracao()
    {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    void RequisicaoGET(String url){

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    RetornoGET(response);
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ErroRequisicao(error);
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s","tccandrejoao","AndreJoao*");
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                    params.put("Authorization", auth);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1000000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void RequisicaoPOST(String url, Object objeto){

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String json = new Gson().toJson(objeto);
            JSONObject jsonBody = new JSONObject(json);

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    RetornoPOST(response);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ErroRequisicao(error);
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s","tccandrejoao","AndreJoao*");
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                    params.put("Authorization", auth);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void RetornoGET(JSONArray resposta) {}
    void RetornoPOST(JSONObject resposta){}
    void ErroRequisicao(VolleyError erro){
        Toast.makeText(this, "Ocorreu uma falha ao tentar se conectar com o servidor", Toast.LENGTH_LONG).show();
    }
}
