package com.anz.code.stack.model

import com.anz.code.stack.application.model.Block
import spock.lang.Specification
import spock.lang.Unroll

class BlockTest extends Specification{

    def "Test Block toString"(){
        given:
        def b0 = Block.builder().length(5).width(8).high(10).stackHigh(10).build()

        when:
        def result = b0.toString()

        then:
        assert result == "Block(length=5, width=8, high=10, stackHigh=10, isStacked=false, bottomBlock=null)"

    }
    def "Test init Block"(){
        given:
        def b0 = Block.builder().length(5).width(8).high(10).stackHigh(10).build()

        when:
        b0.setBottomBlock(Block.builder().length(10).width(20).high(30).stackHigh(30).build())
        b0.setHigh(20)
        b0.setLength(7)
        b0.setWidth(10)
        b0.setStackHigh(40)
        b0.setStacked(true)

        then:
        assert b0 == Block.builder().length(7).width(10).high(20).stackHigh(40).isStacked(true).build()
        assert b0.bottomBlock == Block.builder().length(10).width(20).high(30).stackHigh(30).build()
    }

    @Unroll
    def "Test compare #blocks and check #expected"(){
        given:
        def b0 = Block.builder().length(5).width(8).high(10).stackHigh(10).build()

        when:
        def result = b0 <=> blocks
        then:
        assert result == expected
        where:

        blocks                                                                    | expected
        Block.builder().length(5).width(8).high(10).stackHigh(10).build()         | 0
        Block.builder().length(5).width(8).high(10).stackHigh(20).build()         | 0
        Block.builder().length(5).width(9).high(10).stackHigh(20).build()         | -1
        Block.builder().length(6).width(8).high(10).stackHigh(20).build()         | -1
        Block.builder().length(5).width(8).high(20).stackHigh(20).build()         | -1
        Block.builder().length(5).width(8).high(9).stackHigh(20).build()          | 1
        Block.builder().length(5).width(7).high(10).stackHigh(20).build()         | 1
        Block.builder().length(4).width(8).high(10).stackHigh(20).build()         | 1

    }

    @Unroll
    def "Test stack #blocks and return #expectedBlock"(){
        given:
        def b0 = Block.builder().length(5).width(8).high(10).stackHigh(10).build()


        when:
        def result = b0.stack(blocks)

        then:
        assert result == expectedBlock
        assert result.bottomBlock == expectedBlock.bottomBlock

        where:
        blocks                                                                           | expectedBlock
        Block.builder().length(15).width(18).high(20).stackHigh(20).build()              | Block.builder().length(5).width(8).high(10).stackHigh(30).isStacked(true).bottomBlock(Block.builder().length(15).width(18).high(20).stackHigh(20).build()).build()
        Block.builder().length(5).width(6).high(8).stackHigh(8).build()                  | Block.builder().length(5).width(6).high(8).stackHigh(18).isStacked(true).bottomBlock(Block.builder().length(5).width(8).high(10).stackHigh(10).build()).build()
        Block.builder().length(2).width(18).high(20).stackHigh(20).build()               | Block.builder().length(2).width(18).high(20).stackHigh(20).isStacked(false).build()
        Block.builder().length(15).width(18).high(20).stackHigh(6).build()               | Block.builder().length(5).width(8).high(10).stackHigh(10).isStacked(false).bottomBlock(blocks).build()
    }
}
