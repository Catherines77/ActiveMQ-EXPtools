package cc.kiiy.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CVE20163088Panel extends JPanel {

    private JTabbedPane tabbedPane;

    // Cron 写入组件
    private JTextField ipField;
    private JTextField portField;
    private JButton executeCronButton;
    private JTextArea cronResultArea;

    // Webshell 写入组件
    private JPanel webshellPanel;
    private JTextArea webshellContentArea;
    private JButton executeWebshellButton;
    private JTextArea webshellResultArea;

    public CVE20163088Panel() {
        super(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

        // 1. Cron 写入子面板
        JPanel cronPanel = createCronPanel();
        tabbedPane.addTab("Cron 写入", cronPanel);

        // 2. Webshell 写入子面板
        webshellPanel = createWebshellPanel();
        tabbedPane.addTab("Webshell 写入", webshellPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createCronPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
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
        executeCronButton = new JButton("执行利用 (写入 crontab 反弹 Shell)");
        executeCronButton.setFont(font);
        buttonPanel.add(executeCronButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        configPanel.add(buttonPanel, gbc);

        cronResultArea = new JTextArea(15, 50);
        cronResultArea.setFont(font);
        cronResultArea.setEditable(false);
        cronResultArea.setLineWrap(true);
        cronResultArea.setWrapStyleWord(true);
        cronResultArea.setText("请在攻击机上开启监听：nc -lvp 4444\n\n该利用方式将会：\n1. 通过 PUT 方法上传包含反弹 Shell Perl 脚本的 cron 任务文件。\n2. 通过 MOVE 方法将其移动到目标主机的 /etc/cron.d/root 目录下。\n\n注意：此方法仅在 ActiveMQ 运行权限为 root 且操作系统支持该 cron 目录时生效。\n\n[执行结果将显示在此]");

        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(new TitledBorder("执行输出"));
        resultPanel.add(new JScrollPane(cronResultArea), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, configPanel, resultPanel);
        splitPane.setDividerLocation(150); // 默认分隔位置
        splitPane.setResizeWeight(0.3); // 当窗口调整大小时，上半部分和下半部分的分配比例
        splitPane.setContinuousLayout(true); // 拖动时连续重绘
        
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createWebshellPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 14);

        JPanel configPanel = new JPanel(new BorderLayout(5, 5));
        configPanel.setBorder(new TitledBorder("CVE-2016-3088 (Webshell 写入) 漏洞利用"));

        webshellContentArea = new JTextArea(10, 50);
        webshellContentArea.setFont(font);
        webshellContentArea.setText("<% out.println(\"Webshell written successfully!\"); %>");
        
        JPanel inputWrapper = new JPanel(new BorderLayout());
        inputWrapper.add(new JLabel("Webshell 内容:"), BorderLayout.NORTH);
        inputWrapper.add(new JScrollPane(webshellContentArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        executeWebshellButton = new JButton("执行利用 (写入 Webshell)");
        executeWebshellButton.setFont(font);
        buttonPanel.add(executeWebshellButton);

        configPanel.add(inputWrapper, BorderLayout.CENTER);
        configPanel.add(buttonPanel, BorderLayout.SOUTH);

        webshellResultArea = new JTextArea(10, 50);
        webshellResultArea.setFont(font);
        webshellResultArea.setEditable(false);
        webshellResultArea.setLineWrap(true);
        webshellResultArea.setWrapStyleWord(true);
        webshellResultArea.setText("该利用方式将会：\n1. 向 /admin/test/systemProperties.jsp 发起请求以获取 activemq.home 目录路径。\n2. 通过 PUT 方法将用户输入的 Webshell 内容上传至 /fileserver/ 目录下。\n3. 通过 MOVE 方法将其移动至 {activemq.home}/webapps/api/ 目录下。\n4. GET 请求该 Webshell 验证写入是否成功。\n\n[执行结果将显示在此]");

        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(new TitledBorder("执行输出"));
        resultPanel.add(new JScrollPane(webshellResultArea), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, configPanel, resultPanel);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);
        
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    public String getIp() {
        return ipField.getText().trim();
    }

    public String getPort() {
        return portField.getText().trim();
    }

    public void setCronResult(String result) {
        cronResultArea.setText(result);
    }

    public void setWebshellResult(String result) {
        webshellResultArea.setText(result);
    }

    public String getWebshellContent() {
        return webshellContentArea.getText();
    }

    public void addExecuteCronListener(ActionListener listener) {
        executeCronButton.addActionListener(listener);
    }

    public void addExecuteWebshellListener(ActionListener listener) {
        executeWebshellButton.addActionListener(listener);
    }
}