package com.anz.code.stack.application.model;

import lombok.Builder;

import java.util.List;

@Builder
public class BlockDimensions {
    private List<Integer> dimensions;

    public Blocks createBlocks(){
        dimensions.sort(Integer::compareTo);
        if(dimensions.size() != 3)
            throw new IllegalArgumentException(String.format("Invalid input dimensions %s",dimensions));
        return Blocks.builder()
                .min(dimensions.get(0))
                .middle(dimensions.get(1))
                .max(dimensions.get(2))
                .build();
    }
}
