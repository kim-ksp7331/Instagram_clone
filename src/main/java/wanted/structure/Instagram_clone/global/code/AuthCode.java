package wanted.structure.Instagram_clone.global.code;

import lombok.Getter;

@Getter
public enum AuthCode {

    ADMIN("001", "관리자"),
    USER("002", "회원");

    private final String code;
    private final String name;

    AuthCode(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
