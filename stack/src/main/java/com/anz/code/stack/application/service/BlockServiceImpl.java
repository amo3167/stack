package com.anz.code.stack.application.service;

import com.anz.code.stack.application.datasource.BlockDataSource;
import com.anz.code.stack.application.model.Block;
import com.anz.code.stack.application.model.BlockDimensions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BlockServiceImpl implements BlockService{

    private final BlockDataSource dataSource;

    @Override
    public List<Block> stack(List<BlockDimensions> blockDimensions) {
        List<Block> blocks = dataSource.stack(blockDimensions.stream().map(BlockDimensions::createBlocks).toList());
        int maxHigh = blocks.stream().map(Block::getStackHigh).reduce(Integer::max).orElse(0);
        return blocks.stream().filter(it -> it.getStackHigh() == maxHigh).toList();

    }
}
