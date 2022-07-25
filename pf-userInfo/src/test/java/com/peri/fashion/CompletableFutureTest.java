package com.peri.fashion;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
         * 实例化方式
         * 有两种格式，一种是supply开头的方法，一种是run开头的方法
         * supply开头：这种方法，可以返回异步线程执行之后的结果
         * run开头：这种不会返回结果，就只是执行线程任务
         * 或者可以通过一个简单的无参构造器
         * CompletableFuture<T> completableFuture = new CompletableFuture<T>();
         * 如果没有实现方法就直接get 主线程会一直处于阻塞状态
         */
        CompletableFuture<List<String>> completableFuture1 = CompletableFuture.supplyAsync(ArrayList::new);
        /*
         * 计算完成后续操作1——complete
         * public CompletableFuture<T>     whenComplete(BiConsumer<? super T,? super Throwable> action)
         * public CompletableFuture<T>     whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
         * public CompletableFuture<T>     whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
         * public CompletableFuture<T>     exceptionally(Function<Throwable,? extends T> fn)
         * 方法1和2的区别在于是否使用异步处理
         * 2和3的区别在于是否使用自定义的线程池
         * 前三个方法都会提供一个返回结果和可抛出异常，我们可以使用lambda表达式的来接收这两个参数，然后自己处理
         * 方法4，接收一个可抛出的异常，且必须return一个返回值，类型与钻石表达式种的类型一样，详见下文的exceptionally() 部分，更详细
         */
        completableFuture1.whenComplete((result, throwable) -> {
            result.add("completableFuture - one");
        });
        System.out.println(JSONObject.toJSONString(completableFuture1.get()));
        completableFuture1.thenApply(result -> {
            result.add("completableFuture - two");
            return result;
        });
        System.out.println(JSONObject.toJSONString(completableFuture1.get()));
        /*
         * 计算完成后续操作2——handle
         * public <U> CompletableFuture<U>     handle(BiFunction<? super T,Throwable,? extends U> fn)
         * public <U> CompletableFuture<U>     handleAsync(BiFunction<? super T,Throwable,? extends U> fn)
         * public <U> CompletableFuture<U>     handleAsync(BiFunction<? super T,Throwable,? extends U> fn, Executor executor)
         * handle方法集和上面的complete方法集没有区别，同样有两个参数一个返回结果和可抛出异常，区别就在于返回值，
         * 虽然同样返回CompletableFuture类型，但是里面的参数类型，handle方法是可以自定义的
         */
        CompletableFuture<List<Integer>> completableFuture2 = completableFuture1.handle((result, throwable) -> {
            List<Integer> newResult = new ArrayList<>();
            if (result.size() > 0) {
                newResult.add(result.size());
                return newResult;
            }
            newResult.add(-1);
            return newResult;
        });
        System.out.println(completableFuture2.get());
        /*
         * 计算完成的后续操作3——apply
         * public <U> CompletableFuture<U>  thenApply(Function<? super T,? extends U> fn)
         * public <U> CompletableFuture<U>  thenApplyAsync(Function<? super T,? extends U> fn)
         * public <U> CompletableFuture<U>  thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
         * apply方法和handle方法一样，都是结束计算之后的后续操作
         * 唯一的不同是，handle方法会给出异常，可以让用户自己在内部处理
         * 而apply方法只有一个返回结果，如果异常了，会被直接抛出，交给上一层处理
         * 如果不想每个链式调用都处理异常，那么就使用apply
         *
         * 计算完成的后续操作4——accept
         * public CompletableFuture<Void>  thenAccept(Consumer<? super T> action)
         * public CompletableFuture<Void>  thenAcceptAsync(Consumer<? super T> action)
         * public CompletableFuture<Void>  thenAcceptAsync(Consumer<? super T> action, Executor executor)
         * accept()三个方法只做最终结果的消费
         * 注意此时返回的CompletableFuture是空返回
         * 只消费，无返回，有点像流式编程的终端操作
         */
        CompletableFuture<List<String>> completableFuture3 = completableFuture2.handle((result, throwable) -> {
            List<String> newResult = new ArrayList<>();
            newResult.add("completableFuture - one");
            return newResult;
        });
        System.out.println(completableFuture3.get());
        completableFuture3.thenAccept(result -> {
            result.add("completableFuture - two");
            // 这里取一个不存在的值 然后将异常在后面打印
            // 不管这个异常发生在何处 只要打印了 然后后续有操作 异常会在最后打印出来
            result.get(3);
        }).exceptionally(error -> {
            error.printStackTrace();
            return null;
        });
        System.out.println(completableFuture3.get());
        completableFuture3.thenAccept(result -> {
            result.add("completableFuture - three");
            System.out.println(result);
        });
        completableFuture3.thenAccept(result -> {
            result.get(3);
            // 发送异常 这里不会执行
            result.add("completableFuture - four");
            System.out.println(result);
        }).exceptionally(error -> {
            error.printStackTrace();
            return null;
        });
        completableFuture3.thenAccept(result -> {
            result.add("completableFuture - five");
            System.out.println(result);
        });
        /*
         * 同步获取结果
         * public T    get()
         * public T    get(long timeout, TimeUnit unit)
         * public T    getNow(T valueIfAbsent)
         * public T    join()
         * 获取所有完成结果——allOf 当所有给定的任务完成后，返回一个全新的已完成CompletableFuture
         * 获取率先完成的任务结果——anyOf 仅等待Future集合种最快结束的任务完成（有可能因为他们试图通过不同的方式计算同一个值），并返回它的结果
         */
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(completableFuture1, completableFuture3);
        System.out.println("End:" + objectCompletableFuture.get());

        CompletableFuture.supplyAsync(() -> 1)
                .whenComplete((result, error) -> {
                    System.out.println(result);
                })
                .handle((result, error) -> {
                    result++;
                    System.out.println(result);
                    return result;
                })
                .thenApply(Object::toString)
                .thenApply(Integer::valueOf)
                .thenAccept((param) -> System.out.println("done-" + param));
    }
}
