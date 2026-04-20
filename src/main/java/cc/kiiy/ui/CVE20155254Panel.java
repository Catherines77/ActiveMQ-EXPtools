package cc.kiiy.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CVE20155254Panel extends JPanel {

    private JTextField queueField;
    private JTextArea base64PayloadArea;
    private JButton executeButton;
    private JTextArea resultArea;

    public CVE20155254Panel() {
        super(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        initComponents();
    }

    private void initComponents() {
        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBorder(new TitledBorder("CVE-2015-5254 (ActiveMQ 反序列化) 漏洞利用"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 14);

        JLabel queueLabel = new JLabel("目标队列 (Queue):");
        queueLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        configPanel.add(queueLabel, gbc);

        queueField = new JTextField("event", 15);
        queueField.setFont(font);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 3;
        configPanel.add(queueField, gbc);

        JLabel payloadLabel = new JLabel("Base64 序列化数据:");
        payloadLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        configPanel.add(payloadLabel, gbc);

        base64PayloadArea = new JTextArea(5, 30);
        base64PayloadArea.setFont(font);
        base64PayloadArea.setLineWrap(true);
        base64PayloadArea.setWrapStyleWord(true);
        JScrollPane payloadScrollPane = new JScrollPane(base64PayloadArea);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        configPanel.add(payloadScrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        executeButton = new JButton("发送 Payload");
        executeButton.setFont(font);
        buttonPanel.add(executeButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        configPanel.add(buttonPanel, gbc);

        resultArea = new JTextArea(10, 50);
        resultArea.setFont(font);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("请将由 java-chains, ysoserial 等工具生成的反序列化 payload 进行 Base64 编码后，粘贴至上方文本框。\n该工具将解码 Base64 并将其封装到 ActiveMQ 的 ObjectMessage 中发送至目标 61616 端口。\n\n[执行结果将显示在此]");

        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(new TitledBorder("执行输出"));
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(configPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }

    public String getQueue() {
        return queueField.getText().trim();
    }

    public String getBase64Payload() {
        return base64PayloadArea.getText().trim();
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