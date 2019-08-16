package com.wootube.ioi.web.controller;

import com.wootube.ioi.service.ReplyService;
import com.wootube.ioi.service.dto.ReplyRequestDto;
import com.wootube.ioi.service.dto.ReplyResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/watch")
@Controller
public class ReplyController {
    private ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @ResponseBody
    @PostMapping("/{videoId}/comments/{commentId}/replies")
    public ResponseEntity<ReplyResponseDto> createReply(@PathVariable Long videoId,
                                                        @PathVariable Long commentId,
                                                        @RequestBody ReplyRequestDto replyRequestDto) {
        //로그인 상태인가?
        //commentId가 존재하면 videoId랑 comment의 videoId가 같은가?
        return new ResponseEntity<>(replyService.save(replyRequestDto, commentId), HttpStatus.CREATED);
    }

    @ResponseBody
    @PutMapping("/{videoId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> updateReply(@PathVariable Long videoId,
                                            @PathVariable Long commentId,
                                            @PathVariable Long replyId,
                                            @RequestBody ReplyRequestDto replyRequestDto) {
        //로그인 상태인가?
        //commentId가 존재하면 videoId랑 Comment의 videoId가 같은가?
        replyService.update(replyRequestDto, commentId, replyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
