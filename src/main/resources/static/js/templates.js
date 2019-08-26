const Templates = {
    commentTemplate : (comment, writtenTime) => {return `<li class="comment mrg-btm-30" data-commentid="${comment.id}">
                <img class="img-circle width-50 comment-writer-img" src="/images/default/eastjun_big.jpg" alt="">
                <div class="comment-block">
                    <div class="font-size-13">
                        <span class="user-name">${comment.writer.name}</span>
                        <span class="update-date">${writtenTime}</span>
                    </div>
                    <div class="comment-more-box dropdown">
                        <button class="comment-more-button dropdown-toggle" data-toggle="dropdown">
                            <i class="ti-more-alt"></i>
                        </button>
                    
                        <div class="dropdown-menu" role="menu">
                            <button class="list-group-item list-group-item-action comment-edit-button">
                                <i class="fa fa-pencil"></i>
                                <span>수정</span>
                            </button>
                            <button class="list-group-item list-group-item-action comment-delete-button">
                                <i class="fa fa-trash"></i>
                                <span>삭제</span>
                            </button>
                        </div>
                    </div>
                    <span class="comment-contents font-size-15">${comment.contents}</span>
                    <div>
                        <button class="like-btn">
                            <i class="ti-thumb-up"></i>
                        </button>
                        <span>3.5천</span>
                        <button class="reply-toggle-btn">답글</button>
                    </div>
                </div>
                <div class="comment-update-area display-none mrg-btm-50">
                    <div>
                        <img class="img-circle width-50 comment-writer-img" src="/images/default/eastjun_big.jpg"
                             alt="">
                        <input class="comment-input" type="text" value="${comment.contents}">
                    </div>
                    <button class="btn comment-btn comment-update-cancel-btn">취소</button>
                    <button class="btn comment-btn edit comment-update-btn">수정</button>
                </div>
                <div class="mrg-top-5 reply-area">
                    <div class="reply-edit display-none">
                        <div class="mrg-btm-10">
                            <img class="img-circle width-50 comment-writer-img" src="/images/default/eastjun_big.jpg"
                                 alt="">
                            <input class="comment-input" type="text" placeholder="공개 답글 추가...">
                        </div>
                        <button class="btn comment-btn edit reply-save-btn disabled">답글</button>
                        <button class="btn comment-btn reply-cancel-btn">취소</button>
                    </div>
                    <ul class="reply-list">

                    </ul>
                </div>
            </li>`
    },
    replyTemplate : (reply, writtenTime) => { return `<li class="reply mrg-btm-30" data-commentid="${reply.id}">
                            <img class="img-circle width-50 comment-writer-img" src="/images/default/eastjun_big.jpg" alt="">
                            <div class="comment-block">
                                <div class="font-size-13">
                                    <span class="user-name">${reply.writer.name}</span>
                                    <span class="update-date">${writtenTime}</span>
                                </div>
                                <div class="reply-more-box dropdown">
                                    <button class="reply-more-button dropdown-toggle" data-toggle="dropdown">
                                        <i class="ti-more-alt"></i>
                                    </button>
                                
                                    <div class="dropdown-menu" role="menu">
                                        <button class="list-group-item list-group-item-action reply-edit-button">
                                            <i class="fa fa-pencil"></i>
                                            <span>수정</span>
                                        </button>
                                        <button class="list-group-item list-group-item-action reply-delete-button">
                                            <i class="fa fa-trash"></i>
                                            <span>삭제</span>
                                        </button>
                                    </div>
                                </div>
                                <span class="reply-contents font-size-15">${reply.contents}</span>
                                <div>
                                    <button class="like-btn">
                                        <i class="ti-thumb-up"></i>
                                    </button>
                                    <span>3.5천</span>
                                </div>
                            </div>
                            <div class="comment-update-area display-none mrg-btm-50">
                                <div>
                                    <img class="img-circle width-50 comment-writer-img" src="/images/default/eastjun_big.jpg"
                                         alt="">
                                    <input class="comment-input" type="text" value="${reply.contents}">
                                </div>
                                <button class="btn comment-btn reply-update-cancel-btn">취소</button>
                                <button class="btn comment-btn edit reply-update-btn">수정</button>
                            </div>
                        </li>`
    }
}