package dev.shaundsmith.minecraft.rest;

public class Assertions extends org.assertj.core.api.Assertions {

    public static ClientResponseAssert assertThat(ClientResponse actual) {
        return new ClientResponseAssert(actual);
    }

}
