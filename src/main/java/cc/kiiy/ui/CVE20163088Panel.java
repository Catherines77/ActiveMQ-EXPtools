package cc.kiiy.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CVE20163088Panel extends JPanel {

    private JTextField ipField;
    private JTextField portField;
    private JButton executeButton;
    private JTextArea resultArea;

    public CVE20163088Panel() {
        super(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        initComponents();
    }

    private void initComponents() {
        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBorder(new TitledBorder("CVE-2016-3088 (文件写入 / 反弹 Shell) 漏洞利用"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 14);

        JLabel ipLabel = new JLabel("监听 IP:");
        ipLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        configPanel.add(ipLabel, gbc);

        ipField = new JTextField("192.168.239.129", 15);
        ipField.setFont(font);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        configPanel.add(ipField, gbc);

        JLabel portLabel = new JLabel("监听端口:");
        portLabel.setFont(font);
        gbc.gridx = 2;
        gbc.weightx = 0;
        configPanel.add(portLabel, gbc);

        portField = new JTextField("4444", 10);
        portField.setFont(font);
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        configPanel.add(portField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        executeButton = new JButton("执行利用 (写入 crontab 反弹 Shell)");
        executeButton.setFont(font);
        buttonPanel.add(executeButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        configPanel.add(buttonPanel, gbc);

        resultArea = new JTextArea(15, 50);
        resultArea.setFont(font);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("请在攻击机上开启监听：nc -lvp 4444\n\n该利用方式将会：\n1. 通过 PUT 方法上传包含反弹 Shell Perl 脚本的 cron 任务文件。\n2. 通过 MOVE 方法将其移动到目标主机的 /etc/cron.d/root 目录下。\n\n注意：此方法仅在 ActiveMQ 运行权限为 root 且操作系统支持该 cron 目录时生效。\n\n[执行结果将显示在此]");

        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(new TitledBorder("执行输出"));
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(configPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }

    public String getIp() {
        return ipField.getText().trim();
    }

    public String getPort() {
        return portField.getText().trim();
    }

    public void setResult(String result) {
        resultArea.setText(result);
    }

    public void appendResult(String text) {
        resultArea.append(text + "\n");
    }

    public void addExecuteListener(ActionListener listener) {
        executeButton.addActionListener(listener);
    }
}