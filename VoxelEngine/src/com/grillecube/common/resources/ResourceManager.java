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

package com.grillecube.common.resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.grillecube.common.Logger;
import com.grillecube.common.VoxelEngine;

public abstract class ResourceManager {

	/** the resources directory */
	private File gameDir;
	private ArrayList<AssetsPack> assets;

	/** World manager */
	private WorldManager worldManager;

	/** Block manager */
	private BlockManager blockManager;

	/** Block manager */
	private ItemManager itemManager;

	/** Packets */
	private PacketManager packetManager;

	/** Entities */
	private EntityManager entityManager;

	/** events */
	private EventManager eventManager;

	/** lang manager */
	private LangManager langManager;

	/** managers as a list */
	private ArrayList<GenericManager<?>> managers;

	private VoxelEngine engine;

	/** configuration */
	private ResourceManager.Config config;

	private static ResourceManager RESOURCE_MANAGER_INSTANCE;

	public ResourceManager(VoxelEngine engine) {
		RESOURCE_MANAGER_INSTANCE = this;
		this.engine = engine;
	}

	/** initialize every game resources */
	public final void initialize() {

		Logger.get().log(Logger.Level.FINE, "* initializing resources manager");

		this.assets = new ArrayList<AssetsPack>();
		this.managers = new ArrayList<GenericManager<?>>();
		this.addManagers();

		// prepare path
		this.prepareEnginePath();

		// create config
		this.config = new ResourceManager.Config();

		for (GenericManager<?> manager : this.managers) {
			Logger.get().log(Logger.Level.FINE, manager.getClass().getSimpleName());
			manager.initialize();
		}
	}

	protected void addManagers() {

		this.worldManager = new WorldManager(this);
		this.addManager(this.worldManager);

		this.blockManager = new BlockManager(this);
		this.addManager(this.blockManager);

		this.itemManager = new ItemManager(this);
		this.addManager(this.itemManager);

		this.entityManager = new EntityManager(this);
		this.addManager(this.entityManager);

		this.packetManager = new PacketManager(this);
		this.addManager(this.packetManager);

		this.eventManager = new EventManager(this);
		this.addManager(this.eventManager);

		this.langManager = new LangManager(this);
		this.addManager(this.langManager);
	}

	public void addManager(GenericManager<?> manager) {
		this.managers.add(manager);
	}

	private void prepareEnginePath() {
		String OS = (System.getProperty("os.name")).toUpperCase();
		String gamepath;
		if (OS.contains("WIN")) {
			gamepath = System.getenv("AppData");
		} else {
			gamepath = System.getProperty("user.home");
		}

		if (!gamepath.endsWith("/")) {
			gamepath = gamepath + "/";
		}
		this.gameDir = new File(gamepath + "VoxelEngine");

		Logger.get().log(Logger.Level.FINE, "Game directory is: " + this.gameDir.getAbsolutePath());
		if (!this.gameDir.exists()) {
			this.gameDir.mkdirs();
		} else if (!this.gameDir.canWrite()) {
			this.gameDir.setWritable(true);
		}
	}

	/** engine on which this resource manager correspond to */
	public VoxelEngine getEngine() {
		return (this.engine);
	}

	public static ResourceManager instance() {
		return (RESOURCE_MANAGER_INSTANCE);
	}

	/** deinitilize every game resources */
	public final void deinitialize() {
		Logger.get().log(Logger.Level.FINE, "* Stopping resources manager");
		for (GenericManager<?> manager : this.managers) {
			Logger.get().log(Logger.Level.FINE, manager.getClass().getSimpleName());
			manager.deinitialize();
		}
		this.assets = null;
		this.managers = null;
	}

	/** deinitilize every game resources */
	public final void load() {
		Logger.get().log(Logger.Level.FINE, "* Stopping resources manager");
		for (GenericManager<?> manager : this.managers) {
			Logger.get().log(Logger.Level.FINE, manager.getClass().getSimpleName());
			manager.load();
		}
	}

	/** deinitilize every game resources */
	public final void unload() {
		Logger.get().log(Logger.Level.FINE, "* Stopping resources manager");
		for (GenericManager<?> manager : this.managers) {
			Logger.get().log(Logger.Level.FINE, manager.getClass().getSimpleName());
			manager.deinitialize();
		}
	}

	/** get the world manager */
	public final WorldManager getWorldManager() {
		return (this.worldManager);
	}

	/** language manager */
	public final LangManager getLangManager() {
		return (this.langManager);
	}

