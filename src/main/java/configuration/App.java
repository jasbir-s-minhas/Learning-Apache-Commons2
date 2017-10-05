package configuration;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;

public class App {

    public static void main(String[] args) throws ConfigurationException {
        example1();
        example2();
        example3();
    }

    private static void example1() throws ConfigurationException {
        System.out.println("== Example 1 ==");
        XMLConfiguration config = getConfig("simple-const.xml", false);
        System.out.println(config.getString("database.url"));
        System.out.println(config.getString("database.port"));
    }

    private static void example2() throws ConfigurationException {
        System.out.println("== Example 2 ==");
        XMLConfiguration config = getConfig("const.xml", false);
        System.out.println(config.getString("databases.database(0).url"));
        System.out.println(config.getString("databases.database(1).url"));
    }

    private static void example3() throws ConfigurationException {
        System.out.println("== Example 3 ==");
        XMLConfiguration config = getConfig("const.xml", false);
        config.setExpressionEngine(new XPathExpressionEngine());
        System.out.println(config.getString("databases/database[name = 'dev']/url"));
        System.out.println(config.getString("databases/database[name = 'production']/url"));
    }

    private static XMLConfiguration getConfig(String xmlFileName, boolean validate) {
        XMLConfiguration config = null;
        Parameters params = new Parameters();
        try {
            FileBasedConfigurationBuilder<XMLConfiguration> builder =
                    new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                            .configure(params.xml()
                                    .setFileName(xmlFileName)
                                    .setSchemaValidation(validate));

            config = builder.getConfiguration();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return config;

    }


}
