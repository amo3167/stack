package com.anz.code.stack.service

import com.anz.code.stack.application.datasource.BlockDataSource
import com.anz.code.stack.application.model.Block
import com.anz.code.stack.application.model.BlockDimensions
import com.anz.code.stack.application.service.BlockService
import com.anz.code.stack.application.service.BlockServiceImpl
import spock.lang.Specification

class BlockServiceTest extends Specification{

    private BlockDataSource blockDataSource
    private BlockService blockService

    def setup(){
        blockDataSource = Mock(BlockDataSource)
        blockService = new BlockServiceImpl(blockDataSource)

    }

    def "Test stack blockDimensions"(){
        given:
        def blockDimensionsList = [
                BlockDimensions.builder().dimensions(Arrays.asList(45, 10, 50)).build(),
                BlockDimensions.builder().dimensions(Arrays.asList(12,45,23)).build(),
                BlockDimensions.builder().dimensions(Arrays.asList(95,53,37)).build()
        ]

        def expectedBlocks = [
                Block.builder().length(10).width(45).high(50).stackHigh(145).build(),
                Block.builder().length(45).width(10).high(50).stackHigh(145).build(),
                Block.builder().length(10).width(45).high(50).stackHigh(120).build()
        ]
        1 * blockDataSource.stack(*_) >> expectedBlocks

        when:
        def result = blockService.stack(blockDimensionsList)

        then:
        assert result.size() == 2
        assert result.get(0) == expectedBlocks.get(0)
        assert result.get(1) == expectedBlocks.get(1)

    }

}
