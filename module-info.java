/**
 * <h2>SysML in java</h2> The SysMLinJava module is a java-based software
 * framework for executable SysML model development. It allows the SyML modeler
 * to achieve high-precision executable SysML models. Modeling in SysML has
 * traditionally meant using a commercially available tool to draw the
 * structural and behavioral elements of the model as SysML diagrams. This
 * dependence on drawings has more often than not resulted in costly effort to
 * learn and manipulate the drawing tool and imposed considerable frustration
 * and limitation in modeling complex behaviors. In addition, the ability of the
 * drawings-based tool to execute/test a model has been frustratingly limited
 * and/or esoteric to the vendor product.
 * <h3>High precision executable SysML models</h3> SysMLinJava was developed to
 * make extensive, precise, and executable models relatively easy to develop,
 * analyze, and test. Whereas SysML is based-on the object-oriented paradigm,
 * it's a natural progession to use an object-oriented programming language to
 * develop and execute SysML models. Rather than fumble with drawing tools to
 * specify model objects, the systems engineer can quickly generate model
 * objects as Java classes using any of the myriad powerful easy-to-use Java
 * software development IDEs available today. And the "ultimate" SysML modeling
 * capability - the executable model - is easily realized through Java's
 * extensive and powerful execution capabilities.
 * <h3>SysML meta-model compliance</h3> SysMLinJava was designed/developed to
 * comply with the OMG standard for SysML - a drawings-based language. As a
 * Java-based implementation, SysMLinJava cannot technically comply with the
 * SysML standard. Therefore, SysMLinJava does not claim to be in technical
 * compliance with the SysML standard, but rather SysMLinJava is a
 * representation of the SysML meta-model in the Java programming language.
 * <h3>Java class representation of SysML elements</h3> The elements of SysML -
 * blocks, state machines, value types, activities, etc. - are all mapped to
 * SysMLinJava classes. The SysMLinJava classes represent the properties of the
 * SysML elements as Java variables and methods. The block values, for instance,
 * are represented as Java variables that are specializations of the {@code
 * SysMLValueType}. Block operations and receptions are represented by Java
 * methods. The block's state machine is represented by a variable that is an
 * extension of the {@code SysMLStateMachine} class. And so forth. The details
 * of the SysMLinJava model elements are explained in their respective .java
 * source files. Review of these model elements will clarify the relatively easy
 * and straightforward application of the SysMLinJava framework to SysML-based
 * systems modeling.
 * <h3>Open and independent module</h3> Of course, all of the packages in the
 * module are exported for use by other modules that define SysMLinJava models.
 * The module's only required modules are those of the Java API.
 */
module sysMLinJava
{
	exports sysmlinjava;
	exports sysmlinjava.analysis.bom;
	exports sysmlinjava.analysis.bom.annotations;
	exports sysmlinjava.analysis.barcharts;
	exports sysmlinjava.analysis.common;
	exports sysmlinjava.analysis.statetransitionstransmitters;
	exports sysmlinjava.analysis.linecharts;
	exports sysmlinjava.analysis.animatedareadisplay;
	exports sysmlinjava.analysis.statetransitions;
	exports sysmlinjava.annotations.requirements;
	exports sysmlinjava.annotations.parametrics;
	exports sysmlinjava.annotations.tests;
	exports sysmlinjava.annotations.events;
	exports sysmlinjava.annotations.statemachines;
	exports sysmlinjava.annotations.blockcontainers;
	exports sysmlinjava.analysis.scatterplots;
	exports sysmlinjava.annotations.dependencies;
	exports sysmlinjava.analysis.interactionmessagetransmitter;
	exports sysmlinjava.analysis.timingdiagrams;
	exports sysmlinjava.analysis.interactionsequence;
	exports sysmlinjava.connectors;
	exports sysmlinjava.constraintblocks;
	exports sysmlinjava.events;
	exports sysmlinjava.kinds;
	exports sysmlinjava.units;
	exports sysmlinjava.probability;
	exports sysmlinjava.comments;
	exports sysmlinjava.requirements;
	exports sysmlinjava.annotations.comments;
	exports sysmlinjava.analysis.neuralnetdisplay;
	exports sysmlinjava.valuetypes;
	exports sysmlinjava.analysis.htmldisplay;
	exports sysmlinjava.blocks;
	exports sysmlinjava.common;
	exports sysmlinjava.ports;
	exports sysmlinjava.annotations.views;
	exports sysmlinjava.quantitykinds;
	exports sysmlinjava.annotations;
	exports sysmlinjava.statemachine;
	exports sysmlinjava.tests;
	exports sysmlinjava.views;

	requires transitive java.logging;
}