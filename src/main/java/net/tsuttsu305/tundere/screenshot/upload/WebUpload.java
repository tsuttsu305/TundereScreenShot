package net.tsuttsu305.tundere.screenshot.upload;

import net.tsuttsu305.tundere.screenshot.config.Config;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * Created by tsuttsu305 on 2014/06/29.
 */
public class WebUpload {

    public String uploadImage(File f) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        if (Config.getInstance().getConfig().getProperty("BASIC_AUTH", "false").equals("true")){
            Credentials credentials = new UsernamePasswordCredentials(
                    Config.getInstance().getConfig().getProperty("AUTH_USER"),
                    Config.getInstance().getConfig().getProperty("AUTH_PASS"));
            AuthScope scope = new AuthScope(null, -1);
            httpClient.getCredentialsProvider().setCredentials(scope, credentials);
        }

        HttpPost post = new HttpPost(Config.getInstance().getConfig().getProperty("UPLOAD_URL"));

        FileBody body = new FileBody(f);
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("upfile", body);
        post.setEntity(reqEntity);

        HttpResponse resp = httpClient.execute(post);

        InputStreamReader inputStreamReader = new InputStreamReader(resp.getEntity().getContent());
        StringBuilder sb = new StringBuilder();
        int len;
        char[] buf = new char[32];
        while ((len = inputStreamReader.read(buf)) != -1){
            sb.append(buf, 0, len);
        }

        return sb.toString();
    }
}
