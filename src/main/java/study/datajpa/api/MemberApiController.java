package study.datajpa.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;
import study.datajpa.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member){
        Long save = memberService.join(member);
        return new CreateMemberResponse(save);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest request){
        Member member = new Member();
        member.setUsername(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Validated
            UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member one = memberService.findOne(id);
        return new UpdateMemberResponse(one.getId(), one.getUsername());
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest{
        private String name;

    }

    @Data
    static class CreateMemberRequest{
        String name;

        public CreateMemberRequest(String name) {
            this.name = name;
        }
    }

    @Data
    static class CreateMemberResponse{
        Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
