package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //create blog posts
    @Override
    public PostDto createPost(PostDto postDto) {

        //convert DTO to entity (Objet java -> table)
        Post post = mapToEntity(postDto);

        //save Post entity into database
        Post newPost =  postRepository.save(post);

        //convert entity to DTO(table -> Objet  java)
        PostDto postResponse =  mapToDTO(newPost);

        //retourne le type de la méthode (PostDto)
        return postResponse;
    }

    //Get all posts rest api
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        return posts.stream().map(
                post -> mapToDTO(post))
                .collect(Collectors.toList()
                );

    }

    //Get post rest api by id
    public PostDto getPostById(long id) {

        //get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    //Update post by id rest api
    public PostDto updatePost(PostDto postDto, long id) {

        //get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    //Delete post by id rest api
    public void deletePost(long id) {

        //get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    //convert entity to DTO(table -> Objet  java)
    private PostDto mapToDTO(Post post){

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    //convert DTO to Entity(Object java -> table)
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }


}
