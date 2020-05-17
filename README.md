# SysMLinJava - High-Precision MBSE
SysMLinJava is a Java representation of the Object Management Group's (OMG) System Modeling Language (SysML).  The API is fully open source under the TBD license.  It consists of a Java representation of virtually all of the elements of SysML enabling the development, execution, test, and management of your highly complex and extensive SysML models as Java software.
Using your favorite Java IDE (Exlipse, Idea, Netbeans, etc) you can "code" your model using the power of advanced editors (real-time/incremental compilation, code assist, automatic code generation, refactoring, etc.) to develop you more precise and correct model.  As an executable program, your model can now include arbitrarily complex and detailed definitions of system behaviors such as operations, receptions, and activities, without the drudgery of drawing activity diagrams.  Just specify the behavior in code and verify it through actual execution.
The SysMLinJava state-machine is an executable model of the SysML state-machine specification.  Using simple Java constructs, you can specify the states and their enter, do, and exit behaviors, and the transitions and their triggers, guards, and effects of the state machine and the SysMLStateMachine will execute your FSM precisely in accordance with your specified 

More precise specification of systems architecture/design
Open-standards-based specification - vs not-very-standard XML or proprietary, e.g. IBMs text files, NoMagic's XMI, Sparx's ???
Simple class-based objects available (vs Block or ValueType-based)
Java-based constraints, i.e. no 3rd party languages needed such as Javascript, Matlab, etc.
Java-based simulation, i.e. no complicated diagramming of activities and operations logic, no FuML, - just code it in Java!
Java-based parametric calculations, i.e. no overly-complicated diagramming of parameter ports, bound references, etc. - just connect (thread-safe) ParameterPort (Java observer pattern and set/get field values) to ObservableValueType 
Programming language used to specify activities - vs gratuitously complicated activity diagram notation
SysMLinJava model can serve as baseline templates/patterns for actual implementation code
SysML standard-based annotations and base classes enable development of numerous tools for developing viewpoints, e.g. SSSs/SRSs/SysML requirements from model, ICDs, SSDDs, SDDs, BOM's, project plans, etc.
Open-metamodel enables development of tools that can use Java parsers and proprietary tool's API to translate Java-based model into a tool vendor's model, e.g. into a Rhap, EA, or Cameo model.
SysMLinJava model can use widely available open-standards based SCM tools to CM/test/build model, e.g. git, maven, ant, etc. (instead of vendor's proprietary CM methods)
As standard Java, can use standard open IDEs to develop and maintain, e.g. Eclipse, IDEA, Netbeans, etc.
As standard Java/programming system, can use vast software devOps environment/processes, i.e. atlasian tools git, jira, confluence, etc. 
As standard Java, can leverage large community of Java software engineers to develop model - good for large scale (big "teams"/cross organization) modeling/simulation projects as well as for small scale (the individual SE) projects.   
Enables Agent-Based and/or Discrete Event-based simulations, i.e. SysMLBlocks, SysMLConstrainBlocks, SysMLStateMachine, SysMLFullPorts, SysMLProxyPort, SysMLParameterPort, SysMLTest, SysMLValueType, etc. are simulation-ready Java classes that can simulate agents and state machines/triggers support for discrete events.
Can use software metric tools to measure the system design, e.g. complexity metrics, change metrics,
Provides more reliable and verifiable method (via compilation and execution) of validating component interaction sequences than weak definitions available from sequence diagrams, e.g. weak timing, synch vs asynch messages, reply messages, imprecise parallel, loop, opt, etc.
More efficient/precise SE process.  Instead of "write requirements, model system, feedback, rewrite requirements", SysMLinJava models system and uses model to write requirements.  The model is the requirements.   
