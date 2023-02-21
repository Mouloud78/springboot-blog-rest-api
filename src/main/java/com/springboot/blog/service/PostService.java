package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;

import java.util.List;

public interface PostService {

    //create blog posts
    PostDto createPost(PostDto postDto);

    //Get all posts rest api
    List<PostDto> getAllPosts();

    //Get post rest api by id
    PostDto getPostById(long id);

    //Update post by id rest api
    PostDto updatePost(PostDto postDto, long id);

    //Delete post by id rest api
    void deletePost(long id);
}
