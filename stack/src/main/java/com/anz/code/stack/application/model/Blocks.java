package com.anz.code.stack.application.model;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Blocks {
    private int min;
    private int max;
    private int middle;
    private int stackHigh;

    public Block getMaxHighBlock(){
        return Block.builder().length(min).width(middle).high(max).stackHigh(max).bottomBlock(null).build();

    }
    public List<Block> generateBlocks(){

       return Arrays.asList(
               Block.builder().length(min).width(middle).high(max).stackHigh(max).bottomBlock(null).build(),
               Block.builder().length(min).width(max).high(middle).stackHigh(middle).bottomBlock(null).build(),
               Block.builder().length(middle).width(min).high(max).stackHigh(max).bottomBlock(null).build(),
               Block.builder().length(middle).width(max).high(min).stackHigh(min).bottomBlock(null).build(),
               Block.builder().length(max).width(min).high(middle).stackHigh(middle).bottomBlock(null).build(),
               Block.builder().length(max).width(middle).high(min).stackHigh(min).bottomBlock(null).build()
       );

    }
}
