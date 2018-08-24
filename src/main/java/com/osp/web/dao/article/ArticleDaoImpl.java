package com.osp.web.dao.article;

import com.osp.common.PagingResult;
import com.osp.model.Article;
import com.osp.model.StockMsisdn;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.text.Normalizer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by Admin on 3/8/2018.
 */
@Repository
public class ArticleDaoImpl implements ArticleDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @Override
    public Optional<PagingResult> listByCate(PagingResult page, Integer categoryId) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<Article> root = q.from(Article.class);

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (categoryId != null) {
            predicates.add(cb.equal(root.get("category").get("id"), categoryId));
        }
        predicates.add(cb.equal(root.get("active"), true));
        predicates.add(cb.equal(root.get("deleted"), false));
        q.select(cb.array(root.get("id"), root.get("title"), root.get("shortContent"), root.get("dateCreated"), root.get("imageUrl"), root.get("link"))).where(predicates.toArray(new Predicate[]{}));
        q.orderBy(cb.desc(root.get("dateCreated")));
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
//                Object[] objects = list.get(i);
                Article article = new Article();
                article.setId(Integer.parseInt(list.get(i)[0].toString()));
                article.setTitle(list.get(i)[1].toString());
//                objects[5] = seoUrl(article);
                list.get(i)[5] = seoUrl(article);
            }
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Article> rootCount = criteriaQuery.from(Article.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public Optional<Article> getById(Integer id) {
        Article item = entityManager.find(Article.class, id);
        if (item != null) {
            item.setFormaturl(seoUrl(item));
        }
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Article> getArticleOfCate(Integer id, Integer categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> q = cb.createQuery(Article.class);
        Root<Article> root = q.from(Article.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        if (categoryId != null) {
            predicates.add(cb.equal(root.get("category").get("id"), categoryId));
        }
        predicates.add(cb.equal(root.get("active"), true));
        predicates.add(cb.equal(root.get("deleted"), false));
        q.select(root).where(predicates.toArray(new Predicate[]{}));
        List<Article> list = entityManager.createQuery(q).setFirstResult(0).setMaxResults(1).getResultList();
        if (list != null && list.size() > 0) {
            
            list.get(0).setFormaturl(seoUrl(list.get(0)));
            
            return Optional.of(list.get(0));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<Article> getFirstArticleOfCategory(Integer categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> q = cb.createQuery(Article.class);
        Root<Article> root = q.from(Article.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (categoryId != null) {
            predicates.add(cb.equal(root.get("category").get("id"), categoryId));
        }
        predicates.add(cb.equal(root.get("active"), true));
        predicates.add(cb.equal(root.get("deleted"), false));
        q.select(root).where(predicates.toArray(new Predicate[]{}));
        q.orderBy(cb.desc(root.get("dateCreated")));
        List<Article> list = entityManager.createQuery(q).setFirstResult(0).setMaxResults(1).getResultList();
        if (list != null && list.size() > 0) {
            list.get(0).setFormaturl(seoUrl(list.get(0)));
            return Optional.of(list.get(0));
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<List<Article>> listAllOfCate(Integer categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> q = cb.createQuery(Article.class);
        Root<Article> root = q.from(Article.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (categoryId != null) {
            predicates.add(cb.equal(root.get("category").get("id"), categoryId));
        }
        predicates.add(cb.equal(root.get("active"), true));
        predicates.add(cb.equal(root.get("deleted"), false));
        q.select(root).where(predicates.toArray(new Predicate[]{}));
        q.orderBy(cb.desc(root.get("dateCreated")));
        List<Article> list = entityManager.createQuery(q).getResultList();
        for (Article guide : list) {
            guide.setFormaturl(seoUrl(guide));
        }
        return Optional.ofNullable(list);
    }

    public String seoUrl(Article newsDetail) {
        String formattitle = "";
        if (newsDetail.getTitle() != null && !"".equals(newsDetail.getTitle().trim())) {
            String title = toSlug(newsDetail.getTitle().trim());
            String[] arrTitle = title.split("-");
            for (String string : arrTitle) {
                if (string != null && !"".equals(string)) {
                    formattitle += string + "-";
                }
            }
        }
        if (formattitle != null && !"".equals(formattitle)) {
            return formattitle + newsDetail.getId();
        }
        return "";
    }

    public String toSlug(String input) {
        input = input.toLowerCase();
        input = input.replaceAll("đ", "d");
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
