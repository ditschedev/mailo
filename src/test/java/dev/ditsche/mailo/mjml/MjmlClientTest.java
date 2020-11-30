package dev.ditsche.mailo.mjml;

import dev.ditsche.mailo.config.MailoConfig;
import dev.ditsche.mailo.exception.MjmlClientConfigExcpetion;
import dev.ditsche.mailo.exception.MjmlRenderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MjmlClientTest {

    @Test
    public void shouldCreateMjmlClient() {
        MjmlClient client = new MjmlClient();
        assertThat(client).isNotNull();
    }

    @Test
    public void shouldThrowExceptionWhenRenderingEmptyMjml() {
        MjmlClient client = new MjmlClient();
        String mjml = "";
        assertThatExceptionOfType(MjmlRenderException.class).isThrownBy(() -> {
            client.render(mjml);
        });
        assertThatExceptionOfType(MjmlRenderException.class).isThrownBy(() -> {
            client.render(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidAppSecretIsProvided() {
        MailoConfig config = MailoConfig.get();
        config.setMjmlAppId("null");
        MjmlClient client = new MjmlClient();
        String mjml = "<mjml>\n" +
                "  <mj-body>\n" +
                "    <mj-section>\n" +
                "      <mj-column>\n" +
                "\n" +
                "        <mj-image width=\"100px\" src=\"/assets/img/logo-small.png\"></mj-image>\n" +
                "\n" +
                "        <mj-divider border-color=\"#F45E43\"></mj-divider>\n" +
                "\n" +
                "        <mj-text font-size=\"20px\" color=\"#F45E43\" font-family=\"helvetica\">Hello World</mj-text>\n" +
                "\n" +
                "      </mj-column>\n" +
                "    </mj-section>\n" +
                "  </mj-body>\n" +
                "</mjml>";
        assertThatExceptionOfType(MjmlRenderException.class).isThrownBy(() -> {
            client.render(mjml);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidAppIdIsProvided() {
        MailoConfig config = MailoConfig.get();
        config.setMjmlAppSecret("null");
        MjmlClient client = new MjmlClient();
        String mjml = "<mjml>\n" +
                "  <mj-body>\n" +
                "    <mj-section>\n" +
                "      <mj-column>\n" +
                "\n" +
                "        <mj-image width=\"100px\" src=\"/assets/img/logo-small.png\"></mj-image>\n" +
                "\n" +
                "        <mj-divider border-color=\"#F45E43\"></mj-divider>\n" +
                "\n" +
                "        <mj-text font-size=\"20px\" color=\"#F45E43\" font-family=\"helvetica\">Hello World</mj-text>\n" +
                "\n" +
                "      </mj-column>\n" +
                "    </mj-section>\n" +
                "  </mj-body>\n" +
                "</mjml>";
        assertThatExceptionOfType(MjmlRenderException.class).isThrownBy(() -> {
            client.render(mjml);
        });
    }

    @Test
    public void shouldRenderValidMJML() {
        MjmlClient client = new MjmlClient();
        String mjml = "<mjml>\n" +
                "  <mj-body>\n" +
                "    <mj-section>\n" +
                "      <mj-column>\n" +
                "\n" +
                "        <mj-image width=\"100px\" src=\"/assets/img/logo-small.png\"></mj-image>\n" +
                "\n" +
                "        <mj-divider border-color=\"#F45E43\"></mj-divider>\n" +
                "\n" +
                "        <mj-text font-size=\"20px\" color=\"#F45E43\" font-family=\"helvetica\">Hello World</mj-text>\n" +
                "\n" +
                "      </mj-column>\n" +
                "    </mj-section>\n" +
                "  </mj-body>\n" +
                "</mjml>";

        String rendered = client.render(mjml);
        assertThat(rendered).contains("Hello World");
    }

    @Test
    public void shouldThrowExceptionWhenNoMjmlAppIdIsSet() {
        MailoConfig.get().setMjmlAppId("");
        assertThatExceptionOfType(MjmlClientConfigExcpetion.class).isThrownBy(MjmlClient::new);
    }

    @Test
    public void shouldThrowExceptionWhenNoMjmlAppSecretIsSet() {
        MailoConfig.get().setMjmlAppSecret("");
        assertThatExceptionOfType(MjmlClientConfigExcpetion.class).isThrownBy(MjmlClient::new);
    }

    @AfterEach
    public void resetConfig() {
        MailoConfig.get().reset();
    }

}