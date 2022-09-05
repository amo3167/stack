package com.anz.code.stack.model

import com.anz.code.stack.application.model.Block
import com.anz.code.stack.application.model.Blocks
import spock.lang.Specification

class BlocksTest extends Specification{

    def "Test Blocks toString"(){
        given:
        def blocks = Blocks.builder().min(5).middle(8).max(10).stackHigh(10).build()

        when:
        def result = blocks.toString()

        then:
        assert result == "Blocks(min=5, max=10, middle=8, stackHigh=10)"

    }

    def "Test init Blocks"(){
        given:
        def blocks = Blocks.builder().min(5).middle(8).max(10).stackHigh(10).build()

        when:
        blocks.setMax(20)
        blocks.setMin(9)
        blocks.setMiddle(15)
        blocks.setStackHigh(40)

        then:
        assert blocks == Blocks.builder().min(9).middle(15).max(20).stackHigh(40).build()
    }

    def "Test getMaxHighBlock"(){
        given:
        def blocks = Blocks.builder().min(5).middle(8).max(10).stackHigh(10).build()

        when:

        def result = blocks.getMaxHighBlock()

        then:
        assert result == Block.builder()
                .length(5)
                .width(8)
                .high(10)
                .stackHigh(10)
                .build()
    }

    def "Test generateBlocks"(){
        given:
        def blocks = Blocks.builder().min(5).middle(8).max(10).stackHigh(10).build()

        when:

        def result = blocks.generateBlocks()

        then:
        assert result.size() == 6
        assert result.get(0) == Block.builder().length(5).width(8).high(10).stackHigh(10).build()
        assert result.get(1) == Block.builder().length(5).width(10).high(8).stackHigh(8).build()
        assert result.get(2) == Block.builder().length(8).width(5).high(10).stackHigh(10).build()
        assert result.get(3) == Block.builder().length(8).width(10).high(5).stackHigh(5).build()
        assert result.get(4) == Block.builder().length(10).width(5).high(8).stackHigh(8).build()
        assert result.get(5) == Block.builder().length(10).width(8).high(5).stackHigh(5).build()
    }
}
