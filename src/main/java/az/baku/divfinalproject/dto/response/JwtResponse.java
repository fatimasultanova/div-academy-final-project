package az.baku.divfinalproject.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String phoneNumber;
  private String username;
  private String email;
  private List<String> roles;

  public JwtResponse(String token, Long id,String phoneNumber, String username, String email, List<String> roles) {
    this.token = token;
    this.id = id;
    this.phoneNumber = phoneNumber;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }
}
