package com.tripreader.member.controller;

import com.tripreader.common.util.JWTUtil;
import com.tripreader.member.model.dto.MemberDto;
import com.tripreader.member.model.service.MemberService;
import com.tripreader.news.model.dto.NewsSummaryDto;
import com.tripreader.news.model.dto.NewsSummaryPreviewResponseDto;
import com.tripreader.news.model.service.NewsService;
import com.tripreader.news.model.service.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
@Tag(name = "회원 인증 컨트롤러", description = "로그인 로그아웃, 토큰처리등 회원의 인증관련 처리하는 클래스.")
public class MemberController {
    private final ScrapService scrapService;
    private JWTUtil jwtUtil = new JWTUtil();
    private MemberService memberService;
    private NewsService newsService;

    public MemberController(JWTUtil jwtUtil, MemberService memberService, NewsService newsService, ScrapService scrapService) {
        this.jwtUtil = jwtUtil;
        this.memberService = memberService;
        this.newsService = newsService;
        this.scrapService = scrapService;
    }

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Parameter(description = "회원가입시 필요 정보", required = true) Map<String,Object> params) {
        MemberDto memberDto = new MemberDto();

        // 필수 정보 체크
        if(!haveAllParams(params,"id","password","name","birth"))
            return ResponseEntity.badRequest().body("회원 가입시 필요한 정보가 모두 기입돼야 합니다.");

        memberDto.setId(params.get("id").toString());
        memberDto.setPassword(params.get("password").toString());
        memberDto.setName(params.get("name").toString());
        memberDto.setBirth(params.get("birth").toString());
        memberDto.setEmail(params.get("email").toString());
        
        // 선택 정보 입력
        if(params.containsKey("area_code"))
            memberDto.setAreaCode(Integer.parseInt(params.get("area_code").toString()));

        if(params.containsKey("sigungu_code"))
            memberDto.setSigunguCode(Integer.parseInt(params.get("sigungu_code").toString()));

        if(params.containsKey("mailing"))
            memberDto.setMailing(Integer.parseInt(params.get("mailing").toString()));

        try {
            memberService.join(memberDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "아이디 중복검사", description = "아이디를 중복검사합니다.")
    @GetMapping("/id_validation/{id}")
    public ResponseEntity<?>  idValidation(@PathVariable(name = "id", required = true) @Parameter(description = "검사할 id", required = true) String id) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            if (memberService.idValidation(id) == 0)
                return ResponseEntity.ok().body(true);
            else
                return ResponseEntity.ok().body(false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "회원조회", description = "accessToken을 활용한 회원정보 조회")
    @GetMapping("/info")
    public ResponseEntity<?> getInfo(@RequestHeader("access_token") String header) {
        try {
            if (isAccessToken(header)) {
                String id = jwtUtil.getMemberId(header);
                return ResponseEntity.ok().body(memberService.getMemberInfo(id));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "회원정보 수정", description = "accessToken의 회원정보를 수정")
    @PutMapping("/info")
    public ResponseEntity<?> updateInfo(@RequestHeader("access_token") String header, @RequestBody @Parameter(description = "회원정보 수정시 정보", required = true) Map<String,Object> params) {
        log.info(params.toString());
        if(!haveAllParams(params,"id","password","name","birth", "email","mailing"))
            return ResponseEntity.badRequest().build();

        MemberDto memberDto = new MemberDto();
        memberDto.setId(params.get("id").toString());
        memberDto.setPassword(params.get("password").toString());
        memberDto.setName(params.get("name").toString());
        memberDto.setBirth(params.get("birth").toString());
        memberDto.setEmail(params.get("email").toString());
        memberDto.setMailing(Integer.parseInt(params.get("mailing").toString()));
        if(params.containsKey("area_code") && params.get("area_code") != null) {
            memberDto.setAreaCode(Integer.parseInt(params.get("area_code").toString()));
            if(params.containsKey("sigungu_code") && params.get("sigungu_code") != null && !params.get("sigungu_code").toString().equals("null"))
                memberDto.setSigunguCode(Integer.parseInt(params.get("sigungu_code").toString()));
        }

        try {
            MemberDto chkMember = memberService.getMemberById(memberDto.getId());

            if (isAccessToken(header) && chkMember.getId().equals(memberDto.getId())) {
                log.info("updateInfo");
                memberService.update(memberDto);
                log.info("updateInfo success"+memberDto.toString());
                return ResponseEntity.ok().build();
            }
            else {
                log.info("updateInfo Unauthorized");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/find_password")
    public ResponseEntity<?> findPassword(@RequestBody Map<String,String> map) {
        if(!haveAllParams(map,"id","email"))
            return ResponseEntity.badRequest().build();

        String id = map.get("id");
        String email = map.get("email");

        try {
            if (memberService.findPassword(id, email))
                return ResponseEntity.ok().build();
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "회원탈퇴", description = "accessToken을 활용한 회원탈퇴")
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteMember(@RequestHeader("access_token") String header) {
        try {
            if (isAccessToken(header))
                memberService.delete(jwtUtil.getMemberId(header));
             else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그인", description = "아이디와 비밀번호를 이용하여 로그인 처리.")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Parameter(description = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) Map<String,String> params) {
        if(!haveAllParams(params,"id","password"))
            return ResponseEntity.badRequest().body("아이디 혹은 비밀번호를 전달받지 못했습니다");

        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            MemberDto memberDto = new MemberDto();
            memberDto.setId(params.get("id"));
            memberDto.setPassword(params.get("password"));

            memberDto = memberService.login(memberDto);
            log.info(memberDto.toString());
            if (memberDto != null) {
                String accessToken = jwtUtil.createAccessToken(memberDto.getId());
                String refreshToken = jwtUtil.createRefreshToken(memberDto.getId());
                resultMap.put("name", memberDto.getName());
//				JSON 으로 token 전달.
                resultMap.put("access_token", accessToken);
                resultMap.put("refresh_token", refreshToken);
                log.info("access_token : " + accessToken);
                log.info("refresh_token : " + refreshToken);
                return ResponseEntity.ok().body(resultMap);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "로그아웃", description = "accessKey, refreshKey의 수명을 0 으로 하여 로그아웃")
    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("access_token") String header) {
        try {
            if (isAccessToken(header)) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                String id = jwtUtil.getMemberId(header);
                String accessToken = jwtUtil.expireAccessToken(id);
                String refreshToken = jwtUtil.expireRefreshToken(id);

//				JSON 으로 token 전달.
                resultMap.put("access_token", accessToken);
                resultMap.put("refres_token", refreshToken);

                return ResponseEntity.ok().body(resultMap);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "token 새로고침", description = "refreshToeken으로 access-Token과 refresh-Token 재발급")
    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestHeader("refresh_token") String header) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (isRefreshToken(header)) {
                String id = jwtUtil.getMemberId(header);
                String accessToken = jwtUtil.createAccessToken(id);
                String refreshToken = jwtUtil.createRefreshToken(id);

//				JSON 으로 token 전달.
                resultMap.put("access_token", accessToken);
                resultMap.put("refresh_token", refreshToken);

                return ResponseEntity.ok().body(resultMap);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "스크랩 목록 가져오기", description = "accessKey를 이용하여 유저의 스크랩 목록을 가져옵니다.")
    @GetMapping("/scrap")
    public ResponseEntity<?> getScrap(@RequestHeader("access_token") String header) {
        try {
            if (isAccessToken(header)) {
                String id = jwtUtil.getMemberId(header);
                MemberDto memberDto = memberService.getMemberById(id);
                List<NewsSummaryPreviewResponseDto> scrapList = scrapService.getScrap(memberDto.getMemberKey());

                return ResponseEntity.ok().body(scrapList);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/isScraped/{news_summary_nano}")
    public ResponseEntity<?> isScraped(@RequestHeader("access_token") String header, @PathVariable(name = "news_summary_nano", required = true)String newsSummaryNano) {
        try {
            if (isAccessToken(header)) {
                String id = jwtUtil.getMemberId(header);
                MemberDto memberDto = memberService.getMemberById(id);
                int newsSummaryKey = newsService.getNewsSummaryKeyByNewsSummaryNano(newsSummaryNano);
                boolean isScraped = scrapService.isScraped(memberDto.getMemberKey(), newsSummaryKey) != 0;

                return ResponseEntity.ok().body(isScraped);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }


    @Operation(summary = "스크랩 변경", description = "accessKey를 이용하여 유저의 스크랩을 변경합니다.")
    @PostMapping("/scrap")
    public ResponseEntity<?> insertScrap(
            @RequestHeader("access_token") String header, @RequestBody Map<String, Object> params) {

        Map<String, Object> resultMap = new HashMap<>();
        if(!haveAllParams(params,"news_summary_nano", "i_scrap_this")){
            return ResponseEntity.badRequest().build();
        }

        try {
            if (isAccessToken(header)) {
                String id = jwtUtil.getMemberId(header);
                MemberDto memberDto = memberService.getMemberById(id);
                int newsSummaryKey = newsService.getNewsSummaryKeyByNewsSummaryNano((String) params.get("news_summary_nano"));
                Map<String, Object> map = new HashMap<>();
                map.put("memberKey", memberDto.getMemberKey());
                map.put("newsSummaryKey", newsSummaryKey);
                map.put("i_scrap_this", params.get("i_scrap_this"));
                log.info(map.toString());

                if(scrapService.changeScrap(map)) {
                    resultMap.put("i_scrap_this", true);
                }else {
                    resultMap.put("i_scrap_this", false);
                }
                resultMap.put("scrap_count", newsService.getScrapCount(newsSummaryKey));

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(resultMap);
    }


    private boolean isRefreshToken(String token) {
        return jwtUtil.checkToken(token) && jwtUtil.isRefreshToken(token);
    }

    private boolean isAccessToken(String token) {
        return jwtUtil.checkToken(token) && !jwtUtil.isRefreshToken(token);
    }

    // 필요한 parameter들이 다 있다면 true
    private boolean haveAllParams(Map<String, ?>params, String... chkParam){
        for(String value : chkParam){
            if(!params.containsKey(value))
                return false;
        }
        return true;
    }
}
