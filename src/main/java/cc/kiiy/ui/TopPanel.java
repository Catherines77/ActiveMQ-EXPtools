package cc.kiiy.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TopPanel extends JPanel {

    private JTextField targetAddressField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField openWirePortField;
    private JTextField httpServerField;
    private JComboBox<String> vulnComboBox;
    private JButton envDetectButton;
    private JButton vulnDetectButton;

    public TopPanel() {
        super(new BorderLayout(5, 5));
        setBorder(new EmptyBorder(10, 10, 15, 10));
        initComponents();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        gbc.weighty = 0;

        JLabel targetLabel = new JLabel("目标地址:");
        targetLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        formPanel.add(targetLabel, gbc);

        targetAddressField = new JTextField("http://192.168.239.138:8161");
        targetAddressField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        formPanel.add(targetAddressField, gbc);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 4;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        formPanel.add(userLabel, gbc);

        usernameField = new JTextField(10);
        usernameField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 5;
        gbc.weightx = 0.3;
        formPanel.add(usernameField, gbc);
        
        JLabel passLabel = new JLabel("密码:");
        passLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 6;
        gbc.weightx = 0;
        formPanel.add(passLabel, gbc);
        
        passwordField = new JTextField(10);
        passwordField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 7;
        gbc.weightx = 0.3;
        formPanel.add(passwordField, gbc);
        
        gbc.gridy = 1;
        
        JLabel httpLabel = new JLabel("恶意XML服务器地址:");
        httpLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        formPanel.add(httpLabel, gbc);

        httpServerField = new JTextField("http://192.168.239.129:8081/poc.xml");
        httpServerField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        formPanel.add(httpServerField, gbc);
        
        JLabel openWireLabel = new JLabel(" OpenWire端口:");
        openWireLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 4;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        formPanel.add(openWireLabel, gbc);
        
        openWirePortField = new JTextField("61616", 5);
        openWirePortField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 5;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        formPanel.add(openWirePortField, gbc);
        
        // 占位符，用来吃掉第二行右侧多余的空间，防止其它组件变形
        gbc.gridx = 6;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel(""), gbc);

        gbc.gridy = 2;

        envDetectButton = new JButton("环境检测");
        envDetectButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        formPanel.add(envDetectButton, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        formPanel.add(Box.createHorizontalStrut(1), gbc);

        JLabel vulnLabel = new JLabel("漏洞列表:");
        vulnLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 4;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        formPanel.add(vulnLabel, gbc);

        String[] vulns = {"CVE-2015-5254", "CVE-2016-3088", "CVE-2022-41678",
                "CVE-2023-46604", "CVE-2024-32114", "CVE-2026-34197", "All"};
        vulnComboBox = new JComboBox<>(vulns);
        vulnComboBox.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 5;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        formPanel.add(vulnComboBox, gbc);

        vulnDetectButton = new JButton("漏洞检测");
        vulnDetectButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        gbc.gridx = 6;
        gbc.weightx = 0;
        gbc.gridwidth = 2;
        formPanel.add(vulnDetectButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    public String getTargetAddress() {
        return targetAddressField.getText().trim();
    }

    public void setTargetAddress(String address) {
        targetAddressField.setText(address);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText().trim();
    }

    public int getOpenWirePort() {
        try {
            return Integer.parseInt(openWirePortField.getText().trim());
        } catch (Exception e) {
            return 61616;
        }
    }

    public String getHttpServer() {
        return httpServerField.getText().trim();
    }

    public String getSelectedVulnerability() {
        return (String) vulnComboBox.getSelectedItem();
    }

    public void addEnvDetectListener(ActionListener listener) {
        envDetectButton.addActionListener(listener);
    }

    public void addVulnDetectListener(ActionListener listener) {
        vulnDetectButton.addActionListener(listener);
    }
}
