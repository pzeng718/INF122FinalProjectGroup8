package com.tmge.app.player;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author vladshaternik on 2/21/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultPlayer {

    @Builder.Default
    @SerializedName("id")
    private final String id = UUID.randomUUID().toString();

    @Builder.Default
    @SerializedName("username")
    private final String username = "";

    @Override
    public String toString() {
        return getUsername();
    }
}
