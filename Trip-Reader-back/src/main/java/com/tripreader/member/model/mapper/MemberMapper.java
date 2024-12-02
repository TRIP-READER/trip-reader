package com.tripreader.member.model.mapper;

import com.tripreader.member.model.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void join(MemberDto memberDto);
    void update(MemberDto memberDto);
    void delete(String id);
    int idValidation(String id);
    MemberDto login(MemberDto memberDto);
    MemberDto getMemberById(String id);
    MemberDto getMemberInfo(String id);
//    List<NewsSummaryDto> getScrap(MemberDto memberDto);
//    void deleteScrap()

}
