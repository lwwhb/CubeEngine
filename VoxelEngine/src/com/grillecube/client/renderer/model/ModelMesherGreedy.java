package com.grillecube.client.renderer.model;

import java.util.Stack;

import com.grillecube.client.renderer.model.builder.ModelPartBuilder;
import com.grillecube.client.renderer.model.json.ModelMeshVertex;
import com.grillecube.common.faces.Face;
import com.grillecube.common.world.terrain.Terrain;

public class ModelMesherGreedy extends ModelMesher {

	/**
	 * fill an array of dimension
	 * [Terrain.BLOCK_SIZEIZE_X][Terrain.BLOCK_SIZEIZE_Y][Terrain.
	 * BLOCK_SIZEIZE_Z][6] of terrain faces visibility
	 */
	@Override
	protected Stack<ModelMeshVertex> getVertexStack(ModelPartBuilder part) {

		// prepare the mesh vertex stack
		Stack<ModelMeshVertex> stack = new Stack<ModelMeshVertex>();

		// get visibile faces
		BlockFace[][][][] faces = this.getFacesVisibility(part, stack);
		if (faces == null) {
			return (stack);
		}

		boolean[][][][] visited = new boolean[Face.faces.length][Terrain.DIM][Terrain.DIM][Terrain.DIM];

		// for each face
		for (int faceID = 0; faceID < Face.faces.length; faceID++) {
			for (int z = 0; z < Terrain.DIM; z++) {
				for (int y = 0; y < Terrain.DIM; y++) {
					for (int x = 0; x < Terrain.DIM; x++) {

						if (visited[faceID][x][y][z]) {
							continue;
						}

						BlockFace face = faces[faceID][x][y][z];

						// the face isnt visible, or has already been processed
						if (face == null) {
							continue;
						}

						// TOP OR BOT FACE
						if (faceID == Face.TOP || faceID == Face.BOT) {

							int width = 1;
							int depth = 1;

							// generate the rectangle width
							while (x + width < Terrain.DIM && !visited[faceID][x + width][y][z]
									&& face.equals(faces[faceID][x + width][y][z])) {
								++width;
							}

							// generate the rectangle depth
							depth_test: while (z + depth < Terrain.DIM) {
								for (int dx = 0; dx < width; dx++) {
									if (visited[faceID][x + dx][y][z + depth]
											|| !face.equals(faces[faceID][x + dx][y][z + depth])) {
										break depth_test;
									}
								}
								++depth;
							}

							// now we have the rectangle width / height
							for (int i = 0; i < 4; i++) {

								ModelMeshVertex vertex = face.vertices[i];

								vertex.posx = x * Terrain.BLOCK_SIZE
										+ FACES_VERTICES[faceID][i].x * Terrain.BLOCK_SIZE * width;
								vertex.posz = z * Terrain.BLOCK_SIZE
										+ FACES_VERTICES[faceID][i].z * Terrain.BLOCK_SIZE * depth;
							}
							face.pushVertices(stack);

							// set visited position
							this.setVisited(visited, faceID, x, y, z, width, 1, depth);

							// pass visited blocks
							x += width - 1;
						}

						// RIGHT OR LEFT FACE
						else if (faceID == Face.RIGHT || faceID == Face.LEFT) {
							int width = 1;
							int height = 1;

							// generate the rectangle width
							while (x + width < Terrain.DIM && !visited[faceID][x + width][y][z]
									&& face.equals(faces[faceID][x + width][y][z])) {
								++width;
							}

							// generate the rectangle depth
							height_test: while (y + height < Terrain.DIM) {
								for (int dx = 0; dx < width; dx++) {
									if (visited[faceID][x + dx][y + height][z]
											|| !face.equals(faces[faceID][x + dx][y + height][z])) {
										break height_test;
									}
								}
								++height;
							}

							// now we have the rectangle width / height
							for (int i = 0; i < 4; i++) {

								ModelMeshVertex vertex = face.vertices[i];

								vertex.posx = x * Terrain.BLOCK_SIZE
										+ FACES_VERTICES[faceID][i].x * Terrain.BLOCK_SIZE * width;
								vertex.posy = y * Terrain.BLOCK_SIZE
										+ FACES_VERTICES[faceID][i].y * Terrain.BLOCK_SIZE * height;
							}
							face.pushVertices(stack);

							// set visited position
							this.setVisited(visited, faceID, x, y, z, width, height, 1);

							// pass visited blocks
							x += width - 1;
						}

						// ELSE : FRONT OR BACK
						else {

							int depth = 1;
							int height = 1;

							// generate the rectangle width
							while (z + depth < Terrain.DIM && !visited[faceID][x][y][z + depth]
									&& face.equals(faces[faceID][x][y][z + depth])) {
								++depth;
							}

							// generate the rectangle depth
							height_test: while (y + height < Terrain.DIM) {
								for (int dz = 0; dz < depth; dz++) {
									if (visited[faceID][x][y + height][z + dz]
											|| !face.equals(faces[faceID][x][y + height][z + dz])) {
										break height_test;
									}
								}
								++height;
							}

							// now we have the rectangle width / height
							for (int i = 0; i < 4; i++) {

								ModelMeshVertex vertex = face.vertices[i];

								vertex.posz = z * Terrain.BLOCK_SIZE
										+ FACES_VERTICES[faceID][i].z * Terrain.BLOCK_SIZE * depth;
								vertex.posy = y * Terrain.BLOCK_SIZE
										+ FACES_VERTICES[faceID][i].y * Terrain.BLOCK_SIZE * height;
							}
							face.pushVertices(stack);

							// set visited position
							this.setVisited(visited, faceID, x, y, z, 1, height, depth);

							// pass visited blocks
						}
					}
				}
			}
		}

		return (stack);
	}

