package com.example.cms.serviceimpl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.exception.BlogAlreadyExistsByTitleException;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestDto.BlogRequest;
import com.example.cms.responseDto.BlogResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

	private UserRepository userRepository;
	private BlogRepository blogRepository;
	private ResponseStructure<BlogResponse> responseStructure;
	private ContributionPanelRepository contributionPanelRepository;

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(int userId, BlogRequest blogRequest) {
		return userRepository.findById(userId).map(user -> {
			if (blogRepository.existsByTitle(blogRequest.getTitle())) {
				throw new BlogAlreadyExistsByTitleException("Invalid blog title");
				}
			if(blogRequest.getTopics().length<1) {
				throw new TopicNotSpecifiedException("Failed to create");
			}
			Blog blog = mapToBlog(blogRequest);
			ContributionPanel contributionPanel = new ContributionPanel();
			ContributionPanel panel = contributionPanelRepository.save(contributionPanel);
			user.getBlogs().add(blog);
			blog.setContributionPanel(panel);
			blog.setUser(user);
			blog = blogRepository.save(blog);
			userRepository.save(user);
			
			return blog;
		}).map(blog -> ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
				.setMessage("Blog added sucessfully to thr user").setData(mapToResponse(blog))))
				.orElseThrow(() -> new UserNotFoundByIdException("Invalid userID"));

	}

	private BlogResponse mapToResponse(Blog blog2) {
		return BlogResponse.builder().blogId(blog2.getBlogId()).about(blog2.getAbout()).topics(blog2.getTopics())
				.title(blog2.getTitle()).build();
	}

	private Blog mapToBlog(BlogRequest blogRequest) {
		Blog blog = new Blog();
		blog.setTitle(blogRequest.getTitle());
		blog.setAbout(blogRequest.getAbout());
		blog.setTopics(blogRequest.getTopics());
		return blog;

	}

	@Override
	public ResponseEntity<Boolean> fetchByTitle(String title) {
		return ResponseEntity.ok(blogRepository.existsByTitle(title));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findById(int blogId) {
		return blogRepository.findById(blogId)
				.map(blog -> ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
						.setMessage("Data Fetched").setData(mapToResponse(blog))))
				.orElseThrow(() -> new BlogNotFoundByIdException("Invalid blogId"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlog(int blogId, BlogRequest blogRequest) {
		Blog blog = mapToBlog(blogRequest);
		return blogRepository.findById(blogId).map(exBlog -> {
			blog.setBlogId(exBlog.getBlogId());
			exBlog = blogRepository.save(blog);
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Blog updated sucessfully").setData(mapToResponse(exBlog)));
		}).orElseThrow(() -> new BlogNotFoundByIdException("Invalid Blog id"));
	}
}
