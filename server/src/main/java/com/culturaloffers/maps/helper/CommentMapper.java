package com.culturaloffers.maps.helper;


import com.culturaloffers.maps.dto.CommentDTO;
import com.culturaloffers.maps.model.Comment;
import com.culturaloffers.maps.model.CulturalOffer;
import com.culturaloffers.maps.repositories.CulturalOfferRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper implements MapperInterface<Comment, CommentDTO> {

    @Override
    public Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment();

        comment.setContent(dto.getContent());
        comment.setImageUrls(dto.getImageUrls());
        comment.setCommentedOn(dto.getCommentedOn());

        return comment;
    }

    @Override
    public List<Comment> toEntityList(List<CommentDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public CommentDTO toDto(Comment entity) {
        return new CommentDTO(entity);
    }

    @Override
    public List<CommentDTO> toDtoList(List<Comment> entityList)
    {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
