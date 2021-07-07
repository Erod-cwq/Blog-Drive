package com.example.jpa_learn.security.context;

import com.example.jpa_learn.security.context.Impl.SecurityContextImpl;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class SecurityContextHolder {
    private static final ThreadLocal<SecurityContext> CONTEXT_HOLDER = new ThreadLocal<>();
    private SecurityContextHolder(){}

    @NonNull
    public static SecurityContext getContext() {
        SecurityContext context = CONTEXT_HOLDER.get();
        if(context==null){
            context = createEmptyContext();
            CONTEXT_HOLDER.set(context);
        }
        return context;
    }

    public static void setContext(@Nullable SecurityContext context) {
        CONTEXT_HOLDER.set(context);
    }

    public static void  clearContext(){
        CONTEXT_HOLDER.remove();
    }

    @NonNull
    private static SecurityContext createEmptyContext() {
        return new SecurityContextImpl(null);
    }
}
