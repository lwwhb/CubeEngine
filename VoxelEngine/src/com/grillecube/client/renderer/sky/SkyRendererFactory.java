package com.grillecube.client.renderer.sky;

import com.grillecube.client.renderer.MainRenderer;
import com.grillecube.client.renderer.RendererFactory;
import com.grillecube.client.renderer.camera.CameraProjective;
import com.grillecube.common.world.Sky;

public class SkyRendererFactory extends RendererFactory {

	private CameraProjective camera;
	private Sky sky;

	public SkyRendererFactory(MainRenderer mainRenderer) {
		super(mainRenderer);
	}

	@Override
	public void update(double dt) {

	}

	public final CameraProjective getCamera() {
		return (this.camera);
	}

	public final Sky getSky() {
		return (this.sky);
	}

	public final void setCamera(CameraProjective camera) {
		this.camera = camera;
	}

	public final void setSky(Sky sky) {
		this.sky = sky;
	}

	@Override
	public void render() {
		this.getMainRenderer().getSkyRenderer().render(this.getCamera(), this.getSky());
	}
}
