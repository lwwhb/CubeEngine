package com.grillecube.client.renderer.model.editor.gui.toolbox;

import com.grillecube.client.renderer.gui.components.GuiPrompt;
import com.grillecube.client.renderer.gui.components.GuiSpinner;
import com.grillecube.client.renderer.gui.event.GuiListener;
import com.grillecube.client.renderer.gui.event.GuiPromptEventHeldTextChanged;
import com.grillecube.client.renderer.model.editor.gui.GuiPromptEditor;
import com.grillecube.client.renderer.model.editor.gui.GuiSpinnerEditor;

public class GuiToolboxModelPanelBuild extends GuiToolboxModelPanel {

	/** the building tools list */
	private final GuiSpinner tools;

	/** the model block size unit slider bar */
	private final GuiPromptEditor modelBlockSizeUnit;

	public GuiToolboxModelPanelBuild() {
		super();
		this.tools = new GuiSpinnerEditor();
		this.tools.setHint("Tools...");
		this.tools.setBox(0, 0.70f, 1.0f, 0.05f, 0);
		this.addChild(this.tools);

		// // block size unit slider bar
		this.modelBlockSizeUnit = new GuiPromptEditor("Size-unit:", "1.0");
		this.modelBlockSizeUnit.setBox(0, 0.65f, 1.0f, 0.05f, 0);
		this.modelBlockSizeUnit.getPrompt().addListener(new GuiListener<GuiPromptEventHeldTextChanged<GuiPrompt>>() {
			@Override
			public void invoke(GuiPromptEventHeldTextChanged<GuiPrompt> event) {
				onBlockSizeUnitChanged();
			}
		});
		this.addChild(this.modelBlockSizeUnit);
	}

	@Override
	public void refresh() {
	}

	private final void onBlockSizeUnitChanged() {
		float sizeUnit = 0.0f;
		try {
			sizeUnit = this.modelBlockSizeUnit.getPrompt().asFloat();
		} catch (Exception e) {
		}
		if (sizeUnit == this.getModel().getBlockSizeUnit()) {
			return;
		}
		this.getModel().setBlockSizeUnit(sizeUnit);
		this.getModel().requestMeshUpdate();
	}

	@Override
	public String getTitle() {
		return ("Build");
	}
}
