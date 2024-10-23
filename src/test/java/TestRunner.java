import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("stepsdefinitions")
@ConfigurationParameters(value = {
        @ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "src/test/resources/scenarios"),
        @ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "stepsdefinitions"),
        @ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME, value = "false"),
        @ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report/cucumber.html")
})
public class TestRunner {
}
