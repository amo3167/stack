package com.anz.code.stack.application.service;

import com.anz.code.stack.application.model.Block;
import com.anz.code.stack.application.model.BlockDimensions;

import java.util.List;

public interface BlockService {
    List<Block> stack(List<BlockDimensions> blockDimensions);
}
