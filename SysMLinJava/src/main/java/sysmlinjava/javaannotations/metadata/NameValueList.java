package sysmlinjava.javaannotations.metadata;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a name-value (a.k.a. key-value) list for metadata,
 * i.e. an instance of a {@code 	Map<String, String>}.
 * 
 * @author ModelerOne
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface NameValueList
{

}
