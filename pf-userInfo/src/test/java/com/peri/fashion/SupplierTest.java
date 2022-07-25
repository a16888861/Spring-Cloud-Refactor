package com.peri.fashion;

import java.util.function.Supplier;

public class SupplierTest {

    public static void main(String[] args) {
        String name1 = "张三", name2 = "李四";
        String name = method1(name1, () -> {
            // 这里因为method1有3次判断 会回到这里3次 所以会输出3次
            System.out.println("main - " + name2);
            // 这里的return 相当于是一个重试机制 只要里面有几次if判断 符合条件不继续 不符合继续判断 这里就会重试几次
            return name2;
        });
        System.out.println("result - " + name);
    }

    public static String method1(String name, Supplier<String> supplier) {
        System.out.println("method1 - " + name);
        if (supplier.get().contains("赵")) {
            System.out.println("judge1 - " + name);
            return "王五";
        } else if (supplier.get().contains("钱")) {
            System.out.println("judge2 - " + name);
            return "赵六";
        } else {
            System.out.println("judge3 - " + name);
            return supplier.get().replace("李", "赵");
        }
    }

}
