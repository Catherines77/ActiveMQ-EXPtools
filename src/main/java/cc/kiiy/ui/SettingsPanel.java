package cc.kiiy.ui;

import cc.kiiy.util.HttpUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsPanel extends JPanel {
    
    private JTextArea headerTextArea;
    private JComboBox<String> proxyTypeComboBox;
    private JRadioButton disableProxyRadio;
    private JRadioButton enableProxyRadio;
    private JButton applyProxyButton;
    private JTextField proxyHostField;
    private JTextField proxyPortField;
    private JTextField proxyUsernameField;
    private JTextField proxyPasswordField;
    private JTextArea proxyResultArea;
    
    public SettingsPanel() {
        super(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        initComponents();
        setupListeners();
    }
    
    private void initComponents() {
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setDividerLocation(200);
        mainSplitPane.setResizeWeight(0.4);
        
        mainSplitPane.setTopComponent(createHeaderPanel());
        mainSplitPane.setBottomComponent(createProxyPanel());
        
        add(mainSplitPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new TitledBorder("全局HTTP请求头配置"));
        
        headerTextArea = new JTextArea();
        headerTextArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        headerTextArea.setLineWrap(true);
        headerTextArea.setText("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36\nAccept: */*\nConnection: close\nOrigin: http://192.168.239.138:8161");
        
        panel.add(new JScrollPane(headerTextArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    public void updateOriginHeader(String targetUrl) {
        String currentText = headerTextArea.getText();
        String[] lines = currentText.split("\n");
        StringBuilder newText = new StringBuilder();
        boolean originFound = false;
        
        String baseUrl = targetUrl.endsWith("/") ? targetUrl.substring(0, targetUrl.length() - 1) : targetUrl;
        
        for (String line : lines) {
            if (line.toLowerCase().startsWith("origin:")) {
                newText.append("Origin: ").append(baseUrl).append("\n");
                originFound = true;
            } else {
                newText.append(line).append("\n");
            }
        }
        
        if (!originFound) {
            newText.append("Origin: ").append(baseUrl).append("\n");
        }
        
        headerTextArea.setText(newText.toString().trim());
    }
    
    private JPanel createProxyPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new TitledBorder("全局HTTP流量代理配置"));
        
        JPanel configPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel typeLabel = new JLabel("代理类型:");
        typeLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        configPanel.add(typeLabel, gbc);
        
        String[] proxyTypes = {"HTTP", "SOCKS"};
        proxyTypeComboBox = new JComboBox<>(proxyTypes);
        proxyTypeComboBox.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        configPanel.add(proxyTypeComboBox, gbc);
        
        disableProxyRadio = new JRadioButton("关闭代理");
        disableProxyRadio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        disableProxyRadio.setSelected(true);
        gbc.gridx = 2;
        gbc.weightx = 0;
        configPanel.add(disableProxyRadio, gbc);
        
        enableProxyRadio = new JRadioButton("开启代理");
        enableProxyRadio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 3;
        configPanel.add(enableProxyRadio, gbc);
        
        ButtonGroup proxyGroup = new ButtonGroup();
        proxyGroup.add(disableProxyRadio);
        proxyGroup.add(enableProxyRadio);
        
        applyProxyButton = new JButton("应用代理配置");
        applyProxyButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 4;
        configPanel.add(applyProxyButton, gbc);
        
        JLabel hostLabel = new JLabel("代理地址:");
        hostLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        configPanel.add(hostLabel, gbc);
        
        proxyHostField = new JTextField("127.0.0.1");
        proxyHostField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        configPanel.add(proxyHostField, gbc);
        
        JLabel portLabel = new JLabel("代理端口:");
        portLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        configPanel.add(portLabel, gbc);
        
        proxyPortField = new JTextField("8080");
        proxyPortField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 3;
        configPanel.add(proxyPortField, gbc);
        
        JLabel userLabel = new JLabel("认证用户:");
        userLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        configPanel.add(userLabel, gbc);
        
        proxyUsernameField = new JTextField();
        proxyUsernameField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        configPanel.add(proxyUsernameField, gbc);
        
        JLabel passLabel = new JLabel("认证密码:");
        passLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        configPanel.add(passLabel, gbc);
        
        proxyPasswordField = new JPasswordField();
        proxyPasswordField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 3;
        configPanel.add(proxyPasswordField, gbc);
        
        proxyResultArea = new JTextArea(3, 50);
        proxyResultArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        proxyResultArea.setEditable(false);
        proxyResultArea.setLineWrap(true);
        proxyResultArea.setText("[代理配置已禁用]");
        
        panel.add(configPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(proxyResultArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    private void setupListeners() {
        applyProxyButton.addActionListener(e -> applyProxyConfig());
        
        disableProxyRadio.addActionListener(e -> {
            proxyResultArea.setText("[代理配置已禁用]");
            HttpUtil.setProxyConfig(null);
        });
    }
    
    private void applyProxyConfig() {
        if (enableProxyRadio.isSelected()) {
            try {
                String host = proxyHostField.getText().trim();
                int port = Integer.parseInt(proxyPortField.getText().trim());
                String username = proxyUsernameField.getText().trim();
                String password = new String(((JPasswordField) proxyPasswordField).getPassword());
                
                HttpUtil.ProxyConfig proxyConfig = new HttpUtil.ProxyConfig(
                    true, 
                    (String) proxyTypeComboBox.getSelectedItem(), 
                    host, 
                    port, 
                    username, 
                    password
                );
                
                HttpUtil.setProxyConfig(proxyConfig);
                proxyResultArea.setText("[代理配置已启用]\n类型：" + proxyConfig.getType() + 
                    "\n地址：" + host + ":" + port);
                    
            } catch (NumberFormatException e) {
                proxyResultArea.setText("[错误] 代理端口必须是数字！");
            }
        } else {
            HttpUtil.setProxyConfig(null);
            proxyResultArea.setText("[代理配置已禁用]");
        }
    }
    
    public Map<String, String> getCustomHeaders() {
        Map<String, String> headers = new HashMap<>();
        String[] lines = headerTextArea.getText().split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || !line.contains(":")) {
                continue;
            }
            int colonIndex = line.indexOf(":");
            String key = line.substring(0, colonIndex).trim();
            String value = line.substring(colonIndex + 1).trim();
            headers.put(key, value);
        }
        return headers;
    }
}
