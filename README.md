### springboot-jwt-example
   
-  JWT验证忽略注解
```java
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface PassToken {
        boolean required() default true;
    }
```

-  需要token 验证的注解
```java
   @Target({ElementType.METHOD, ElementType.TYPE})
   @Retention(RetentionPolicy.RUNTIME)
   public @interface UserLoginToken {
       boolean required() default true;
   }
```

-  无需要token访问接口
```java
    @PassToken
    @GetMapping("/withNotToken")
    public Result withNotToken() {
        log.info("无需要token访问接口");
        return Result.SUCCESS();
    }
```