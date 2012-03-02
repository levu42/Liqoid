/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.quadrillenschule.liquidroid.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

/**
 *
 * @author andi
 */
public class CachedAPI1Queries {

    File cacheFolder;
    public String url;
    public boolean wasReadFromNetwork = false;

    public CachedAPI1Queries(File cacheFolder) {
        this.cacheFolder = cacheFolder;
    }

    public void storeInCache(InputStream is) {
        if (wasReadFromNetwork) {
            File myfile = new File(cacheFolder, url.hashCode() + ".xml");
            try {
                myfile.createNewFile();
                FileOutputStream fos = new FileOutputStream(myfile);
                byte[] b = new byte[1];
                while (is.read(b) >= 0) {
                    fos.write(b);
                }
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public boolean cacheExists(String purl) {
        File myfile = new File(cacheFolder, purl.hashCode() + ".xml");
        return myfile.exists();
    }

    public InputStream queryInputStream(String api, String parameters, String apiUrl, String developerkey, boolean forceNetwork) throws IOException, FileNotFoundException {
        url = apiUrl + api + ".html?key=" + developerkey + parameters;
        if (forceNetwork) {
            return networkInputStream(url);
        }
        if (cacheExists(url)) {
            return cacheInputStream(url);
        }
        return networkInputStream(url);
    }

    private InputStream cacheInputStream(String purl) throws FileNotFoundException {
        File myfile = new File(cacheFolder, purl.hashCode() + ".xml");
        FileInputStream fis = new FileInputStream(myfile);

        return fis;
    }

    private InputStream networkInputStream(String url) throws IOException {
        DefaultHttpClient httpClient;

        if (url.startsWith("https")) {
            HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            DefaultHttpClient client = new DefaultHttpClient();
            SchemeRegistry registry = new SchemeRegistry();
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
            registry.register(new Scheme("https", socketFactory, 443));
            SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
            httpClient = new DefaultHttpClient(mgr, client.getParams());
            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        } else {
            httpClient = new DefaultHttpClient();
        }
        HttpPost httpPost = new HttpPost(url);
        String responseString = "";
        HttpResponse response = (HttpResponse) httpClient.execute(httpPost);
        wasReadFromNetwork = true;


        storeInCache(response.getEntity().getContent());
        return cacheInputStream(url);
    }
}