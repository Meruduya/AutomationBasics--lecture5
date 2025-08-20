package dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class UserInfo {
    private final String city;
    private final String name;
    private final String phone;
}