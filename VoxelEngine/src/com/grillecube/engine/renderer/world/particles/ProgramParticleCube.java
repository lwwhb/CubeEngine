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

package com.grillecube.engine.renderer.world.particles;

import org.lwjgl.opengl.GL20;

import com.grillecube.engine.opengl.GLH;
import com.grillecube.engine.opengl.object.GLProgram;
import com.grillecube.engine.renderer.camera.CameraProjectiveWorld;
import com.grillecube.engine.resources.R;

public class ProgramParticleCube extends GLProgram {

	protected int _mvp_matrix;

	public ProgramParticleCube() {
		super();
		this.addShader(GLH.glhLoadShader(R.getResPath("shaders/cube.fs"), GL20.GL_FRAGMENT_SHADER));
		this.addShader(GLH.glhLoadShader(R.getResPath("shaders/cube.vs"), GL20.GL_VERTEX_SHADER));
		this.link();
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "transf_matrix"); // matrix take 1, 2, 3, 4
		super.bindAttribute(5, "color");
		super.bindAttribute(6, "health");
	}

	@Override
	public void linkUniforms() {
		this._mvp_matrix = super.getUniform("mvp_matrix");
	}

	/** load global uniforms */
	public void loadGlobalUniforms(CameraProjectiveWorld camera) {
		super.loadUniformMatrix(this._mvp_matrix, camera.getMVPMatrix());
	}
}