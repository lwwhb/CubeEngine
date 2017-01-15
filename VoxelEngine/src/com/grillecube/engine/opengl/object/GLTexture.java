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

package com.grillecube.engine.opengl.object;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class GLTexture implements GLObject {
	/** opengl texture id */
	private int _glID;

	/** texture size */
	private int _width;
	private int _height;

	public GLTexture(int glID) {
		this._glID = glID;
		this._width = 0;
		this._height = 0;
	}

	public GLTexture() {
		this(0);
	}

	@Override
	public void delete() {
		GL11.glDeleteTextures(this._glID);
		this._glID = 0;
		this._width = 0;
		this._height = 0;
	}

	/** set pixels data */
	public void setData(BufferedImage img) {
		if (img == null) {
			return;
		}

		byte[] pixels = ImageUtils.getImagePixels(img);
		ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length);
		buffer.put(pixels);
		buffer.flip();

		this.bind(GL13.GL_TEXTURE0, GL11.GL_TEXTURE_2D);

		this.image2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, img.getWidth(), img.getHeight(), 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, buffer);

		this.parameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		this.parameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		this.unbind(GL11.GL_TEXTURE_2D);

		this._width = img.getWidth();
		this._height = img.getHeight();
	}

	public int getWidth() {
		return (this._width);
	}

	public int getHeight() {
		return (this._height);
	}
	
	public void bind(int target) {
		GL11.glBindTexture(target, this._glID);
	}
	
	public void bind(int texture, int target) {
		GL13.glActiveTexture(texture);
		GL11.glBindTexture(target, this._glID);
	}

	public void unbind(int target) {
		GL11.glBindTexture(target, 0);
	}

	/**
	 * int target, int level, int internalformat, int width, int height, int
	 * border, int format, int type, ByteBuffer pixels
	 */
	public void image2D(int target, int level, int internalformat, int width, int height, int border, int format,
			int type, ByteBuffer pixels) {
		GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	public void parameteri(int target, int pname, int param) {
		GL11.glTexParameteri(target, pname, param);
	}

	public void parameterf(int target, int pname, float param) {
		GL11.glTexParameterf(target, pname, param);
	}

	public int getID() {
		return (this._glID);
	}
}