	private void setVisited(boolean[][][][] visited, int faceID, int x, int y, int z, int width, int height,
			int depth) {
		for (int dx = 0; dx < width; dx++) {
			for (int dy = 0; dy < height; dy++) {
				for (int dz = 0; dz < depth; dz++) {
					visited[faceID][x + dx][y + dy][z + dz] = true;
				}
			}
		}
	}

	/**
	 * return an array which contains standart block faces informations. Non
	 * cubic blocks are pushed to the stack
	 */
	protected BlockFace[][][][] getFacesVisibility(ModelPartBuilder part, Stack<ModelMeshVertex> stack) {

		BlockFace[][][][] faces = new BlockFace[Face.faces.length][Terrain.DIM][Terrain.DIM][Terrain.DIM];

		// for each block
		for (int z = 0; z < Terrain.DIM; ++z) {
			for (int y = 0; y < Terrain.DIM; ++y) {
				for (int x = 0; x < Terrain.DIM; ++x) {

					// if the block is visible
					if (part.isBlockSet(x, y, z)) {
						this.createBlockFaces(part, faces, x, y, z);
					}
				}
			}
		}
		return (faces);
	}

	private void createBlockFaces(ModelPartBuilder part, BlockFace[][][][] faces, int x, int y, int z) {
		// generate every faces
		faces[Face.BACK][x][y][z] = super.getBlockFace(part, x, y, z, Face.F_BACK);
		faces[Face.FRONT][x][y][z] = super.getBlockFace(part, x, y, z, Face.F_FRONT);
		faces[Face.BOT][x][y][z] = super.getBlockFace(part, x, y, z, Face.F_BOT);
		faces[Face.TOP][x][y][z] = super.getBlockFace(part, x, y, z, Face.F_TOP);
		faces[Face.LEFT][x][y][z] = super.getBlockFace(part, x, y, z, Face.F_LEFT);
		faces[Face.RIGHT][x][y][z] = super.getBlockFace(part, x, y, z, Face.F_RIGHT);
	}
}