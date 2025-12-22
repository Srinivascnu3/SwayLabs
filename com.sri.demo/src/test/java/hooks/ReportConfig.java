package hooks;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Initializes report output path to include a timestamp so reports are not overwritten.
 */
public class ReportConfig {

    private static final String DEFAULT_OUT = "target/ExtentReport/ApiReport.html";
    private static String configuredOut = null;

    @BeforeAll
    public static void setupReportPath() {
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").format(LocalDateTime.now());
        String dir = "target/ExtentReport";
        File d = new File(dir);
        if (!d.exists()) {
            d.mkdirs();
        }
        String out = dir + File.separator + "ApiReport-" + timestamp + ".html";
        System.setProperty("extent.reporter.spark.out", out);
        configuredOut = out;
        // also set the adapter property so both env and properties file are overridden
        System.out.println("[ReportConfig] Setting extent.reporter.spark.out=" + out);
    }

    @AfterAll
    public static void archiveReport() {
        // If adapter already used configuredOut, nothing to do.
        try {
            Path configured = (configuredOut != null) ? Path.of(configuredOut) : null;
            Path defaultPath = Path.of(DEFAULT_OUT);

            if (configured != null && Files.exists(configured)) {
                System.out.println("[ReportConfig] Report created at: " + configured.toString());
                return;
            }

            if (Files.exists(defaultPath)) {
                // move default to timestamped file
                String timestamp = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").format(LocalDateTime.now());
                Path target = Path.of("target/ExtentReport/ApiReport-" + timestamp + ".html");
                Files.copy(defaultPath, target, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("[ReportConfig] Archived report to: " + target.toString());
            } else {
                System.out.println("[ReportConfig] No report file found to archive.");
            }
        } catch (IOException e) {
            System.err.println("[ReportConfig] Failed to archive report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}