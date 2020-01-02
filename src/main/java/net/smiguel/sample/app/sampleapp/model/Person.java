package net.smiguel.sample.app.sampleapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    @Builder.Default
    private String name = "sergio";

    @Builder.Default
    private String userName = "smiguelnet";

    @Builder.Default
    private String email = "sergio@smiguel.net";
}
