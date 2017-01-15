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

package com.grillecube.engine.renderer.gui.components;

public class GuiLabel extends GuiText {
	
	public GuiLabel() {
		super();
	}
	
	public GuiLabel(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public GuiLabel(float x, float y) {
		super(x, y);
	}
}

