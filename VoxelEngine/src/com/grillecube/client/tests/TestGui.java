package com.grillecube.client.tests;

import com.grillecube.client.VoxelEngineClient;
import com.grillecube.client.opengl.GLH;
import com.grillecube.client.renderer.gui.GuiRenderer;
import com.grillecube.client.renderer.gui.components.Gui;
import com.grillecube.client.renderer.gui.components.GuiLabel;
import com.grillecube.client.renderer.gui.components.GuiPrompt;
import com.grillecube.client.renderer.gui.components.GuiSliderBar;
import com.grillecube.client.renderer.gui.components.GuiText;
import com.grillecube.client.renderer.gui.components.GuiTexture;
import com.grillecube.client.renderer.gui.components.GuiView;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import com.grillecube.client.renderer.gui.listeners.GuiListenerMouseEnter;
import com.grillecube.client.renderer.gui.listeners.GuiListenerMouseExit;
import com.grillecube.client.renderer.model.editor.gui.GuiSliderBarEditor;
import com.grillecube.common.Logger;
import com.grillecube.common.resources.R;

public class TestGui {

	public static void main(String[] args) {

		VoxelEngineClient engine = new VoxelEngineClient();
		engine.initialize();
		engine.load();

		GuiView guiView = new GuiView() {

			@Override
			protected void onInitialized(GuiRenderer renderer) {
				// this.addGuiTexture();
				// this.addGuiLabel();
				this.addGuiTextPrompt();
				// this.addGuiSliderBar();
			}

			@Override
			protected void onDeinitialized(GuiRenderer renderer) {
			}

			@Override
			protected void onUpdate(float x, float y, boolean pressed) {
			}

			private void addGuiSliderBar() {
				GuiSliderBarEditor guiSliderBar = new GuiSliderBarEditor();
				guiSliderBar.setBox(0.25f, 0.75f, 0.5f, 0.10f, 0.0f);
				Float[] floats = GuiSliderBar.floatRange(0, 1000, 1001);
				guiSliderBar.addValues(floats);
				this.addChild(guiSliderBar);
			}

			private void addGuiTexture() {
				GuiTexture t = new GuiTexture();
				float ux = 144.0f / 256.0f;
				float uy = 16.0f / 256.0f;
				t.setBox(0.1f, 0.1f, 0.8f, 0.16f, 0.5f);
				t.setTexture(GLH.glhGenTexture(R.getResPath("textures/block_atlas/256x256.png")), 0.0f, 0.0f, ux, uy);
				t.addListener(new GuiListenerMouseEnter<GuiTexture>() {
					@Override
					public void invokeMouseEnter(GuiTexture gui, double mousex, double mousey) {
						Logger.get().log(Logger.Level.DEBUG, "in");
					}
				});

				t.addListener(new GuiListenerMouseExit<GuiTexture>() {
					@Override
					public void invokeMouseExit(GuiTexture gui, double mousex, double mousey) {
						Logger.get().log(Logger.Level.DEBUG, "out");
					}
				});
				this.addChild(t);
			}

			private void addGuiTextPrompt() {
				GuiPrompt lbl = new GuiPrompt();
				lbl.setFontColor(1, 1, 1, 1.0f);
				lbl.setBox(0, 0.5f, 0.5f, 0.5f, 0);
				lbl.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
				lbl.addTextParameter(new GuiTextParameterTextCenterBox());

				this.addChild(lbl);
			}

			private void addGuiLabel() {
				GuiLabel lbl = new GuiLabel();
				lbl.setFontColor(1, 1, 1, 1.0f);
				lbl.setText("this is a sample string");
				lbl.setBox(0, 0, 0.5f, 0.5f, 0);
				lbl.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
				lbl.addTextParameter(new GuiTextParameterTextCenterBox());
				lbl.getGuiText().addListener(new GuiListenerMouseEnter<GuiText>() {
					@Override
					public void invokeMouseEnter(GuiText gui, double mousex, double mousey) {
						Logger.get().log(Logger.Level.DEBUG, "in");
					}
				});

				lbl.getGuiText().addListener(new GuiListenerMouseExit<GuiText>() {
					@Override
					public void invokeMouseExit(GuiText gui, double mousex, double mousey) {
						Logger.get().log(Logger.Level.DEBUG, "out");
					}
				});

				this.addChild(lbl);
			}

			@Override
			public void onAddedTo(Gui gui) {
			}

			@Override
			public void onRemovedFrom(Gui gui) {
			}

		};
		// guiView.set(0.5f, 0.5f, 0.5f, 0.5f, 0.0f);
		guiView.setBox(0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
		engine.getRenderer().getGuiRenderer().addGui(guiView);

		try {
			engine.loop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		engine.deinitialize();
	}
}
