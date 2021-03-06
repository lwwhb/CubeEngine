package com.grillecube.client.renderer.blocks;

import java.util.ArrayList;

import com.grillecube.client.renderer.world.TerrainMeshTriangle;
import com.grillecube.client.renderer.world.TerrainMeshVertex;
import com.grillecube.client.renderer.world.TerrainMesher;
import com.grillecube.client.renderer.world.flat.BlockFace;
import com.grillecube.common.faces.Face;
import com.grillecube.common.maths.Vector3i;
import com.grillecube.common.world.Terrain;
import com.grillecube.common.world.block.Block;
import com.grillecube.common.world.block.BlockLiquid;
import com.grillecube.common.world.block.Blocks;
import com.grillecube.common.world.block.instances.BlockInstanceLiquid;

public class BlockRendererLiquid extends BlockRendererCube {

	public BlockRendererLiquid(int textureID) {
		super(textureID);
	}

	@Override
	public void generateBlockVertices(TerrainMesher terrainMesher, Terrain terrain, Block block, int x, int y, int z,
			BlockFace[][][][] faces, ArrayList<TerrainMeshTriangle> stack) {
		for (Face face : Face.values()) {
			Vector3i vec = face.getVector();
			// get the neighbor of this face
			Block neighbor = terrain.getBlock(x + vec.x, y + vec.y, z + vec.z);

			// if the face-neighboor block is invisible or non opaque
			if (!neighbor.isVisible() || neighbor.isTransparent()) {
				// then add the face
				this.pushFaceVertices(terrainMesher, terrain, neighbor, stack, x, y, z, face);
			}
		}
	}

	/** push vertices for a liquid */
	private void pushFaceVertices(TerrainMesher mesher, Terrain terrain, Block neighbor,
			ArrayList<TerrainMeshTriangle> stack, int x, int y, int z, Face face) {

		// get the instance for this block
		BlockInstanceLiquid instance = (BlockInstanceLiquid) terrain.getBlockInstance(x, y, z);
		if (instance == null) {
			return;
		}

		// check neighbor, and do not render invisible faces
		int nx = x + face.getVector().x;
		int ny = y + face.getVector().y;
		int nz = z + face.getVector().z;

		if (neighbor instanceof BlockLiquid) {
			BlockInstanceLiquid other = (BlockInstanceLiquid) terrain.getBlockInstance(nx, ny, nz);
			if (other != null && instance.getAmount() < other.getAmount()) {
				return;
			}
		}

		// get every vertices as a standart cube
		TerrainMeshVertex v0 = super.createBlockFaceVertex(terrain, face, x, y, z, 0);
		TerrainMeshVertex v1 = super.createBlockFaceVertex(terrain, face, x, y, z, 1);
		TerrainMeshVertex v2 = super.createBlockFaceVertex(terrain, face, x, y, z, 2);
		TerrainMeshVertex v3 = super.createBlockFaceVertex(terrain, face, x, y, z, 3);

		// offset the standart vertices to create flowing effect

		float unit = 1.0f - instance.getAmount() * BlockInstanceLiquid.LIQUID_HEIGHT_UNIT;

		if (instance.getBlockUnder() != Blocks.AIR) {
			int faceID = face.getID();
			if (faceID == Face.TOP) {
				v0.posy -= unit;
				v1.posy -= unit;
				v2.posy -= unit;
				v3.posy -= unit;
			} else if (faceID != Face.BOT) {
				v0.posy -= unit;
				v3.posy -= unit;
			}
		}
		stack.add(new TerrainMeshTriangle(v0, v1, v2));
		stack.add(new TerrainMeshTriangle(v0, v2, v3));
	}
}
