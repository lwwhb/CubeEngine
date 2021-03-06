package com.grillecube.client.renderer.model.editor.gui;

import com.grillecube.client.renderer.gui.GuiRenderer;
import com.grillecube.client.renderer.gui.components.GuiColoredQuad;
import com.grillecube.client.renderer.gui.components.GuiLabel;
import com.grillecube.client.renderer.gui.components.GuiItemBar;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextCenterBox;
import com.grillecube.client.renderer.gui.components.parameters.GuiTextParameterTextFillBox;
import com.grillecube.client.renderer.gui.event.GuiListener;
import com.grillecube.client.renderer.gui.event.GuiItemBarEventValueChanged;
import com.grillecube.common.maths.Vector4f;
import com.grillecube.common.utils.Color;

public class GuiItemBarEditor extends GuiItemBar {

	private static final GuiListener<GuiItemBarEventValueChanged<GuiItemBarEditor>> LISTENER = new GuiListener<GuiItemBarEventValueChanged<GuiItemBarEditor>>() {
		@Override
		public void invoke(GuiItemBarEventValueChanged<GuiItemBarEditor> event) {
			event.getGui().onValueChanged();
		}
	};

	private static final Color FILL_COLOR = new Color(0.6f, 0.6f, 1.0f, 1.0f);
	private static final Color BG_COLOR = new Color(0.87f, 0.87f, 0.87f, 1.0f);

	private static final Color FILL_DISABLED_COLOR = Color.scale(null, FILL_COLOR, 0.5f);
	private static final Color BG_DISABLED_COLOR = Color.scale(null, BG_COLOR, 0.5f);

	private GuiColoredQuad total;
	private GuiColoredQuad selected;
	private GuiLabel guiLabel;

	public GuiItemBarEditor() {
		super();
		this.total = new GuiColoredQuad();
		this.total.setColor(BG_COLOR);
		this.total.setHoverable(false);
		this.addChild(this.total);

		this.selected = new GuiColoredQuad();
		this.selected.setColor(FILL_COLOR);
		this.selected.setHoverable(false);
		this.addChild(this.selected);

		this.guiLabel = new GuiLabel();
		this.guiLabel.setHoverable(false);
		this.guiLabel.setFontColor(0, 0, 0, 1.0f);
		this.guiLabel.addTextParameter(new GuiTextParameterTextFillBox(0.75f));
		this.guiLabel.addTextParameter(new GuiTextParameterTextCenterBox());
		this.addChild(this.guiLabel);

		this.addListener(LISTENER);
	}

	@Override
	protected void onRender(GuiRenderer guiRenderer) {
		if (this.isEnabled()) {
			this.selected.setColor(FILL_COLOR);
			this.total.setColor(BG_COLOR);
		} else {
			this.total.setColor(BG_DISABLED_COLOR);
			this.selected.setColor(FILL_DISABLED_COLOR);
		}
	}

	protected void onValueChanged() {
		float width = this.getPercent();
		this.selected.setBox(0, 0, width, 1, 0);
		this.guiLabel.setText(this.getPrefix() + this.getSelectedValue().toString() + this.getSuffix());
	}

	public void setText(String title) {
		this.guiLabel.setText(title);
	}
}
