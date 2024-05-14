# Java Performance Diagnostic Methodology

## Exercise 0: Runtime Setup

### Configuring `setEnv`

- **Setting JAVA_HOME**: After unpacking the zip, adjust the value of `JAVA_HOME` in the `setEnv` script.
    - For OSX: `export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home`
    - **For Windows Users**: Escape spaces in paths. For example, use `C:\\My\\ Documents`.
    - Test the script by running `./setEnv.sh` (`setEnv.bat` in Windows) which should display the version of Java you configured.

### JVM Configuration

- The `jvm.config` file (or `jvm.bat` for Windows) contains JVM configurations including:
    - Choice of garbage collector
    - Size of heap
    - GC logging
    - Other configurations to support various types of investigations.

### Download Tooling

- **Tools**:
    - [VisualVM](https://visualvm.github.io/download.html)
    - Java Mission Control and Flight Recorder can also be used.
    - [async profiler](https://github.com/async-profiler/async-profiler)

### Final Note

This workshop is designed to help you become more effective in resolving performance regressions. The exercises are open-ended, allowing for exploration of different performance aspects. If you encounter issues, do not hesitate to ask for help.

### Warning

- Running some exercises may cause your CPUs to run at close to capacity, which could hinder the ability to terminate the application. Avoid running unnecessary applications.

### Copyright Notice

Copyright 2024 Kodewerk Ltd. All Rights Reserved.
