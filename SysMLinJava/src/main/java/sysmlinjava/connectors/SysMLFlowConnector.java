/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.connectors;

import java.util.List;

import sysmlinjava.parts.SysMLPart;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.ports.SysMLProxyPort;

/**
 * SysMLinJava representation of the SysML flow connector. The
 * {@code SysMLFlowConnector} specifies the ports that are to be connected by
 * the connector.
 * <p>
 * <b>Note:</b> Within the context of the Java language, a "flow connector" is
 * implemented as a simple Java object reference, i.e. a field that is
 * initialized with the reference to another port. Therefore, the
 * {@code SysMLFlowConnector} realizes a connector by invoking a method on the
 * source port(s) to add the destination port to its set of connections. For
 * example, the connector function connects a pair of peer ports/protocols by
 * invoking the {@code connectToPeerPort(<destination port>)} operation of the
 * source port with the destination port for the argument.
 * <p>
 * Connectors can be constructed between pairs of ports where the ports are at
 * the same level in a "protocol stack" (peer to peer), upper to lower levels
 * (client to server), lower to upper (server to client), and virtually peer
 * connected (virtual). The virtual connector is used for modeling/specification
 * of the SysML "interface" and is not executable. The other three types of
 * connectors are executable, i.e. items can be transmitted and received via
 * these types of connectors.
 * 
 * @author ModelerOne
 */
public class SysMLFlowConnector extends SysMLPart
{
	/**
	 * Enumeration of the types of connector
	 */
	public enum TypesEnum
	{
		/**
		 * Connects peer-level ports/protocols, i.e. peers in a protocol
		 */
		peertopeer,
		/**
		 * Connects adjacent client-to-server ports/protocols, i.e. upper level to lower
		 * level protocol
		 */
		clienttoserver,
		/**
		 * Connects adjacent server-to-client ports/protocols, i.e. lower level to upper
		 * level protocol
		 */
		servertoclient,
		/**
		 * Connects ports/protocols virtually, i.e. connection is for modeling purposes
		 * only to specify a SysML "interface". Connections are not executable.
		 */
		virtual
	}

	/**
	 * Constructor of a connection between ports.
	 * 
	 * @param type       the type of connector
	 * @param fullDuplex true if this connection is to be full duplex (two-way)
	 *                   communications
	 * @param sourcePort port from which items are transmitted
	 * @param targetPort port by which items are received
	 * @param name       unique name
	 * @param id         unique identifier
	 */
	public SysMLFlowConnector(TypesEnum type, boolean fullDuplex, SysMLPort sourcePort, SysMLPort targetPort, String name, Long id)
	{
		super(name, id);
		connect(type, fullDuplex, List.of(sourcePort), List.of(targetPort));
	}

	/**
	 * Constructor of multiple connections between ports. Each source port is
	 * connected to the desination port that is in the same position in the lists
	 * 
	 * @param type        the type of connector
	 * @param fullDuplex  true if this connection is to be full duplex (two-way)
	 *                    communications
	 * @param sourcePorts ports from which items are transmitted, one to one
	 * @param targetPorts ports to which items are transmitted, one to one
	 * @param name        unique name
	 * @param id          unique identifier
	 */
	public SysMLFlowConnector(TypesEnum type, boolean fullDuplex, List<? extends SysMLPort> sourcePorts, List<? extends SysMLPort> targetPorts, String name, Long id)
	{
		super(name, id);
		connect(type, fullDuplex, sourcePorts, targetPorts);
	}

	/**
	 * Constructor of connection between proxy ports.
	 * 
	 * @param sourcePort proxy port from which action is invoked
	 * @param targetPort proxy port on which action is invoked
	 * @param name       unique name
	 * @param id         unique identifier
	 */
	public SysMLFlowConnector(SysMLProxyPort sourcePort, SysMLProxyPort targetPort, String name, Long id)
	{
		super(name, id);
		connect(List.of(sourcePort), List.of(targetPort));
	}

	/**
	 * Connects the specified pairs of ports in accordance with type and duplex
	 * specification
	 * 
	 * @param type        the type of connector
	 * @param fullDuplex  true if this connection is to be full duplex (two-way)
	 *                    communications
	 * @param sourcePorts ports from which items are transmitted, one to one
	 * @param targetPorts ports to which items are transmitted, one to one
	 */
	private void connect(TypesEnum type, boolean fullDuplex, List<? extends SysMLPort> sourcePorts, List<? extends SysMLPort> targetPorts)
	{
		switch (type)
		{
		case clienttoserver:
			for (int i = 0; i < sourcePorts.size(); i++)
			{
				sourcePorts.get(i).connectToServerPort(targetPorts.get(i));
				if (fullDuplex)
					targetPorts.get(i).connectToClientPort(sourcePorts.get(i));
			}
			break;
		case peertopeer:
			for (int i = 0; i < sourcePorts.size(); i++)
			{
				sourcePorts.get(i).connectToPeerPort(targetPorts.get(i));
				if (fullDuplex)
					targetPorts.get(i).connectToPeerPort(sourcePorts.get(i));
			}
			break;
		case servertoclient:
			for (int i = 0; i < sourcePorts.size(); i++)
			{
				sourcePorts.get(i).connectToClientPort(targetPorts.get(i));
				if (fullDuplex)
					targetPorts.get(i).connectToServerPort(sourcePorts.get(i));
			}
			break;
		case virtual:
			for (int i = 0; i < sourcePorts.size(); i++)
			{
				sourcePorts.get(i).virtualConnectToPeerPort(targetPorts.get(i));
				if (fullDuplex)
					targetPorts.get(i).virtualConnectToPeerPort(sourcePorts.get(i));
			}
			break;
		default:
			logger.severe("unrecognized type of connector: " + type);
			break;
		}
	}

	/**
	 * Connects the specified pairs of proxy ports
	 * 
	 * @param sourceProxys      proxy ports from which action is invoked
	 * @param destinationProxys proxy ports on which action is invoked
	 */
	private void connect(List<? extends SysMLProxyPort> sourceProxys, List<? extends SysMLProxyPort> destinationProxys)
	{
		for (int i = 0; i < sourceProxys.size(); i++)
			sourceProxys.get(i).addConnectedPortPeer(destinationProxys.get(i));
	}
}
