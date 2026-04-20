package cc.kiiy.service;

import cc.kiiy.util.HttpUtil;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnvironmentService {
    
    public EnvResult detectEnvironment(String url) {
        try {
            HttpUtil.HttpResponse response = HttpUtil.sendGet(url, 5000, 5000);
            int responseCode = response.getResponseCode();
            String responseBody = response.getResponseBody();
            
            boolean hasActiveMQTitle = responseBody.contains("<title>Apache ActiveMQ</title>");
            
            StringBuilder result = new StringBuilder();
            
            if (responseCode == 200 && hasActiveMQTitle) {
                result.append("[*] 检测到 Apache ActiveMQ，环境正常\n");
            } else if (responseCode == 200 && !hasActiveMQTitle) {
                result.append("[*] 未检测到 Apache ActiveMQ 环境\n");
            } else {
                result.append("[-] 请检查目标 url 是否正确\n");
            }
            
            result.append("\n响应码：").append(responseCode).append("\n");
            result.append("URL: ").append(url).append("\n");
            
            return new EnvResult(result.toString(), url, null, null, null);
            
        } catch (Exception e) {
            return new EnvResult("[-] 请检查目标 url 是否正确\n\n错误信息：" + e.getMessage(), url, null, null, null);
        }
    }
    
    public EnvResult detectEnvironmentWithAuth(String url, String username, String password) {
        try {
            String baseUrl = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
            String adminUrl = baseUrl + "/admin/";
            
            String credentials = username + ":" + password;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes("UTF-8"));
            
            HttpUtil.HttpResponse response = HttpUtil.sendGetWithAuth(adminUrl, encodedCredentials, 5000, 5000);
            int responseCode = response.getResponseCode();
            String responseBody = response.getResponseBody();
            
            StringBuilder result = new StringBuilder();
            
            if (responseCode == 200 && responseBody.contains("Welcome to the Apache ActiveMQ Console of")) {
                result.append("[+] 认证成功！\n\n");
                
                String hostname = extractInfo(responseBody, "Name", "Version");
                String version = extractInfo(responseBody, "Version", "ID");
                String uptime = extractInfo(responseBody, "Uptime", "Store percent used");
                
                result.append("主机名：").append(hostname).append("\n");
                result.append("版本信息：").append(version).append("\n");
                result.append("已运行时间：").append(uptime).append("\n");
                
                return new EnvResult(result.toString(), url, hostname, version, uptime);
                
            } else if (responseCode == 200) {
                result.append("[-] 认证失败，请检查用户名和密码\n");
                result.append("\n响应码：").append(responseCode).append("\n");
            } else {
                result.append("[-] 连接失败，请检查目标地址\n");
                result.append("\n响应码：").append(responseCode).append("\n");
            }
            
            return new EnvResult(result.toString(), url, null, null, null);
            
        } catch (Exception e) {
            return new EnvResult("[-] 检测异常：" + e.getMessage(), url, null, null, null);
        }
    }
    
    private String extractInfo(String html, String startTag, String endTag) {
        try {
            Pattern pattern = Pattern.compile("<td>" + startTag + "</td>\\s*<td><b>(.*?)</b></td>");
            Matcher matcher = pattern.matcher(html);
            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "未知";
    }
    
    public static class EnvResult {
        private String message;
        private String baseUrl;
        private String hostname;
        private String version;
        private String uptime;
        
        public EnvResult(String message, String baseUrl, String hostname, String version, String uptime) {
            this.message = message;
            this.baseUrl = baseUrl;
            this.hostname = hostname;
            this.version = version;
            this.uptime = uptime;
        }
        
        public String getMessage() {
            return message;
        }
        
        public String getBaseUrl() {
            return baseUrl;
        }
        
        public String getHostname() {
            return hostname;
        }
        
        public String getVersion() {
            return version;
        }
        
        public String getUptime() {
            return uptime;
        }
    }
}
