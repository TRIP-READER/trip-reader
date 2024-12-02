package com.tripreader.member.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberDto {
    int memberKey;
    String id;
    String password;
    String name;
    String email;
    int areaCode;
    int sigunguCode;
    String birth;
    String authority;
    int enabled;
    int mailing;
}
