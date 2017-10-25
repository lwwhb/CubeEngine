package com.grillecube.client.renderer.model.editor.mesher;

import com.grillecube.common.faces.Face;

/**
 * Represent a plan of an EditableModel.
 * 
 * Each plan are mapped on the same quad on the texture, and it vertex can be
 * indexed properly
 * 
 * @author Romain
 *
 */
public final class ModelPlane {

	/** face of this plan */
	private final Face face;

	/**
	 * corner of the plan, coordinates are relative to the model referential
	 */
	private final int xmin;
	private final int ymin;
	private final int zmin;

	/** width/height (or depth, but a plan only have two dimensions!) */
	private final int width;
	private final int height;
	private final int depth;

	/** texture width/height in term of blocks */
	private final int txWidth;
	private final int txHeight;

	/** uv for the model plane */
	private int u;
	private int v;

	public ModelPlane(Face face, int xmin, int ymin, int zmin, int width, int height, int depth, int txWidth,
			int txHeight) {
		this.face = face;
		this.xmin = xmin;
		this.ymin = ymin;
		this.zmin = zmin;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.txWidth = txWidth;
		this.txHeight = txHeight;
		this.u = 0;
		this.v = 0;
	}

	/**
	 * @return @see {@link #xmin}
	 */
	public final int getXMin() {
		return (this.xmin);
	}

	/**
	 * @return @see {@link #ymin}
	 */
	public final int getYMin() {
		return (this.ymin);
	}

	/**
	 * @return @see {@link #zmin}
	 */
	public final int getZMin() {
		return (this.zmin);
	}

	/**
	 * @return @see {@link #width}
	 */
	public final int getWidth() {
		return (this.width);
	}

	/**
	 * @return @see {@link #height}
	 */
	public final int getHeight() {
		return (this.height);
	}

	/**
	 * @return @see {@link #depth}
	 */
	public final int getDepth() {
		return (this.depth);
	}

	/**
	 * @return @see {@link #face}
	 */
	public final Face getFace() {
		return (this.face);
	}

	/**
	 * @return the area of this plane
	 */
	public final int getArea() {
		return (this.width * this.height * this.depth);
	}

	public final int getTextureWidth() {
		return (this.txWidth);
	}

	public final int getTextureHeight() {
		return (this.txHeight);
	}

	public final int getU() {
		return (this.u);
	}

	public final int getV() {
		return (this.v);
	}

	public final void setUV(int u, int v) {
		this.u = u;
		this.v = v;
	}

}