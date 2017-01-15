/**
**	This file is part of the project https://github.com/toss-dev/VoxelEngine
**
**	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
**
**	PEREIRA Romain
**                                       4-----7          
**                                      /|    /|
**                                     0-----3 |
**                                     | 5___|_6
**                                     |/    | /
**                                     1-----2
*/

package com.grillecube.engine.world.block;

public class BlockStone extends BlockCubeOpaque {
	public BlockStone(int blockID) {
		super(blockID, Blocks.T_STONE);
	}

	@Override
	public String getName() {
		return ("stone");
	}
}
