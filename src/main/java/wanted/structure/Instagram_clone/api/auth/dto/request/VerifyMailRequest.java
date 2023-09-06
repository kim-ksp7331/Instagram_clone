package wanted.structure.Instagram_clone.api.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyMailRequest {

    private String email;
    private String authNum;
}
