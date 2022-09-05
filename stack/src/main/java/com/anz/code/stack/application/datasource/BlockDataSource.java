package com.anz.code.stack.application.datasource;

import com.anz.code.stack.application.model.Block;
import com.anz.code.stack.application.model.Blocks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BlockDataSource{

    private final Deque<Blocks> blocksStack = new LinkedList<>();

    public void cleanUp(){

        blocksStack.clear();
    }

    public boolean isEmpty(){
        return blocksStack.isEmpty();

    }
    private Block quickStack(List<Blocks> blocks){
        List<Block> b = blocks.stream().map(Blocks::getMaxHighBlock).sorted(Collections.reverseOrder()).toList();
        Block internal = b.get(0);
        for(int i = 1; i< b.size(); i++){
            internal = internal.stack(b.get(i));
            if(!internal.isStacked())
                break;
        }

        return internal;
    }

    public List<Block> stack(List<Blocks> blocks){
        if(blocks.isEmpty())
            return new ArrayList<>();

        Block quickBlock = quickStack(blocks);
        if(quickBlock.isStacked())
            return List.of(quickBlock);
        else{
            return stackByGroups(blocks);
        }
    }

    private List<Block> stackByGroups(List<Blocks> blocks){

        Map<Object, List<Blocks>> map = blocks.stream().collect(Collectors.groupingBy(compositeKey,Collectors.toList()));

        blocksStack.addAll(map.values().stream()
                .map(b -> Blocks.builder()
                        .max(b.get(0).getMax())
                        .middle(b.get(0).getMiddle())
                        .min(b.get(0).getMin())
                        .stackHigh(b.get(0).getMax() * b.size())
                        .build()).toList());
        return internalStack();
    }

    Function<Blocks, List<Object>> compositeKey = blocks ->
            Arrays.asList(blocks.getMax(), blocks.getMiddle(),blocks.getMin());

    private List<Block> internalStack(){

        if(blocksStack.size() == 1) {
            return blocksStack.remove().generateBlocks();
        }
        else {
            Blocks b = blocksStack.remove();

            return internalStack().stream()
                    .flatMap(it -> b.generateBlocks().stream().map(i-> i.stack(it))).distinct().toList();
        }
    }




}
