/**
 * Contains the SysMLinJava representation of numerous SysML value types. It
 * does not include the base class for all value types - the
 * {@code SysMLValueType} - which can be found in the {@code sysmlinjava.common}
 * package. Numerous commonly used value types are provided. As in SysML, base
 * value types for real, integer, string, and boolean are provided as are many
 * specializations of these value types.
 * <p>
 * Each value type is an {@code ObservableValue} which is observable by
 * {@code ValueObserver}s. The observable enables emulation of the "binding"
 * connector for the value types used for constraint parameters and other models
 * requiring value change notification.
 * <p>
 * Value types in SysMLinJava models are not limited to the value types in this
 * package. Additional value types may be created by simply extending the
 * {@code SysMLValueType}, or any of the specializations of the
 * {@code SysMLValueType} contained in this package.
 */
package sysmlinjava.valuetypes;