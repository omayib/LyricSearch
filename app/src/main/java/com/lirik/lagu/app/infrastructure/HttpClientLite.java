package com.lirik.lagu.app.infrastructure;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

public class HttpClientLite {
	public static final int HTTP_TIMEOUT = 30 * 1000;
	private static HttpClient client;
	static FileInputStream fileInputStream = null;
	static String sResponse;

	private static HttpClient getHttpClient() {
		if (client == null) {
			client = new DefaultHttpClient();
			ClientConnectionManager mgr = client.getConnectionManager();
			HttpParams params = client.getParams();
			client = new DefaultHttpClient(new ThreadSafeClientConnManager(
					params, mgr.getSchemeRegistry()), params);
			final HttpParams parameterHttp = client.getParams();

			HttpConnectionParams.setConnectionTimeout(parameterHttp,
                    HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(parameterHttp, HTTP_TIMEOUT);
		}
		return client;

	}
    public static void addBodyParams(String name,Objects value){

    }
    public static String putRequest(String url) throws Exception {
        System.out.println("url :" + url);
        BufferedReader in = null;
        try {
            HttpClient hc = getHttpClient();
            HttpPut req = new HttpPut(url);
            HttpResponse resp = hc.execute(req);
            in = new BufferedReader(new InputStreamReader(resp.getEntity()
                    .getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String hasil = sb.toString();
            return hasil;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
	public static String putRequestWithRawData(String url, String data) throws IOException {
		System.out.println("url :" + url);
		String response = "";
		BufferedReader in = null;
		try {
			HttpClient client = getHttpClient();
			HttpPut req = new HttpPut(url);
			req.setHeader("Content-Type", "application/json");
			HttpEntity entity = new StringEntity(data);
			req.setEntity(entity);
			HttpResponse respon = client.execute(req);
			in = new BufferedReader(new InputStreamReader(respon.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String hasil = sb.toString();
			return hasil;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}

		}
		return response;
	}

	public static String postRequest(String url,
			ArrayList<NameValuePair> postParameters) throws Exception {
		System.out.println("url :" + url);
		BufferedReader in = null;
		try {
			HttpClient client = getHttpClient();
			HttpPost req = new HttpPost(url);
			if (!postParameters.isEmpty()) {
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
						postParameters);
				req.setEntity(formEntity);
			}
			HttpResponse respon = client.execute(req);
			in = new BufferedReader(new InputStreamReader(respon.getEntity()
					.getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String hasil = sb.toString();
			return hasil;
		} finally {
			if (in != null) {
				in.close();
			}

		}

	}

	public static String getRequest(String url) throws Exception {
		System.out.println("url :" + url);
		BufferedReader in = null;
		try {
			HttpClient hc = getHttpClient();
			HttpGet req = new HttpGet();
			req.setURI(new URI(url));
			HttpResponse resp = hc.execute(req);
			in = new BufferedReader(new InputStreamReader(resp.getEntity()
					.getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String hasil = sb.toString();
			return hasil;
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public static boolean isNetworkAvailable(Context con) {
		ConnectivityManager connectivityManager = (ConnectivityManager) con
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
