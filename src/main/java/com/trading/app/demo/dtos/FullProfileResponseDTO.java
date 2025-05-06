package com.trading.app.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullProfileResponseDTO {
      private String email;
      private String firstName;
      private String lastName;
      private String address;
      private String phoneNumber;
}
