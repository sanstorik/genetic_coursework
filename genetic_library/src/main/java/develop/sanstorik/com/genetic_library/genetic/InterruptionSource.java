package develop.sanstorik.com.genetic_library.genetic;

public class InterruptionSource {
    private static final double ACCURACY = 6.1e-5;
    private static final int MAX_ITERATION = 1000;

    private InterruptionSource(){}

    @FunctionalInterface public interface Interruptible{
        boolean ended(int iteration, double funcValue);
    }

    public static Interruptible createIterationSource(final int maxIteration){
        return (iteration, funcVal)-> iteration > maxIteration || iteration > MAX_ITERATION;
    }

    public static Interruptible createFunctionMaxValueSource(final double funcValue){
        return (iteration, funcVal) ->  funcVal >= funcValue || iteration > MAX_ITERATION;
    }

    public static Interruptible createFunctionMinValueSource(final double funcValue){
        return (iteration, funcVal) -> funcVal <= funcValue || iteration > MAX_ITERATION;
    }
}