package cc.kiiy.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class TargetEnvPanel extends JPanel {

    private JTextField baseUrlField;
    private JTextField hostnameField;
    private JTextField versionField;
    private JTextPane resultPane;

    // 统一字体定义，方便后续一键修改
    private final Font globalFont = new Font("Microsoft YaHei UI", Font.PLAIN, 14);

    public TargetEnvPanel() {
        super(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        initComponents();
    }

    private void initComponents() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        // infoPanel.setBackground(new Color(43, 43, 43)); // 如果需要匹配截图中的深色模式，可以取消注释
        infoPanel.setBorder(new EmptyBorder(10, 5, 10, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Base URL ---
        JLabel baseUrlLabel = new JLabel("Base URL:");
        baseUrlLabel.setFont(globalFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        infoPanel.add(baseUrlLabel, gbc);

        baseUrlField = new JTextField();
        baseUrlField.setFont(globalFont);
        baseUrlField.setEditable(false);
        // 让 Base URL 占据主要的剩余空间
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        infoPanel.add(baseUrlField, gbc);

        // --- 主机名 ---
        JLabel hostnameLabel = new JLabel("主机名:");
        hostnameLabel.setFont(globalFont);
        gbc.gridx = 2;
        gbc.weightx = 0;
        infoPanel.add(hostnameLabel, gbc);

        hostnameField = new JTextField(15); // 通过列数控制宽度，比 setPreferredSize 更灵活
        hostnameField.setFont(globalFont);
        hostnameField.setEditable(false);
        gbc.gridx = 3;
        gbc.weightx = 0.2; // 给一点权重避免极度压缩
        infoPanel.add(hostnameField, gbc);

        // --- 版本信息 ---
        JLabel versionLabel = new JLabel("版本信息:");
        versionLabel.setFont(globalFont);
        gbc.gridx = 4;
        gbc.weightx = 0;
        infoPanel.add(versionLabel, gbc);

        versionField = new JTextField(10);
        versionField.setFont(globalFont);
        versionField.setEditable(false);
        gbc.gridx = 5;
        gbc.weightx = 0.2;
        infoPanel.add(versionField, gbc);

        // --- 下方输出框 ---
        resultPane = new JTextPane();
        resultPane.setFont(globalFont);
        resultPane.setEditable(false);

        add(infoPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultPane), BorderLayout.CENTER);
    }

    // ... 其他方法保持不变 (setEnvInfo, clearEnvInfo, appendResult 等)

    public void setEnvInfo(String baseUrl, String hostname, String version, String uptime) {
        baseUrlField.setText(baseUrl != null ? baseUrl : "");
        hostnameField.setText(hostname != null ? hostname : "");
        versionField.setText(version != null ? version : "");
    }

    public void clearEnvInfo() {
        baseUrlField.setText("");
        hostnameField.setText("");
        versionField.setText("");
    }

    public void appendResult(String text) {
        setResult(text, true);
    }

    public void setResult(String text) {
        setResult(text, false);
    }
    
    private void setResult(String text, boolean append) {
        if (!append) {
            resultPane.setText("");
        }
        if (text == null || text.isEmpty()) {
            return;
        }
        
        StyledDocument doc = resultPane.getStyledDocument();
        Style defaultStyle = resultPane.addStyle("Default", null);
        StyleConstants.setForeground(defaultStyle, resultPane.getForeground());
        
        Style successStyle = resultPane.addStyle("Success", null);
        StyleConstants.setForeground(successStyle, new Color(46, 204, 113)); // 绿色
        
        String[] lines = text.split("\n");
        try {
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (line.startsWith("[+]")) {
                    doc.insertString(doc.getLength(), line + (i < lines.length - 1 ? "\n" : ""), successStyle);
                } else {
                    doc.insertString(doc.getLength(), line + (i < lines.length - 1 ? "\n" : ""), defaultStyle);
                }
            }
            if (text.endsWith("\n") && lines.length > 0) {
                doc.insertString(doc.getLength(), "\n", defaultStyle);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return resultPane.getText();
    }

    public void clearResult() {
        resultPane.setText("");
    }
}
