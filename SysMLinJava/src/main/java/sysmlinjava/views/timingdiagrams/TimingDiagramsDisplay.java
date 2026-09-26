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
package sysmlinjava.views.timingdiagrams;

/**
 * The {@code TimingDiagramsDisplay} is a simple console application that
 * receives {@code StateTime}s via a UDP socket and displays the data as text on
 * the console.
 * <p>
 * <b>Note:</b> This simple text-based display is part of the basic SysMLinJava
 * tool set. More capable graphics-based displays are available commercially.
 * See SysMLinJava.com for details.
 * 
 * @author ModelerOne
 *
 */
public class TimingDiagramsDisplay
{
	/**
	 * Default UDP port that will be used for datagram receives
	 */
	public static final int udpPort = 8896;

	/**
	 * Receiver of the {@code TimingDiagram} and {@code StateTime} objects to be
	 * displayed
	 */
	TimingDiagramsReceiver receiver;

	/**
	 * Constructor
	 */
	public TimingDiagramsDisplay()
	{
		super();
		receiver = new TimingDiagramsReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the state timing data and displays them as log entry-like strings to
	 * the console.<br>
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		TimingDiagramsDisplay display = new TimingDiagramsDisplay();
		display.receiver.run();
		Runtime.getRuntime().exit(0);
	}
}
