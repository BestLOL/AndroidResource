package com.example.administrator.androidresources.EventBusPlugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//明确修饰类型
@Target(ElementType.METHOD)
//明确保留策略
@Retention(RetentionPolicy.RUNTIME)
public @interface SubScribe {
    ThreadModel thread() default ThreadModel.MAIN;
}
