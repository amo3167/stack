package com.anz.code.stack.datasource

import com.anz.code.stack.application.datasource.BlockDataSource
import com.anz.code.stack.application.model.Block
import com.anz.code.stack.application.model.Blocks
import spock.lang.Specification

class BlockDataSourceTest extends Specification{

    private BlockDataSource blockDataSource = new BlockDataSource()

    def "Clean up stacks"(){
        given:

        when:
        blockDataSource.cleanUp()

        then:
        assert blockDataSource.isEmpty()
    }
    def "Stack empty blocks return empty result"(){
        given:

        when:
        def result = blockDataSource.stack([])

        then:
        assert result.stream().map(it -> it.stackHigh).reduce(Integer::max).isEmpty()

    }


    def "Stack two blocks return expected max high"(){
        given:
        def blocks = [Blocks.builder().min(5).middle(8).max(10).stackHigh(10).build(),
                              Blocks.builder().min(15).middle(20).max(25).stackHigh(25).build()]

        when:
        def result = blockDataSource.stack(blocks)

        then:
        assert result.stream().map(it -> it.stackHigh).reduce(Integer::max).get() == 35

    }

    def "Stack three blocks return expected max high"(){
        given:
        def blocks = [
                               Blocks.builder().min(20).middle(30).max(50).build(),
                               Blocks.builder().min(12).middle(35).max(45).build(),
                               Blocks.builder().min(37).middle(53).max(95).build()]

        when:
        def result = blockDataSource.stack(blocks)

        then:
        assert result.stream().map(it -> it.stackHigh).reduce(Integer::max).get() == 145

    }

    def "Stack two not stackable blocks return expected max high"(){
        given:
        def blocks = [Blocks.builder().min(25).middle(38).max(45).build(),
                              Blocks.builder().min(3).middle(35).max(76).build()]

        when:
        def result = blockDataSource.stack(blocks)

        then:
        assert result.stream().map(it -> it.stackHigh).reduce(Integer::max).get() == 76
        def results =  result.stream().filter(it -> it.stackHigh == 76 ).toList()
        assert results.size() == 2

    }

    def "Stack the blocks with one same size group"(){
        given:
        def b = Blocks.builder().min(7).middle(11).max(17).build()
        def blocks = [b,b.toBuilder().build(),b.toBuilder().build(),b.toBuilder().build()]

        when:
        def result = blockDataSource.stack(blocks)

        then:
        assert result.stream().map(it -> it.stackHigh).reduce(Integer::max).get() == b.max*4

    }

    def "Stack the blocks with two same size groups"(){
        given:
        def b = Blocks.builder().min(7).middle(11).max(17).build()
        def b1 = Blocks.builder().min(17).middle(21).max(27).build()
        def blocks = [b,
                              b.toBuilder().build(),
                              b.toBuilder().build(),
                              b1,
                              b.toBuilder().build(),
                              b1.toBuilder().build()]

        when:
        def result = blockDataSource.stack(blocks)

        then:
        assert result.stream().map(it -> it.stackHigh).reduce(Integer::max).get() == b.max*4+b1.max*2

    }

    def "Quick stack with the best max high option"(){
        given:
        def blocks = [
                Blocks.builder().min(20).middle(45).max(50).build(),
                Blocks.builder().min(12).middle(23).max(45).build(),
                Blocks.builder().min(37).middle(53).max(95).build()]

        when:
        def result = blockDataSource.stack(blocks)

        then:
        assert result.size() == 1
        assert result.get(0).isStacked()
        assert result.get(0) == Block.builder().high(45).width(23).length(12).build()
        assert result.get(0).stackHigh == 190
        assert result.get(0).bottomBlock == Block.builder().high(50).width(45).length(20).build()
        assert result.get(0).bottomBlock.bottomBlock == Block.builder().high(95).width(53).length(37).build()

    }
}
