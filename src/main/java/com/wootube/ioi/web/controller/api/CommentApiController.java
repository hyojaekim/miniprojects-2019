package com.wootube.ioi.web.controller.api;

import com.wootube.ioi.service.CommentLikeService;
import com.wootube.ioi.service.CommentService;
import com.wootube.ioi.service.dto.CommentLikeResponseDto;
import com.wootube.ioi.service.dto.CommentRequestDto;
import com.wootube.ioi.service.dto.CommentResponseDto;
import com.wootube.ioi.web.controller.exception.NotFoundSortDirectionException;
import com.wootube.ioi.web.controller.exception.NotFoundSortException;
import com.wootube.ioi.web.session.UserSession;
import com.wootube.ioi.web.session.UserSessionManager;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/videos/{videoId}/comments")
@RestController
public class CommentApiController {
    private static final String UPDATE_TIME = "update-time";
    private static final String LIKE_COUNT = "like-count";
    private static final String DESCENDING = "desc";
    private static final String ASCENDING = "asc";

    private static final Sort DESC_SORT_BY_UPDATE_TIME = new Sort(Sort.Direction.DESC, "updateTime");
    private static final Sort ASC_SORT_BY_UPDATE_TIME = new Sort(Sort.Direction.ASC, "updateTime");

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final UserSessionManager userSessionManager;

    public CommentApiController(CommentService commentService, CommentLikeService commentLikeService, UserSessionManager userSessionManager) {
        this.commentService = commentService;
        this.commentLikeService = commentLikeService;
        this.userSessionManager = userSessionManager;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> sortComment(@PathVariable Long videoId,
                                                                @RequestParam(value = "sort") String sort,
                                                                @RequestParam(value = "order") String order) {
        List<CommentResponseDto> comments = findSort(videoId, sort, order);

        saveCommentLikeByUserSession(comments);
        return ResponseEntity.ok(comments);
    }

    private List<CommentResponseDto> findSort(Long videoId, String sortName, String orderType) {
        if (sortName.equals(LIKE_COUNT)) {
            return commentLikeService.sortCommentByLikeCount(videoId);
        }

        if (sortName.equals(UPDATE_TIME)) {
            return commentService.sortComment(findSortByUpdateTime(orderType), videoId);
        }

        throw new NotFoundSortException();
    }

    private Sort findSortByUpdateTime(String oderType) {
        if (oderType.equals(ASCENDING)) {
            return ASC_SORT_BY_UPDATE_TIME;
        }

        if (oderType.equals(DESCENDING)) {
            return DESC_SORT_BY_UPDATE_TIME;
        }

        throw new NotFoundSortDirectionException();
    }

    private void saveCommentLikeByUserSession(List<CommentResponseDto> comments) {
        if (userSessionManager.getUserSession() != null) {
            commentLikeService.saveCommentLike(comments, userSessionManager.getUserSession().getId());
            return;
        }
        commentLikeService.saveCommentLike(comments);
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long videoId,
                                                            @RequestBody CommentRequestDto commentRequestDto) {
        UserSession userSession = userSessionManager.getUserSession();
        CommentResponseDto commentResponseDto = commentService.save(commentRequestDto, videoId, userSession.getEmail());

        commentLikeService.saveCommentLike(commentResponseDto);

        return ResponseEntity.created(URI.create("/api/videos/" + videoId + "/comments/" + commentResponseDto.getId()))
                .body(commentResponseDto);
    }

    @PostMapping("/{commentId}/likes")
    public ResponseEntity<CommentLikeResponseDto> like(@PathVariable Long videoId,
                                                       @PathVariable Long commentId) {
        UserSession userSession = userSessionManager.getUserSession();
        CommentLikeResponseDto commentLikeResponseDto = commentLikeService.likeComment(userSession.getId(), videoId, commentId);

        return ResponseEntity.created(URI.create("/api/videos/" + videoId + "/comments/" + commentId))
                .body(commentLikeResponseDto);
    }

    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<CommentLikeResponseDto> dislike(@PathVariable Long videoId,
                                                          @PathVariable Long commentId) {
        UserSession userSession = userSessionManager.getUserSession();
        CommentLikeResponseDto commentLikeResponseDto = commentLikeService.dislikeComment(userSession.getId(), commentId, videoId);

        return ResponseEntity.created(URI.create("/api/videos/" + videoId + "/comments/" + commentId))
                .body(commentLikeResponseDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long videoId,
                                        @PathVariable Long commentId,
                                        @RequestBody CommentRequestDto commentRequestDto) {
        UserSession userSession = userSessionManager.getUserSession();
        commentService.update(commentId, userSession.getEmail(), videoId, commentRequestDto);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long videoId, @PathVariable Long commentId) {
        UserSession userSession = userSessionManager.getUserSession();
        commentService.delete(commentId, userSession.getEmail(), videoId);
        return ResponseEntity.noContent()
                .build();
    }
}
