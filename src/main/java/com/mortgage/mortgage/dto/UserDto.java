package com.mortgage.mortgage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;
  
    private Integer user_id;
    private String name;
   // @ApiModelProperty
    private String email;
    private Double amount;
    private Double accountNo;

    private String password;
   // @ApiModelProperty
    private String role;


}
