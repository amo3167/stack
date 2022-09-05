package com.anz.code.stack.model

import com.anz.code.stack.application.model.BlockDimensions
import com.anz.code.stack.application.model.Blocks
import spock.lang.Specification
import spock.lang.Unroll

class BlockDimensionsTest extends Specification{

    @Unroll
    def "Test createBlocks from #blockDimensions and return #expectedBlocks"(){
        given:

        when:
        def result = blockDimensions.createBlocks()

        then:
        assert result == expectedBlocks

        where:
        blockDimensions                                                                 |   expectedBlocks
        BlockDimensions.builder().dimensions(Arrays.asList(45, 10, 50)).build()         |   Blocks.builder().max(50).min(10).middle(45).build()
        BlockDimensions.builder().dimensions(Arrays.asList(10, 45, 50)).build()         |   Blocks.builder().max(50).min(10).middle(45).build()

    }

    def "Test createBlocks with invalid blockDimensions then throw exception"(){
        given:
        def blockDimensions =BlockDimensions.builder().dimensions(Arrays.asList(10, 45)).build()
        when:
        blockDimensions.createBlocks()

        then:
        thrown(IllegalArgumentException)



    }
}
