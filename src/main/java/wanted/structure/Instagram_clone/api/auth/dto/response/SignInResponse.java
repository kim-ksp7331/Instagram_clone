package wanted.structure.Instagram_clone.api.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignInResponse {

    private String accessToken;

    private String refreshToken;
}