	/** get the block manager */
	public final BlockManager getBlockManager() {
		return (this.blockManager);
	}

	/** get the item manager */
	public ItemManager getItemManager() {
		return (this.itemManager);
	}

	/** get the packet manager */
	public final PacketManager getPacketManager() {
		return (this.packetManager);
	}

	/** get the entity manager */
	public final EntityManager getEntityManager() {
		return (this.entityManager);
	}

	/** get the event manager */
	public final EventManager getEventManager() {
		return (this.eventManager);
	}

	/** get the resource filepath */
	public String getResourcePath(String modid, String path) {
		String assetsdir = "assets" + File.separator;
		int length = this.gameDir.getAbsolutePath().length() + assetsdir.length() + modid.length() + path.length() + 1;
		StringBuilder builder = new StringBuilder(length);
		String respath = this.gameDir.getAbsolutePath();
		builder.append(respath);
		if (!respath.endsWith(File.separator)) {
			builder.append(File.separator);
		}
		builder.append(assetsdir);
		builder.append(modid);
		if (!modid.endsWith(File.separator)) {
			builder.append(File.separator);
		}
		builder.append(path);
		return (builder.toString().replace("/", File.separator));
	}

	/** add an assets pack (zip file) to be added to the game */
	public AssetsPack putAssets(AssetsPack pack) {
		for (AssetsPack tmppack : this.assets) {
			if (tmppack.getModID().equals(pack.getModID())) {
				Logger.get().log(Logger.Level.ERROR,
						"Tried to put an assets pack which already exists. Canceling operation. ModID: "
								+ pack.getModID());
				return (null);
			}
		}
		this.assets.add(pack);
		pack.extract();
		return (pack);
	}

	// config file constants
	private static final char COMMENT_CHAR = '#';

	public static final HashMap<String, String> getConfigFile(String filepath, int defaultcapacity) {
		return (getConfigFile(filepath, new HashMap<String, String>(defaultcapacity)));
	}

	/**
	 * a function which parse the given file and return a hashmap containing
	 * 'key-values'
	 */
	public static final HashMap<String, String> getConfigFile(String filepath, HashMap<String, String> map) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				++i;

				line = line.trim();
				int index = line.indexOf(COMMENT_CHAR);
				if (index == 0) {
					continue;
				}
				String data = null;

				data = index == -1 ? line : line.substring(0, index - 1);
				if (data != null && data.length() > 0) {
					String[] strs = data.split("=", 0);
					if (strs.length < 2) {
						Logger.get().log(Logger.Level.DEBUG,
								"line malformatted: l " + i + " : `" + line + "` in file " + filepath);
						continue;
					}
					String value = strs[strs.length - 1].trim();
					for (int j = 0; j < strs.length - 1; j++) {
						map.put(strs[j].trim(), value);
					}
				}
			}
			reader.close();

		} catch (IOException e) {
			Logger.get().log(Logger.Level.ERROR, "Couldnt read config file: " + filepath);
		}
		return (map);
	}

	public static boolean fileExists(String filepath) {
		return (new File(filepath).exists());
	}

	/** export the map to the given filepath */
	public static final void exportConfigFile(String filepath, HashMap<String, String> map) {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
			ArrayList<String> keys = new ArrayList<String>(map.keySet());
			Collections.sort(keys);
			for (String key : keys) {
				writer.append(key);
				writer.append('=');
				writer.append(map.get(key));
				writer.newLine();
			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			Logger.get().log(Logger.Level.ERROR, "Couldnt read config file: " + filepath);
		}

	}

	/** get the config informations */
	public Config getConfig() {
		return (this.config);
	}

	public class Config {

		/** config file name */
		public static final String CONFIG_FILE = ".config";

		/** config **/
		private HashMap<String, String> config;

		public Config() {

			this.config = new HashMap<String, String>(1024);

			// configuration stuff
			String configpath = R.getResPath(CONFIG_FILE);
			if (ResourceManager.fileExists(configpath)) {
				ResourceManager.getConfigFile(configpath, this.config);
			} else {
				ResourceManager.exportConfigFile(configpath, this.config);
			}
		}

		/** get a config value */
		public String get(String key, String default_value) {
			String value = this.config.get(key);
			return (value == null ? default_value : value);
		}

		/** set a new config value */
		public void set(String key, String value) {
			this.config.put(key, value);
		}

		public boolean isSet(String key) {
			return (this.config.get(key) != null);
		}
	}
}
