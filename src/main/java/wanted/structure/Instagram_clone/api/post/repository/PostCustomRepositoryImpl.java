package wanted.structure.Instagram_clone.api.post.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import wanted.structure.Instagram_clone.api.post.entity.Post;

import java.util.List;
import java.util.Optional;

import static wanted.structure.Instagram_clone.api.post.entity.QPost.post;

@Repository
public class PostCustomRepositoryImpl extends QuerydslRepositorySupport implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    public PostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return jpaQueryFactory
                .selectFrom(post)
                .leftJoin(post.hashTags)
                .where(post.id.eq(id))
                .stream().findAny();
    }

    @Override
    public Page<Post> findPage(Pageable pageable) {
        JPAQuery<Post> query = jpaQueryFactory.selectFrom(post).leftJoin(post.hashTags);
        Long count = jpaQueryFactory.select(post.count()).from(post).fetchCount();

        List<Post> list = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(list, pageable, count);
    }
}
