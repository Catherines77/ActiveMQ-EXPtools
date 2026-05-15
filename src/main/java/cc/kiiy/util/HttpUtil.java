package cc.kiiy.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtil {
    
    static {
        // 允许 HttpURLConnection 设置受限的请求头，例如 Origin
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        
        // 忽略 HTTPS 证书校验和主机名校验
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static ProxyConfig proxyConfig;
    private static Map<String, String> customHeaders;
    
    public static void setProxyConfig(ProxyConfig config) {
        proxyConfig = config;
    }
    
    public static void setCustomHeaders(Map<String, String> headers) {
        customHeaders = headers;
    }
    
    public static HttpResponse sendGet(String urlStr, int connectTimeout, int readTimeout) throws Exception {
        return sendRequest(urlStr, "GET", null, connectTimeout, readTimeout, null, null);
    }
    
    public static HttpResponse sendGet(String urlStr, Map<String, String> headers, int connectTimeout, int readTimeout) throws Exception {
        return sendRequest(urlStr, "GET", null, connectTimeout, readTimeout, null, headers);
    }
    
    public static HttpResponse sendGetWithAuth(String urlStr, String encodedCredentials, int connectTimeout, int readTimeout) throws Exception {
        return sendRequest(urlStr, "GET", null, connectTimeout, readTimeout, encodedCredentials, null);
    }
    
    public static HttpResponse sendPost(String urlStr, String body, Map<String, String> headers, int connectTimeout, int readTimeout) throws Exception {
        return sendRequest(urlStr, "POST", body, connectTimeout, readTimeout, null, headers);
    }
    
    public static HttpResponse sendPost(String urlStr, String body, Map<String, String> headers, String encodedCredentials, int connectTimeout, int readTimeout) throws Exception {
        return sendRequest(urlStr, "POST", body, connectTimeout, readTimeout, encodedCredentials, headers);
    }
    
    public static HttpResponse sendPut(String urlStr, String body, int connectTimeout, int readTimeout) throws Exception {
        return sendRequest(urlStr, "PUT", body, connectTimeout, readTimeout, null, null);
    }
    
    public static HttpResponse sendMove(String urlStr, String destination, int connectTimeout, int readTimeout) throws Exception {
        Map<String, String> headers = new java.util.HashMap<>();
        headers.put("Destination", destination);
        return sendRequest(urlStr, "MOVE", null, connectTimeout, readTimeout, null, headers);
    }
    
    private static HttpResponse sendRequest(String urlStr, String method, String body, int connectTimeout, int readTimeout, String encodedCredentials, Map<String, String> requestHeaders) throws Exception {
        return sendRequestWithRedirect(urlStr, method, body, connectTimeout, readTimeout, encodedCredentials, requestHeaders, 0);
    }

    private static HttpResponse sendRequestWithRedirect(String urlStr, String method, String body, int connectTimeout, int readTimeout, String encodedCredentials, Map<String, String> requestHeaders, int redirectCount) throws Exception {
        if (redirectCount > 5) {
            throw new Exception("Too many redirects");
        }
        
        URL url = new URL(urlStr);
        
        HttpURLConnection conn;
        if (proxyConfig != null && proxyConfig.isEnabled()) {
            Proxy.Type proxyType = "SOCKS".equalsIgnoreCase(proxyConfig.getType()) ? Proxy.Type.SOCKS : Proxy.Type.HTTP;
            Proxy proxy = new Proxy(proxyType, new InetSocketAddress(proxyConfig.getHost(), proxyConfig.getPort()));
            
            if (proxyConfig.getUsername() != null && !proxyConfig.getUsername().isEmpty()) {
                java.net.Authenticator.setDefault(new java.net.Authenticator() {
                    @Override
                    protected java.net.PasswordAuthentication getPasswordAuthentication() {
                        return new java.net.PasswordAuthentication(proxyConfig.getUsername(), proxyConfig.getPassword().toCharArray());
                    }
                });
            } else {
                java.net.Authenticator.setDefault(null);
            }
            
            conn = (HttpURLConnection) url.openConnection(proxy);
        } else {
            java.net.Authenticator.setDefault(null);
            conn = (HttpURLConnection) url.openConnection();
        }
        
        try {
            conn.setRequestMethod(method);
        } catch (java.net.ProtocolException e) {
            // 通过反射绕过 HttpURLConnection 对受限方法（如 MOVE）的检查
            try {
                java.lang.reflect.Field methodField = HttpURLConnection.class.getDeclaredField("method");
                methodField.setAccessible(true);
                methodField.set(conn, method);
            } catch (Exception ex) {
                // 如果是 HttpsURLConnectionImpl，需要获取其底层的 delegate
                java.lang.reflect.Field delegateField = conn.getClass().getDeclaredField("delegate");
                delegateField.setAccessible(true);
                Object delegate = delegateField.get(conn);
                java.lang.reflect.Field methodField = HttpURLConnection.class.getDeclaredField("method");
                methodField.setAccessible(true);
                methodField.set(delegate, method);
            }
        }
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setInstanceFollowRedirects(false); // 我们自己处理重定向
        
        if (encodedCredentials != null && !encodedCredentials.isEmpty()) {
            conn.setRequestProperty("Authorization", "Basic " + encodedCredentials);
        }
        
        if (customHeaders != null) {
            for (Map.Entry<String, String> header : customHeaders.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        if (requestHeaders != null) {
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        
        if (body != null && !body.isEmpty()) {
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", String.valueOf(body.getBytes("UTF-8").length));
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes("UTF-8"));
                os.flush();
            }
        }
        
        int responseCode = conn.getResponseCode();
        
        // 处理 301/302 重定向
        if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
            || responseCode == HttpURLConnection.HTTP_MOVED_PERM
            || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
            String newUrl = conn.getHeaderField("Location");
            if (newUrl != null) {
                // 如果是相对路径，拼接到完整的 URL
                if (!newUrl.startsWith("http")) {
                    URL currentUrl = new URL(urlStr);
                    newUrl = new URL(currentUrl, newUrl).toString();
                }
                // 跟进重定向，保持 method（GET等）和 header 不变，body清空避免二次提交
                return sendRequestWithRedirect(newUrl, "GET", null, connectTimeout, readTimeout, encodedCredentials, requestHeaders, redirectCount + 1);
            }
        }

        String responseBody = "";
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                responseCode == 200 || responseCode == 204 ? conn.getInputStream() : conn.getErrorStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            reader.close();
            responseBody = response.toString();
        } catch (Exception e) {
            responseBody = "";
        }
        
        return new HttpResponse(responseCode, responseBody);
    }
    
    public static class HttpResponse {
        private int responseCode;
        private String responseBody;
        
        public HttpResponse(int responseCode, String responseBody) {
            this.responseCode = responseCode;
            this.responseBody = responseBody;
        }
        
        public int getResponseCode() {
            return responseCode;
        }
        
        public String getResponseBody() {
            return responseBody;
        }
    }
    
    public static class ProxyConfig {
        private boolean enabled;
        private String type;
        private String host;
        private int port;
        private String username;
        private String password;
        
        public ProxyConfig(boolean enabled, String type, String host, int port, String username, String password) {
            this.enabled = enabled;
            this.type = type;
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
        }
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public String getType() {
            return type;
        }
        
        public String getHost() {
            return host;
        }
        
        public int getPort() {
            return port;
        }
        
        public String getUsername() {
            return username;
        }
        
        public String getPassword() {
            return password;
        }
    }
}
