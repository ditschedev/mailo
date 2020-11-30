package dev.ditsche.mailo.mjml;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ditsche.mailo.config.MailoConfig;
import dev.ditsche.mailo.exception.MjmlClientConfigExcpetion;
import dev.ditsche.mailo.exception.MjmlRenderException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.Base64;

public class MjmlClient {

    private final ObjectMapper mapper;

    private final HttpClient httpClient;

    private final String authHeaderValue;

    public MjmlClient() {
        MailoConfig config = MailoConfig.get();
        authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((config.getMjmlAppId() + ":" + config.getMjmlAppSecret()).getBytes());
        if(config.getMjmlAppId() == null || config.getMjmlAppId().trim().isEmpty() || config.getMjmlAppSecret() == null || config.getMjmlAppSecret().trim().isEmpty())
            throw new MjmlClientConfigExcpetion("Mjml api keys can not be null or empty");
        this.mapper = new ObjectMapper();
        try {
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, (chain, authType) -> true)
                    .build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
            this.httpClient = HttpClientBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String render(String mjml) {
        if(mjml == null || mjml.trim().isEmpty())
            throw new MjmlRenderException("Can not render empty mjml");
        try {
            HttpPost httpRequest = new HttpPost("https://api.mjml.io/v1/render");
            httpRequest.addHeader("Authorization", authHeaderValue);
            httpRequest.addHeader("Accept", "application/json");
            MjmlRequest request = new MjmlRequest(mjml);
            HttpEntity httpRequestEntity = new StringEntity(mapper.writeValueAsString(request), ContentType.APPLICATION_JSON);
            httpRequest.setEntity(httpRequestEntity);

            HttpResponse response = httpClient.execute(httpRequest);
            HttpEntity httpResponseEntity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case 200:
                    RenderResponse renderResponse = mapper.readValue(httpResponseEntity.getContent(), RenderResponse.class);
                    return renderResponse.getHtml();
                case 400:
                case 401:
                case 403:
                case 500:
                    ErrorResponse errorResponse = mapper.readValue(httpResponseEntity.getContent(), ErrorResponse.class);
                    throw new MjmlRenderException(errorResponse.getMessage());
                default:
                    throw new MjmlRenderException("Unknown exception (Status: " + statusCode + "). Open an issue on Github");
            }
        } catch (IOException e) {
            throw new MjmlRenderException(e.getMessage());
        }
    }

}
