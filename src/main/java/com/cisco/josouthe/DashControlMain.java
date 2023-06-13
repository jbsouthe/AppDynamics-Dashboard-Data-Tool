package com.cisco.josouthe;

import com.cisco.josouthe.output.OutputPrinter;
import com.cisco.josouthe.output.XMLOutputPrinter;
import com.cisco.josouthe.util.Utility;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.Properties;

public class DashControlMain {
    //private static final Logger logger = LogManager.getFormatterLogger(DashControlMain.class);

    public static void main( String[] args ) {

        ArgumentParser parser = ArgumentParsers.newFor("DashControl")
                .singleMetavar(true)
                .build()
                .defaultHelp(true)
                .version(String.format("Dashboard Data Tool version %s build date %s", MetaData.VERSION, MetaData.BUILDTIMESTAMP))
                .description("Export Dashboard Source Data from AppDynamics for a given metric.");
        parser.addArgument("-v", "--version").action(Arguments.version());
        parser.addArgument("-c", "--config")
                .setDefault("config.properties")
                .metavar("./config.properties")
                .help("Use this specific config file.");
        parser.addArgument("-a", "--application")
                .metavar("Application")
                .help("Metric is in this specific application");
        parser.addArgument("-b", "--baseline")
                .metavar("Baseline")
                .choices("Default", "Weekly", "Daily", "Monthly")
                .setDefault("Default")
                .help("Only manage a specific data type: {\"Default\", \"Weekly\", \"Daily\", \"Monthly\"}");
        parser.addArgument("-m", "--metric")
                .metavar("Metric")
                .help("Metric name to extract");
        parser.addArgument("-d", "--days")
                .metavar("Days")
                .setDefault(7)
                .help("Days of data to extract");
        parser.addArgument("-o", "--output")
                .metavar("Output Format")
                .choices("XML", "JSON", "CSV", "BRAIL")
                .setDefault("XML")
                .help("Output Formats: {\"XML\", \"JSON\", \"CSV\", \"BRAIL\"}");
        parser.addArgument("--debug")
                .metavar("Verbose logging level")
                .choices("WARN", "INFO", "DEBUG", "TRACE")
                .setDefault("INFO")
                .help("Print debug level logging during run: {\"WARN\", \"INFO\", \"DEBUG\", \"TRACE\"}");

        Namespace namespace = null;
        try {
            namespace = parser.parseArgs(args);
            //logger.info("parser: %s", namespace);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        builder.setStatusLevel(Level.ERROR);
        // naming the logger configuration
        builder.setConfigurationName("DefaultLogger");

        // create a console appender
        AppenderComponentBuilder appenderBuilder = builder.newAppender("Console", "CONSOLE")
                .addAttribute("target", ConsoleAppender.Target.SYSTEM_ERR);
        // add a layout like pattern, json etc
        appenderBuilder.add(builder.newLayout("PatternLayout")
                .addAttribute("pattern", "%d %p %c [%t] %m%n"));
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Utility.getLevel(namespace.getString("debug")));
        rootLogger.add(builder.newAppenderRef("Console"));

        builder.add(appenderBuilder);
        builder.add(rootLogger);
        Configurator.reconfigure(builder.build());
        Logger logger = LogManager.getFormatterLogger(DashControlMain.class);
        Properties configProperties = new Properties();
        try {
            configProperties.load(new FileInputStream(namespace.getString("config")));
        } catch (Exception e) {
            parser.printHelp();
            logger.error("Error reading config properties file " + namespace.getString("config") + " Exception: " + e, e);
            System.exit(1);
        }

        Controller controller = null;
        try {
            /*
            AnalyzeApplicationTiers analyzeApplicationTiers = new AnalyzeApplicationTiers( configProperties,
                    namespace.getString("application"), namespace.getString("tier"), namespace.getInt("nodes"),
                    namespace.getString("confidence"), namespace.getString("error"));

             */
            controller = new Controller(configProperties);
        } catch (MalformedURLException e) {
            logger.error("Error in controller-url config property '%s' Exception: %s", configProperties.getProperty("controller-url"), e.getMessage());
            System.exit(1);
        }
        String application = namespace.getString("application");
        String metricName = namespace.getString("metric");
        String baseline = namespace.getString("baseline");
        int days = namespace.getInt("days");
        OutputPrinter outputPrinter = null;
        switch (namespace.getString("output")) {
            case "XML":
            default: {
                outputPrinter = new XMLOutputPrinter();
                break;
            }
        }
        outputPrinter.print( System.out );
        //TODO: query the data along with the baseline data, and output it however the user asks
    }
}