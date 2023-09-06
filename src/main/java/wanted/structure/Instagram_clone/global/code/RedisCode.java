package wanted.structure.Instagram_clone.global.code;

import lombok.Getter;

@Getter
public enum RedisCode {
    AUTH_NUM("001","이메일 인증 코드");
    private final String code;
    private final String name;

    RedisCode(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
