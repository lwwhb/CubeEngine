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

package com.grillecube.common.world.block;

import com.grillecube.common.world.Terrain;

public class BlockAir extends Block {
	public BlockAir() {
		super((short) Blocks.AIR_ID);
	}

	@Override
	public String getName() {
		return ("air");
	}

	@Override
	public boolean isVisible() {
		return (false);
	}

	@Override
	public boolean isOpaque() {
		return (false);
	}

	@Override
	public boolean isCrossable() {
		return (true);
	}

	@Override
	public boolean hasTransparency() {
		return (true);
	}

	@Override
	public void update(Terrain terrain, int x, int y, int z) {
	}

	@Override
	public void onSet(Terrain terrain, int x, int y, int z) {
	}

	@Override
	public void onUnset(Terrain terrain, int x, int y, int z) {
	}
}
