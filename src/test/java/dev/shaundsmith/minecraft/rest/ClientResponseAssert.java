package dev.shaundsmith.minecraft.rest;

import org.assertj.core.api.AbstractObjectAssert;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.json.JsonObjectBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientResponseAssert extends AbstractObjectAssert<ClientResponseAssert, ClientResponse> {

    public ClientResponseAssert(ClientResponse clientResponse) {
        super(clientResponse, ClientResponseAssert.class);
    }

    public ClientResponseAssert hasResponseCode(int responseCode) {
        isNotNull();
        assertThat(actual.getResponseCode()).isEqualTo(responseCode);
        return this;
    }

    public ClientResponseAssert hasBody(JsonObjectBuilder jsonObjectBuilder) {
        isNotNull();
        try {
            JSONAssert.assertEquals(jsonObjectBuilder.build().toString(), actual.getBody(), true);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
        return this;
    }

    public ClientResponseAssert doesNotHaveBody() {
        isNotNull();
        assertThat(actual.getBody()).isEmpty();
        return this;
    }
}
