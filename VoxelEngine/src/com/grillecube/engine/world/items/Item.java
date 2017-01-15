package com.grillecube.engine.world.items;

import com.grillecube.engine.renderer.model.Model;
import com.grillecube.engine.resources.R;
import com.grillecube.engine.world.entity.EntityModeledLiving;

public abstract class Item {

	/** rarity */
	public enum Rarity {
		VALUELESS("valueless"), COMMON("common"), RARE("rare");

		/** rarity name */
		String _name;

		Rarity(String str) {
			this._name = R.getWord("rarity." + str);
		}

		@Override
		public String toString() {
			return (this._name);
		}
	}

	/** unique item id, set when the item is registered */
	private final short _id;

	/** the item texture for inventories */
	private final int _textureID;

	/** the model when this item is rendered */
	private final Model _model;

	/** the skin id to use (relative to the given model) */
	private final int _skinID;

	/** the model string id (to get unlocalized strings) */
	private final String _textID;

	/** item rarity */
	private Rarity _rarity;

	public Item(short id, int textureID, Model model, int skinID, String textID) {
		this._id = id;
		this._textureID = textureID;
		this._model = model;
		this._skinID = skinID;
		this._textID = textID;
		this.setRarity(Item.Rarity.COMMON);
	}

	/** set the item rarity */
	public void setRarity(Rarity rarity) {
		this._rarity = rarity;
	}

	/** get the item rarity */
	public Rarity getRarity() {
		return (this._rarity);
	}

	/** item id */
	public int getID() {
		return (this._id);
	}

	/** texture id */
	public int getTextureID() {
		return (this._textureID);
	}

	/** model id */
	public Model getModel() {
		return (this._model);
	}

	/** skin id */
	public int getSkinID() {
		return (this._skinID);
	}

	/** return the text id for this item */
	public String getTextID() {
		return (this._textID);
	}

	/** return the name of the item */
	public String getName() {
		return (R.getWord("item." + this._textID + ".name"));
	}

	/** return the description of the item */
	public String getDescription() {
		return (R.getWord("item." + this._textID + ".description"));
	}

	/** return the comment of an item */
	public String getComment() {
		return (R.getWord("item." + this._textID + ".comment"));
	}

	/**
	 * called when this item is unequipped on a living entity at the given
	 * equipment id
	 */
	public void onUnequipped(EntityModeledLiving entity, int equipmentID) {
	}

	/**
	 * called when this item is equipped on a living entity at the given
	 * equipment id
	 */
	public void onEquipped(EntityModeledLiving entity, int equipmentID) {
	}
}
