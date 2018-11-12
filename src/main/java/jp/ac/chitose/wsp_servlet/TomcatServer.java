package jp.ac.chitose.wsp_servlet;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class TomcatServer {
  public static void main(String[] args) throws LifecycleException, IOException {
    Tomcat tomcat = new Tomcat();

    Path tempPath = Files.createTempDirectory("tomcat-base-dir");
    tomcat.setBaseDir(tempPath.toString());

    String webPort = System.getenv("PORT");
    if (Objects.isNull(webPort) || webPort.isEmpty()) {
      webPort = "8080";
    }
    tomcat.setPort(Integer.valueOf(webPort));
    tomcat.getConnector();

    String webappDir = new File("src/main/webapp/").getAbsolutePath();
    StandardContext ctx = (StandardContext) tomcat.addWebapp("/wsp_servlet", webappDir);
    ctx.setParentClassLoader(TomcatServer.class.getClassLoader());
    WebResourceRoot root = new StandardRoot(ctx);
    String classDir = new File("target/classes").getAbsolutePath();
    root.addPreResources(new DirResourceSet(root, "/WEB-INF/classes", classDir, "/"));
    ctx.setResources(root);

    tomcat.start();
    tomcat.getServer().await();
  }
}
