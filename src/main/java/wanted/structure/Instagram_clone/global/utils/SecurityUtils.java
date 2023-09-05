package wanted.structure.Instagram_clone.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {

    public static String getUserId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}