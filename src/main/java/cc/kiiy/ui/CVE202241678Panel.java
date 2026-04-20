package cc.kiiy.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CVE202241678Panel extends JPanel {

    private JComboBox<String> exploitMethodComboBox;
    private JTextField webshellPathField;
    private JTextField commandField;
    private JTextField customWebshellField;
    private JButton writeWebshellButton;
    private JButton executeButton;
    private JTextPane outputArea;

    public CVE202241678Panel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 利用方式和 Webshell 路径在同一行
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        inputPanel.add(new JLabel("利用方式:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.0; // 降低权重，使其不要占太大空间
        String[] methods = {"auto", "log4j2", "jfr"};
        exploitMethodComboBox = new JComboBox<>(methods);
        exploitMethodComboBox.setPreferredSize(new Dimension(80, 25)); // 限制宽度
        inputPanel.add(exploitMethodComboBox, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        inputPanel.add(new JLabel("Webshell路径:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0; // 让 Webshell 路径占满剩余空间
        webshellPathField = new JTextField();
        webshellPathField.setToolTipText("留空则自动利用漏洞并写入，写入成功后会自动填充");
        inputPanel.add(webshellPathField, gbc);

        // 自定义Webshell内容
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        inputPanel.add(new JLabel("自定义Webshell:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        customWebshellField = new JTextField();
        customWebshellField.setToolTipText("留空则使用默认Webshell（支持命令回显），若输入则会替换默认Webshell");
        inputPanel.add(customWebshellField, gbc);

        // 写入Webshell按钮
        gbc.gridx = 4;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        writeWebshellButton = new JButton("写入Webshell");
        inputPanel.add(writeWebshellButton, gbc);

        // 执行命令
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel("执行命令:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3; // 跨三列
        commandField = new JTextField("whoami");
        inputPanel.add(commandField, gbc);

        // 执行按钮
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        executeButton = new JButton("执行利用/命令");
        inputPanel.add(executeButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextPane();
        outputArea.setEditable(false);
        outputArea.setText("支持log4j2和jfr两种方式，auto默认使用log4j2。\n如果想重新写入webshell，请将webshell路径输入框置空\n执行命令默认使用工具自带的webshell\n自定义webshell连接时需要加上认证头部Authorization: Basic b64encode(uname:pass)");
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addExecuteListener(ActionListener listener) {
        executeButton.addActionListener(listener);
    }

    public void addWriteWebshellListener(ActionListener listener) {
        writeWebshellButton.addActionListener(listener);
    }

    public String getExploitMethod() {
        return (String) exploitMethodComboBox.getSelectedItem();
    }

    public String getCommand() {
        return commandField.getText().trim();
    }

    public String getWebshellPath() {
        return webshellPathField.getText().trim();
    }

    public String getCustomWebshell() {
        return customWebshellField.getText().trim();
    }

    public void setWebshellPath(String path) {
        webshellPathField.setText(path);
    }

    public void setResult(String result) {
        outputArea.setText(result);
    }
}

