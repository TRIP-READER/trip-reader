package com.tripreader.member.model.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.tripreader.common.util.Email.EmailMessage;
import com.tripreader.common.util.Email.EmailService;
import com.tripreader.common.util.EncryptUtil;
import com.tripreader.member.model.dto.MemberDto;
import com.tripreader.member.model.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {
    MemberMapper memberMapper;
    EmailService emailService;

    public MemberService(MemberMapper memberMapper, EmailService emailService) {
        this.memberMapper = memberMapper;
        this.emailService = emailService;
    }
    public void join(MemberDto memberDto){
        setPasswordEncrypt(memberDto);
        this.memberMapper.join(memberDto);
    }
    public void update(MemberDto memberDto){
        setPasswordEncrypt(memberDto);
        this.memberMapper.update(memberDto);
    }
    public void delete(String id){
        this.memberMapper.delete(id);
    }
    public int idValidation(String id){
        return this.memberMapper.idValidation(id);
    }

    public MemberDto login(MemberDto memberDto){
        log.info("login : " + memberDto.toString());
        setPasswordEncrypt(memberDto);
        log.info("login : " + memberDto.toString());
        return memberMapper.login(memberDto);
    }

    public boolean findPassword(String id, String email){
        MemberDto memberDto = getMemberById(id);
        //메일과 아이디로 일단 유저를 조회함
        if(memberDto == null || !memberDto.getEmail().equals(email))
            return false;

        String tmpPassword = NanoIdUtils.randomNanoId(); //임시 비번 생성
        memberDto.setPassword(tmpPassword); // 비번 변경
        update(memberDto); // 멤버 비번 암호화 업데이트

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(memberDto.getEmail());
        emailMessage.setSubject("TripReader 임시 비밀번호 발급");
        emailMessage.setMessage("임시 비밀번호는 [  " + tmpPassword + "  ] 입니다.");
        log.info("tmpPassword : " + tmpPassword);
        log.info("memberDto : " + memberDto.toString());

        emailService.sendMail(emailMessage);
        return true;
    }

    public MemberDto getMemberById(String id){
        return this.memberMapper.getMemberById(id);
    }

    public MemberDto getMemberInfo(String id){
        return this.memberMapper.getMemberInfo(id);
    }
//    List<NewsSummaryDto> getScrap(MemberDto memberDto);
//    void deleteScrap()

    private void setPasswordEncrypt(MemberDto memberDto){
        memberDto.setPassword(EncryptUtil.getEncrypt(memberDto.getPassword()));
    }
}
