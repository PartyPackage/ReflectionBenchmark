package com.mcpartypack;

import com.github.hervian.lambdas.Lambda;
import com.github.hervian.lambdas.LambdaFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Threads(value = 8)
public class ReflectionBenchmark {

    public static final Class<?> CLASS = classForName(ExampleClass.class.getCanonicalName());
    public static final Constructor<?> CONSTRUCTOR = CLASS.getConstructors()[0];
    public static final Method SQUARED = getMethod(CLASS, "squared");
    public static final Field I_FIELD = getField(CLASS, "i");

    @State(Scope.Thread)
    public static class LambdaMethodState {

        ExampleClass object;
        Lambda squared;

        @Setup(Level.Iteration)
        public void setup() throws Throwable {
            object = new ExampleClass(10);
            squared = LambdaFactory.create(SQUARED);
        }

        @TearDown(Level.Iteration)
        public void teardown() {
            object = null;
            squared = null;
        }

    }

    @State(Scope.Thread)
    public static class MethodState {

        ExampleClass object;

        @Setup(Level.Iteration)
        public void setup() {
            object = new ExampleClass(10);
        }

        @TearDown(Level.Iteration)
        public void teardown() {
            object = null;
        }

    }

    @Benchmark
    public void _0_nativeNewInstance(Blackhole blackhole) {
        var v = new ExampleClass(10);
        blackhole.consume(v);
    }

    @Benchmark
    public void _1_reflectionNewInstance(Blackhole blackhole) throws Throwable {
        var v = CONSTRUCTOR.newInstance(10);
        blackhole.consume(v);
    }

    @Benchmark
    public void _2_nativeInvokeMethod(MethodState state, Blackhole blackhole) {
        var v = state.object.squared();
        blackhole.consume(v);
    }

    @Benchmark
    public void _3_reflectionInvokeMethod(MethodState state, Blackhole blackhole) throws Throwable {
        var v = SQUARED.invoke(state.object);
        blackhole.consume(v);
    }

    @Benchmark
    public void _4_lambdaInvokeMethod(LambdaMethodState state, Blackhole blackhole) {
        var v = state.squared.invoke_for_int(state.object);
        blackhole.consume(v);
    }

    @Benchmark
    public void _5_nativeGetField(MethodState state, Blackhole blackhole) {
        var v = state.object.i;
        blackhole.consume(v);
    }

    @Benchmark
    public void _6_reflectionGetField(MethodState state, Blackhole blackhole) throws Throwable {
        var v = I_FIELD.get(state.object);
        blackhole.consume(v);
    }

    @Nullable
    private static Class<?> classForName(@NotNull String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private static Method getMethod(@NotNull Class<?> clazz, @NotNull String name, @Nullable Class<?>... paramTypes) {
        try {
            return clazz.getMethod(name, paramTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private static Field getField(@NotNull Class<?> clazz, @NotNull String name) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

}
