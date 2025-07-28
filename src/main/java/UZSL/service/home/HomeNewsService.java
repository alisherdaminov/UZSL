package UZSL.service.home;

import UZSL.dto.Home.HomeNewsCreatedDTO;
import UZSL.dto.Home.HomeNewsDTO;
import UZSL.dto.app.AppResponse;
import org.springframework.data.domain.PageImpl;

public interface HomeNewsService {

    HomeNewsDTO createPostNews(Integer userId, HomeNewsCreatedDTO homeNewsCreatedDTO);

    HomeNewsDTO getByUserIdPostNews(String postNewsId);

    PageImpl<HomeNewsDTO> getPostNewsList(int page, int size);

    HomeNewsDTO updatePostNews(String postId, Integer userId, HomeNewsCreatedDTO createdDTO);

    AppResponse<HomeNewsDTO> deletePostNews(String postNewsId);
}
