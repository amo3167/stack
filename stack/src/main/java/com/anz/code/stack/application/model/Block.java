package com.anz.code.stack.application.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false,exclude = {"bottomBlock"})
public class Block implements Comparable<Block>{
    private int length;
    private int width;
    private int high;
    private int stackHigh;
    private boolean isStacked;
    private Block bottomBlock;

    public Block stack(Block block){

        if(this.width>= block.width && this.length >= block.length && this.high >= block.high) {
            block.addStackHistory(this);
            return block.toBuilder().stackHigh(block.stackHigh + this.stackHigh).isStacked(true).build();
        }
        if(this.width<= block.width && this.length <= block.length && this.high <= block.high) {
            this.addStackHistory(block);
            return this.toBuilder().stackHigh(block.stackHigh + this.stackHigh).isStacked(true).build();
        }
        else if(this.high >= block.high)
            return this.toBuilder().isStacked(false).build();
        else
            return block.toBuilder().isStacked(false).build();
    }

    private void addStackHistory(Block block){
        bottomBlock = block;
    }

    @Override
    public int compareTo(Block o) {
        int result = Integer.compare(this.high,o.high);
        if(result == 0) {
            result = Integer.compare(this.width, o.width);
            if(result == 0)
                result = Integer.compare(this.length,o.length);
        }
        return result;
    }
}
