package cf.strafe.kb.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {
    String[] names();

    String permission();

    boolean hidden() default false;

    boolean async() default false;

    String description() default "";
}
