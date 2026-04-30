package cc.kiiy.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class JolokiaLoadXmlPanel extends JPanel {

    private JTabbedPane tabbedPane;

    // CVE-2026-34197 Components
    private JButton injectCVE202634197Button;
    private JTextArea cve202634197ResultArea;

    // CVE-2026-40466 Components
    private JButton getBrokerButton;
    private JButton discoveryLoadXmlButton;
    private JTextArea cve202640466ResultArea;

    public JolokiaLoadXmlPanel() {
        setLayout(new BorderLayout(10, 10));

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("CVE-2026-34197", createCVE202634197Panel());
        tabbedPane.addTab("CVE-2026-40466", createCVE202640466Panel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createCVE202634197Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(new TitledBorder("内存马注入"));
        
        JLabel hintLabel = new JLabel("<html><b>提示：</b>请先在“BeanXML设置”面板中生成 XML 载荷，<br>并将其部署到你的恶意 HTTP 服务器的根路径下。</html>");
        hintLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
        
        injectCVE202634197Button = new JButton("远程加载xml");
        injectCVE202634197Button.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));

        topPanel.add(hintLabel);
        topPanel.add(injectCVE202634197Button);

        cve202634197ResultArea = new JTextArea();
        cve202634197ResultArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        cve202634197ResultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(cve202634197ResultArea);
        resultScroll.setBorder(new TitledBorder("注入执行输出"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(resultScroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCVE202640466Panel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(new TitledBorder("漏洞利用"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        
        JLabel hintLabel = new JLabel("<html><b>利用步骤：</b><br>" +
                "1. 填写上方“恶意XML服务器地址”为你的 HTTP 服务器地址。<br>" +
                "2. 点击“获取Broker”获取根节点并移除可能存在的 NC 连接。工具会在输出中生成你需要的文件内容。<br>" +
                "3. 在你的恶意 HTTP 服务器下创建 <b>/discovery-registry/default</b> 文件，填入输出中生成的内容。<br>" +
                "4. 点击“discovery加载xml”向 /api/jolokia 发送执行请求。</html>");
        hintLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
        topPanel.add(hintLabel, gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        
        getBrokerButton = new JButton("获取Broker");
        getBrokerButton.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        topPanel.add(getBrokerButton, gbc);
        
        gbc.gridx = 1;
        discoveryLoadXmlButton = new JButton("discovery加载xml");
        discoveryLoadXmlButton.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        topPanel.add(discoveryLoadXmlButton, gbc);

        cve202640466ResultArea = new JTextArea();
        cve202640466ResultArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        cve202640466ResultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(cve202640466ResultArea);
        resultScroll.setBorder(new TitledBorder("执行输出"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(resultScroll, BorderLayout.CENTER);

        return panel;
    }

    public void addInjectCVE202634197Listener(ActionListener listener) {
        injectCVE202634197Button.addActionListener(listener);
    }

    public void addGetBrokerCVE202640466Listener(ActionListener listener) {
        getBrokerButton.addActionListener(listener);
    }

    public void addDiscoveryLoadXmlCVE202640466Listener(ActionListener listener) {
        discoveryLoadXmlButton.addActionListener(listener);
    }

    public void setCVE202634197ResultText(String text) {
        cve202634197ResultArea.setText(text);
    }
    
    public void setCVE202640466ResultText(String text) {
        cve202640466ResultArea.setText(text);
    }
}