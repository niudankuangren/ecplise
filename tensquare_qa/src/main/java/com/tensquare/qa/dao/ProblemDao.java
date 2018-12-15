package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tensquare.qa.pojo.Problem;

import entity.PageResult;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

	@Query(value="SELECT * FROM tb_problem ,tb_pl WHERE id=problemid AND labelid= ? ORDER BY createtime DESC",nativeQuery=true)
	Page<Problem> newlist(String labelId, Pageable pageable);
	
	@Query(value="SELECT * FROM tb_problem ,tb_pl WHERE id=problemid AND labelid= ? ORDER BY reply DESC",nativeQuery=true)
	Page<Problem> hotlist(String labelId, Pageable pageable);
	
	@Query(value="SELECT * FROM tb_problem ,tb_pl WHERE id=problemid AND labelid= ? And reply=0 ORDER BY createtime DESC",nativeQuery=true)
	Page<Problem> waitlist(String labelId, Pageable pageable);
}
