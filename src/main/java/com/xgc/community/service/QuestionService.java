package com.xgc.community.service;

import com.xgc.community.dto.PaginationDTO;
import com.xgc.community.dto.QuestionDTO;
import com.xgc.community.mapper.QuestionMapper;
import com.xgc.community.mapper.UserMapper;
import com.xgc.community.model.Question;
import com.xgc.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        Integer totalPage;
        if (totalCount % size ==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }

        if (page<1){
            page=1;
        }

        if (page>totalPage){
            page=totalPage;
        }

        paginationDTO.setPagination(totalPage,page);


        //size*(page-1)
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        Integer totalPage;
        if (totalCount % size ==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }

        if (page<1){
            page=1;
        }

        if (page>totalPage){
            page=totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        //size*(page-1)
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
