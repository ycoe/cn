package com.duoec.cn;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.common.exceptions.BusinessException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.Properties;

/**
 * 启动jetty容器，本地开发使用。
 *
 * @author
 * @since 1.0.0
 */
public class JettyServer {
    private static final String DEFAULT_CONFIG_FILE = "common.properties";
    private static Properties properties;
    static{

        String rootPath = System.getProperty("user.dir");
        System.setProperty("log.output.path", rootPath + "/logs");
        System.setProperty("configRootPath", rootPath + "/src/main/resources");

        Resource resource = new ClassPathResource(DEFAULT_CONFIG_FILE);
        try {
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new BusinessException("加载配置文件失败!", e);
        }
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(JettyServer.class);
    private static final String DEFAULT_WEBAPP_PATH = "/src/main/webapp";

    private JettyServer(){}

    public static void main(String[] args) throws Exception {
        String contextPath = getHttpContextPath();
        int port = getServerPort();

        CommonConst.EXECUTOR_SERVICE.execute(()->{
            //执行 gulp watch
            try {
                Process process = Runtime.getRuntime().exec("gulp watch");
                readProcessOutput(process);
            } catch (IOException e) {
                LOGGER.error("执行gulp watch失败，如果未安装gulp，请执行mvn clean install", e);
            }
        });


        Server server = createServer(contextPath, port);
        try {
            server.start();
            server.join();

            Thread.sleep(2000);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(-1);
        }
    }
    /**
     * 打印进程输出
     *
     * @param process 进程
     */
    private static void readProcessOutput(final Process process) {
        // 将进程的正常输出在 System.out 中打印，进程的错误输出在 System.err 中打印
        read(process.getInputStream(), System.out);
        read(process.getErrorStream(), System.err);
    }

    // 读取输入流
    private static void read(InputStream inputStream, PrintStream out) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getServerPort() {
        return getHttpPort();
    }

    /**
     * 创建server,使用开发环境的webapp路径.
     *
     * @param contextPath
     * @param port
     * @return
     */
    private static Server createServer(String contextPath, int port) {
        Server server = new Server(port);

        String webappRootPath = JettyServer.class.getResource("/").getPath();
        webappRootPath = webappRootPath.substring(0, webappRootPath.indexOf("/target"));

        WebAppContext webapp = new WebAppContext(webappRootPath + DEFAULT_WEBAPP_PATH , contextPath);

        server.setHandler(webapp);

        return server;
    }

    /**
     * 从server.properties文件中获取contextPath
     * @return
     */
    private static String getHttpContextPath() {
        String contextPath = properties.getProperty("jetty.http.context");
        if (contextPath == null || "".equals(contextPath)) {
            return "";
        }
        if (contextPath.indexOf("/") == -1) {
            contextPath = "/" + contextPath;
        }
        if (contextPath.indexOf("ROOT") != -1) {
            contextPath = "/";
        }
        return contextPath;
    }

    /**
     * 从server.properties文件中获取http端口.
     *
     * @return
     */
    private static int getHttpPort() {
        String portStr = properties.getProperty("jetty.http.port");
        return new Integer(portStr.trim());
    }
}
