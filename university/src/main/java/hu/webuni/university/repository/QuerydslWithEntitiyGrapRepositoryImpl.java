package hu.webuni.university.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import hu.webuni.university.model.Course;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;

public class QuerydslWithEntitiyGrapRepositoryImpl
	extends SimpleJpaRepository<Course, Integer>
	implements QuerydslWithEntitiyGrapRepository<Course, Integer>{

	private EntityManager entityManager;
	private EntityPath<Course> path;
	private PathBuilder<Course> builder;
	private Querydsl querydsl;
	
	public QuerydslWithEntitiyGrapRepositoryImpl(EntityManager em) {
		super(Course.class, em);
		this.entityManager = em;
		this.path = SimpleEntityPathResolver.INSTANCE.createPath(Course.class);
		this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
		this.querydsl = new Querydsl(em, builder);
	}
	
	@Override
	public List<Course> findAll(Predicate predicate, String egName, Sort sort) {
		
		JPAQuery query = (JPAQuery) querydsl.applySorting(sort, createQuery(predicate).select(path));
		
		EntityGraph<?> eg = entityManager.getEntityGraph(egName);
		query.setHint(org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD.getKey() , eg);
		return query.fetch(); 
	}

	private JPAQuery createQuery(Predicate predicate) {		
		return querydsl.createQuery(path).where(predicate);
	}
	

}
