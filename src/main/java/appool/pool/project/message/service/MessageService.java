package appool.pool.project.message.service;

import appool.pool.project.file.exception.FileException;
import appool.pool.project.file.service.S3Uploader;
import appool.pool.project.message.exception.MessageException;
import appool.pool.project.message.exception.MessageExceptionType;
import appool.pool.project.user.PoolUser;
import appool.pool.project.user.exception.PoolUserException;
import appool.pool.project.user.exception.PoolUserExceptionType;
import appool.pool.project.message.Message;
import appool.pool.project.message.dto.MessageEdit;
import appool.pool.project.message.repository.MessageRepository;
import appool.pool.project.message.dto.MessageCreate;
import appool.pool.project.message.dto.MessageSearch;
import appool.pool.project.message.dto.MessageResponse;
import appool.pool.project.user.repository.UserRepository;
import appool.pool.project.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    public void write(MessageCreate messageCreate, List<MultipartFile> multipartFiles) throws FileException {
        Message message = messageCreate.toEntity();

        message.confirmWriter(userRepository.findByUsername(SecurityUtil.getLoginUsername())
                .orElseThrow(() -> new PoolUserException(PoolUserExceptionType.NOT_FOUND_MEMBER)));

        if(multipartFiles != null) {
            message.getFilePath().add(s3Uploader.getThumbnailPath(s3Uploader.uploadImage(multipartFiles).get(0)));
        }

        messageRepository.save(message);
    }

    public MessageResponse get(Long id) {
        return new MessageResponse(messageRepository.findWithWriterById(id).orElseThrow(() -> new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND)));
    }

    public List<MessageResponse> getList(MessageSearch messageSearch) {
        return messageRepository.getList(messageSearch).stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }

    public List<MessageResponse> getMainList(Long cursor, Pageable pageable) {
        List<MessageResponse> mainList = getMessageList(cursor, pageable).stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());

//        mainList.forEach(f -> f.setCommentAble()
//
//        );

        return mainList;
    }

    private List<Message> getMessageList(Long id, Pageable page) {
        Optional<PoolUser> poolUser = userRepository.findByUsername(SecurityUtil.getLoginUsername());
        return id.equals(0L)
                ? messageRepository.mainFeed(poolUser.get().getId(), page)
                : messageRepository.mainFeedLess(poolUser.get().getId(), id, page);
    }

    public void edit(Long id, MessageEdit messageEdit) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND));

        checkAuthority(message, MessageExceptionType.NOT_AUTHORITY_UPDATE_MESSAGE);

        messageEdit.title().ifPresent(message::updateTitle);
        messageEdit.body().ifPresent(message::updateTitle);
        messageEdit.messageLink().ifPresent(message::updateMessageLink);

//        if(message.getFilePath() != null) {
//            fileService.delete(message.getFilePath());
//        }

//        messageEdit.uploadFile().ifPresentOrElse(
//                multipartFile -> message.updateFilePath(fileService.save(multipartFile)),
//                () -> message.updateFilePath(null)
//        );
    }

    public void delete(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND));

        checkAuthority(message, MessageExceptionType.NOT_AUTHORITY_DELETE_MESSAGE);

//        if(message.getFilePath() != null){
//            fileService.delete(message.getFilePath());
//        }

        messageRepository.delete(message);
    }

    private void checkAuthority(Message message, MessageExceptionType messageExceptionType) {
        if(!message.getWriter().getUsername().equals(SecurityUtil.getLoginUsername())) {
            throw new MessageException(messageExceptionType);
        }
    }

}
