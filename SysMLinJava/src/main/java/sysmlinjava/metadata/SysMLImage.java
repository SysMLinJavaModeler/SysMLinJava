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
package sysmlinjava.metadata;

import java.util.Optional;

/**
 * SysMLinJava's representation of the SysML image metadata. {@code SysMLImage}
 * is a specialized type of {@code SysMLMetadata} that is used for the
 * attributes of the {@code SysMLIcon}. It consists of optional binary data for
 * the image, optional location URI for the image file, optional encoding
 * definition, and optional mime type of the image.
 * 
 * @author ModelerOne
 */
public final class SysMLImage extends SysMLMetadata
{
	/**
	 * Binary data for the image according to the given MIME type, encoded as given
	 * by the encoding.
	 */
	public Optional<String> content;

	/**
	 * Describes how characters in the content are to be decoded into binary data.
	 * At least "base64", "hex", "identify", and "JSONescape" shall be supported.
	 */
	public Optional<String> encoding;

	/**
	 * A URI for the location of a resource containing the image content, as an
	 * alternative for embedding it in the content
	 */
	public Optional<String> location;

	/**
	 * The MIME type according to which the content should be interpreted.
	 */
	public Optional<String> mimeType;

	/**
	 * Constructor for all attributes
	 * 
	 * @param content  optional binary data (as string) for image
	 * @param encoding optional encoding method of the binary data
	 * @param location optional URI for the image file, in lieu of the content
	 * @param mimeType optional mime type of the image
	 */
	public SysMLImage(Optional<String> content, Optional<String> encoding, Optional<String> location, Optional<String> mimeType)
	{
		super("noname", 0L);
		this.content = content;
		this.encoding = encoding;
		this.location = location;
		this.mimeType = mimeType;
	}

	/**
	 * Constructor for all attributes
	 * 
	 * @param content  optional binary data (as string) for image
	 * @param encoding optional encoding method of the binary data
	 * @param location optional URI for the image file, in lieu of the content
	 * @param mimeType optional mime type of the image
	 * @param name     unique name
	 * @param id       unique identifier
	 */
	public SysMLImage(Optional<String> content, Optional<String> encoding, Optional<String> location, Optional<String> mimeType, String name, long id)
	{
		super(name, id);
		this.content = content;
		this.encoding = encoding;
		this.location = location;
		this.mimeType = mimeType;
	}
}
