package com.grillecube.client.renderer.camera;

import com.grillecube.client.opengl.window.GLFWWindow;
import com.grillecube.common.maths.Vector3f;
import com.grillecube.common.maths.Vector3i;
import com.grillecube.common.world.Terrain;
import com.grillecube.common.world.World;
import com.grillecube.common.world.block.Block;

public abstract class CameraProjectiveWorld extends CameraProjective implements RaycastingCallback {

	/** the world */
	private World world;

	/** looking block informations */
	private Vector3f lookCube;
	private Vector3f lookFace;

	/** the terrain index the camera is inside */
	private Vector3i world_index;

	public CameraProjectiveWorld(GLFWWindow window) {
		super(window);

		this.lookCube = new Vector3f();
		this.lookFace = new Vector3f();

		this.world_index = new Vector3i();
	}

	public Vector3i getWorldIndex() {
		return (this.world_index);
	}

	@Override
	public void update() {
		super.update();
		if (this.getWorld() == null) {
			return;
		}
		this.getWorld().getTerrainIndex(this.getPosition(), this.world_index);
		Raycasting.raycast(this.getPosition(), this.getViewVector(), 128.0f, this);
	}

	@Override
	public boolean onRaycastCoordinates(int x, int y, int z, Vector3i face) {
		if (this.world == null) {
			return (true);
		}
		Block block = this.world.getBlock(x, y, z);
		if (block.isVisible() && !block.bypassRaycast()) {
			this.lookCube.set(x + face.x, y + face.y, z + face.z);
			this.lookFace.set(face);
			return (true);
		}
		return (false);
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return (this.world);
	}

	public void setBlock(Block block, Vector3f pos) {

		if (this.getWorld() == null) {
			return;
		}

		Terrain terrain = this.getWorld().setBlock(block, pos.x, pos.y, pos.z);

		if (terrain == null) {
			return;
		}

		terrain.requestFaceVisibilityUpdate();
	}

	/** return the height of the last liquid seen */
	public Vector3f getLookFace() {
		return (this.lookFace);
	}

	public Vector3f getLookCoords() {
		return (this.lookCube);
	}
}
