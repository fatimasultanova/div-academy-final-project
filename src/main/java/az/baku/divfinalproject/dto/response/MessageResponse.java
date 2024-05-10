package az.baku.divfinalproject.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class MessageResponse {
  private String message;

  public MessageResponse(String message) {
    this.message = message;
  }

}
