package com.culturaloffers.maps.helper;


import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper implements MapperInterface<Comment, CommentDTO> {

    @Override
    public Comment toEntity(CommentDTO dto) {
        return null;
    }

    @Override
    public List<Comment> toEntityList(List<CommentDTO> dtoList) {
        return null;
    }

    @Override
    public CommentDTO toDto(Comment entity) {
        return new CommentDTO(entity.getContent(), entity.getCommentedOn(), entity);
    }

    @Override
    public List<CommentDTO> toDtoList(List<Comment> entityList)
    {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
