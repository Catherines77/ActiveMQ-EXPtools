package cc.kiiy.service;

public class JfrTemplate {
    public static final String TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"\n" +
"<!--\n" +
"     Recommended way to edit .jfc files is to use Java Mission Control,\n" +
"     see Window -> Flight Recorder Template Manager.\n" +
"-->\n" +
"\n" +
"<configuration version=\"2.0\" label=\"Continuous\" description=\"Low overhead configuration safe for continuous use in production environments, typically less than 1 % overhead.\" provider=\"Oracle\">\n" +
"\n" +
"    <event name=\"jdk.ThreadAllocationStatistics\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\"><![CDATA[||| %s |||]]></setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ClassLoadingStatistics\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">1000 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ClassLoaderStatistics\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.JavaThreadStatistics\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">1000 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ThreadStart\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ThreadEnd\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ThreadSleep\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"synchronization-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ThreadPark\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"synchronization-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.JavaMonitorEnter\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"synchronization-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.JavaMonitorWait\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"synchronization-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.JavaMonitorInflate\">\n" +
"      <setting name=\"enabled\">false</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"synchronization-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.BiasedLockRevocation\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.BiasedLockSelfRevocation\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.BiasedLockClassRevocation\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ReservedStackActivation\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ClassLoad\">\n" +
"      <setting name=\"enabled\" control=\"class-loading-enabled\">false</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ClassDefine\">\n" +
"      <setting name=\"enabled\" control=\"class-loading-enabled\">false</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ClassUnload\">\n" +
"      <setting name=\"enabled\" control=\"class-loading-enabled\">false</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.JVMInformation\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.InitialSystemProperty\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ExecutionSample\">\n" +
"      <setting name=\"enabled\" control=\"method-sampling-enabled\">true</setting>\n" +
"      <setting name=\"period\" control=\"method-sampling-java-interval\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.NativeMethodSample\">\n" +
"      <setting name=\"enabled\" control=\"method-sampling-enabled\">true</setting>\n" +
"      <setting name=\"period\" control=\"method-sampling-native-interval\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SafepointBegin\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SafepointStateSynchronization\">\n" +
"      <setting name=\"enabled\">false</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SafepointWaitBlocked\">\n" +
"      <setting name=\"enabled\">false</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SafepointCleanup\">\n" +
"      <setting name=\"enabled\">false</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SafepointCleanupTask\">\n" +
"      <setting name=\"enabled\">false</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SafepointEnd\">\n" +
"      <setting name=\"enabled\">false</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ExecuteVMOperation\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.Shutdown\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ThreadDump\">\n" +
"      <setting name=\"enabled\" control=\"thread-dump-enabled\">true</setting>\n" +
"      <setting name=\"period\" control=\"thread-dump-interval\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.IntFlag\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.UnsignedIntFlag\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.LongFlag\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.UnsignedLongFlag\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.DoubleFlag\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.BooleanFlag\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.StringFlag\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.IntFlagChanged\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.UnsignedIntFlagChanged\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.LongFlagChanged\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.UnsignedLongFlagChanged\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.DoubleFlagChanged\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.BooleanFlagChanged\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.StringFlagChanged\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ObjectCount\">\n" +
"      <setting name=\"enabled\" control=\"memory-profiling-enabled-all\">false</setting>\n" +
"      <setting name=\"period\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCConfiguration\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"period\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCHeapConfiguration\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.YoungGenerationConfiguration\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCTLABConfiguration\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCSurvivorConfiguration\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ObjectCountAfterGC\">\n" +
"      <setting name=\"enabled\">false</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCHeapSummary\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.PSHeapSummary\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.G1HeapSummary\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.MetaspaceSummary\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.MetaspaceGCThreshold\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.MetaspaceAllocationFailure\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.MetaspaceOOM\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.MetaspaceChunkFreeListSummary\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GarbageCollection\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ParallelOldGarbageCollection\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.YoungGarbageCollection\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.OldGarbageCollection\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.G1GarbageCollection\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCPhasePause\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCPhasePauseLevel1\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCPhasePauseLevel2\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCPhasePauseLevel3\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-all\">false</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCPhasePauseLevel4\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-all\">false</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCPhaseConcurrent\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-all\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.GCReferenceStatistics\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.PromotionFailed\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.EvacuationFailed\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.EvacuationInformation\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.G1MMU\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.G1EvacuationYoungStatistics\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.G1EvacuationOldStatistics\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.G1BasicIHOP\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.G1AdaptiveIHOP\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.PromoteObjectInNewPLAB\">\n" +
"      <setting name=\"enabled\" control=\"memory-profiling-enabled-medium\">false</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.PromoteObjectOutsidePLAB\">\n" +
"      <setting name=\"enabled\" control=\"memory-profiling-enabled-medium\">false</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ConcurrentModeFailure\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.AllocationRequiringGC\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-all\">false</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.TenuringDistribution\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-normal\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.G1HeapRegionInformation\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-all\">false</setting>\n" +
"      <setting name=\"period\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.G1HeapRegionTypeChange\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-all\">false</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ShenandoahHeapRegionInformation\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-all\">false</setting>\n" +
"      <setting name=\"period\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ShenandoahHeapRegionStateChange\">\n" +
"      <setting name=\"enabled\" control=\"gc-enabled-all\">false</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.OldObjectSample\">\n" +
"      <setting name=\"enabled\" control=\"memory-leak-detection-enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\" control=\"memory-leak-detection-stack-trace\">false</setting>\n" +
"      <setting name=\"cutoff\" control=\"memory-leak-detection-cutoff\">0 ns</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CompilerConfiguration\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CompilerStatistics\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"period\">1000 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.Compilation\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"compiler-compilation-threshold\">1000 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CompilerPhase\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"compiler-phase-threshold\">60 s</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CompilationFailure\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled-failure\">false</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CompilerInlining\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled-failure\">false</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CodeSweeperConfiguration\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CodeSweeperStatistics\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"period\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SweepCodeCache\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"compiler-sweeper-threshold\">100 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CodeCacheConfiguration\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CodeCacheStatistics\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"period\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CodeCacheFull\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.OSInformation\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.VirtualizationInformation\">\n" +
"     <setting name=\"enabled\">true</setting>\n" +
"     <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CPUInformation\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ThreadContextSwitchRate\">\n" +
"      <setting name=\"enabled\" control=\"compiler-enabled\">true</setting>\n" +
"      <setting name=\"period\">10 s</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CPULoad\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">1000 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ThreadCPULoad\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">10 s</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.CPUTimeStampCounter\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SystemProcess\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">endChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.NetworkUtilization\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">5 s</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.InitialEnvironmentVariable\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">beginChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.PhysicalMemory\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ObjectAllocationInNewTLAB\">\n" +
"      <setting name=\"enabled\" control=\"memory-profiling-enabled-medium\">false</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ObjectAllocationOutsideTLAB\">\n" +
"      <setting name=\"enabled\" control=\"memory-profiling-enabled-medium\">false</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.NativeLibrary\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">everyChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ModuleRequire\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">endChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ModuleExport\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">endChunk</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.FileForce\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"file-io-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.FileRead\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"file-io-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.FileWrite\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"file-io-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SocketRead\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"socket-io-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SocketWrite\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"      <setting name=\"threshold\" control=\"socket-io-threshold\">20 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.SecurityPropertyModification\">\n" +
"       <setting name=\"enabled\">false</setting>\n" +
"       <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.TLSHandshake\">\n" +
"      <setting name=\"enabled\">false</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.X509Validation\">\n" +
"       <setting name=\"enabled\">false</setting>\n" +
"       <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.X509Certificate\">\n" +
"       <setting name=\"enabled\">false</setting>\n" +
"       <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.JavaExceptionThrow\">\n" +
"      <setting name=\"enabled\" control=\"enable-exceptions\">false</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.JavaErrorThrow\">\n" +
"      <setting name=\"enabled\" control=\"enable-errors\">true</setting>\n" +
"      <setting name=\"stackTrace\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ExceptionStatistics\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"period\">1000 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ActiveRecording\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ActiveSetting\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.DataLoss\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.DumpReason\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ZPageAllocation\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ZThreadPhase\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"threshold\">0 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ZStatisticsCounter\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <event name=\"jdk.ZStatisticsSampler\">\n" +
"      <setting name=\"enabled\">true</setting>\n" +
"      <setting name=\"threshold\">10 ms</setting>\n" +
"    </event>\n" +
"\n" +
"    <!--\n" +
"        Contents of the control element is not read by the JVM, it's used\n" +
"        by Java Mission Control to change settings that carry the control attribute.\n" +
"    -->\n" +
"    <control>\n" +
"      <selection name=\"gc-level\" default=\"detailed\" label=\"Garbage Collector\">\n" +
"        <option label=\"Off\" name=\"off\">off</option>\n" +
"        <option label=\"Normal\" name=\"detailed\">normal</option>\n" +
"        <option label=\"All\" name=\"all\">all</option>\n" +
"      </selection>\n" +
"\n" +
"      <condition name=\"gc-enabled-normal\" true=\"true\" false=\"false\">\n" +
"        <or>\n" +
"          <test name=\"gc-level\" operator=\"equal\" value=\"normal\"/>\n" +
"          <test name=\"gc-level\" operator=\"equal\" value=\"all\"/>\n" +
"        </or>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"gc-enabled-all\" true=\"true\" false=\"false\">\n" +
"        <test name=\"gc-level\" operator=\"equal\" value=\"all\"/>\n" +
"      </condition>\n" +
"\n" +
"      <selection name=\"memory-profiling\" default=\"off\" label=\"Memory Profiling\">\n" +
"        <option label=\"Off\" name=\"off\">off</option>\n" +
"        <option label=\"Object Allocation and Promotion\" name=\"medium\">medium</option>\n" +
"        <option label=\"All, including Heap Statistics (May cause long full GCs)\" name=\"all\">all</option>\n" +
"      </selection>\n" +
"\n" +
"      <condition name=\"memory-profiling-enabled-medium\" true=\"true\" false=\"false\">\n" +
"        <or>\n" +
"          <test name=\"memory-profiling\" operator=\"equal\" value=\"medium\"/>\n" +
"          <test name=\"memory-profiling\" operator=\"equal\" value=\"all\"/>\n" +
"        </or>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"memory-profiling-enabled-all\" true=\"true\" false=\"false\">\n" +
"        <test name=\"memory-profiling\" operator=\"equal\" value=\"all\"/>\n" +
"      </condition>\n" +
"\n" +
"      <selection name=\"compiler-level\" default=\"normal\" label=\"Compiler\">\n" +
"        <option label=\"Off\" name=\"off\">off</option>\n" +
"        <option label=\"Normal\" name=\"normal\">normal</option>\n" +
"        <option label=\"Detailed\" name=\"detailed\">detailed</option>\n" +
"        <option label=\"All\" name=\"all\">all</option>\n" +
"      </selection>\n" +
"\n" +
"      <condition name=\"compiler-enabled\" true=\"false\" false=\"true\">\n" +
"        <test name=\"compiler-level\" operator=\"equal\" value=\"off\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"compiler-enabled-failure\" true=\"true\" false=\"false\">\n" +
"        <or>\n" +
"          <test name=\"compiler-level\" operator=\"equal\" value=\"detailed\"/>\n" +
"          <test name=\"compiler-level\" operator=\"equal\" value=\"all\"/>\n" +
"        </or>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"compiler-sweeper-threshold\" true=\"0 ms\" false=\"100 ms\">\n" +
"        <test name=\"compiler-level\" operator=\"equal\" value=\"all\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"compiler-compilation-threshold\" true=\"1000 ms\">\n" +
"        <test name=\"compiler-level\" operator=\"equal\" value=\"normal\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"compiler-compilation-threshold\" true=\"100 ms\">\n" +
"        <test name=\"compiler-level\" operator=\"equal\" value=\"detailed\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"compiler-compilation-threshold\" true=\"0 ms\">\n" +
"        <test name=\"compiler-level\" operator=\"equal\" value=\"all\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"compiler-phase-threshold\" true=\"60 s\">\n" +
"        <test name=\"compiler-level\" operator=\"equal\" value=\"normal\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"compiler-phase-threshold\" true=\"10 s\">\n" +
"        <test name=\"compiler-level\" operator=\"equal\" value=\"detailed\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"compiler-phase-threshold\" true=\"0 s\">\n" +
"        <test name=\"compiler-level\" operator=\"equal\" value=\"all\"/>\n" +
"      </condition>\n" +
"\n" +
"      <selection name=\"method-sampling-interval\" default=\"normal\" label=\"Method Sampling\">\n" +
"        <option label=\"Off\" name=\"off\">off</option>\n" +
"        <option label=\"Normal\" name=\"normal\">normal</option>\n" +
"        <option label=\"High\" name=\"high\">high</option>\n" +
"        <option label=\"Ludicrous (High Overhead)\" name=\"ludicrous\">ludicrous</option>\n" +
"      </selection>\n" +
"      \n" +
"      <condition name=\"method-sampling-java-interval\" true=\"999 d\">\n" +
"        <test name=\"method-sampling-interval\" operator=\"equal\" value=\"off\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"method-sampling-java-interval\" true=\"20 ms\">\n" +
"        <test name=\"method-sampling-interval\" operator=\"equal\" value=\"normal\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"method-sampling-java-interval\" true=\"10 ms\">\n" +
"        <test name=\"method-sampling-interval\" operator=\"equal\" value=\"high\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"method-sampling-java-interval\" true=\"1 ms\">\n" +
"        <test name=\"method-sampling-interval\" operator=\"equal\" value=\"ludicrous\"/>\n" +
"      </condition>\n" +
"      \n" +
"      <condition name=\"method-sampling-native-interval\" true=\"999 d\">\n" +
"        <test name=\"method-sampling-interval\" operator=\"equal\" value=\"off\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"method-sampling-native-interval\" true=\"20 ms\">\n" +
"        <or>\n" +
"          <test name=\"method-sampling-interval\" operator=\"equal\" value=\"normal\"/>\n" +
"          <test name=\"method-sampling-interval\" operator=\"equal\" value=\"high\"/>\n" +
"          <test name=\"method-sampling-interval\" operator=\"equal\" value=\"ludicrous\"/>\n" +
"        </or>\n" +
"      </condition>  \n" +
"\n" +
"      <condition name=\"method-sampling-enabled\" true=\"false\" false=\"true\">\n" +
"        <test name=\"method-sampling-interval\" operator=\"equal\" value=\"off\"/>\n" +
"      </condition>\n" +
"\n" +
"      <selection name=\"thread-dump-interval\" default=\"normal\" label=\"Thread Dump\">\n" +
"        <option label=\"Off\" name=\"off\">999 d</option>\n" +
"        <option label=\"At least Once\" name=\"normal\">everyChunk</option>\n" +
"        <option label=\"Every 60 s\" name=\"everyMinute\">60 s</option>\n" +
"        <option label=\"Every 10 s\" name=\"everyTenSecond\">10 s</option>\n" +
"        <option label=\"Every 1 s\" name=\"everySecond\">1 s</option>\n" +
"      </selection>\n" +
"\n" +
"      <condition name=\"thread-dump-enabled\" true=\"false\" false=\"true\">\n" +
"        <test name=\"thread-dump-interval\" operator=\"equal\" value=\"999 d\"/>\n" +
"      </condition>\n" +
"\n" +
"      <selection name=\"exception-level\" default=\"errors\" label=\"Exceptions\">\n" +
"        <option label=\"Off\" name=\"off\">off</option>\n" +
"        <option label=\"Errors Only\" name=\"errors\">errors</option>\n" +
"        <option label=\"All Exceptions, including Errors\" name=\"all\">all</option>\n" +
"      </selection>\n" +
"\n" +
"      <condition name=\"enable-errors\" true=\"true\" false=\"false\">\n" +
"        <or>\n" +
"          <test name=\"exception-level\" operator=\"equal\" value=\"errors\"/>\n" +
"          <test name=\"exception-level\" operator=\"equal\" value=\"all\"/>\n" +
"        </or>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"enable-exceptions\" true=\"true\" false=\"false\">\n" +
"        <test name=\"exception-level\" operator=\"equal\" value=\"all\"/>\n" +
"      </condition>\n" +
"\n" +
"      <selection name=\"memory-leak-detection\" default=\"minimal\" label=\"Memory Leak Detection\">\n" +
"        <option label=\"Off\" name=\"off\">off</option>\n" +
"        <option label=\"Object Types\" name=\"minimal\">minimal</option>\n" +
"        <option label=\"Object Types + Allocation Stack Traces\" name=\"medium\">medium</option>\n" +
"        <option label=\"Object Types + Allocation Stack Traces + Path to GC Root\" name=\"full\">full</option>\n" +
"      </selection>\n" +
"\n" +
"      <condition name=\"memory-leak-detection-enabled\" true=\"false\" false=\"true\">\n" +
"        <test name=\"memory-leak-detection\" operator=\"equal\" value=\"off\"/>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"memory-leak-detection-stack-trace\" true=\"true\" false=\"false\">\n" +
"        <or>\n" +
"          <test name=\"memory-leak-detection\" operator=\"equal\" value=\"medium\"/>\n" +
"          <test name=\"memory-leak-detection\" operator=\"equal\" value=\"full\"/>\n" +
"        </or>\n" +
"      </condition>\n" +
"\n" +
"      <condition name=\"memory-leak-detection-cutoff\" true=\"1 h\" false=\"0 ns\">\n" +
"        <test name=\"memory-leak-detection\" operator=\"equal\" value=\"full\"/>\n" +
"      </condition>\n" +
"\n" +
"      <text name=\"synchronization-threshold\" label=\"Synchronization Threshold\" contentType=\"timespan\" minimum=\"0 s\">20 ms</text>\n" +
"\n" +
"      <text name=\"file-io-threshold\" label=\"File I/O Threshold\" contentType=\"timespan\" minimum=\"0 s\">20 ms</text>\n" +
"\n" +
"      <text name=\"socket-io-threshold\" label=\"Socket I/O Threshold\" contentType=\"timespan\" minimum=\"0 s\">20 ms</text>\n" +
"\n" +
"      <flag name=\"class-loading-enabled\" label=\"Class Loading\">false</flag>\n" +
"\n" +
"    </control>\n" +
"\n" +
"</configuration>\n" +
"\n";
}
