package com.grillecube.client.renderer.model.editor.gui;

import com.grillecube.client.renderer.gui.components.GuiButton;
import com.grillecube.client.renderer.gui.components.GuiLabel;
import com.grillecube.client.renderer.gui.components.GuiPrompt;
import com.grillecube.client.renderer.gui.components.GuiText;
import com.grillecube.client.renderer.gui.components.GuiWindow;
import com.grillecube.client.renderer.gui.components.parameters.GuiParameter;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import com.grillecube.client.renderer.gui.event.GuiEventPress;
import com.grillecube.client.renderer.gui.event.GuiListener;
import com.grillecube.client.renderer.model.animation.Bone;
import com.grillecube.client.renderer.model.editor.camera.CameraToolRigging;
import com.grillecube.client.renderer.model.editor.mesher.ModelBlockData;
import com.grillecube.common.utils.Color;

public class GuiWindowRigging extends GuiWindow {

	private final GuiLabel info;
	private final GuiSpinnerEditor[] bones;
	private final GuiPrompt[] weight;
	private final GuiButton confirm;
	private final GuiButton cancel;

	public GuiWindowRigging(CameraToolRigging cameraToolRigging) {
		super();

		super.setBox(0.25f, 0.25f, 0.5f, 0.5f, 0.0f);

		GuiParameter<GuiText> txtSize = new GuiTextParameterTextFillBox(0.75f);
		GuiParameter<GuiText> txtCenter = new GuiTextParameterTextCenterBox();

		float w = 0.2f;
		float h = w / 1.6f;

		this.info = new GuiLabel();
		this.info.setBox(0.0f, 0.8f, 1.0f, h, 0.0f);
		this.info.setFontColor(Color.WHITE);
		this.info.addTextParameter(txtSize);
		this.info.addTextParameter(txtCenter);
		this.info.setText("Please select bones and weights for the selected blocks");
		this.addChild(info);

		this.bones = new GuiSpinnerEditor[3];
		this.weight = new GuiPrompt[3];
		for (int i = 0; i < 3; i++) {
			this.bones[i] = new GuiSpinnerEditor();
			this.bones[i].add(null);
			for (Bone bone : cameraToolRigging.getModel().getSkeleton().getBones()) {
				this.bones[i].add(bone.getName());
			}
			this.bones[i].setBox(0.35f, 0.65f - i * h, w, h, 0.0f);
			this.bones[i].pick(0);

			this.weight[i] = new GuiPrompt();
			this.weight[i].setHint("blocks bone-weights");
			this.weight[i].setBox(0.55f, 0.65f - i * h, w, h, 0.0f);
			this.weight[i].setTextTestFormat(GuiPrompt.FORMAT_FLOAT);
			this.weight[i].setHeldTextColor(Color.WHITE);
			this.weight[i].addTextParameter(txtSize);
			this.weight[i].addTextParameter(txtCenter);

			this.addChild(this.bones[i]);
			this.addChild(this.weight[i]);
		}

		this.confirm = new GuiButton();
		this.confirm.setText("Confirm");
		this.confirm.addTextParameter(txtSize);
		this.confirm.addTextParameter(txtCenter);
		this.confirm.setBox(0.25f, 0.15f, w, h, 0.0f);
		this.addChild(this.confirm);
		this.confirm.addListener(new GuiListener<GuiEventPress<GuiButton>>() {
			@Override
			public void invoke(GuiEventPress<GuiButton> event) {
				this.setBoneWeight();
				close();
			}

			private final void setBoneWeight() {
				int x0 = cameraToolRigging.getX();
				int y0 = cameraToolRigging.getY();
				int z0 = cameraToolRigging.getZ();
				for (int dx = 0; dx < cameraToolRigging.getWidth(); dx++) {
					for (int dy = 0; dy < cameraToolRigging.getHeight(); dy++) {
						for (int dz = 0; dz < cameraToolRigging.getDepth(); dz++) {
							int x = x0 + dx;
							int y = y0 + dy;
							int z = z0 + dz;
							ModelBlockData blockData = cameraToolRigging.getModel().getBlockData(x, y, z);
							if (blockData != null) {
								int i = 0;
								String bone = cameraToolRigging.getModel().getSkeleton().getBones().get(0).getName();
								float weight = 0.5f;
								blockData.setBone(i, bone, weight);
							}
						}
					}
				}
			}
		});

		this.cancel = new GuiButton();
		this.cancel.setText("Decline");
		this.cancel.setBox(0.55f, 0.15f, w, h, 0.0f);
		this.cancel.addTextParameter(txtSize);
		this.cancel.addTextParameter(txtCenter);
		this.cancel.addListener(new GuiListener<GuiEventPress<GuiButton>>() {
			@Override
			public void invoke(GuiEventPress<GuiButton> event) {
				close();
			}
		});
		this.addChild(this.cancel);
	}
}
