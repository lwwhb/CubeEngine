package com.grillecube.common.network;

import com.grillecube.common.VoxelEngine;

public interface INetwork {
	/** stop the network */
	public void stop();

	/** get side of the network */
	public VoxelEngine.Side getSide();
}
