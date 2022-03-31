# Java 18 Reflection Benchmarks
I was curious as to how much faster the reimplementation of core reflection in Java 18 
([JEP 416](https://openjdk.java.net/jeps/416)) was in relation to Java 17, so I made some benchmarks.
This was also my first time making benchmarks, so it was a fun project to learn how to use 
[JMH](https://github.com/openjdk/jmh).

## Java 17
```
Benchmark                                      Mode  Cnt   Score   Error  Units
ReflectionBenchmark._0_nativeNewInstance       avgt   25  13.644 ± 0.024  ns/op
ReflectionBenchmark._1_reflectionNewInstance   avgt   25  34.142 ± 0.124  ns/op

ReflectionBenchmark._2_nativeInvokeMethod      avgt   25   0.722 ± 0.008  ns/op
ReflectionBenchmark._3_reflectionInvokeMethod  avgt   25  13.639 ± 0.041  ns/op
ReflectionBenchmark._4_lambdaInvokeMethod      avgt   25   4.699 ± 0.047  ns/op

ReflectionBenchmark._5_nativeGetField          avgt   25   0.723 ± 0.049  ns/op
ReflectionBenchmark._6_reflectionGetField      avgt   25   4.254 ± 0.027  ns/op
```

## Java 18
```
Benchmark                                      Mode  Cnt   Score   Error  Units
ReflectionBenchmark._0_nativeNewInstance       avgt   25  13.734 ± 0.080  ns/op
ReflectionBenchmark._1_reflectionNewInstance   avgt   25  13.745 ± 0.101  ns/op

ReflectionBenchmark._2_nativeInvokeMethod      avgt   25   0.739 ± 0.046  ns/op
ReflectionBenchmark._3_reflectionInvokeMethod  avgt   25   1.857 ± 0.016  ns/op
ReflectionBenchmark._4_lambdaInvokeMethod      avgt   25   4.652 ± 0.137  ns/op

ReflectionBenchmark._5_nativeGetField          avgt   25   0.725 ± 0.023  ns/op
ReflectionBenchmark._6_reflectionGetField      avgt   25   1.833 ± 0.046  ns/op
```
