package knr.yes24slc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackendApplication {

    private final static Logger log = LoggerFactory.getLogger(BackendApplication.class);

    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(BackendApplication.class);

        app.setBanner((env, sourceClass, out) -> {

            String profile = env.getProperty("spring.profiles.active", "default");
            String port = env.getProperty("server.port", "8080");
            String dbUrl = env.getProperty("spring.datasource.url", "N/A");
            String dbUser = env.getProperty("spring.datasource.username", "N/A");
            String appName = env.getProperty("spring.application.name", "OSR-SYSTEM");

            out.println("\n==================================================");
            out.println("🚀 " + appName + " STARTING...");
            out.println("--------------------------------------------------");
            out.println("📌 PROFILE   : " + profile);
            out.println("🌐 PORT      : " + port);
            out.println("🗄️ DB URL    : " + dbUrl);
            out.println("👤 DB USER   : " + dbUser);
            out.println("☕ JAVA      : " + System.getProperty("java.version"));
            out.println("🖥️ OS        : " + System.getProperty("os.name"));
            out.println("==================================================\n");
        });

        app.run(args);
        //SpringApplication.run(BackendApplication.class, args);
    }


}



