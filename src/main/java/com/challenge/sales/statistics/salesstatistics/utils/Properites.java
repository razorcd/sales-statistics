package com.challenge.sales.statistics.salesstatistics.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * Class to store application properties.
 */
public class Properites {

    private static final Logger LOGGER = LoggerFactory.getLogger(Properites.class);

    /**
     * Stores application properties
     */
    private static Properties properties = new Properties();

    public static Properties getProperties() {
        return properties;
    }

    private Properites() {
    }

    /**
     * Initialize loading properties file.
     */
    public static void initialize(String filename) {
        tryLoadProperties(filename);
    }

    private static void tryLoadProperties(String filename) {

        try(InputStream input = Properites.class.getClassLoader().getResourceAsStream(filename)) {
            if (Objects.isNull(input)) throw new FileNotFoundException();
            properties.load(input);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Properties file '" + filename + "' not found.");
        } catch(IOException er) {
            throw new RuntimeException("IO error while reading properties file '" + filename + "'.");
        }

        LOGGER.info("Properties file '{}' loaded successfully.", filename);
    }
}
