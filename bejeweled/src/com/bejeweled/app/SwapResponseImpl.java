package com.bejeweled.app;

import com.tmge.app.collection.SwapResponse;
import lombok.Data;

@Data
public class SwapResponseImpl implements SwapResponse {
    private final int tilesRemoved;
}
