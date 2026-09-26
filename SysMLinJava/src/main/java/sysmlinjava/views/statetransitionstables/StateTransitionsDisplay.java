/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.views.statetransitionstables;

/**
 * Console display of rows of state transitions table as received via
 * {@code StateTransitionStringsReceiver}
 * 
 * @author ModelerOne
 *
 */
public class StateTransitionsDisplay
{
	/**
	 * UDP port number on which the receiver operates
	 */
	public static final int udpPort = 8895;

	/**
	 * The receiver of the strings that contain the state transition table rows
	 */
	StateTransitionStringsReceiver receiver;

	/**
	 * Constructor (no args)
	 */
	public StateTransitionsDisplay()
	{
		super();
		receiver = new StateTransitionStringsReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the row strings and displays them as toStrings() to
	 * the console.<br>
	 * <b>Note:</b> This simple text-based (console) display is part of the basic
	 * SysMLinJava tool set. More capable graphics-based displays of the state
	 * transition tables are available commercially. See SysMLinJava.com for
	 * details.
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		StateTransitionsDisplay display = new StateTransitionsDisplay();
		display.receiver.run();
		Runtime.getRuntime().exit(0);
	}
}
